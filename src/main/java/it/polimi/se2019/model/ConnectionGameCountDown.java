package it.polimi.se2019.model;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.statePattern.GameSetUpState;

import java.util.concurrent.TimeUnit;

public class ConnectionGameCountDown implements Runnable {

    private int numberOfConnectionAtInstantiationTime;

    public ConnectionGameCountDown(int numberOfConnectionAtInstantiationTime){
        this.numberOfConnectionAtInstantiationTime = numberOfConnectionAtInstantiationTime;
    }

    @Override
    public void run() {
        try {
            System.out.println("<SERVER> Reached minimum number of players connected. Starting COUNT DOWN of " +GameConstant.countdownInSeconds+ " seconds.");
            TimeUnit.SECONDS.sleep(GameConstant.countdownInSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if((ModelGate.model.getNumberOfClientsConnected()) == this.numberOfConnectionAtInstantiationTime){
            System.out.println("<SERVER> COUNT DOWN has ended and nobody has connected.");
            System.out.println("<SERVER> STARTING GAME.");
            ViewControllerEventHandlerContext.setNextState(new GameSetUpState());
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getPlayerList().getPlayer("User1"));
        }
        else{
            System.out.println("<SERVER> COUNT DOWN aborted: number of players connected has changed.");
        }
    }
}
