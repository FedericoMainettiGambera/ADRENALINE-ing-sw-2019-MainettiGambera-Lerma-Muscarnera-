package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.ViewControllerEvent;

public class ShootPeopleState implements State {

    private int actionNumber;

    public ShootPeopleState(int actionNumber){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        //ask for input
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {


        /*
        //can make the game became in FinalFrenzy mode
        if(ModelGate.model.getFinalFrenzy() && ModelGate.model.getKillshotTrack().areSkullsOver()){
            ViewControllerEventHandlerContext.setNextState(new FinalFrenzySetUpState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
        //can make the game end
        else if(ModelGate.model.getKillshotTrack().areSkullsOver()){
            ViewControllerEventHandlerContext.setNextState(new FinalScoringState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
        */
    }
}
