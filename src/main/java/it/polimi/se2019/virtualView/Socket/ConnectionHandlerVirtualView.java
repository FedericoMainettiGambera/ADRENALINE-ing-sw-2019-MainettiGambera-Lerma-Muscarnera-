package it.polimi.se2019.virtualView.Socket;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.statePattern.GameSetUpState;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.GameConstant;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PlayersList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static it.polimi.se2019.controller.ViewControllerEventHandlerContext.setNextState;
import static it.polimi.se2019.controller.ViewControllerEventHandlerContext.state;

public class ConnectionHandlerVirtualView extends Thread {

    private ServerSocket serverSocket;

    private boolean isServerSocketLive;

    private Socket tempSocket;

    private ViewControllerEventHandlerContext controller;

    //private int numberOfConnections;


    public ConnectionHandlerVirtualView(ServerSocket serverSocket, ViewControllerEventHandlerContext controller){
        this.serverSocket = serverSocket;
        this.isServerSocketLive = true;
        this.tempSocket = null;
        this.controller = controller;
        //this.numberOfConnections = 0;
    }

    public void CloseServerSocket() throws IOException{
        this.serverSocket.close();
        this.isServerSocketLive = false;
    }

    @Override
    public void run(){

        //while(this.isServerSocketLive && numberOfConnections <= GameConstant.maxNumberOfPlayerPerGame-1){
        while(this.isServerSocketLive && ModelGate.model.getNumberOfClientsConnected() <= GameConstant.maxNumberOfPlayerPerGame-1){
            try{
                this.tempSocket = serverSocket.accept();

                //this.numberOfConnections++;
                ModelGate.model.setNumberOfClientsConnected(ModelGate.model.getNumberOfClientsConnected()+1);

                System.out.println("<SERVER-socket> New Connection from: " + this.tempSocket.getInetAddress().getHostAddress());
                //System.out.println("<SERVER-socket> Number of Connections: " + this.numberOfConnections);
                System.out.println("<SERVER-socket> Number of Connections: " + ModelGate.model.getNumberOfClientsConnected());

            }
            catch(IOException e){
                e.printStackTrace();
                try {
                    this.tempSocket.close();
                }
                catch(IOException a) {
                    a.printStackTrace();
                }
            }


            //ObjectOutputStream
            try {
                System.out.println("<SERVER-socket> Creating a Player.");
                Player p = new Player();
                p.setOos(new ObjectOutputStream(this.tempSocket.getOutputStream()));
                //p.setNickname("User"+numberOfConnections);
                p.setNickname("User"+ModelGate.model.getNumberOfClientsConnected());
                System.out.println("<SERVER-socket> Adding Player (" + p.getNickname() + ") to the PlayerList.");
                ModelGate.model.getPlayerList().addPlayer(p);
            }
            catch(IOException e){
                e.printStackTrace();
            }

            //ObjectInputStream
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(this.tempSocket.getInputStream());
            }
            catch (IOException e){
                e.printStackTrace();
            }
            ClientListenerVirtualView sl = new ClientListenerVirtualView(this.tempSocket, ois, controller);
            //starts the Thread that listen for Object sent from the NetworkHandler.
            new Thread(sl).start();
        }

        System.out.println("<SERVER-socket>Not accepting connections anymore.");

        //make the game start
        setNextState(new GameSetUpState());
        state.askForInput(ModelGate.model.getPlayerList().getPlayer("User1"));

    }


}
