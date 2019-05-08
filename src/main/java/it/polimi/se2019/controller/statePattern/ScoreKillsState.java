package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.ViewControllerEvent;

import java.util.ArrayList;

public class ScoreKillsState implements State {
    @Override
    public void askForInput(Player playerToAsk) {

    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        for (int i = 0; i < ModelGate.model.getPlayerList().getNumberOfPlayers(); i++) {
            if(ModelGate.model.getPlayerList().getPlayers().get(i).isDead()){
                this.scoreKill(ModelGate.model.getPlayerList().getPlayers().get(i));
            }
        }
    }

    public void scoreKill(Player deadPlayer){
        //first blood
        Player firstBlood = deadPlayer.getPlayerBoard().getDamagesSlot(0).getShootingPlayer();
        firstBlood.addPoints(1);

        ArrayList<Integer> pointsList = deadPlayer.getPointsList();

        ArrayList<Player> playerRankInOrder = deadPlayer.getPlayersDamageRank();

        //adding skull to the dead player
        deadPlayer.addDeath();
    }

}
