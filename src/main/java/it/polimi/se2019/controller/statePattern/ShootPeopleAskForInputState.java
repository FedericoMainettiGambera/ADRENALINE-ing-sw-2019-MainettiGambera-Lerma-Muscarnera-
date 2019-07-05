package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.Effect;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventListOfObject;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.SquareV;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**this state implements functions to ask the user a specific input for a given effect*/
public class ShootPeopleAskForInputState implements State {

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(ShootPeopleAskForInputState.class.getName());

    /** player to ask the input to*/
    private Player playerToAsk;

    /**timer that activates the count down to the afk status*/
    private Thread inputTimer;

    /** the effect chosen by the user to be used*/
    private Effect chosenEffect;

    /** indicates if it's action number 1 or 2*/
    private int actionNumber;

    /**contains the weapon card the user decided to use*/
    private WeaponCard chosenWeaponCard;

   /**constructor,
    * @param actionNumber if it's first or second action
    * @param chosenEffect which effect the player wants to use
    *@param chosenWeaponCard  which weapon card the player wants to use*/
    public ShootPeopleAskForInputState(Effect chosenEffect, WeaponCard chosenWeaponCard, int actionNumber){
        out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
        this.chosenEffect = chosenEffect;
        this.chosenWeaponCard = chosenWeaponCard;
    }


    private Integer inputRequestCounterF = 0;

    private boolean canIncrementRequest(){
        return inputRequestCounterF < this.chosenEffect.requestedInputs().size() - 1;
    }

    /**@param playerToAsk the player the input is asked*/
    @Override
    public void askForInput(Player playerToAsk){
        this.playerToAsk = playerToAsk;
        out.println("<SERVER> (" + this.getClass() + ") Asking the player an input \"" + playerToAsk.getNickname() + "\"");

        EffectInfoType inputType = this.chosenEffect.getEffectInfo().getEffectInfoElement().get(inputRequestCounterF).getEffectInfoTypelist();

        if(isToSend(inputType)){
            try {
                out.println("<SERVER> sending the player a" + inputType + "with the possible options.");

                SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(playerToAsk);
                SelectorGate.getCorrectSelectorFor(playerToAsk).askEffectInputs(inputType, this.chosenEffect.usableInputs().get(inputRequestCounterF).get(0));
                this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString(), "ask effect input for shoot"));
                this.inputTimer.start();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "EXCEPTION", e);
            }
        }
        else{
            out.println("<SERVER> " + inputType + " is not meant to be sent to player.");
            askMoreOrExec();
        }
    }



    /**extrapolates needed data from
     * @param viewControllerEvent that is the event asked the user
     * then calls the function askMoreOrExec*/
    @Override
    public void doAction(ViewControllerEvent viewControllerEvent) {
        this.inputTimer.interrupt();

        parsevce(viewControllerEvent);
        askMoreOrExec();
    }

    /**@param viewControllerEvent event this function extrapolates the data from
     * */
    private void parsevce(ViewControllerEvent viewControllerEvent){

        out.println("<SERVER> player answered before timer expired.");
        out.println("<SERVER> " + this.getClass() + ".doAction();");

        List<Object> response = ((ViewControllerEventListOfObject)viewControllerEvent).getAnswer();
        Object[] inputRow = new Object[10];

        int inputRowCurrent = 0;
        for(Object o: response) {
            if(o.getClass().toString().contains("PlayerV")){
                inputRow[inputRowCurrent] = ModelGate.getModel().getPlayerList().getPlayer(((PlayerV)o).getNickname());
            }
            else{
                inputRow[inputRowCurrent] = ModelGate.getModel().getBoard().getMap()[((SquareV)o).getX()][((SquareV)o).getY()];
            }
            // <L>
            System.out.println("<SERVER> content of input: " + inputRow[inputRowCurrent]);
            // </L>
            inputRowCurrent++;
        }

        this.chosenEffect.handleRow(this.chosenEffect.getEffectInfo().getEffectInfoElement().get(inputRequestCounterF),inputRow);
    }

    /***/
    private void askMoreOrExec(){
        if(canIncrementRequest()) {
            inputRequestCounterF++;
            askForInput(playerToAsk);
        }
        else {
            //this.afterPayment();
            ChooseHowToPayState.makePayment(playerToAsk, this.chosenEffect.getUsageCost());
        }
    }

    /**this function manages to damage the right player after the payment has been effectuated,
     * then directs the  state pattern towards the right following state*/
    void afterPayment(){


        List<Player> damagedPlayer=damagePlayer();

        State nextState = null;

        if(this.actionNumber == 2){
            nextState = (new ReloadState(false));
        }
        else if(this.actionNumber == 1){
            nextState = (new TurnState(2));
        }

        ViewControllerEventHandlerContext.setNextState(new TargetingScopeState(nextState, damagedPlayer));
        ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());

    }


    /**this function manages to add the damage to the right players,
     * @return the list of the players he has damaged, if there are any */
    private List<Player> damagePlayer(){

        this.chosenWeaponCard.unload();
        List<List<Player>> listListDamagedPlayer = this.chosenEffect.Exec();

        //transform the List<List<Player>> in List<Player>
        List<Player> damagedPlayer = new ArrayList<>();
        for (List<Player> listOfPlayer: listListDamagedPlayer) {
            for(Player p: listOfPlayer)
                if(!damagedPlayer.contains(p)) {
                    damagedPlayer.add(p);
                    System.out.println("<SERVER> DAMAGED PLAYERS ADD " + p);
                }
        }

        //remove the terminator from the damagedPlayer list
        for (Player p: damagedPlayer){
            if(p.isBot()){
                out.println("<SERVER> removed the bot from the damagedPlayers list");
                damagedPlayer.remove(p);
                break;
            }
        }

        out.println("<SERVER> list of damaged player from the last EXEC: ");
        if(damagedPlayer.isEmpty()){
            out.println("         nobody");
        }
        else{
            for (Player p: damagedPlayer) {
                out.println("         " + p.getNickname());
            }
        }

        return damagedPlayer;
    }


    /**set the user AFK notifying it to them and make them pass their turn*/
    @Override
    public void handleAFK(){
        this.playerToAsk.setAFKWithNotify(true);
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
        //pass turn
        if(!ViewControllerEventHandlerContext.getState().getClass().toString().contains("FinalScoringState")) {
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.getState().doAction(null);
        }
    }

/** @param infoType a string extrapolated from the effect that allows
 * this function to acknowledges the right type of the effect chosen by the user*/
    public boolean isToSend(EffectInfoType infoType){
        if(infoType.equals(EffectInfoType.player) ||
                infoType.equals(EffectInfoType.playerSquare)||
                infoType.equals(EffectInfoType.targetListBySameSquareOfPlayer)||
                infoType.equals(EffectInfoType.singleRoom)||
                infoType.equals(EffectInfoType.squareOfLastTargetSelected)||
                infoType.equals(EffectInfoType.targetBySameSquareOfPlayer)||
                infoType.equals(EffectInfoType.targetListBySquareOfLastTarget)||
                infoType.equals(EffectInfoType.targetListByLastTargetSelectedSquare)
        ){
            Object[] inputRow = new Object[10];
            this.chosenEffect.handleRow(this.chosenEffect.getEffectInfo().getEffectInfoElement().get(inputRequestCounterF),inputRow);
            return false;
        }
        return true;
    }
}

