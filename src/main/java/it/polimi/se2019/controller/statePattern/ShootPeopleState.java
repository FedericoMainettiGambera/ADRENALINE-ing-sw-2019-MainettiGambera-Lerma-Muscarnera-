package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

import java.util.ArrayList;

public class ShootPeopleState implements State {

    private int actionNumber;

    public ShootPeopleState(int actionNumber){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        System.out.println("<SERVER> (" + this.getClass() + ") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        if(canShoot()) {
//ask for input
            if(ViewControllerEventHandlerContext.networkConnection.equals("SOCKET")) {
                SelectorGate.selectorSocket.setPlayerToAsk(playerToAsk);
                SelectorGate.selectorSocket.askShootOrMove();
            }
            else{
                SelectorGate.selectorRMI.setPlayerToAsk(playerToAsk);
                SelectorGate.selectorRMI.askShootOrMove();
            }        }
        else{
            System.out.println("<SERVER> Player can't shoot");
            ViewControllerEventHandlerContext.setNextState(new TurnState(this.actionNumber));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        if(canShoot()){
            //set State
            ViewControllerEventHandlerContext.setNextState(new TurnState(this.actionNumber));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
            //TurnState
            //ReloadState
        }  //(based on actionNumber)
    }

    public ArrayList<WeaponCard> LoadedWep(){
        ArrayList<WeaponCard> UsableWep=new ArrayList<>();

        for (WeaponCard card:ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCards()) {

          if(card.isLoaded()){
              UsableWep.add(card);
          }

        }
        return UsableWep;
    }

    public boolean canShoot(){

        if(LoadedWep().isEmpty()){
            return false;
        }
        else return true;
    }
}
