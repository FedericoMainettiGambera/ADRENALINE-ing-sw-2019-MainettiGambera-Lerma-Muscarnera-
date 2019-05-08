package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.WeaponCard;
import it.polimi.se2018.model.events.ViewControllerEvent;
import it.polimi.se2018.model.events.ViewControllerEventBoolean;
import it.polimi.se2018.model.events.ViewControllerEventString;

public class ReloadState implements State{
    @Override
    public void askForInput(Player playerToAsk){
        if(canReload()){
            //ask if they want to reload
        }
        else{
            ViewControllerEventHandlerContext.setNextState(new PassTurnState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }

    }

    @Override
    public void doAction(ViewControllerEvent VCE){
        ViewControllerEventBoolean VCEBoolean=(ViewControllerEventBoolean)VCE;
        if(VCEBoolean.getInput()){
            ViewControllerEventHandlerContext.setNextState(new ReloadWeaponState());
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else{
            ViewControllerEventHandlerContext.setNextState(new PassTurnState());
            ViewControllerEventHandlerContext.state.doAction(null);
        }
    }

    public boolean canReload(){

        for (WeaponCard weaponCard : ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCards()){
            if(ModelGate.model.getCurrentPlayingPlayer().canPayAmmoCubes(weaponCard.getReloadCost())){
                return true;
            }
        }

        return false;
    }
}
