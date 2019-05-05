package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventPlayerSetUp;

public class PlayerSetUpState implements State {

    private int numberOfPlayer;

    private int numberOfEventReceived;

    public PlayerSetUpState(){
        this.numberOfPlayer = ModelGate.model.getPlayerList().getNumberOfPlayers();
        this.numberOfEventReceived = 0;
    }
    @Override
    public void doAction(ViewControllerEvent VCE) {
        ViewControllerEventPlayerSetUp VCEPlayerSetUp = (ViewControllerEventPlayerSetUp)VCE;

        ModelGate.model.getPlayerList().getPlayers().get(numberOfEventReceived).setNickname(VCEPlayerSetUp.getNickname());
        ModelGate.model.getPlayerList().getPlayers().get(numberOfEventReceived).setColor(VCEPlayerSetUp.getColor());

        //fai pescare due power up

        numberOfEventReceived++;

        if(numberOfEventReceived < numberOfPlayer -1){ //not sure
            //ask next player to Set up
        }
        else{
            ModelGate.model.getPlayerList().setCurrentPlayingPlayer(ModelGate.model.getPlayerList().getStartingPlayer());

            //set next State
            ViewControllerEventHandlerContext.setNextState(new FirstSpawnState());

            //ask cueewntPlayingPlayer to spawn;
        }
    }
}
