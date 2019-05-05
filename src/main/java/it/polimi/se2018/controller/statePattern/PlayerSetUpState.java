package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.model.events.ViewControllerEvent;

public class PlayerSetUpState implements State {

    private int numberOfPlayer;

    private int numberOfEventReceived;

    PlayerSetUpState(){
        this.numberOfPlayer = ModelGate.model.getPlayerList().getNumberOfPlayers();
        this.numberOfEventReceived = 0;
    }
    @Override
    public void doAction(ViewControllerEvent VCE) {
        if(numberOfEventReceived < numberOfPlayer){

        }
        else{
            //set next State
        }
    }
}
