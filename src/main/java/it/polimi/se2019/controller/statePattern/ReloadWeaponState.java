package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventString;

import java.util.ArrayList;

public class ReloadWeaponState implements State {

    public  ReloadWeaponState(){
        System.out.println("<SERVER> New state: " + this.getClass());
    }
    @Override
    public void askForInput(Player playerToAsk){
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        //ask which weapon to reload
        ArrayList<WeaponCard> toReaload = new ArrayList<>();
        for (WeaponCard wc: playerToAsk.getWeaponCardsInHand().getCards()) {
            if (!wc.isLoaded()){
                toReaload.add(wc);
            }
        }
        String toPrintln = "";
        for (int i = 0; i < toReaload.size() ; i++) {
            toPrintln += "[" + toReaload.get(i).getID() + "]  ";
        }
        System.out.println("<SERVER> Possible weapons that can be reloaded: " + toPrintln);


        if(ViewControllerEventHandlerContext.networkConnection.equals("SOCKET")) {
            SelectorGate.selectorSocket.setPlayerToAsk(playerToAsk);
            SelectorGate.selectorSocket.askWhatReaload(toReaload);
        }
        else{
            SelectorGate.selectorRMI.setPlayerToAsk(playerToAsk);
            SelectorGate.selectorRMI.askGameSetUp();
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE){
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventString VCEString=(ViewControllerEventString) VCE;

        System.out.println("<SERVER> Reloading and paying reload cost for weapon card: " + VCEString.getInput());
        ModelGate.model.getCurrentPlayingPlayer().payAmmoCubes(
                ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCard(VCEString.getInput()).getReloadCost()
                );
        ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCard(VCEString.getInput()).reload();

        ViewControllerEventHandlerContext.setNextState(new ReloadState());
        ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
    }
}
