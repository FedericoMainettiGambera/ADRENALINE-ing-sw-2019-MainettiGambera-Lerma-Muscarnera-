package it.polimi.se2019.controller;

import it.polimi.se2019.virtualView.Socket.VirtualViewSelectorSocket;

public class SelectorGate {

    public static VirtualViewSelectorSocket selectorSocket = new VirtualViewSelectorSocket();

    public static RMIVirtualViewSelector selectorRMI = new RMIVirtualVIewSelector();

}
