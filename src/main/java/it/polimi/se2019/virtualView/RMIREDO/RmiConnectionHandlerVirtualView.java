package it.polimi.se2019.virtualView.RMIREDO;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.SelectorEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventNickname;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**this class handles the connection received by the server, it sorts them out depending on whether they are new connection
 * or reconnection */
public class RmiConnectionHandlerVirtualView implements Runnable{

    private RmiInterface client;

    private static final Logger logger=Logger.getLogger(RmiConnectionHandlerVirtualView.class.getName());
    private PrintWriter out=new PrintWriter(System.out, true);

    /** constructor, initialize a private variable client with the new client,
     * it is of rmiInterface type because bidirectional rmi is implemented*/
    RmiConnectionHandlerVirtualView(RmiInterface client){
        this.client = client;
    }

    /** a thread is taking care of listening to every new connection that overcome*/
    @Override
    public void run(){
        if(Game.isHasGameBegun()){
            System.out.println("<SERVER> Game has already begun, the connection received must be a request of Reconnection.");
            //in a separate Thread handle the reconnection.
            (new Reconnection()).start();
        }
        else {
            System.out.println("<SERVER> Game hasn't begun, the connection received must be a new Player.");
            //in a separate Thread ask for the nickname and listen for the answer, once done it should create a player and add it to the model.
            (new NewConnection()).start();
        }
    }

    /** the new connection is a reconnection, if the game has already begun
     * so we understand if the name the user logged in with is one of the AFK player's nickname,
     * if else, it is not possible to reconnect*/
    private class Reconnection extends Thread {
        RmiInterface tempinterface;

        Reconnection(){
            tempinterface=client;
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
                    client.send(new ReconnectionEvent(listOfAFKnames));
                } catch (RemoteException e) {
                   logger.log(Level.SEVERE, "EXCEPTION:", e);
                }


            }
            else {

                out.println("non è possibile riconnettersi");
            }
        }
    }

    /**if the game still isn't started, the connection received must be a new one
     *a new player is created */
    private class NewConnection extends Thread {

        NewConnection(){

            //empty
        }

        @Override
        public void run() {

            System.out.println("<SERVER-rmi> Creating a Player.");
            Player p = new Player();


            p.setRmiInterface(client);
            RmiVirtualView.setNewPlayer(p);

            try {
                p.getRmiInterface().send(new SelectorEvent(SelectorEventTypes.askNickname));
            } catch (RemoteException e) {
                logger.log(Level.SEVERE, "EXCEPTION:", e);
            }

            //the next part of the process is done in the method "RmiConnectionHandlerVirtualView.handleNewConnectionNickname()", called from the method "RmiVirtualView.send()" in the first if statement.
        }
    }

    /**it is necessary that each player has a different nickname
     * @param viewControllerEventNickname contains the nickname chosen from a player, here it is
     * checked if the nickname is available, whether it is or not, a boolean ir returned
     * @return boolean value*/
     static boolean handleNewConnectionNickname(ViewControllerEventNickname viewControllerEventNickname){
        //the viewControllerEventNickname represents the nickname received from the client

        boolean correctNicknameFound = false;
        //set nickname
        if(viewControllerEventNickname!=null) {
            if (ModelGate.model.getPlayerList().getPlayer(viewControllerEventNickname.getNickname()) != null || viewControllerEventNickname.getNickname().equals("Terminator")) {
                correctNicknameFound = false;
            } else {
                RmiVirtualView.newPlayer.setNickname(viewControllerEventNickname.getNickname());

                System.out.println("<SERVER-socket> Adding Player (" + RmiVirtualView.newPlayer.getNickname() + ") to the PlayerList.");
                ModelGate.model.getPlayerList().addPlayer(RmiVirtualView.newPlayer);

                ModelGate.model.setNumberOfClientsConnected(ModelGate.model.getNumberOfClientsConnected() + 1);
                System.out.println("<SERVER-socket> Number of Connections: " + ModelGate.model.getNumberOfClientsConnected());

                correctNicknameFound = true;
            }
        }

        return correctNicknameFound;
    }
}
