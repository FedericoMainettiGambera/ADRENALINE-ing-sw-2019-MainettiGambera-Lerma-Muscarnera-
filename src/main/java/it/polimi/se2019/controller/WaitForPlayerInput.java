package it.polimi.se2019.controller;

import it.polimi.se2019.model.GameConstant;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.timerEvent.TimerEvent;
import it.polimi.se2019.virtualView.RMIREDO.RmiVirtualView;
import it.polimi.se2019.virtualView.Socket.SocketVirtualView;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**implements the timer
 * @author LudoLerma
 *@author FedericoMainettiGambera*/
public class WaitForPlayerInput implements Runnable{

    private static PrintWriter out= new PrintWriter(System.out, true);


    /**attribute that contain the player the input is expected from*/
    private Player playerToAsk;

    /**a random ID for the thread launched that implements a count down till AFK STATUS*/
    private int randomID;

    /**the class that called the waitForInput*/
    private String callingClass;
    /**reference to the socket server*/
    private SocketVirtualView socketVirtualView = ViewControllerEventHandlerContext.getSocketVV();
    /**reference to rmi server*/
    private RmiVirtualView rmiVirtualView = ViewControllerEventHandlerContext.getRmiVirtualView();
    /**what to be asked*/
    private String ask;
    /**constructor,
     * @param p to initialize playerToAsk attribute
     * @param ask to initialize ask attribute
     * @param callingClass to initialize callingClass attribute*/
    public WaitForPlayerInput(Player p, String callingClass, String ask){
        this.playerToAsk = p;
        this.callingClass = callingClass;
        Random rand = new Random();
        this.randomID = rand.nextInt(50000);
        this.ask=ask;
        ViewControllerEventHandlerContext.addElementTOStackOfStatesAndTimers(this, "new timer started with id (" + this.randomID + ") for class (" + this.callingClass + ")" + "for player (" + this.playerToAsk.getNickname() + ")");
    }

    /**prints the count down with the initialized parameters to context  it*/
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

        ViewControllerEventHandlerContext.printStackOfStatesAndTImers();
        if(this.callingClass.getClass().toString().contains("ChooseHowToPayState")){
            ViewControllerEventHandlerContext.getPaymentProcess().handleAFK();
        }
        else {
            ViewControllerEventHandlerContext.getState().handleAFK();
        }
    }
}
