package it.polimi.se2019.model;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.statePattern.GameSetUpState;
import it.polimi.se2019.model.events.timerEvent.TimerEvent;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionGameCountDown implements Runnable {


    private int numberOfConnectionAtInstantiationTime;
    private static final Logger logger=Logger.getLogger(ConnectionGameCountDown.class.getName());

    public ConnectionGameCountDown(int numberOfConnectionAtInstantiationTime){
        this.numberOfConnectionAtInstantiationTime = numberOfConnectionAtInstantiationTime;
    }

    @Override
    public void run() {
        String string="<SERVER> STARTING GAME.";
        System.out.println("<SERVER> Reached minimum number of players connected. Starting COUNT DOWN of " +GameConstant.COUNTDOWN_IN_SECONDS_FOR_CONNECTION_QUEUE + " seconds.");

        int i = 1;
        while(i<=GameConstant.COUNTDOWN_IN_SECONDS_FOR_CONNECTION_QUEUE) {

            if(ViewControllerEventHandlerContext.RMIVV!=null){
                ViewControllerEventHandlerContext.RMIVV.sendAllClient(new TimerEvent(i, GameConstant.COUNTDOWN_IN_SECONDS_FOR_CONNECTION_QUEUE, "Connection"));
            }
            if(ViewControllerEventHandlerContext.socketVV!=null) {
                ViewControllerEventHandlerContext.socketVV.sendAllClient(new TimerEvent(i, GameConstant.COUNTDOWN_IN_SECONDS_FOR_CONNECTION_QUEUE, "Connection"));
            }
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("                                            Thread: <SERVER-ConnectionCountDOwn> time passed: " + i + " seconds.");
            } catch (InterruptedException e) {
                logger.log(Level.WARNING, "EXCEPTION ", e);
                Thread.currentThread().interrupt();
            }
            if(ModelGate.getModel().getNumberOfClientsConnected() > GameConstant.MAX_NUMBER_OF_PLAYER_PER_GAME -1){
                System.out.println("<SERVER> max number of clients connected.");
                System.out.println(string);
                ViewControllerEventHandlerContext.setNextState(new GameSetUpState());
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.getModel().getPlayerList().getPlayers().get(0));
                return;
            }
            i++;
        }

        if((ModelGate.getModel().getNumberOfClientsConnected()) == this.numberOfConnectionAtInstantiationTime){
            System.out.println("<SERVER> COUNT DOWN has ended and the number of connection hasn't changed");
            System.out.println(string);
            ViewControllerEventHandlerContext.setNextState(new GameSetUpState());
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.getModel().getPlayerList().getPlayers().get(0));
        }
        else{
            System.out.println("<SERVER> COUNT DOWN has ended but the number of connection has changed");
            if(ModelGate.getModel().getNumberOfClientsConnected() >= GameConstant.MIN_NUMBER_OF_PLAYER_PER_GAME){
                System.out.println("<SERVER> There are " + ModelGate.getModel().getNumberOfClientsConnected() + " clients connected. The Game is Playeable.");
                System.out.println(string);
                ViewControllerEventHandlerContext.setNextState(new GameSetUpState());
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.getModel().getPlayerList().getPlayers().get(0));
            }
            else{
                System.out.println("<SERVER> There are " + ModelGate.getModel().getNumberOfClientsConnected() + " clients connected.");
                System.out.println("<SERVER> NOT ENOUGHT CLIENTS.");

            }
        }
    }
}
