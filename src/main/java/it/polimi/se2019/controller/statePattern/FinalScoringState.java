package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.Kill;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

/**this state is meant to score the kill shot track  and provide a final ranking. It is, obviously, only called once, at the end of the game*/
public class FinalScoringState implements State {

    private static PrintWriter out = new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(FinalScoringState.class.getName());

    /**a list of players with their points in decreasing order*/
    private ArrayList<PlayerPoint> graduatory;

    /**constructor*/
    public FinalScoringState() {
        out.println("<SERVER> New state: " + this.getClass());
        graduatory=new ArrayList<>();
    }

    /**
     * no input required
     * @param playerToAsk is null
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
    public void doAction(ViewControllerEvent viewControllerEvent) {
        out.println("<SERVER> " + this.getClass() + ".doAction();");
        int score;


        /*when the game ends, we score any player that still got tokens on his damage track*/
        for (Player player : ModelGate.getModel().getPlayerList().getPlayers()) {
            scoreTokens(player);
        }

        if (ModelGate.getModel().getKillshotTrack().returnKills().get(0).getKillingPlayer() == null){
            score = 0;
        } else score = 8;

        try {
            getGraduatory();
        } catch (Exception e) {
            logger.severe("Error occurred:" + e.getClass() + e.getCause() + Arrays.toString(e.getStackTrace()));
        }



        if (graduatory != null){

            setPoints(score);
            bubbleSort();
            sendResults(graduatory);


           } else out.println("mhh something went wrong with scoring points");

        System.exit(0);
    }

/**gives deserved points based on number of killshots
 * @param punti  needed because if nobody died, nobody deserves the points for max number of killshots*/
    private   void setPoints( int punti ) {

        int score = punti;

        if (graduatory != null){
            for (PlayerPoint playerPoint : graduatory){

                for (Player p : ModelGate.getModel().getPlayerList().getPlayers()) {

                    if (playerPoint.player.getNickname().equals(p.getNickname())){
                        playerPoint.quantity = p.getScore() + score + playerPoint.overkill;
                    }
                }

                if (score == 0) {
                    score=0;
                } else if (score > 2) {
                    score = score - 2;
                } else score = 1;
            }

        }
    }

    /**send results to all clients
     * @param classifica obviously needed to know the rankings to send*/
    private void sendResults(ArrayList<PlayerPoint> classifica){

        int i = 1;
        out.println("Final Classification is :");
        for (PlayerPoint p : classifica) {
            p.player.setScore(p.quantity);
            out.println("<SERVER> " + i + ":" + p.player.getNickname() + " with " + p.quantity + " points ");
            ModelGate.getModel().notifyClients(new ModelViewEvent(p.player.getNickname(), ModelViewEventTypes.finalScoring, p.quantity, " " +i));
            i++;
        }

    }

    /**
     * set the player AFK in case they don't send required input in a while
     * */
    @Override
    public void handleAFK() {
        out.println("<SERVER> (" + this.getClass() + ") Handling AFK Player.");
        //empty, never called
    }


    /**
     * when the game ends, we score any player that still got tokens on his damage track
     *
     * @param player we do it for every player
     */
    private void scoreTokens(Player player) {

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

        private PlayerPoint(Player player) {
            this.player = player;
            this.quantity = 0;
            this.numberOfSkullTaken = 0;
        }
    }


    /**
     * this function analyze the killshot track in order to get a final score for each player and a graduatory
     */
    private void getGraduatory(){

        int k=0;

        //takes the killshot track, check which name there's on the first kill and look for it through the whole killshot track,
        //removing each occurance and scoring the points in "quantity", saving the last occurance of it for tiebreaks, do it again till the list is empty.

        for (Player p : ModelGate.getModel().getPlayerList().getPlayers()){

            graduatory.add(new PlayerPoint(p));


            for (Kill kill : ModelGate.getModel().getKillshotTrack().returnKills()) {

                if ((!kill.isSkull()) && p.getNickname().equals(kill.getKillingPlayer().getNickname())) {

                    graduatory.get(k).quantity += 1;

                    if (graduatory.get(k).numberOfSkullTaken < kill.getOccurance()) {
                        graduatory.get(k).numberOfSkullTaken = kill.getOccurance();
                    }

                    if (kill.isOverKill()) {
                        graduatory.get(k).overkill += 1;
                    }

                }
            }
       k=k+1;

        }

       bubbleSort();

    }


    /**very common and simple function to order a list, known as bubbleSort
     * (
     * although there are more efficent algorythm of sorting,
     * linear complexity of this one for list of under 20 elements is satisfying
     * )
     * */
    private void bubbleSort(){

        if (!graduatory.isEmpty()){

            PlayerPoint temporaryPlayer;
            int k;
            int s = 0;
            while (s < graduatory.size()) {
                k = 1;
                int j = 0;
                while (k < graduatory.size()) {
                    if ((graduatory.get(j).quantity < graduatory.get(k).quantity) ||
                            (graduatory.get(j).quantity == graduatory.get(k).quantity && graduatory.get(j).numberOfSkullTaken > graduatory.get(k).numberOfSkullTaken)) {
                        temporaryPlayer = graduatory.get(j);
                        graduatory.set(j, graduatory.get(k));
                        graduatory.set(k, temporaryPlayer);
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

    }

}