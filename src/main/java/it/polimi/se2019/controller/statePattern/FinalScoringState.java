package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.Kill;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PlayersList;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FinalScoringState implements State {
    private static PrintWriter out = new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(FinalScoringState.class.getName());

    List<PlayerKills> playerKills=new ArrayList<>();

    public FinalScoringState() {
        out.println("<SERVER> New state: " + this.getClass());
    }

    @Override
    public void askForInput(Player playerToAsk) {
        out.println("<SERVER> (" + this.getClass() + ") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        //empty
    }

    @Override
    public void doAction(ViewControllerEvent VCE) throws Exception {
        out.println("<SERVER> " + this.getClass() + ".doAction();");


        //when the game ends, we score any player that still got tokens on his damage track
        for (Player player : ModelGate.model.getPlayerList().getPlayers()) {
            scoreTokens(player);
        }

        playerKills=getPlayersKillsRank();

        for (PlayerKills p : playerKills){

            


        }

        System.exit(0);

    }

    @Override
    public void handleAFK() {
        out.println("<SERVER> (" + this.getClass() + ") Handling AFK Player.");
        //empty
    }


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


    //subclass
    class PlayerKills {
        Player player;
        int quantity;


        public PlayerKills(Player player) throws Exception {
            this.player = player;
            this.quantity = 0;
        }
    }



        public ArrayList<PlayerKills> getPlayersKillsRank() throws Exception {

            int i = 0;
            ArrayList<PlayerKills> playerScores = new ArrayList<>();

            for (Player player : ModelGate.model.getPlayerList().getPlayers()) {

                PlayerKills playerKills = new PlayerKills(player);
                playerScores.add(playerKills);

                for (Kill kill : ModelGate.model.getKillshotTrack().returnKills()) {
                    if (kill.getKillingPlayer().getNickname().equals(player.getNickname())) {
                        playerScores.get(i).quantity++;
                        if (kill.getOverKillingPlayer().getNickname().equals(player.getNickname())) {
                            playerScores.get(i).quantity++;
                        }
                    }
                }
                i++;
            }

            // Insertion sort

            int value;
            Player playervalue;
            int k;
            for (int j = 1; i < playerScores.size(); j++) {

                value = playerScores.get(j).quantity;
                playervalue = playerScores.get(j).player;

                k = j - 1;
                while (k >= 0 && playerScores.get(k).quantity > value) {

                    playerScores.get(k + 1).quantity = playerScores.get(k).quantity;
                    playerScores.get(k + 1).player = playerScores.get(k).player;
                    k = k - 1;
                    playerScores.get(k + 1).quantity = value;
                }
                playerScores.get(k + 1).player = playervalue;

            }

            int price = 8;
            for (PlayerKills p : playerScores) {
                p.quantity = +price;

                if (price != 1) {
                    if (price == 2) {
                        price = price - 1;
                    } else price = price - 2;
                } else price = 1;
            }
            return playerScores;

        }




}