package it.polimi.se2019.virtualView.RMIREDO;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventNickname;
import it.polimi.se2019.virtualView.RMI.RMIInterface;

import it.polimi.se2019.virtualView.VirtualView;


import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**implements rmi server*/
public class RmiVirtualView extends VirtualView implements RmiInterface{

    private PrintWriter out=new PrintWriter(System.out, true);

    private static final Logger logger= Logger.getLogger(RmiVirtualView.class.getName());

    private ViewControllerEventHandlerContext controller;

    public static Player newPlayer;

    private String name="http://AdrenalineServer:1099";

    public RmiVirtualView(ViewControllerEventHandlerContext controller){
        this.controller = controller;
    }

    /** start the rmi server*/
    public void startRMI() throws RemoteException {

        RmiInterface RMIS = new RmiVirtualView(controller);
        RmiInterface stub = (RmiInterface) UnicastRemoteObject.exportObject(RMIS, 1099);
        Registry reg = LocateRegistry.createRegistry(1099);
        reg.rebind(name, stub);

        out.println("<SERVER>: RMI SERVER running at the adress "+ name);

    }

    public void sendAllClient(Object o){
        if(ModelGate.getModel().getPlayerList()!=null && ModelGate.getModel().getPlayerList().getPlayers()!=null){
            for (Player p : ModelGate.getModel().getPlayerList().getPlayers()) {
                this.sendToClient(p,o);
            }
        }
    }

    public static void sendToClient(Player playerToSend, Object o){


            if((!playerToSend.isBot())&&(!playerToSend.isAFK())&&((playerToSend.getRmiInterface()!=null))){
                try {
                    playerToSend.getRmiInterface().send(o);
                } catch (RemoteException e) {
                   playerToSend.setAFKWIthoutNotify(true);
                }
            }
    }

    public static void sendToClientEvenAFK(Player playerToSend, Object o) {

        try {
            playerToSend.getRmiInterface().send(o);
        } catch (RemoteException e) {
            logger.log(Level.SEVERE,"EXCEPTION", e);
        }
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

                (new Thread(new RmiConnectionHandlerVirtualView(RmiVirtualView.newPlayer.getRmiInterface()))).start();

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
