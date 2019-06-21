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
        if(inputRequestCounterF < this.chosenPowerUp.getSpecialEffect().requestedInputs().size()-1 ){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void askForInput(Player playerToAsk) {
        this.playerToAsk = playerToAsk;

        this.chosenPowerUp.getSpecialEffect().passContext(playerToAsk, ModelGate.model.getPlayerList(), ModelGate.model.getBoard());

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
            //TODO il pagamento nelle PowerUp avviene pagando un cubo qualunque... gestisci questa cosa
            //ChooseHowToPayState.makePayment(playerToAsk, this.chosenPowerUp.getSpecialEffect().getUsageCost());
        }
    }
    public void afterPayment(){

        this.chosenPowerUp.getSpecialEffect().Exec();

        //TODO DISCARD THE POWER UP, BUT I STILL DON'T HAVE THE UNIQUE ID
        //playerToAsk.getPowerUpCardsInHand().moveCardTo(ModelGate.model.getPowerUpDiscardPile(), this.chosenPowerUp.getID());

        ViewControllerEventHandlerContext.setNextState(this.nextState);
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }

    public boolean isToSend(EffectInfoType infoType) {
        return (! ( infoType.equals(EffectInfoType.player) ||
                    infoType.equals(EffectInfoType.playerSquare) ||
                    infoType.equals(EffectInfoType.targetListBySameSquareOfPlayer) ||
                    infoType.equals(EffectInfoType.singleRoom) ||
                    infoType.equals(EffectInfoType.squareOfLastTargetSelected) ||
                    infoType.equals(EffectInfoType.targetBySameSquareOfPlayer) ||
                    infoType.equals(EffectInfoType.targetListByLastTargetSelectedSquare)
                  )
               );
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        this.inputTimer.interrupt();
        out.println("<SERVER> player has answered before the timer ended.");

        out.println("<SERVER> " + this.getClass() + ".doAction();");

        List<Object> response = ((ViewControllerEventListOfObject)VCE).getAnswer();

        Object[] inputRow = new Object[10];

        int inputRowCurrent = 0;
        for(Object o: response) {
            if(o.getClass().toString().contains("PlayerV")){
                inputRow[inputRowCurrent] = ModelGate.model.getPlayerList().getPlayer(((PlayerV)o).getNickname());
            }
            else{
                inputRow[inputRowCurrent] = ModelGate.model.getBoard().getMap()[((SquareV)o).getX()][((SquareV)o).getY()];
            }
            inputRowCurrent++;
        }

        this.chosenPowerUp.getSpecialEffect().handleRow(this.chosenPowerUp.getSpecialEffect().getEffectInfo().getEffectInfoElement().get(inputRequestCounterF),inputRow);

        askMoreOrExec();
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
}
