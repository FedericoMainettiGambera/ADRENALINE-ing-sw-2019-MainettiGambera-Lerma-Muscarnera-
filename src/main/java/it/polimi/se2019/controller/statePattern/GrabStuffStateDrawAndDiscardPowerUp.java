package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.ViewControllerEvent;
import it.polimi.se2019.model.events.ViewControllerEventString;

public class GrabStuffStateDrawAndDiscardPowerUp implements State {

    private int actionNumber;

    public GrabStuffStateDrawAndDiscardPowerUp(int actionNumber){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        //ask which power up to discard
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        ViewControllerEventString VCEString = (ViewControllerEventString)VCE;

        //discard power up
        ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().moveCardTo(
                ModelGate.model.getPowerUpDiscardPile(),
                VCEString.getInput()
        );

        //continue with normal grab of ammo and power up
        ViewControllerEventHandlerContext.setNextState(new GrabStuffStateDrawPowerUp(this.actionNumber));
        ViewControllerEventHandlerContext.state.doAction(null);
    }
}
