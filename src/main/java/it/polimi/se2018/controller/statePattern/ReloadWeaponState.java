package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventString;

public class ReloadWeaponState implements State {
    @Override
    public void askForInput(Player playerToAsk){
        //ask which weapon to reload
    }

    @Override
    public void doAction(ViewControllerEvent VCE){
        ViewControllerEventString VCEString=(ViewControllerEventString) VCE;

        ModelGate.model.getCurrentPlayingPlayer().payAmmoCubes(
                ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCard(VCEString.getInput()).getReloadCost()
                );
        ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCard(VCEString.getInput()).reload();

        ViewControllerEventHandlerContext.setNextState(new ReloadState());
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }
}
