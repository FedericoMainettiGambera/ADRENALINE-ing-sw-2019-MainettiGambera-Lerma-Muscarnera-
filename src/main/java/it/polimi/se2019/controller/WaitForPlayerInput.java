package it.polimi.se2019.controller;

import it.polimi.se2019.model.GameConstant;
import it.polimi.se2019.model.Player;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WaitForPlayerInput implements Runnable{

    private Player playerToAsk;

    private int randomID;

    private boolean stop;

    public WaitForPlayerInput(Player p){
        this.playerToAsk = p;
        this.stop= false;
        Random rand = new Random();
        this.randomID = rand.nextInt(50000);
    }

    @Override
    public void run() {
        System.out.println("<SERVER> Waiting for " + playerToAsk.getNickname() + "'s input.");


        int i = 1;
        while (i <= GameConstant.timeToInsertInputInSeconds) {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("                                            Thread: <SERVER-InputTimer-for-"+playerToAsk.getNickname()+"> time passed: " + i + " seconds.  ID: " + this.randomID);
            } catch (InterruptedException e) {
                stop = true;
            }
            i++;
            if(this.stop){
                return;
            }
        }
        System.out.println("Thread: <SERVER-InputTimer> setting " + playerToAsk.getNickname() + " AFK");
        ViewControllerEventHandlerContext.state.handleAFK();
    }

    public void killTimer(){
        this.stop=true;
    }
}
