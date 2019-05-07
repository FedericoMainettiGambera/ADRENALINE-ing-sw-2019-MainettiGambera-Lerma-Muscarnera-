package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventString;

public class GrabStuffState implements State {

    private int actionNumber;

    public GrabStuffState(int actionNumber){
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        //ask for input
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        ViewControllerEventString VCEString = (ViewControllerEventString)VCE;
        String choice = VCEString.getInput();

        if(choice.equals("move")){

        }
        else{
            //if(choice.equals("grab")

        }
    }
}
