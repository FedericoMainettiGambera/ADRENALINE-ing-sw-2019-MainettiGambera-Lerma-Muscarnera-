package it.polimi.se2019.model;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.statePattern.GameSetUpState;
import it.polimi.se2019.model.events.timerEvent.TimerEvent;

import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionGameCountDown implements Runnable {

    private int numberOfConnectionAtInstantiationTime;
    static final Logger logger=Logger.getLogger(ConnectionGameCountDown.class.getName());

    public ConnectionGameCountDown(int numberOfConnectionAtInstantiationTime){
        this.numberOfConnectionAtInstantiationTime = numberOfConnectionAtInstantiationTime;
    }

    @Override
    public void run() {
        System.out.println("<SERVER> Reached minimum number of players connected. Starting COUNT DOWN of " +GameConstant.countdownInSecondsForConnectionQueue + " seconds.");

        int i = 1;
        while(i<=GameConstant.countdownInSecondsForConnectionQueue) {

            try {
                ViewControllerEventHandlerContext.RMIVV.sendAllClient(new TimerEvent(i, GameConstant.countdownInSecondsForConnectionQueue, "Connection"));
            } catch (RemoteException e) {
                logger.log(Level.SEVERE, "EXCEPTION ", e);
            }
            ViewControllerEventHandlerContext.socketVV.sendAllClient(new TimerEvent(i, GameConstant.countdownInSecondsForConnectionQueue, "Connection"));

            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("                                            Thread: <SERVER-ConnectionCountDOwn> time passed: " + i + " seconds.");
            } catch (InterruptedException e) {
                logger.log(Level.WARNING, "EXCEPTION ", e);
                Thread.currentThread().interrupt();
            }
            if(ModelGate.model.getNumberOfClientsConnected() > GameConstant.maxNumberOfPlayerPerGame-1){
                System.out.println("<SERVER> max number of clients connected.");
                System.out.println("<SERVER> STARTING GAME.");
                ViewControllerEventHandlerContext.setNextState(new GameSetUpState());
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getPlayerList().getPlayers().get(0));
                return;
            }
            i++;
        }

        if((ModelGate.model.getNumberOfClientsConnected()) == this.numberOfConnectionAtInstantiationTime){
            System.out.println("<SERVER> COUNT DOWN has ended and the number of connection hasn't changed");
            System.out.println("<SERVER> STARTING GAME.");
            ViewControllerEventHandlerContext.setNextState(new GameSetUpState());
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getPlayerList().getPlayers().get(0));
        }
        else{
            System.out.println("<SERVER> COUNT DOWN has ended but the number of connection has changed");
            if(ModelGate.model.getNumberOfClientsConnected() >= GameConstant.minNumberOfPlayerPerGame){
                System.out.println("<SERVER> There are " + ModelGate.model.getNumberOfClientsConnected() + " clients connected. The Game is Playeable.");
                System.out.println("<SERVER> STARTING GAME.");
                ViewControllerEventHandlerContext.setNextState(new GameSetUpState());
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getPlayerList().getPlayers().get(0));
            }
            else{
                System.out.println("<SERVER> There are " + ModelGate.model.getNumberOfClientsConnected() + " clients connected.");
                System.out.println("<SERVER> NOT ENOUGHT CLIENTS.");

            }
        }
    }
}
