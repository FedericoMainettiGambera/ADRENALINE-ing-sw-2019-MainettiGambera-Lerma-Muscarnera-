package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.Kill;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PlayersList;
import it.polimi.se2019.model.enumerations.ModelViewEventTypes;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class FinalScoringState implements State {
    private static PrintWriter out = new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(FinalScoringState.class.getName());


    public FinalScoringState() {
        out.println("<SERVER> New state: " + this.getClass());
    }

    @Override
    public void askForInput(Player playerToAsk) {
        out.println("<SERVER> (" + this.getClass() + ") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        //empty
    }

    @Override
    public void doAction(ViewControllerEvent VCE){
        out.println("<SERVER> " + this.getClass() + ".doAction();");


        //when the game ends, we score any player that still got tokens on his damage track
        for (Player player : ModelGate.model.getPlayerList().getPlayers()) {
            scoreTokens(player);
        }

        ArrayList<PlayerPoint> graduatory = null;
        try {
            graduatory = getGraduatory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int score=8;
        for(PlayerPoint playerPoint: graduatory){

            for(Player p: ModelGate.model.getPlayerList().getPlayers()){

                if(playerPoint.player.getNickname().equals(p.getNickname())){
                    playerPoint.quantity=p.getScore()+score+playerPoint.overkill;
                }
            }

            if(score>2){
                score=score-2;
            }
            else score=1;
        }

        PlayerPoint temporaryPlayer;
        int s=0;
        while(s<graduatory.size()){
            int k=1;
            int j=0;
            while(k<graduatory.size()) {
                if((graduatory.get(j).quantity < graduatory.get(k).quantity)||
                        (graduatory.get(j).quantity == graduatory.get(k).quantity&&graduatory.get(j).numberOfSkullTaken>graduatory.get(k).numberOfSkullTaken)){
                    temporaryPlayer = graduatory.get(j);
                    graduatory.set(j, graduatory.get(k));
                    graduatory.set(k, temporaryPlayer);
                    j += 1;
                    k += 1;
                }
            }
            s+=1;
        }

        int i=1;
        out.println("Final Classification is :");
        for(PlayerPoint p: graduatory){
            out.println("<SERVER> " + i + ":"+p.player.getNickname()+" with "+p.quantity+" points ");
            ModelGate.model.notifyClients(new ModelViewEvent(p.player.getNickname(), ModelViewEventTypes.finalScoring, p.quantity, i));
            i++;
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


    //structure needed to score the kills
    class PlayerPoint{
        Player player;
        int quantity;
        int numberOfSkullTaken;
        int overkill;

        public PlayerPoint(Player player) throws Exception {
            this.player = player;
            this.quantity = 0;
            this.numberOfSkullTaken=0;
            int overkill=0;
        }
    }


/**
 * this function analyze the killshot track in order to get a final score for each player and a graduatory
 * @return graduatory
 * */
        public ArrayList<PlayerPoint> getGraduatory() throws Exception {

            int last;
            int k = 0;
            ArrayList<PlayerPoint> playerKilling = new ArrayList<>();
            Player p;

            //takes the killshot track, check which name there's on the first kill and look for it through the whole killshot track,
            //removing each occurance and scoring the points in "quantity", saving the last occurance of it for tiebreaks, do it again till the list is empty.

            while (!ModelGate.model.getKillshotTrack().returnKills().isEmpty()) {

                p = (ModelGate.model.getKillshotTrack().returnKills().get(0).getKillingPlayer());
                playerKilling.add(new PlayerPoint(p));
                playerKilling.get(k).quantity = 0;

                Iterator<Kill> killIterator=ModelGate.model.getKillshotTrack().returnKills().iterator();


                for (Kill kill : ModelGate.model.getKillshotTrack().returnKills()) {

                    if (p.getNickname().equals(kill.getKillingPlayer().getNickname())) {
                        playerKilling.get(k).quantity += 1;

                        if (playerKilling.get(k).numberOfSkullTaken > kill.getOccurance()) {
                            playerKilling.get(k).numberOfSkullTaken = kill.getOccurance();
                        }

                        if (kill.getOverKillingPlayer().getNickname().equals(p.getNickname())) {
                            playerKilling.get(k).overkill += 1;
                        }


                        last = ModelGate.model.getKillshotTrack().returnKills().size();

                        while(ModelGate.model.getKillshotTrack().returnKills().get(last).getKillingPlayer().getNickname().equals(p.getNickname())) {
                            playerKilling.get(k).quantity += 1;

                            if (kill.getOverKillingPlayer().getNickname().equals(p.getNickname())) {
                                playerKilling.get(k).overkill += 1;
                            }
                            ModelGate.model.getKillshotTrack().returnKills().remove(last);
                            last = ModelGate.model.getKillshotTrack().returnKills().size();

                            if (playerKilling.get(k).numberOfSkullTaken > kill.getOccurance()) {
                                playerKilling.get(k).numberOfSkullTaken = kill.getOccurance();
                            }
                        }
                        ModelGate.model.getKillshotTrack().returnKills().set(ModelGate.model.getKillshotTrack().returnKills().indexOf(kill), ModelGate.model.getKillshotTrack().returnKills().get(last));
                        ModelGate.model.getKillshotTrack().returnKills().remove(last);

                    }
                }
                k = +1;
            }


            // kinda bubblesort
            PlayerPoint temporaryPlayer;
            int s=0;
            while(s<playerKilling.size()){
            k=1;
            int j=0;
            while(k<playerKilling.size()) {
                if((playerKilling.get(j).quantity < playerKilling.get(k).quantity)||
                        (playerKilling.get(j).quantity == playerKilling.get(k).quantity&&playerKilling.get(j).numberOfSkullTaken>playerKilling.get(k).numberOfSkullTaken)){
                    temporaryPlayer = playerKilling.get(j);
                    playerKilling.set(j, playerKilling.get(k));
                    playerKilling.set(k, temporaryPlayer);
                    j += 1;
                    k += 1;
                }
            }
            s+=1;
          }
       return playerKilling;
        }

}

