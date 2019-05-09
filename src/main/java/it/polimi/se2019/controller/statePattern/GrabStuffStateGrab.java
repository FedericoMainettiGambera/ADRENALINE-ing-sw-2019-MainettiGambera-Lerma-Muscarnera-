package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.AmmoCard;
import it.polimi.se2019.model.NormalSquare;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.enumerations.SquareTypes;
import it.polimi.se2019.model.events.ViewControllerEvent;

public class GrabStuffStateGrab implements State {

    private int actionNumber;

    public GrabStuffStateGrab(int actionNumber){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");

        if(ModelGate.model.getBoard().getSquare(ModelGate.model.getCurrentPlayingPlayer().getPosition()).getSquareType() == SquareTypes.spawnPoint){
            ViewControllerEventHandlerContext.setNextState(new GrabStuffStateGrabWeapon(this.actionNumber));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        else if(ModelGate.model.getBoard().getSquare(ModelGate.model.getCurrentPlayingPlayer().getPosition()).getSquareType() == SquareTypes.normal){
            AmmoCard ammoCard = (AmmoCard)((NormalSquare)ModelGate.model.getBoard().getSquare(
                    ModelGate.model.getCurrentPlayingPlayer().getPosition())
            ).getAmmoCards().getFirstCard();

            //draw ammocubes
            ModelGate.model.getCurrentPlayingPlayer().addAmmoCubes(ammoCard.getAmmunitions());

            //set next state
            if((ammoCard.isPowerUp())&&(ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand().getCards().size() >=2)){
                ViewControllerEventHandlerContext.setNextState(new GrabStuffStateDrawAndDiscardPowerUp(this.actionNumber));
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
            }
            else if(ammoCard.isPowerUp()){
                ViewControllerEventHandlerContext.setNextState(new GrabStuffStateDrawPowerUp(this.actionNumber));
                ViewControllerEventHandlerContext.state.doAction(null);
            }
            else{
                if(actionNumber==1){
                    ViewControllerEventHandlerContext.setNextState(new TurnState(2));
                }
                else if(actionNumber==2){
                    ViewControllerEventHandlerContext.setNextState(new ReloadState());

                }
                ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
            }
        }

    }

    @Override
    public void doAction(ViewControllerEvent VCE) {

    }
}
