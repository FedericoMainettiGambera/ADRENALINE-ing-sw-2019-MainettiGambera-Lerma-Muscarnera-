package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ScoreKillsState implements State {
    private static PrintWriter out= new PrintWriter(System.out, true);

    //list used to respawn dead players in SpawnState
    private ArrayList<Player> deadPlayers;

    public ScoreKillsState(){
        this.deadPlayers = new ArrayList<>();
        out.println("<SERVER> New state: " + this.getClass());
    }

    public ScoreKillsState(ArrayList<Player> deadPlayers){
        this.deadPlayers = deadPlayers;
        out.println("<SERVER> New state: " + this.getClass());
    }


    @Override
    public void askForInput(Player playerToAsk) {
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        //empty
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        //(VCE is null)

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        if(ModelGate.model.isBotActive()){
            out.println("<SERVER> setting bot usable for next player...");
            ModelGate.model.getPlayerList().getPlayer("Terminator").setBotUsed(false);
        }

        //score dead players and create the list of dead players
        if(this.deadPlayers.isEmpty()){
            out.println("<SERVER> Searching for dead players and creating the deadPlayers list");
            for (int i = 0; i < ModelGate.model.getPlayerList().getNumberOfPlayers(); i++) {
                if(ModelGate.model.getPlayerList().getPlayers().get(i).isDead()&&!ModelGate.model.getPlayerList().getPlayers().get(i).isBot()) {
                    out.println("<SERVER> Scoring player: " + ModelGate.model.getPlayerList().getPlayers().get(i).getNickname());
                    this.scoreKill(ModelGate.model.getPlayerList().getPlayers().get(i));
                    deadPlayers.add(ModelGate.model.getPlayerList().getPlayers().get(i));
                }
            }
        }

        if(deadPlayers.isEmpty()){
            out.println("<SERVER> Ended scoring and spawning players.");



            //Game is not ended --> TurnState or FirstSpawnState
            if((!ModelGate.model.getKillshotTrack().areSkullsOver())||ModelGate.model.hasFinalFrenzyBegun()){
                //we are in final frenzy and this was the last playin player-> Final Scoring state
                if(ModelGate.model.getCurrentPlayingPlayer().getLastPlayingPlayer()){
                    ViewControllerEventHandlerContext.setNextState(new FinalScoringState());
                    ViewControllerEventHandlerContext.state.doAction(null);
                }
                else{
                      ModelGate.model.getPlayerList().setNextPlayingPlayer();
                    if(ModelGate.model.getCurrentPlayingPlayer().getPosition() == null){
                        ViewControllerEventHandlerContext.setNextState(new FirstSpawnState());
                        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
                    }
                    else{
                        ViewControllerEventHandlerContext.setNextState(new TurnState(1));
                        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
                    }
                }
            }
            //Game is ended and FinalFrenzy isn't active --> FinalScoringState
            else if(ModelGate.model.getKillshotTrack().areSkullsOver() && (!ModelGate.model.isFinalFrenzy())){
                ViewControllerEventHandlerContext.setNextState(new FinalScoringState());
                ViewControllerEventHandlerContext.state.doAction(null);
            }
            //Game is ended and FinalFrenzy is active --> FFSetUpState
            else if(ModelGate.model.getKillshotTrack().areSkullsOver() && (ModelGate.model.isFinalFrenzy())){
                ModelGate.model.getPlayerList().setNextPlayingPlayer();
                ViewControllerEventHandlerContext.setNextState(new FFSetUpState());
                ViewControllerEventHandlerContext.state.doAction(null);
            }
        }
        else{
          //If a player dies during FF --> make player board FF
            if(ModelGate.model.hasFinalFrenzyBegun()){
                for(Player player : deadPlayers){
                    player.makePlayerBoardFinalFrenzy();
                }
            }

            out.println("<SERVER> Spawning player: " + this.deadPlayers.get(0).getNickname());

            ViewControllerEventHandlerContext.setNextState(new SpawnState(this.deadPlayers));
            ViewControllerEventHandlerContext.state.askForInput(null);
        }
    }

    @Override
    public void handleAFK() {
        //TODO
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
    }

    public void scoreKill(Player deadPlayer){
        out.println("<SERVER> Scoring dead players.");

        //first blood
        if(!ModelGate.model.hasFinalFrenzyBegun()){
            out.println("<SERVER> First blood goes to " + deadPlayer.getPlayerBoard().getDamagesSlot(0).getShootingPlayer().getNickname());
            Player firstBlood = deadPlayer.getPlayerBoard().getDamagesSlot(0).getShootingPlayer();
            firstBlood.addPoints(1);
           }

        //list of points (es 8,6,4,2,1,1)
        ArrayList<Integer> pointsList = deadPlayer.getPointsList();
        //list of players in order from the one who made most damages to the one who did the less damages
        ArrayList<Player> playerRankInOrder = deadPlayer.getPlayersDamageRank();
        //give points to each player
        for (int i = 0; i < playerRankInOrder.size(); i++) {
            out.println("<SERVER> Player " + playerRankInOrder.get(i).getNickname() +" receives " + pointsList.get(i) + " points.");
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

        //restoring health to the player
        deadPlayer.emptyDamagesTracker();

    }

}
