package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.WeaponCard;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

public class ShootPeopleChooseWepState implements State {
    @Override
    public void askForInput(Player playerToAsk){


        for (WeaponCard card : ModelGate.model.getCurrentPlayingPlayer().getWeaponCardsInHand().getCards()){

            card.getEffects();

        }

    }

    @Override
    public void doAction(ViewControllerEvent VCE){

    }

    @Override
    public void handleAFK() {
        System.out.println("<SERVER> ("+ this.getClass() +") Handling AFK Player.");
    }
}
