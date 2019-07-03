package it.polimi.se2019.model;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.statePattern.GameSetUpState;
import it.polimi.se2019.model.events.timerEvent.TimerEvent;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionGameCountDown implements Runnable {

   /**the number of connection when the game is instanced*/
    private int numberOfConnectionAtInstantiationTime;
    /**a logger for this class*/
    private static final Logger logger=Logger.getLogger(ConnectionGameCountDown.class.getName());

    /**@param numberOfConnectionAtInstantiationTime, initialize numberOfConnectionAtInstantiationTime attribute*/
     ConnectionGameCountDown(int numberOfConnectionAtInstantiationTime){
        this.numberOfConnectionAtInstantiationTime = numberOfConnectionAtInstantiationTime;
    }

    /**this thread sends all the clients connected so far the count down till the game to begin,
     * it will begin, although, only if a minimum number of clients is reached,
     * the count down will stop ealier if the maximum number of player is reached */
    @Override
    public void run() {
        String string="<SERVER> STARTING GAME.";
        System.out.println("<SERVER> Reached minimum number of players connected. Starting COUNT DOWN of " +GameConstant.COUNTDOWN_IN_SECONDS_FOR_CONNECTION_QUEUE + " seconds.");

        int i = 1;
        while(i<=GameConstant.COUNTDOWN_IN_SECONDS_FOR_CONNECTION_QUEUE) {

            if(ViewControllerEventHandlerContext.getRmiVirtualView()!=null){
                ViewControllerEventHandlerContext.getRmiVirtualView().sendAllClient(new TimerEvent(i, GameConstant.COUNTDOWN_IN_SECONDS_FOR_CONNECTION_QUEUE, "Connection"));
            }
            if(ViewControllerEventHandlerContext.getSocketVV()!=null) {
                ViewControllerEventHandlerContext.getSocketVV().sendAllClient(new TimerEvent(i, GameConstant.COUNTDOWN_IN_SECONDS_FOR_CONNECTION_QUEUE, "Connection"));
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
                ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getPlayerList().getPlayers().get(0));
                return;
            }
            i++;
        }

        if((ModelGate.getModel().getNumberOfClientsConnected()) == this.numberOfConnectionAtInstantiationTime){
            System.out.println("<SERVER> COUNT DOWN has ended and the number of connection hasn't changed");
            System.out.println(string);
            ViewControllerEventHandlerContext.setNextState(new GameSetUpState());
            ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getPlayerList().getPlayers().get(0));
        }
        else{
            System.out.println("<SERVER> COUNT DOWN has ended but the number of connection has changed");
            if(ModelGate.getModel().getNumberOfClientsConnected() >= GameConstant.MIN_NUMBER_OF_PLAYER_PER_GAME){
                System.out.println("<SERVER> There are " + ModelGate.getModel().getNumberOfClientsConnected() + " clients connected. The Game is Playeable.");
                System.out.println(string);
                ViewControllerEventHandlerContext.setNextState(new GameSetUpState());
                ViewControllerEventHandlerContext.getState().askForInput(ModelGate.getModel().getPlayerList().getPlayers().get(0));
            }
            else{
                System.out.println("<SERVER> There are " + ModelGate.getModel().getNumberOfClientsConnected() + " clients connected.");
                System.out.println("<SERVER> NOT ENOUGHT CLIENTS.");

            }
        }
    }
}
