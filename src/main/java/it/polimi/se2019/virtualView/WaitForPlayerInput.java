package it.polimi.se2019.virtualView;

import it.polimi.se2019.model.GameConstant;
import it.polimi.se2019.model.Player;

import java.util.concurrent.TimeUnit;

public class WaitForPlayerInput implements Runnable{

    private Player playerToAsk;

    public WaitForPlayerInput(Player p){
        this.playerToAsk = p;
    }

    @Override
    public void run() {
        System.out.println("<SERVER> Waiting for " + playerToAsk.getNickname() + "'s input.");
        int i = 1;
        while (i <= GameConstant.timeToInsertInputInSeconds) {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("<SERVER> time passed: " + i + " seconds.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (playerToAsk.hasAnswered()) {
                System.out.println("<SERVER> " + playerToAsk.getNickname() + " has answered.");
                playerToAsk.setHasAnswered(false);
                return;
            }
            i++;
        }
        System.out.println("<SERVER> setting " + playerToAsk.getNickname() + " AFK");
        playerToAsk.setIsAFK(true);
    }
}
