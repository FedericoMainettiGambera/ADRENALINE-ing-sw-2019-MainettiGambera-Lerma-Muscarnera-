package it.polimi.se2019.controller.statePattern;

import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;

/**state interface, offers three methods*/
public interface State {

    /**@param playerToAsk the player to be asked the input*/
     void askForInput(Player playerToAsk);
     /**@param viewControllerEvent the event with information from the user as response to the askForInput*/
     void doAction(ViewControllerEvent viewControllerEvent);
     /**method implemented to set player AFKs under given circumstances*/
     void handleAFK();
}
