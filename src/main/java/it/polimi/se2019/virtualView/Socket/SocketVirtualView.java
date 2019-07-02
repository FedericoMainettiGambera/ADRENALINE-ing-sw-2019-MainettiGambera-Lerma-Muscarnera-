package it.polimi.se2019.virtualView.Socket;


import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.GameConstant;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.virtualView.VirtualView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/***/
public class SocketVirtualView extends VirtualView{

    private static final Logger logger=Logger.getLogger(SocketVirtualView.class.getName());

    private ServerSocket serverSocket;

    private ViewControllerEventHandlerContext controller;

    /**constructor,
     * @param controller needed to be registered as an observer since it needs to be notified of the events
     *                   received from the clients connected to the server
     * */
    public SocketVirtualView(ViewControllerEventHandlerContext controller){

        this.controller = controller;

        try{
            serverSocket = new ServerSocket(0, GameConstant.MAX_NUMBER_OF_PLAYER_PER_GAME, InetAddress.getLocalHost());

        }
        catch (IOException e) {
          logger.log(Level.SEVERE, "EXCEPTION", e);
        }


    }

    /**makes the server start
     * */
    public void startServer(){
        ConnectionHandlerVirtualView connectionHandler = new ConnectionHandlerVirtualView(this.serverSocket, this.controller);
        connectionHandler.start();
        System.out.println("<SERVER-socket> FOR SOCKETS CLIENTS. Running Server on: " + this.serverSocket.getInetAddress().getHostAddress() + ":" + this.serverSocket.getLocalPort());
    }

    /**Anytime the model is updated, the server sends the changes to all client*/
    @Override
    public void update(Observable o, Object arg) {
        //System.out.println("                                        <SERVER-socket> SENDING MVE FROM: " +o.getClass());
        //used for MVEs, here they are all to all the clients.
        this.sendAllClient(arg);
    }

    public void sendAllClient(Object o) {
        if(ModelGate.getModel().getPlayerList()!=null && ModelGate.getModel().getPlayerList().getPlayers()!=null){
            for (Player p : ModelGate.getModel().getPlayerList().getPlayers()) {
                sendToClient(p,o);
            }
        }
    }

    public static void sendToClient(Player playerToSend, Object o){
        try{
            if(!playerToSend.isAFK() && playerToSend.getOos()!=null&&!playerToSend.isBot()) {
                playerToSend.getOos().writeObject(o);
                playerToSend.getOos().reset();
            }
        }catch (IOException e ){
            logger.severe(playerToSend.getNickname() + " is not reachable. Setting him AFK. Executed from method SocketVirtualView.sendToClient()");
            playerToSend.setAFKWIthoutNotify(true);
        }
    }

    public static void sendToClientEvenAFK(Player playerToSend, Object o){
        try{
            playerToSend.getOos().writeObject(o);
            playerToSend.getOos().reset();
        }catch (IOException e ){
            logger.severe(playerToSend.getNickname() + " is not reachable. Setting him AFK. Executed from method SocketVirtualView.sendToClient()");
            playerToSend.setAFKWIthoutNotify(true);
        }
    }
}
