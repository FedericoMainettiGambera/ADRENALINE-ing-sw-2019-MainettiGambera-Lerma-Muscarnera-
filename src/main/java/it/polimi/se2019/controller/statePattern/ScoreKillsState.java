package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**When the turn of any user comes to an end, we score the dead players and make them spawn */
public class ScoreKillsState implements State {
    private static PrintWriter out= new PrintWriter(System.out, true);

    //list used to respawn dead players in SpawnState
    private List<Player> deadPlayers;

    public ScoreKillsState(){
        this.deadPlayers = new ArrayList<>();
        out.println("<SERVER> New state: " + this.getClass());
    }

    /**this state may need a list of the dead players*/
    public ScoreKillsState(List<Player> deadPlayers){
        this.deadPlayers = deadPlayers;
        out.println("<SERVER> New state: " + this.getClass());
    }

  /**no input needed, no player to ask inputs to needed
   * @param playerToAsk is irrelevant*/
    @Override
    public void askForInput(Player playerToAsk) {
        out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        //empty
    }

    /**each different situation leads to different states, this is handled by "carrefour" function
     * @param viewControllerEvent is irrelevant since no input is needed*/
    @Override
    public void doAction(ViewControllerEvent viewControllerEvent) {
        //(VCE is null)

        out.println("<SERVER> "+ this.getClass() +".doAction();");

        if(ModelGate.getModel().isBotActive()){
           setBotUsable();
        }

        //score dead players and create the list of dead players
        if(this.deadPlayers.isEmpty()){

            createDeadPlayersList();
        }

        //we finished spawn all dead players
        if(deadPlayers.isEmpty()){
            carrefour();
        }

        //we still need to spawn all dead players
        else {
            //If a player dies during FF --> make player board FF
            if (ModelGate.getModel().hasFinalFrenzyBegun()) {
                for (Player player : deadPlayers) {
                    player.makePlayerBoardFinalFrenzy();
                }
            }

            out.println("<SERVER> Spawning player: " + this.deadPlayers.get(0).getNickname());

            ViewControllerEventHandlerContext.setNextState(new SpawnState(this.deadPlayers));
            ViewControllerEventHandlerContext.getState().askForInput(null);
        }

    }

    public void setBotUsable(){

        out.println("<SERVER> setting bot usable for next player...");
        ModelGate.getModel().getPlayerList().getPlayer("Terminator").setBotUsed(false);

    }

    public void createDeadPlayersList(){

        out.println("<SERVER> Searching for dead players and creating the deadPlayers list");

        for (int i = 0; i < ModelGate.getModel().getPlayerList().getNumberOfPlayers(); i++) {
            if(ModelGate.getModel().getPlayerList().getPlayers().get(i).isDead()&&!ModelGate.getModel().getPlayerList().getPlayers().get(i).isBot()) {
                out.println("<SERVER> Scoring player: " + ModelGate.getModel().getPlayerList().getPlayers().get(i).getNickname());
                this.scoreKill(ModelGate.getModel().getPlayerList().getPlayers().get(i));
                this.deadPlayers.add(ModelGate.getModel().getPlayerList().getPlayers().get(i));
            }
        }
    }

    /**in case a player happen to disconnect, we handle it*/
    @Override
    public void handleAFK() {
        //TODO
        out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
    }

    /**@param deadPlayer is the player we need to score the damageTrack of
     * with this method we ooze a ranking of the players who damaged them the most
     * and assign the right score
     * */
    public void scoreKill(Player deadPlayer){
        out.println("<SERVER> Scoring dead players.");

        //first blood
        if(!ModelGate.getModel().hasFinalFrenzyBegun()){
            out.println("<SERVER> First blood goes to " + deadPlayer.getPlayerBoard().getDamagesSlot(0).getShootingPlayer().getNickname());
            Player firstBlood = deadPlayer.getPlayerBoard().getDamagesSlot(0).getShootingPlayer();
            firstBlood.addPoints(1);
           }

        //list of points (es 8,6,4,2,1,1)
        ArrayList<Integer> pointsList = deadPlayer.getPointsList();
        //list of players in order from the one who made most damages to the one who did the less damages
        ArrayList<Player> playerRankInOrder = deadPlayer.getPlayersDamageRank();
        //give points to each player
        for (int i = 0; i < playerRankInOrder.size(); i++){
            out.println("<SERVER> Player " + playerRankInOrder.get(i).getNickname() +" receives " + pointsList.get(i) + " points.");
            playerRankInOrder.get(i).addPoints(pointsList.get(i));
        }

        //add skull to the killshotTrack
        if(!ModelGate.getModel().hasFinalFrenzyBegun()) {
            ModelGate.getModel().getKillshotTrack().deathOfPlayer(deadPlayer.getLastDamageSlot().getShootingPlayer(), deadPlayer.isOverkilled());
        }

        //the overkilling player receive a mark from the overkilled player
        if(deadPlayer.isOverkilled()){
            deadPlayer.getDamageSlot(11).getShootingPlayer().addMarksFrom(deadPlayer,1);
        }

        //adding skull to the dead player
        if(!ModelGate.getModel().hasFinalFrenzyBegun()) {
            deadPlayer.addDeath();
        }

        //restoring health to the player
        deadPlayer.emptyDamagesTracker();


    }


    /** When we get here, we have spawned all of the dead players, if there were any, now we face a series of distinguished cases where
     * the game can happen to be and we have to sort them out*/
    public void carrefour(){

        out.println("<SERVER> Ended scoring and spawning players.");


        //Game is not ended --> TurnState or FirstSpawnState
        if ((!ModelGate.getModel().getKillshotTrack().areSkullsOver()) || ModelGate.getModel().hasFinalFrenzyBegun()) {

            gameOverOrNot();
        }
        //Game is ended and FinalFrenzy isn't active --> FinalScoringState
        else if (ModelGate.getModel().getKillshotTrack().areSkullsOver() && (!ModelGate.getModel().isFinalFrenzy())) {
            ViewControllerEventHandlerContext.setNextState(new FinalScoringState());
            ViewControllerEventHandlerContext.getState().doAction(null);
        }
        //Game is ended and FinalFrenzy is active --> FFSetUpState
        else if (ModelGate.getModel().getKillshotTrack().areSkullsOver() && (ModelGate.getModel().isFinalFrenzy())) {
            ViewControllerEventHandlerContext.setNextState(new FFSetUpState());
            ViewControllerEventHandlerContext.getState().doAction(null);
        }
    }



    /** in case the game is ended, next state must be final scoring state, but in the case the game isn't ended,
     * either that the game has entered the final frenzy phase or not, next playing player need to play it's turn! if they
     * haven't played yet, they will need to spawn for the first time*/
public void gameOverOrNot(){
    //we are in final frenzy and this was the last playin player-> Final Scoring state
    if (ModelGate.getModel().getCurrentPlayingPlayer().getLastPlayingPlayer()){

        ViewControllerEventHandlerContext.setNextState(new FinalScoringState());
        ViewControllerEventHandlerContext.getState().doAction(null);
    }
    else {
        //if the game aint ended then let's go on playing! we spawned already all dead players, so you can either not have played yet
        ModelGate.getModel().getPlayerList().setNextPlayingPlayer();

        if (ModelGate.getModel().getCurrentPlayingPlayer().getPosition() == null) {

            ViewControllerEventHandlerContext.setNextState(new FirstSpawnState());
            ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
        }
        //or just being waiting for ur turn
        else{

            ViewControllerEventHandlerContext.setNextState(new TurnState(1));
            ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getCurrentPlayingPlayer());
        }
    }

}
}