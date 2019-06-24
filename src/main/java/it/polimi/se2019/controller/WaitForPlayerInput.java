package it.polimi.se2019.controller;

import it.polimi.se2019.controller.statePattern.ChooseHowToPayState;
import it.polimi.se2019.controller.statePattern.SpawnState;
import it.polimi.se2019.model.GameConstant;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.timerEvent.TimerEvent;
import it.polimi.se2019.virtualView.RMI.RMIVirtualView;
import it.polimi.se2019.virtualView.Socket.SocketVirtualView;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WaitForPlayerInput implements Runnable{

    private static PrintWriter out= new PrintWriter(System.out, true);
    private static final Logger logger = Logger.getLogger(WaitForPlayerInput.class.getName());


    private Player playerToAsk;

    private int randomID;

    private String callingClass;

    private SocketVirtualView SVV = ViewControllerEventHandlerContext.socketVV;

    private RMIVirtualView RMIVV = ViewControllerEventHandlerContext.RMIVV;

    public WaitForPlayerInput(Player p, String callingClass){
        this.playerToAsk = p;
        this.callingClass = callingClass;
        Random rand = new Random();
        this.randomID = rand.nextInt(50000);
    }

    @Override
    public void run() {
        out.println("                                            Thread: <SERVER> Waiting for " + playerToAsk.getNickname() + "'s input.");
        out.println("                                                             from class: " + this.callingClass);


        int i = 1;

        while (i <= GameConstant.timeToInsertInputInSeconds) {
            try {
                this.RMIVV.sendAllClient(new TimerEvent(i, GameConstant.timeToInsertInputInSeconds, "input"));
            } catch (RemoteException e) {
                logger.log(Level.WARNING,"Exception Occurred: "+e.getClass()+" "+e.getCause());
            }
            this.SVV.sendAllClient(new TimerEvent(i, GameConstant.timeToInsertInputInSeconds, "input"));
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
                i=GameConstant.timeToInsertInputInSeconds+1;
            }
        }

        out.println("                                            Thread: <SERVER-InputTimer> " + playerToAsk.getNickname() + "is AFK.");
        out.println("                                                                        from class: " + this.callingClass);
        out.println("                                                                        VCEHC at the moment is: " + ViewControllerEventHandlerContext.state.getClass());

        if(this.callingClass.getClass().toString().contains("ChooseHowToPayState")){
            ViewControllerEventHandlerContext.paymentProcess.handleAFK();
        }
        else {
            ViewControllerEventHandlerContext.state.handleAFK();
        }
    }
}
