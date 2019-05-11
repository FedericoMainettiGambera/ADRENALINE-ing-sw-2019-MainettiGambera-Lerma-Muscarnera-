package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.*;
import it.polimi.se2019.model.events.ViewControllerEvent;

public class GrabStuffStateDrawPowerUp implements State {
    private int actionNumber;

    public GrabStuffStateDrawPowerUp(int actionNumber){
        System.out.println("<SERVER> New state: " + this.getClass());
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        System.out.println("<SERVER> ("+ this.getClass() +") Asking input to Player \"" + playerToAsk.getNickname() + "\"");
        //empty
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        System.out.println("<SERVER> "+ this.getClass() +".doAction();");

        Position PlayerPosition = ModelGate.model.getCurrentPlayingPlayer().getPosition();
        OrderedCardList<AmmoCard> squareCards = ((NormalSquare)ModelGate.model.getBoard().getSquare(PlayerPosition)).getAmmoCards();
        AmmoCard ammoCard = squareCards.getFirstCard();
        ModelGate.model.getCurrentPlayingPlayer().addAmmoCubes(ammoCard.getAmmunitions());

        OrderedCardList<PowerUpCard> playerPowerUpHand = ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand();
        if(ammoCard.isPowerUp()){
            ModelGate.model.getPowerUpDeck().moveCardTo(
                    ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand(),
                    ModelGate.model.getPowerUpDeck().getFirstCard().getID()
            );
        }

        //set next state
        if(this.actionNumber == 1){
            ViewControllerEventHandlerContext.setNextState(new TurnState(2));
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
        if(this.actionNumber == 2) {
            ViewControllerEventHandlerContext.setNextState(new ReloadState());
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getCurrentPlayingPlayer());
        }
    }
}
