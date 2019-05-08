package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventString;

public class GrabStuffStateGrabAmmoAndDiscardPowerUp implements State {

    private int actionNumber;

    public GrabStuffStateGrabAmmoAndDiscardPowerUp(int actionNumber){
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
        ViewControllerEventHandlerContext.setNextState(new GrabStuffStateGrabAmmo(this.actionNumber));
        ViewControllerEventHandlerContext.state.doAction(null);
    }
}
