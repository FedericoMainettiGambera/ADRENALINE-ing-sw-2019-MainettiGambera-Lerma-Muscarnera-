package it.polimi.se2019.controller;

import it.polimi.se2019.model.Player;
import it.polimi.se2019.virtualView.RMIREDO.VirtualViewSelectorRmi;
import it.polimi.se2019.virtualView.Socket.VirtualViewSelectorSocket;
import it.polimi.se2019.virtualView.VirtualViewSelector;

public class SelectorGate {

    public static VirtualViewSelectorSocket selectorSocket = new VirtualViewSelectorSocket();

    public static VirtualViewSelectorRmi selectorRmi = new VirtualViewSelectorRmi();

    public static VirtualViewSelector getCorrectSelectorFor(Player p) throws Exception {
        if(p.getOos()!=null){
            return selectorSocket;
        }
        else if(p.getRmiInterface()!=null){
            return selectorRmi;
        }
        throw new Exception("the player " + p.getNickname() + " network method is unknown to the server.");
    }

}
