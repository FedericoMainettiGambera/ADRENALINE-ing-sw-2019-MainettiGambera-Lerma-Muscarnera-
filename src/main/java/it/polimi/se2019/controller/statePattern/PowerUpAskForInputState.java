package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.WaitForPlayerInput;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PowerUpCard;
import it.polimi.se2019.model.enumerations.EffectInfoType;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventListOfObject;
import it.polimi.se2019.view.components.PlayerV;
import it.polimi.se2019.view.components.SquareV;

import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/** */
public class PowerUpAskForInputState implements State {

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(TurnState.class.getName());

    private State nextState;
    private PowerUpCard chosenPowerUp;
    private Player playerToAsk;

    private Thread inputTimer;

    public PowerUpAskForInputState(State nextState, PowerUpCard chosenPowerUp){
        out.println("<SERVER> New state: " + this.getClass());

        this.nextState = nextState;
        this.chosenPowerUp = chosenPowerUp;
    }

    private Integer inputRequestCounterF = 0;
    public boolean canIncrementRequest(){
        return inputRequestCounterF < this.chosenPowerUp.getSpecialEffect().requestedInputs().size() - 1;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        this.playerToAsk = playerToAsk;

        this.chosenPowerUp.getSpecialEffect().passContext(playerToAsk, ModelGate.getModel().getPlayerList(), ModelGate.getModel().getBoard());

        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        EffectInfoType inputType = this.chosenPowerUp.getSpecialEffect().getEffectInfo().getEffectInfoElement().get(inputRequestCounterF).getEffectInfoTypelist();

        if(isToSend(inputType)) {
            try {
                out.println("<SERVER> sending " + inputType + " to player with the possible options.");

                SelectorGate.getCorrectSelectorFor(playerToAsk).setPlayerToAsk(this.playerToAsk);
                SelectorGate.getCorrectSelectorFor(playerToAsk).askEffectInputs(inputType, this.chosenPowerUp.getSpecialEffect().usableInputs().get(inputRequestCounterF).get(0));
                this.inputTimer = new Thread(new WaitForPlayerInput(this.playerToAsk, this.getClass().toString()));
                this.inputTimer.start();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "EXCEPTION", e);
            }
        }
        else{
            out.println("<SERVER> " + inputType + " is not meant to be sent to player.");
            Object[] inputRow = new Object[10];
            this.chosenPowerUp.getSpecialEffect().handleRow(this.chosenPowerUp.getSpecialEffect().getEffectInfo().getEffectInfoElement().get(inputRequestCounterF),inputRow);
            askMoreOrExec();
        }
    }

    public void askMoreOrExec(){
        if(canIncrementRequest()) {
            inputRequestCounterF++;
            askForInput(playerToAsk);
        }
        else {

            //there is no payment process because this state is meant only for power up teleport and newton
            this.chosenPowerUp.getSpecialEffect().Exec();

            out.println("<SERVER> discarding the used power up");
            playerToAsk.getPowerUpCardsInHand().moveCardTo(ModelGate.getModel().getPowerUpDiscardPile(), this.chosenPowerUp.getID());

            ViewControllerEventHandlerContext.setNextState(this.nextState);
            ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
        }
    }

    public boolean isToSend(EffectInfoType infoType) {
        return (! ( infoType.equals(EffectInfoType.player) ||
                infoType.equals(EffectInfoType.playerSquare) ||
                infoType.equals(EffectInfoType.targetListBySameSquareOfPlayer) ||
                infoType.equals(EffectInfoType.singleRoom) ||
                infoType.equals(EffectInfoType.squareOfLastTargetSelected) ||
                infoType.equals(EffectInfoType.targetBySameSquareOfPlayer) ||
                infoType.equals(EffectInfoType.targetListBySquareOfLastTarget) ||
                infoType.equals(EffectInfoType.targetListByLastTargetSelectedSquare)
        )
        );
    }

    @Override
    public void doAction(ViewControllerEvent viewControllerEvent) {
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

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
            inputRowCurrent++;
        }

        this.chosenPowerUp.getSpecialEffect().handleRow(this.chosenPowerUp.getSpecialEffect().getEffectInfo().getEffectInfoElement().get(inputRequestCounterF),inputRow);

        askMoreOrExec();
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
}
