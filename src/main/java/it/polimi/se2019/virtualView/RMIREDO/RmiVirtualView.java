package it.polimi.se2019.virtualView.RMIREDO;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventNickname;
import it.polimi.se2019.virtualView.Socket.ConnectionHandlerVirtualView;
import it.polimi.se2019.virtualView.VirtualView;

import java.util.Observable;

public class RmiVirtualView extends VirtualView implements RmiInterface{

    private ViewControllerEventHandlerContext controller;

    public static Player newPlayer;

    public RmiVirtualView(ViewControllerEventHandlerContext controller){
        this.controller = controller;
    }

    public void startRMI(){
        //TODO
        //fa partire l'Rmi, fa tutte le cose che deve...
    }

    public void sendAllClient(Object o){
        if(ModelGate.model.getPlayerList()!=null && ModelGate.model.getPlayerList().getPlayers()!=null){
            for (Player p : ModelGate.model.getPlayerList().getPlayers()) {
                this.sendToClient(p,o);
            }
        }
    }

    public static void sendToClient(Player playerToSend, Object o){
        //TODO
        //extract all the information needed to perform the send to client from the "playerToSend"
        //send to player "playerToSend" the object "o", but don't send him the event if he is AFK or BOT
        //esempio di funzionamento:
        //      if(!playerToSend.isAFK() && playerToSend."getInterfacciaDelClient()"!=null && !playerToSend.isBot()) {
        //          playerToSend."getInterfacciaDelClient()".send(o);
        //      }
    }

    public static void sendToClientEvenAFK(Player playerToSend, Object o) {
        //TODO
        //extract all the information needed to perform the send to client from the "playerToSend"
        //send to player "playerToSend" the object "o", without checking for BOT or AFK
        //esempio di funzionamento:
        //      playerToSend."getInterfacciaDelClient()".send(o);
    }

        /**
         * this update is called from the notify of the Model, it sends MVEs to all clients*/
    @Override
    public void update(Observable o, Object arg){
        //send to all client the object arg
        sendAllClient(arg);
    }

    public static void setNewPlayer(Player p){
        newPlayer = p;
    }


    @Override
    public void send(Object o){
        if(o.getClass().toString().contains("ViewControllerEventNickname")){
            //case of NewConnection
            boolean validNickname = RmiConnectionHandlerVirtualView.handleNewConnectionNickname((ViewControllerEventNickname)o);
            if(!validNickname){
                //restart the Connection process
                //      //TODO
                //      (new Thread(new RmiConnectionHandlerVirtualView(RmiVirtualView.newPlayer."getInterfacciaDelClinet()"))).start();

            }
        }
        else{
            //default case:
            //fa partire un thread: RmiClientListenerVirtualView
            (new Thread(new RmiClientListenerVirtualView(o, this.controller))).start();
        }
    }

    @Override
    public void connect(RmiInterface client) {
        //fa partire un thread: RmiCOnnectionHandlerVirtualView
        (new Thread(new RmiConnectionHandlerVirtualView(client))).start();
    }
}
