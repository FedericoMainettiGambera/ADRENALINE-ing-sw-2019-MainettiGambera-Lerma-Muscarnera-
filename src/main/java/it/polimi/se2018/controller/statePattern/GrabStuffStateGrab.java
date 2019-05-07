package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.enumerations.SquareTypes;
import it.polimi.se2018.model.events.ViewControllerEvent;

public class GrabStuffStateGrab implements State {

    private int actionNumber;

    public GrabStuffStateGrab(int actionNumber){
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        if(ModelGate.model.getBoard().getSquare(ModelGate.model.getCurrentPlayingPlayer().getPosition()).getSquareType() == SquareTypes.spawnPoint){
            //ask wich Weapon card from the SpawnPoint he wants
        }
        else{
            this.drawPowerUp();
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {

    }

    public void drawPowerUp(){

    }
}
