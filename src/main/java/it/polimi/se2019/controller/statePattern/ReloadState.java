package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.AmmoList;
import it.polimi.se2019.model.NormalSquare;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;
import it.polimi.se2019.controller.WaitForPlayerInput;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class ReloadState implements State{

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(ReloadState.class.getName());

    private boolean calledFromShootPeople;

    private Player playerToAsk;

    private Thread inputTimer;

    private int actionNumber;

    public ReloadState(boolean CalledFromShootPeople){
        this.calledFromShootPeople = CalledFromShootPeople;
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = 0;
    }

    public ReloadState(boolean CalledFromShootPeople, int actionNumber){
        this.calledFromShootPeople = CalledFromShootPeople;
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        if ((ModelGate.model.hasFinalFrenzyBegun()  && !calledFromShootPeople)) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
        else if(canReload()){
            out.println("<SERVER> The player can reload");
            //ask which weapon to reload
            ArrayList<WeaponCard> toReaload = new ArrayList<>();
            for (WeaponCard wc: playerToAsk.getWeaponCardsInHand().getCards()) {
                if (!wc.isLoaded() && (new ChooseHowToPayState(playerToAsk,wc.getReloadCost())).canPayInSomeWay()){
                    toReaload.add(wc);
                }
            }
            StringBuilder toPrintln = new StringBuilder();
            for (WeaponCard weaponCard : toReaload) {
                toPrintln.append("[").append(weaponCard.getID()).append("]  ");
            }
            out.println("<SERVER> Possible weapons that can be reloaded: " + toPrintln);


            try {
                SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
                SelectorGate.getCorrectSelectorFor(playerToAsk).askWhatReaload(toReaload);
                this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
                this.inputTimer.start();
            } catch (Exception e) {
              logger.severe("Exception Occured"+" "+e.getClass()+" "+e.getCause()+ Arrays.toString(e.getStackTrace()));
            }
        }
        else{
            out.println("<SERVER> The player can't reload");

            out.println("<SERVER> Placing Ammo cards on all empty NormalSquares");
            for (int i = 0; i < ModelGate.model.getBoard().getMap().length; i++) {
                for (int j = 0; j < ModelGate.model.getBoard().getMap()[0].length; j++) {
                    if((ModelGate.model.getBoard().getMap()[i][j]!=null)
                            &&   (ModelGate.model.getBoard().getMap()[i][j].getSquareType() == SquareTypes.normal)){
                        if(((NormalSquare)ModelGate.model.getBoard().getMap()[i][j]).getAmmoCards().getCards().isEmpty()){
                            ModelGate.model.getAmmoDeck().moveCardTo(
                                    ((NormalSquare)ModelGate.model.getBoard().getMap()[i][j]).getAmmoCards(),
                                    ModelGate.model.getAmmoDeck().getFirstCard().getID()
                            );
                            out.println("<SERVER> Added Ammo card to square [" + i + "][" + j + "]");
                        }
                    }
                }
            }
            if(calledFromShootPeople){
                ViewControllerEventHandlerContext.setNextState(new ShootPeopleChooseWepState(this.actionNumber));
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
                Thread t = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
                t.start();
            }
            else {
                ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
                ViewControllerEventHandlerContext.state.doAction(null);
            }
        }

    }

    @Override
    public void doAction(ViewControllerEvent VCE){
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventString VCEString=(ViewControllerEventString)VCE;

        if( ! VCEString.getInput().equals("SKIP")){
            out.println("<SERVER> Reloading and paying reload cost for weapon card: " + VCEString.getInput());

            AmmoList cost=ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCard(VCEString.getInput()).getReloadCost();
            ChooseHowToPayState.makePayment(ModelGate.model.getCurrentPlayingPlayer(), cost);

            //reload the card
            ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCard(VCEString.getInput()).reload();

            if(calledFromShootPeople){
                ViewControllerEventHandlerContext.setNextState(new ShootPeopleChooseWepState(this.actionNumber));
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
            }
            else {
                ViewControllerEventHandlerContext.setNextState(new ReloadState(false));
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
            }
        }
        else{
            out.println("<SERVER> Player decided not to reload.");
            if(calledFromShootPeople){
                ViewControllerEventHandlerContext.setNextState(new ShootPeopleChooseWepState(this.actionNumber));
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
            }
            else {
                ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
                ViewControllerEventHandlerContext.state.doAction(null);
            }
        }
    }

    @Override
    public void handleAFK() {
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        if(!ViewControllerEventHandlerContext.state.getClass().toString().contains("FinalScoringState")) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
    }

    public boolean canReload(){

        for (WeaponCard weaponCard : ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCards()){
            if((new ChooseHowToPayState(ModelGate.model.getCurrentPlayingPlayer(),weaponCard.getReloadCost())).canPayInSomeWay() && !weaponCard.isLoaded()){
                return true;
            }
        }

        return false;
    }
}
