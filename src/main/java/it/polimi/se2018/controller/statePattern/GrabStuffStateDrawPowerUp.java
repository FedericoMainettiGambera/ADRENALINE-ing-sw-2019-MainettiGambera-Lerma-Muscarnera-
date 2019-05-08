package it.polimi.se2018.controller.statePattern;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.events.ViewControllerEvent;

public class GrabStuffStateDrawPowerUp implements State {
    private int actionNumber;

    public GrabStuffStateDrawPowerUp(int actionNumber){
        this.actionNumber = actionNumber;
    }

    @Override
    public void askForInput(Player playerToAsk) {
        //empty
    }

    @Override
    public void doAction(ViewControllerEvent VCE) {
        Position PlayerPosition = ModelGate.model.getCurrentPlayingPlayer().getPosition();
        OrderedCardList<AmmoCard> squareCards = ((NormalSquare)ModelGate.model.getBoard().getSquare(PlayerPosition)).getAmmoCards();
        AmmoCard ammoCard = squareCards.getFirstCard();
        ModelGate.model.getCurrentPlayingPlayer().addAmmoCubes(ammoCard.getAmmunitions());

        OrderedCardList<PowerUpCard> playerPowerUpHand = ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand();
        if(ammoCard.isPowerUp()){
            squareCards.moveAllCardsTo(ModelGate.model.getCurrentPlayingPlayer().getPowerUpCardsInHand());
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
