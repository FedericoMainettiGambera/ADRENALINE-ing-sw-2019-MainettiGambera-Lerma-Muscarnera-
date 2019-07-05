package it.polimi.se2019.virtualView.RMIREDO;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventNickname;

import it.polimi.se2019.virtualView.VirtualView;


import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**implements rmi server
 * @author LudoLerma
 * @author Federico Mainetti Gambera
 * */
public class RmiVirtualView extends VirtualView implements RmiInterface{

    private PrintWriter out=new PrintWriter(System.out, true);

    private static final Logger logger= Logger.getLogger(RmiVirtualView.class.getName());

    /**handle the controller*/
    private ViewControllerEventHandlerContext controller;

    /**new player created*/
    private static Player newPlayer;

    /**@return newPlayer*/
    public static Player getNewPlayer() {
        return newPlayer;
    }

    /**constructor,
     * @param controller needed to initialize controller attribute*/
    public RmiVirtualView(ViewControllerEventHandlerContext controller){
        this.controller = controller;
    }

    /** starts the rmi server
     throws RemoteException, rmi */
    public void startRMI() throws RemoteException {

        RmiInterface rmiInterface = new RmiVirtualView(controller);
        RmiInterface stub = (RmiInterface) UnicastRemoteObject.exportObject(rmiInterface, 1099);
        Registry reg = LocateRegistry.createRegistry(1099);
        String name = "http://AdrenalineServer:1099";
        reg.rebind(name, stub);

        out.println("<SERVER>: RMI SERVER running at the adress "+ name);

    }

    /**@param o it's the object to send to all clients*/
    public void sendAllClient(Object o){
        if(ModelGate.getModel().getPlayerList()!=null && ModelGate.getModel().getPlayerList().getPlayers()!=null){
            for (Player p : ModelGate.getModel().getPlayerList().getPlayers()) {
                sendToClient(p,o);
            }
        }
    }

    /**@param o it's the object to send to the client
     * @param playerToSend is the specific client to send the object to*/
     static void sendToClient(Player playerToSend, Object o){


            if((!playerToSend.isBot())&&(!playerToSend.isAFK())&&((playerToSend.getRmiInterface()!=null))){
                try {
                    playerToSend.getRmiInterface().send(o);
                } catch (RemoteException e) {
                   playerToSend.setAFKWIthoutNotify(true);
                }
            }
    }

    /**@param playerToSend is player to send the event
     * @param o to, it sends the event to client even if the player is AFK*/
     static void sendToClientEvenAFK(Player playerToSend, Object o) {

        try {
            playerToSend.getRmiInterface().send(o);
        } catch (RemoteException e) {
            logger.log(Level.SEVERE,"EXCEPTION", e);
        }
    }

        /**
         * this update is called from the notify of the Model, it sends MVEs to all clients
         * @param arg object to send to all client */
    @Override
    public void update(Observable o, Object arg){
        //send to all client the object arg
        sendAllClient(arg);
    }

    /**@param p the new player to set newPlayer attribute in class*/
     static void setNewPlayer(Player p){
        newPlayer = p;
    }


    /**send
     * @param o to parse the event received and act consequentially
     *          it may be a nickname, so a newconnection or a reconnection
     *          or a normal event, so a specific
     * */
    @Override
    public void send(Object o){
        if(o.getClass().toString().contains("ViewControllerEventNickname")){
            //case of NewConnection
            boolean validNickname = RmiConnectionHandlerVirtualView.handleNewConnectionNickname((ViewControllerEventNickname)o);
            if(!validNickname){
                //restart the Connection process

                (new Thread(new RmiConnectionHandlerVirtualView(RmiVirtualView.getNewPlayer().getRmiInterface()))).start();

            }
        }
        else{
            //default case:
            //fa partire un thread: RmiClientListenerVirtualView
            (new Thread(new RmiClientListenerVirtualView(o, this.controller))).start();
        }
    }

    /**@param client contains the reference to the client's interface
     * needed to communicate to them
     * */
    @Override
    public void connect(RmiInterface client) {
        //fa partire un thread: RmiCOnnectionHandlerVirtualView

        (new Thread(new RmiConnectionHandlerVirtualView(client))).start();
    }
}
