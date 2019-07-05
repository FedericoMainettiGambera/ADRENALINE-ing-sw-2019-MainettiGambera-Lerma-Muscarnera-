package it.polimi.se2019.controller;

import it.polimi.se2019.model.Player;
import it.polimi.se2019.virtualView.RMIREDO.VirtualViewSelectorRmi;
import it.polimi.se2019.virtualView.Socket.VirtualViewSelectorSocket;
import it.polimi.se2019.virtualView.VirtualViewSelector;

/**this class implements method to get the right way to communicate with the client
 *
 * /**starts the server
 *  * @author LudoLerma
 *  * @author FedericoMainettiGambera
 *  * */
public class SelectorGate {

    private SelectorGate(){
        //not to initialize
    }

    /**instance a new selector for socket connections*/
     private static VirtualViewSelectorSocket selectorSocket = new VirtualViewSelectorSocket();
       /**instance a new selector for rmi connections*/
    private static VirtualViewSelectorRmi selectorRmi = new VirtualViewSelectorRmi();
    /**@param p  the player to be asked some input
     * @return the right selector for that player*/
    public static VirtualViewSelector getCorrectSelectorFor(Player p){
        ViewControllerEventHandlerContext.addElementTOStackOfStatesAndTimers(new SelectorGate(), "requestes virtualViewSelector to ask something to a player");
        if(p.getOos()!=null){
            return selectorSocket;
        }
        else if(p.getRmiInterface()!=null){
            return selectorRmi;
        }
        throw new IllegalStateException("the player " + p.getNickname() + " network method is unknown to the server.");
    }

}
