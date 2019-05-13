package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.ViewControllerEvent;

import java.util.ArrayList;

public class ScoreKillsState implements State {

    public ScoreKillsState(){
        System.out.println("<SERVER> New state: " + this.getClass());
    }

    @Override
    public void askForInput(Player playerToAsk) {
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        //empty
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        //list used to respawn dead players in SpawnState
        ArrayList<Player> deadPlayers = new ArrayList<>();

        //score dead players
        for (int i = 0; i < ModelGate.model.getPlayerList().getNumberOfPlayers(); i++) {
            if(ModelGate.model.getPlayerList().getPlayers().get(i).isDead()){
                this.scoreKill(ModelGate.model.getPlayerList().getPlayers().get(i));
                deadPlayers.add(ModelGate.model.getPlayerList().getPlayers().get(i));
            }
        }

        if(deadPlayers.isEmpty()){
            ModelGate.model.getPlayerList().setNextPlayingPlayer();
            ViewControllerEventHandlerContext.setNextState(new TurnState(1));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else{
            ViewControllerEventHandlerContext.setNextState(new SpawnState(deadPlayers));
            ViewControllerEventHandlerContext.state.askForInput(null);
        }
    }

    public void scoreKill(Player deadPlayer){
        //first blood
        Player firstBlood = deadPlayer.getPlayerBoard().getDamagesSlot(0).getShootingPlayer();
        firstBlood.addPoints(1);

        //list of points (es 8,6,4,2,1,1)
        ArrayList<Integer> pointsList = deadPlayer.getPointsList();
        //list of players in order from the one who made most damages to the one who did the less damages
        ArrayList<Player> playerRankInOrder = deadPlayer.getPlayersDamageRank();
        //give points to each player
        for (int i = 0; i < playerRankInOrder.size(); i++) {
            playerRankInOrder.get(i).addPoints(pointsList.get(i));
        }

        //add skull to the killshotTrack
        ModelGate.model.getKillshotTrack().deathOfPlayer(deadPlayer, deadPlayer.isOverkilled());

        //the overkilling player receive a mark from the overkilled player
        if(deadPlayer.isOverkilled()){
            deadPlayer.getDamageSlot(12).getShootingPlayer().addMarksFrom(deadPlayer,1);
        }

        //adding skull to the dead player
        deadPlayer.addDeath();
        deadPlayer.emptyDamagesTracker();
    }

}
