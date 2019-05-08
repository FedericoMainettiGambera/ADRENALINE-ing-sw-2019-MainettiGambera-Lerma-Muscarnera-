package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.ViewControllerEvent;

public class ShootPeopleState implements State {

    private int actionNumber;

    public ShootPeopleState(int actionNumber){
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        //ask for input
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {

    }
}
