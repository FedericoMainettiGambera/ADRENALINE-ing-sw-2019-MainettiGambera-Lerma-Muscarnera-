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

public class SocketVirtualView extends VirtualView {

    private ServerSocket serverSocket;

    private int port;

    private List<ObjectOutputStream> oos;

    private ConnectionHandlerVirtualView connectionHandler;

    private ViewControllerEventHandlerContext controller;

    public SocketVirtualView(ViewControllerEventHandlerContext controller){

        this.controller = controller;

        try{
            serverSocket = new ServerSocket(0, GameConstant.maxNumberOfPlayerPerGame, InetAddress.getLocalHost());
            this.port = serverSocket.getLocalPort();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        this.oos = null;
    }

    public void startServer() throws IOException{
        this.connectionHandler = new ConnectionHandlerVirtualView(this.serverSocket, this.controller);
        this.connectionHandler.start();
        System.out.println("<SERVER-socket> FOR SOCKETS CLIENTS. Running Server on: " + this.serverSocket.getInetAddress().getHostAddress() + ":" + this.serverSocket.getLocalPort());
    }

    @Override
    public void update(Observable o, Object arg) {
        //System.out.println("                                        <SERVER-socket> SENDING MVE FROM: " +o.getClass());
        //used for MVEs, here they are all to all the clients.
        this.sendAllClient(arg);
    }

    public void sendAllClient(Object o) {
        if(ModelGate.model.getPlayerList()!=null && ModelGate.model.getPlayerList().getPlayers()!=null){
            for (Player p : ModelGate.model.getPlayerList().getPlayers()) {
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
            System.err.println(playerToSend.getNickname() + " is not reachable. Setting him AFK. Executed from method SocketVirtualView.sendToClient()");
            playerToSend.setAFKWIthoutNotify(true);
        }
    }

    public static void sendToClientEvenAFK(Player playerToSend, Object o){
        try{
            playerToSend.getOos().writeObject(o);
            playerToSend.getOos().reset();
        }catch (IOException e ){
            System.err.println(playerToSend.getNickname() + " is not reachable. Setting him AFK. Executed from method SocketVirtualView.sendToClient()");
            playerToSend.setAFKWIthoutNotify(true);
        }
    }
}
