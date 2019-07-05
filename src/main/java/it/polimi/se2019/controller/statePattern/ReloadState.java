package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.*;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import it.polimi.se2019.controller.WaitForPlayerInput;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**implements reloading weapons
 * * @author LudoLerma
 *  * @author FedericoMainettiGambera
 *  */
public class ReloadState implements State{

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(ReloadState.class.getName());

    /**indicates if this state was called  from shoot people state*/
    private boolean calledFromShootPeople;
    /**it's the player the input is asked */
    private Player playerToAsk;
    /**the timer for the count down*/
    private Thread inputTimer;
    /**action 1 or 2*/
    private int actionNumber;

    /**bot nickname*/
    private String botName = "Terminator";

    /**@param calledFromShootPeople indicates if this state was called  from shoot people state
     * constructor 1*/
    public ReloadState(boolean calledFromShootPeople){
        this.calledFromShootPeople = calledFromShootPeople;
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = 0;
    }
    /**@param calledFromShootPeople indicates if this state was called  from shoot people state
     * @param actionNumber if it's action number 1 or 2
     * constructor 2*/
    public ReloadState(boolean calledFromShootPeople, int actionNumber){
        this.calledFromShootPeople = calledFromShootPeople;
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    /**set the player to ask as
     * @param playerToAsk given*/
    public void setPlayerToAsk(Player playerToAsk){
        this.playerToAsk = playerToAsk;

        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
    }


    /** @param playerToAsk the player to ask input to
     * this function manages to ask the player the right input*/
    @Override
    public void askForInput(Player playerToAsk){

        setPlayerToAsk(playerToAsk);

        if((ModelGate.getModel().hasFinalFrenzyBegun()&&!calledFromShootPeople)){

            if(ModelGate.getModel().isBotActive() && !ModelGate.getModel().getPlayerList().getPlayer(botName).isBotUsed()){

                changeState(new BotMoveState(new WantToPlayPowerUpState()));
            }
            else {

                changeState(new WantToPlayPowerUpState());
            }
        }

        else if(canReload()){

            inCaseYouCanReload();
        }
        else{

            placeAmmosOnEmptyNormalSquares();

            if(calledFromShootPeople){

                exceptionalChangeState();
            }
            else{

                notCalledFromShootPeople();
            }
        }
    }

    /**if you already used your bot, you are asked if you want to play a power up, if else,
     * you are forced to use your bot*/
    private void notCalledFromShootPeople(){

        if(ModelGate.getModel().isBotActive() && !ModelGate.getModel().getPlayerList().getPlayer(botName).isBotUsed()){
            changeState(new BotMoveState(new WantToPlayPowerUpState()));
        }

        else {
            changeState(new WantToPlayPowerUpState());
        }
    }

    /**you are asked which weapon you want to reload, in case you can*/
    private void inCaseYouCanReload(){

        List<WeaponCard> toReload=weaponToReload();

        try {

            askWhichWep(toReload);

        } catch (Exception e) {

            logger.severe("Exception Occurred"+" "+e.getClass()+" "+e.getCause()+ Arrays.toString(e.getStackTrace()));
        }
    }

    /** the input is asked to the current playing player */
    private void exceptionalChangeState(){

        ViewControllerEventHandlerContext.setNextState(new ShootPeopleChooseWepState(this.actionNumber));
        ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
        //Thread t = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
        //t.start();
    }

    /**ask the player which weapon they want to reload beetwen the one indicated in the
     * @param toReload, the parameter given*/
    private void askWhichWep(List<WeaponCard> toReload){

        SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
        SelectorGate.getCorrectSelectorFor(playerToAsk).askWhatReaload(toReload);
        this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString(), "ask what reload"));
        this.inputTimer.start();
    }

    /**change the state to the
     * @param state indicated*/
    private void changeState(State state){

        ViewControllerEventHandlerContext.setNextState(state);
        ViewControllerEventHandlerContext.getState().askForInput(playerToAsk);

    }

    /** sort a list of which weapon the player can reload,
     * @return toReload, said list*/
    public List<WeaponCard> weaponToReload(){

        out.println("<SERVER> The player can reload");
        //ask which weapon to reload
        List<WeaponCard> toReload = new ArrayList<>();
        for (WeaponCard wc: playerToAsk.getWeaponCardsInHand().getCards()) {
            if (!wc.isLoaded() && (new ChooseHowToPayState(playerToAsk,wc.getReloadCost())).canPayInSomeWay()){
                toReload.add(wc);
            }
        }
        StringBuilder toPrintln = new StringBuilder();
        for (WeaponCard weaponCard : toReload) {
            toPrintln.append("[").append(weaponCard.getID()).append("]  ");
        }
        out.println("<SERVER> Possible weapons that can be reloaded: " + toPrintln);

        return toReload;


    }

    /**this function fill all the empty normal square with ammunition cards*/
    public void placeAmmosOnEmptyNormalSquares(){


        out.println("<SERVER> The player can't reload");

        out.println("<SERVER> Placing Ammo cards on all empty NormalSquares");
        for (int i = 0; i < ModelGate.getModel().getBoard().getMap().length; i++) {
            for (int j = 0; j < ModelGate.getModel().getBoard().getMap()[0].length; j++) {
                if((ModelGate.getModel().getBoard().getMap()[i][j]!=null)
                        &&
                        (ModelGate.getModel().getBoard().getMap()[i][j].getSquareType() == SquareTypes.normal)
                        &&
                        ((NormalSquare)ModelGate.getModel().getBoard().getMap()[i][j]).getAmmoCards().getCards().isEmpty()){
                    ModelGate.getModel().getAmmoDeck().moveCardTo(
                            ((NormalSquare)ModelGate.getModel().getBoard().getMap()[i][j]).getAmmoCards(),
                            ModelGate.getModel().getAmmoDeck().getFirstCard().getID()
                    );
                    out.println("<SERVER> Added Ammo card to square [" + i + "][" + j + "]");

                }
                //TODO check if correct...
                else if(ModelGate.getModel().getBoard().getMap()[i][j]!=null && (ModelGate.getModel().getBoard().getMap()[i][j].getSquareType() == SquareTypes.spawnPoint)){
                    int counter = ((SpawnPointSquare) ModelGate.getModel().getBoard().getMap()[i][j]).getWeaponCards().getCards().size();
                    if (counter<3){
                        for (int k = counter; k < 3; k++) {
                            if(ModelGate.getModel().getWeaponDeck().getCards().size()>0) {
                                ModelGate.getModel().getWeaponDeck().moveCardTo(
                                        ((SpawnPointSquare) ModelGate.getModel().getBoard().getMap()[i][j]).getWeaponCards(),
                                        ModelGate.getModel().getAmmoDeck().getFirstCard().getID()
                                );
                                out.println("<SERVER> Added Ammo card to square [" + i + "][" + j + "]");
                            }
                            else{
                                out.println("<SERVER> Should add a card to the square, but the deck is empty.");
                            }
                        }
                    }
                }
            }
        }

    }

    /**@param viewControllerEvent contains the information needed about the user
     * wanting to reload or not
     * this function performs reloading or change state*/
    @Override
    public void doAction(ViewControllerEvent viewControllerEvent){
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventString viewControllerEventString=(ViewControllerEventString)viewControllerEvent;

        if( ! viewControllerEventString.getInput().equals("SKIP")){
            out.println("<SERVER> Reloading and paying reload cost for weapon card: " + viewControllerEventString.getInput());

            //reload the card
            System.out.println("<SERVER> reloading card: " + ModelGate.getModel().getCurrentPlayingPlayer().getWeaponCardsInHand().getCard(viewControllerEventString.getInput()).getID() );
            ModelGate.getModel().getCurrentPlayingPlayer().getWeaponCardsInHand().getCard(viewControllerEventString.getInput()).reload();

            //payment
            AmmoList cost=ModelGate.getModel().getCurrentPlayingPlayer().getWeaponCardsInHand().getCard(viewControllerEventString.getInput()).getReloadCost();
            ChooseHowToPayState.makePayment(ModelGate.getModel().getCurrentPlayingPlayer(), cost);

        }
        else{
            out.println("<SERVER> Player decided not to reload.");
            if(calledFromShootPeople){
                ViewControllerEventHandlerContext.setNextState(new ShootPeopleChooseWepState(this.actionNumber));
                ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
            }
            else {
                if(ModelGate.getModel().isBotActive() && !ModelGate.getModel().getPlayerList().getPlayer(botName).isBotUsed()){

                    changeState(new BotMoveState(new WantToPlayPowerUpState()));

                }
                else{
                    changeState(new WantToPlayPowerUpState());

                }
            }
        }
    }

    /** this function handles the after payment situation*/
    void afterPayment(){
        if(calledFromShootPeople){
            ViewControllerEventHandlerContext.setNextState(new ShootPeopleChooseWepState(this.actionNumber));
            ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
        }
        else {
            ViewControllerEventHandlerContext.setNextState(new ReloadState(false));
            ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
        }
    }

    /**
     * set the player AFK in case they don't send required input in a while
     * */
    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        if(!ViewControllerEventHandlerContext.getState().getClass().toString().contains("FinalScoringState")) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.getState().doAction(null);
        }
    }

    /**@return boolean information about the effective possibility of reloading*/
    public boolean canReload(){

        for (WeaponCard weaponCard : ModelGate.getModel().getCurrentPlayingPlayer().getWeaponCardsInHand().getCards()){
            if(!weaponCard.isLoaded() && (new ChooseHowToPayState(ModelGate.getModel().getCurrentPlayingPlayer(),weaponCard.getReloadCost())).canPayInSomeWay()){
                return true;
            }
        }

        return false;
    }
}