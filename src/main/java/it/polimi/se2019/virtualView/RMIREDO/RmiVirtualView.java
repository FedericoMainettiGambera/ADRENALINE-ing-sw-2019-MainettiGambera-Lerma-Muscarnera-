package it.polimi.se2019.virtualView.RMIREDO;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.virtualView.VirtualView;

import java.util.Observable;

public class RmiVirtualView extends VirtualView implements RmiInterface{

    // costruttore, inizializza l rmi
    public RmiVirtualView(ViewControllerEventHandlerContext controller){
    }

    //start rmi
    //      deve inizializzare rmi connection handler

    //metodo sendallclient
    public void sendAllClient(Object o){
        if(ModelGate.model.getPlayerList()!=null && ModelGate.model.getPlayerList().getPlayers()!=null){
            for (Player p : ModelGate.model.getPlayerList().getPlayers()) {
                sendToClient(p,o);
            }
        }
    }

    //metodo sendclient
    public void sendToClient(Player playerToSend, Object o){
        //extract all the information needed to perform the send to client from the "playerToSend"
        //send to player "playerToSend" the object "o"
    }

    @Override
    public void update(Observable o, Object arg){
        //used  for MVE
        //send to all client the object arg
    }


    @Override
    public void send(Object o){
        //fai partire il thread del clientlistenerrmi

    }

    @Override
    public void connect(RmiInterface client) {
        //fai partire il thread del connection handler rmi

    }
}
