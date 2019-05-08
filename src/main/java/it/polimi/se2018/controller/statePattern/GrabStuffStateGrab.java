package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
import it.polimi.se2018.model.AmmoCard;
import it.polimi.se2018.model.NormalSquare;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.enumerations.SquareTypes;
import it.polimi.se2018.model.events.ViewControllerEvent;

public class GrabStuffStateGrab implements State {

    private int actionNumber;

    public GrabStuffStateGrab(int actionNumber){
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {

        if(ModelGate.model.getBoard().getSquare(ModelGate.model.getCurrentPlayingPlayer().getPosition()).getSquareType() == SquareTypes.spawnPoint){
            ViewControllerEventHandlerContext.setNextState(new GrabStuffStateGrabWeapon(this.actionNumber));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else if(ModelGate.model.getBoard().getSquare(ModelGate.model.getCurrentPlayingPlayer().getPosition()).getSquareType() == SquareTypes.normal){
            AmmoCard ammoCard = (AmmoCard)((NormalSquare)ModelGate.model.getBoard().getSquare(
                    ModelGate.model.getCurrentPlayingPlayer().getPosition())
                    ).getAmmoCards().getFirstCard();

            if((ammoCard.isPowerUp())&&(ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCards().size() >= 2)){
                ViewControllerEventHandlerContext.setNextState(new GrabStuffStateGrabAmmoAndDiscardPowerUp(this.actionNumber));
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
            }
            else{
                ViewControllerEventHandlerContext.setNextState(new GrabStuffStateGrabAmmo(this.actionNumber));
                ViewControllerEventHandlerContext.state.doAction(null);
            }
        }

    }

    @Override
    public void doAction(ViewControllerEvent VCE) {

    }
}
