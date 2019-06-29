package it.polimi.se2019.virtualView.Socket;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.SelectorGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.statePattern.GameSetUpState;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.GameConstant;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PlayersList;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.SelectorEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventNickname;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventPlayerSetUp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.se2019.controller.ViewControllerEventHandlerContext.setNextState;
import static it.polimi.se2019.controller.ViewControllerEventHandlerContext.state;

public class ConnectionHandlerVirtualView extends Thread {

    private static final Logger logger=Logger.getLogger(ConnectionHandlerVirtualView.class.getName());

    private ServerSocket serverSocket;

    private boolean isServerSocketLive;

    private Socket tempSocket;

    private ViewControllerEventHandlerContext controller;

    private String exception = "EXCEPTION";

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
        while((this.isServerSocketLive && ModelGate.model.getNumberOfClientsConnected() <= GameConstant.maxNumberOfPlayerPerGame-1)){
            try{
                this.tempSocket = serverSocket.accept();
            }
            catch(IOException e){
                logger.log(Level.SEVERE, exception, e);
            }

            if(Game.isHasGameBegun()){
                System.out.println("<SERVER-soket> Game has already begun, the connection received must be a request of Reconnection.");
                //in a separate Thread handle the reconnection.
                (new Reconnection(tempSocket)).start();
            }
            else {
                System.out.println("<SERVER-soket> Game hasn't begun, the connection received must be a new Player.");
                //in a separate Thread ask for the nickname and listen for the answer, once done it should create a player and add it to the model.
                (new NewConnection(tempSocket)).start();
            }
        }

    }


    private class Reconnection extends Thread {
        private Socket tempSocket;

        public Reconnection(Socket tempSocket) {
            this.tempSocket = tempSocket;
        }

        @Override
        public void run() {
            if(ModelGate.model.getPlayerList().isSomeoneAFK()){
                ArrayList<String> listOfAFKnames = new ArrayList<>();
                for (Player p: ModelGate.model.getPlayerList().getPlayers()) {
                    if(p.isAFK()&&!p.isBot()){
                        listOfAFKnames.add(p.getNickname());
                    }
                }
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(this.tempSocket.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(this.tempSocket.getInputStream());

                    ClientListenerVirtualView sl = new ClientListenerVirtualView(this.tempSocket, ois, controller);
                    sl.passOos(oos);
                    Thread t = new Thread(sl);
                    t.start();
                    //ask for the old nickname
                    oos.writeObject(new ReconnectionEvent(listOfAFKnames));
                } catch (IOException e) {
                    System.err.println("can't use the OutputStream during reconnection: " + e.getMessage());
                }
            }
            else{
                System.out.println("<SERVER> nobody is AFK");
                //refusing connection
                try {
                    tempSocket.close();
                } catch (IOException e) {
                    System.out.println("couldn't close the connection");
                }
            }
        }
    }

    private class NewConnection extends Thread{
        private Socket tempSocket;
        public NewConnection(Socket tempSocket){
            this.tempSocket=tempSocket;
        }
        @Override
        public void run() {

            System.out.println("<SERVER-socket> New Connection from: " + this.tempSocket.getInetAddress().getHostAddress());
            //System.out.println("<SERVER-socket> Number of Connections: " + this.numberOfConnections);

            //ObjectOutputStream
            System.out.println("<SERVER-socket> Creating a Player.");
            Player p = new Player();
            try {
                p.setOos(new ObjectOutputStream(this.tempSocket.getOutputStream()));
            } catch (IOException e) {
                logger.log(Level.SEVERE, exception, e);            }

            //ObjectInputStream
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(this.tempSocket.getInputStream());
            } catch (IOException e) {
                logger.log(Level.SEVERE, exception, e);            }

            boolean correctNicknameFound = false;
            while(!correctNicknameFound) {
                //send request for nickname
                try {
                    p.getOos().writeObject(new SelectorEvent(SelectorEventTypes.askNickname));
                } catch (IOException e) {
                    System.err.println("can't use the OutputStream during connection: " + e.getMessage());
                    break;
                }
                //read nickname received
                ViewControllerEventNickname viewControllerEventNickname = null;
                try {
                    if(ois!=null){
                        viewControllerEventNickname = (ViewControllerEventNickname) ois.readObject();
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("can't use the OutputStream during connection: " + e.getMessage());
                    break;
                }

                //set nickname
                if(viewControllerEventNickname!=null) {
                    if (ModelGate.model.getPlayerList().getPlayer(viewControllerEventNickname.getNickname()) != null || viewControllerEventNickname.getNickname().equals("Terminator")) {
                        correctNicknameFound = false;
                    } else {
                        p.setNickname(viewControllerEventNickname.getNickname());

                        ClientListenerVirtualView sl = new ClientListenerVirtualView(this.tempSocket, ois, controller);

                        //starts the Thread that listen for Object sent from the NetworkHandler.
                        Thread t = new Thread(sl);
                        t.start();

                        System.out.println("<SERVER-socket> Adding Player (" + p.getNickname() + ") to the PlayerList.");
                        ModelGate.model.getPlayerList().addPlayer(p);

                        ModelGate.model.setNumberOfClientsConnected(ModelGate.model.getNumberOfClientsConnected() + 1);
                        System.out.println("<SERVER-socket> Number of Connections: " + ModelGate.model.getNumberOfClientsConnected());

                        correctNicknameFound = true;
                    }
                }
            }
        }
    }


}
