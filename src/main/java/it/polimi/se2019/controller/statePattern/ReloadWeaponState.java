package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.ViewControllerEvent;
import it.polimi.se2019.model.events.ViewControllerEventString;

public class ReloadWeaponState implements State {

    public  ReloadWeaponState(){
        System.out.println("<SERVER> New state: " + this.getClass());
    }
    @Override
    public void askForInput(Player playerToAsk){
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //ask which weapon to reload
    }

    @Override
    public void doAction(ViewControllerEvent VCE){
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventString VCEString=(ViewControllerEventString) VCE;

        ModelGate.model.getCurrentPlayingPlayer().payAmmoCubes(
                ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCard(VCEString.getInput()).getReloadCost()
                );
        ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCard(VCEString.getInput()).reload();

        ViewControllerEventHandlerContext.setNextState(new ReloadState());
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }
}
