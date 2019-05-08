package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.ViewControllerEvent;

public class GrabStuffStateGrabWeapon implements  State {

    private int actionNumber;

    public GrabStuffStateGrabWeapon(int actionNumber){
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {

    }

    @Override
    public void doAction(ViewControllerEvent VCE) {

    }
}
