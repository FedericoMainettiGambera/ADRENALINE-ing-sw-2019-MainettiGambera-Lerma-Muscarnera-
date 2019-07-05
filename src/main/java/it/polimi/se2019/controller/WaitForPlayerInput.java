package it.polimi.se2019.controller;

import it.polimi.se2019.model.GameConstant;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.timerEvent.TimerEvent;
import it.polimi.se2019.virtualView.RMIREDO.RmiVirtualView;
import it.polimi.se2019.virtualView.Socket.SocketVirtualView;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class WaitForPlayerInput implements Runnable{

    private static PrintWriter out= new PrintWriter(System.out, true);


    private Player playerToAsk;

    private int randomID;

    private String callingClass;

    private SocketVirtualView socketVirtualView = ViewControllerEventHandlerContext.getSocketVV();

    private RmiVirtualView rmiVirtualView = ViewControllerEventHandlerContext.getRmiVirtualView();

    private String ask;

    public WaitForPlayerInput(Player p, String callingClass, String ask){
        this.playerToAsk = p;
        this.callingClass = callingClass;
        Random rand = new Random();
        this.randomID = rand.nextInt(50000);
        this.ask=ask;
    }

    @Override
    public void run() {
        out.println("                                            Thread: <SERVER> Waiting for " + playerToAsk.getNickname() + "'s input.");
        out.println("                                                             from class: " + this.callingClass);
        out.println("                                                             ID: " + this.randomID);
        out.println("                                                             relative ask: " + this.ask);


        int i = 1;

        while (i <= GameConstant.TIME_TO_INSERT_INPUT_IN_SECONDS) {
            this.rmiVirtualView.sendAllClient(new TimerEvent(i, GameConstant.TIME_TO_INSERT_INPUT_IN_SECONDS, "input"));
            this.socketVirtualView.sendAllClient(new TimerEvent(i, GameConstant.TIME_TO_INSERT_INPUT_IN_SECONDS, "input"));
            try {
                TimeUnit.SECONDS.sleep(1);
                out.println("                                            Thread: <SERVER-InputTimer-for-"+playerToAsk.getNickname()+"> time passed: " + i + " seconds.  ID: " + this.randomID);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                return;
            }
            i++;
            if(playerToAsk.isAFK()){
               out.println("                                            Thread: <SERVER-InputTimer-for-"+playerToAsk.getNickname()+"> the player is AFK, so i make the timer go forward to the end.");
                i=GameConstant.TIME_TO_INSERT_INPUT_IN_SECONDS +1;
            }
        }

        out.println("                                            Thread: <SERVER-InputTimer> " + playerToAsk.getNickname() + "is AFK.");
        out.println("                                                                        from class: " + this.callingClass);
        out.println("                                                                        VCEHC at the moment is: " + ViewControllerEventHandlerContext.getState().getClass());

        if(this.callingClass.getClass().toString().contains("ChooseHowToPayState")){
            ViewControllerEventHandlerContext.getPaymentProcess().handleAFK();
        }
        else {
            ViewControllerEventHandlerContext.getState().handleAFK();
        }
    }
}
