package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.Kill;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PlayersList;
import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**this state is meant to score the killshot track  and provide a final ranking. It is, obviously, only called once, at the end of the game*/
public class FinalScoringState implements State {
    private static PrintWriter out = new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(FinalScoringState.class.getName());


    public FinalScoringState() {
        out.println("<SERVER> New state: " + this.getClass());
    }

    /**
     * no input required
     */
    @Override
    public void askForInput(Player playerToAsk) {
        out.println("<SERVER> (" + this.getClass() + ") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        //empty
    }

    /**
     * this doAction does this things in this order:
     * 1: score the playerBoards that got some damages on it even if the owners of said playerBoards are still alive
     * 2: calls function getgraduatory
     * 3:Effectuate the necessary calculus to get the very final ranking, adding up, for each player,  the points earned during the whole game play,
     * the points from overkills and the points from number of killshots
     * it eventually effectuate a bubble sort in order to get the ranking,
     * calls a MVE event in order to get all clients to see the ranking and
     * ends the application
     */
    @Override
    public void doAction(ViewControllerEvent VCE) {
        out.println("<SERVER> " + this.getClass() + ".doAction();");
        int score;


        /*when the game ends, we score any player that still got tokens on his damage track*/
        for (Player player : ModelGate.model.getPlayerList().getPlayers()) {
            scoreTokens(player);
        }

        if (ModelGate.model.getKillshotTrack().returnKills().get(0).getKillingPlayer() == null){
            score = 0;
        } else score = 8;

        ArrayList<PlayerPoint> graduatory = null;
        try {
            graduatory = getGraduatory();
        } catch (Exception e) {
            logger.severe("Error occurred:" + e.getClass() + e.getCause() + Arrays.toString(e.getStackTrace()));
        }


        /*gives deserved points based on number of killshots*/
        if (graduatory != null){

            graduatory=setPoints(graduatory, score);
            graduatory=bubbleSort(graduatory);
            sendResults(graduatory);


           } else out.println("mhh something went wrong with scoring points");

        System.exit(0);
    }


    private  ArrayList<PlayerPoint> setPoints(ArrayList<PlayerPoint> classifica, int punti ) {
        ArrayList<PlayerPoint> graduatory = classifica;
        int score = punti;

        if (graduatory != null){
            for (PlayerPoint playerPoint : graduatory){

                for (Player p : ModelGate.model.getPlayerList().getPlayers()) {

                    if (playerPoint.player.getNickname().equals(p.getNickname())){
                        playerPoint.quantity = p.getScore() + score + playerPoint.overkill;
                    }
                }

                if (score == 0) {
                    score = 0;
                } else if (score > 2) {
                    score = score - 2;
                } else score = 1;
            }

        }
        return graduatory;
    }

    /**send results to all clients
     * @param classifica obviously needed to know the rankings to send*/
    private void sendResults(ArrayList<PlayerPoint> classifica){

        ArrayList<PlayerPoint> graduatory=classifica;

        int i = 1;
        out.println("Final Classification is :");
        for (PlayerPoint p : graduatory) {


            out.println("<SERVER> " + i + ":" + p.player.getNickname() + " with " + p.quantity + " points ");
            ModelGate.model.notifyClients(new ModelViewEvent(p.player.getNickname(), ModelViewEventTypes.finalScoring, p.quantity, " " +i));
            i++;
        }

    }






    @Override
    public void handleAFK() {
        out.println("<SERVER> (" + this.getClass() + ") Handling AFK Player.");
        //empty
    }


    /**
     * when the game ends, we score any player that still got tokens on his damage track
     *
     * @param player we do it for every player
     */
    public void scoreTokens(Player player) {

        //list of points (es 8,6,4,2,1,1)
        ArrayList<Integer> pointsList = player.getPointsList();
        //list of players in order from the one who made most damages to the one who did the less damages
        ArrayList<Player> playerRankInOrder = player.getPlayersDamageRank();

        for (int i = 0; i < playerRankInOrder.size(); i++) {
            out.println("<SERVER> Player " + playerRankInOrder.get(i).getNickname() + " receives " + pointsList.get(i) + " points.");
            playerRankInOrder.get(i).addPoints(pointsList.get(i));
        }

    }


    /**
     * structure needed to score the kills
     */
    class PlayerPoint {
        Player player;
        int quantity;
        int numberOfSkullTaken;
        int overkill;

        public PlayerPoint(Player player) {
            this.player = player;
            this.quantity = 0;
            this.numberOfSkullTaken = 0;
            int overkill = 0;
        }
    }


    /**
     * this function analyze the killshot track in order to get a final score for each player and a graduatory
     *
     * @return graduatory
     */
    private ArrayList<PlayerPoint> getGraduatory(){

        int k=0;
        ArrayList<PlayerPoint> playerKilling = new ArrayList<>();

        //takes the killshot track, check which name there's on the first kill and look for it through the whole killshot track,
        //removing each occurance and scoring the points in "quantity", saving the last occurance of it for tiebreaks, do it again till the list is empty.

        for (Player p : ModelGate.model.getPlayerList().getPlayers()){

            playerKilling.add(new PlayerPoint(p));


            Iterator<Kill> killIterator = ModelGate.model.getKillshotTrack().returnKills().iterator();


            while(killIterator.hasNext()) {

                Kill kill = killIterator.next();


                if ((!kill.isSkull()) && p.getNickname().equals(kill.getKillingPlayer().getNickname())) {

                    playerKilling.get(k).quantity += 1;

                    if (playerKilling.get(k).numberOfSkullTaken < kill.getOccurance()) {
                        playerKilling.get(k).numberOfSkullTaken = kill.getOccurance();
                    }

                    if(kill.isOverKill()) {
                        playerKilling.get(k).overkill += 1;
                    }

                }
            }
       k=k+1;

        }

        playerKilling=bubbleSort(playerKilling);


        return playerKilling;
    }


    private ArrayList<PlayerPoint> bubbleSort(ArrayList<PlayerPoint> listToSort){

        ArrayList<PlayerPoint> playerKilling=listToSort;



        if (!playerKilling.isEmpty()){

            PlayerPoint temporaryPlayer;
            int k = 0;
            int s = 0;
            while (s < playerKilling.size()) {
                k = 1;
                int j = 0;
                while (k < playerKilling.size()) {
                    if ((playerKilling.get(j).quantity < playerKilling.get(k).quantity) ||
                            (playerKilling.get(j).quantity == playerKilling.get(k).quantity && playerKilling.get(j).numberOfSkullTaken > playerKilling.get(k).numberOfSkullTaken)) {
                        temporaryPlayer = playerKilling.get(j);
                        playerKilling.set(j, playerKilling.get(k));
                        playerKilling.set(k, temporaryPlayer);
                        j += 1;
                        k += 1;
                    } else {
                        j += 1;
                        k += 1;
                    }
                }
                s += 1;
            }
        }
        return playerKilling;
    }

}