package it.polimi.se2019.controller;

import it.polimi.se2019.model.GameConstant;
import it.polimi.se2019.model.Player;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WaitForPlayerInput implements Runnable{

    private Player playerToAsk;

    private int randomID;

    private boolean stop;

    private String callingClass;

    public WaitForPlayerInput(Player p, String callingClass){
        this.playerToAsk = p;
        this.callingClass = callingClass;
        this.stop= false;
        Random rand = new Random();
        this.randomID = rand.nextInt(50000);
    }

    @Override
    public void run() {
        System.out.println("                                            Thread: <SERVER> Waiting for " + playerToAsk.getNickname() + "'s input.");
        System.out.println("                                                             from class: " + this.callingClass);


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
        System.out.println("                                            Thread: <SERVER-InputTimer> " + playerToAsk.getNickname() + "is AFK.");
        System.out.println("                                                                        from class: " + this.callingClass);
        System.out.println("                                                                        VCEHC at the moment is: " + ViewControllerEventHandlerContext.state.getClass());

        ViewControllerEventHandlerContext.state.handleAFK();
    }

    public int getRandomID(){
        return this.randomID;
    }
    public void killTimer(){
        this.stop=true;
    }
}
