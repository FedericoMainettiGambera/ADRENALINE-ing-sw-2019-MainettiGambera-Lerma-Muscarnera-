package it.polimi.se2018.virtualView.Socket;

import it.polimi.se2018.controller.ModelGate;
import it.polimi.se2018.controller.ViewControllerEventHandlerContext;
import it.polimi.se2018.controller.statePattern.GameSetUpState;
import it.polimi.se2018.model.Game;
import it.polimi.se2018.model.GameConstant;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayersList;
import it.polimi.se2018.model.events.ViewControllerEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observer;

import static it.polimi.se2018.controller.ViewControllerEventHandlerContext.setNextState;
import static it.polimi.se2018.controller.ViewControllerEventHandlerContext.state;

public class ConnectionHandlerVirtualView extends Thread {

    private ServerSocket serverSocket;

    private boolean isServerSocketLive;

    private ArrayList<ObjectOutputStream> oos;

    private Socket tempSocket;

    private ViewControllerEventHandlerContext controller;

    private int numberOfConnections;


    public ConnectionHandlerVirtualView(ServerSocket serverSocket, ViewControllerEventHandlerContext controller){
        this.serverSocket = serverSocket;
        this.isServerSocketLive = true;
        this.oos = new ArrayList<>();
        this.tempSocket = null;
        this.controller = controller;
        this.numberOfConnections = 0;
    }

    public void CloseServerSocket() throws IOException{
        this.serverSocket.close();
        this.isServerSocketLive = false;
    }

    public ArrayList<ObjectOutputStream> getOos(){
        return this.oos;
    }

    @Override
    public void run(){

        System.out.println("<SERVER>Creating the Game.");
        ModelGate.model = new Game();
        System.out.println("<SERVER>Creating a PlayerList.");
        PlayersList pl = new PlayersList();
        ModelGate.model.setPlayerList(pl);

        while(this.isServerSocketLive && numberOfConnections <= GameConstant.maxNumberOfPlayerPerGame-1){
            try{
                this.tempSocket = serverSocket.accept();
                this.numberOfConnections++;
                System.out.println("<SERVER>New Connection from: " + this.tempSocket.getInetAddress().getHostAddress());
                System.out.println("<SERVER>Number of Connections: " + this.numberOfConnections);
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
                System.out.println("<SERVER>Creating a Player.");
                Player p = new Player();
                p.setOos(new ObjectOutputStream(this.tempSocket.getOutputStream()));
                p.setNickname("User"+numberOfConnections);
                System.out.println("<SERVER>Adding Player (" + p.getNickname() + ") to the PlayerList.");
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

        System.out.println("<SERVER>Not accepting connections anymore.");

        //make the game start
        setNextState(new GameSetUpState());
        state.doAction(new ViewControllerEvent());

    }


}
