package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.ViewControllerEvent;

public class FinalFrenzySetUpState implements State {

    private int actionNumber;

    public FinalFrenzySetUpState(int actionNumber){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {

    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        //makes the players boards that has no damages in FinalFrenzy mode
        for(Player player : ModelGate.model.getPlayerList().getPlayers()){
            if(player.getBoard().getDamagesTracker().isEmpty()){
                player.makePlayerBoardFinalFrenzy();
            }
        }

        //TODO: other stuff

        //set state
        //based on the action number we start a single turn for every player...
    }
}
