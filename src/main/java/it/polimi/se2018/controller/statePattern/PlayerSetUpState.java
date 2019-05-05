package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventPlayerSetUp;

public class PlayerSetUpState implements State {

    private int numberOfPlayer;

    private int numberOfEventReceived;

    PlayerSetUpState(){
        this.numberOfPlayer = ModelGate.model.getPlayerList().getNumberOfPlayers();
        this.numberOfEventReceived = 0;
    }
    @Override
    public void doAction(ViewControllerEvent VCE) {
        ViewControllerEventPlayerSetUp VCEPlayerSetUp = (ViewControllerEventPlayerSetUp)VCE;
        if(numberOfEventReceived < numberOfPlayer){
            ModelGate.model.getPlayerList().getPlayers().get(numberOfEventReceived).setNickname(VCEPlayerSetUp.getNickname());
            ModelGate.model.getPlayerList().getPlayers().get(numberOfEventReceived).setColor(VCEPlayerSetUp.getColor());

        }
        else{
            //set next State
        }
    }
}
