package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.events.ViewControllerEvent;
import it.polimi.se2019.model.events.ViewControllerEventBoolean;

public class ReloadState implements State{

    public ReloadState(){
        System.out.println("<SERVER> New state: " + this.getClass());
    }
    @Override
    public void askForInput(Player playerToAsk){
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        if(canReload()){
            //ask if they want to reload
            SelectorGate.selector.setPlayerToAsk(playerToAsk);
            SelectorGate.selector.askIfReload();
        }
        else{
            System.out.println("<SERVER> Player can't reload anymore");
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }

    }

    @Override
    public void doAction(ViewControllerEvent VCE){
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        ViewControllerEventBoolean VCEBoolean=(ViewControllerEventBoolean)VCE;

        if(VCEBoolean.getInput()){
            System.out.println("<SERVER>Player decided to reload.");
            ViewControllerEventHandlerContext.setNextState(new ReloadWeaponState());
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else{
            System.out.println("<SERVER>Player decided not to reload.");
            ModelGate.model.getPlayerList().setNextPlayingPlayer();
            ViewControllerEventHandlerContext.setNextState(new ScoreKillsState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
    }

    public boolean canReload(){

        for (WeaponCard weaponCard : ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCards()){
            if((ModelGate.model.getCurrentPlayingPlayer().canPayAmmoCubes(weaponCard.getReloadCost())) && (!weaponCard.isLoaded())){
                return true;
            }
        }

        return false;
    }
}
