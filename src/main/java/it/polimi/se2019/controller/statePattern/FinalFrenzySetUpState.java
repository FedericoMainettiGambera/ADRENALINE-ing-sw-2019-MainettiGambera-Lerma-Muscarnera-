package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.ViewControllerEvent;

public class FinalFrenzySetUpState implements State {

    private int actionNumber;

    @Override
    public void askForInput(Player playerToAsk) {

    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        for(Player player : ModelGate.model.getPlayerList().getPlayers()){
            if(player.getBoard().getDamagesTracker().isEmpty()){
                player.makePlayerBoardFinalFrenzy();
            }
        }
    }
}
