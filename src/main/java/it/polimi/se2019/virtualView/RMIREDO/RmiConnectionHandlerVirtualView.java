package it.polimi.se2019.virtualView.RMIREDO;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.enumerations.SelectorEventTypes;
import it.polimi.se2019.model.events.reconnectionEvent.ReconnectionEvent;
import it.polimi.se2019.model.events.selectorEvents.SelectorEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEventNickname;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class RmiConnectionHandlerVirtualView implements Runnable{

    private RmiInterface client;

    public RmiConnectionHandlerVirtualView(RmiInterface client){
        this.client = client;
    }

    @Override
    public void run(){
        if(Game.hasGameBegun){
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

    private class Reconnection extends Thread {
        private RmiInterface tempinterface=client;

        public Reconnection(){
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
                    e.printStackTrace();
                }


            }
            else {

                System.out.println("non è possibile riconnettersi");
            }
        }
    }

    private class NewConnection extends Thread {

        public NewConnection() {
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
                e.printStackTrace();
            }

            //the next part of the process is done in the method "RmiConnectionHandlerVirtualView.handleNewConnectionNickname()", called from the method "RmiVirtualView.send()" in the first if statement.
        }
    }

    public static boolean handleNewConnectionNickname(ViewControllerEventNickname viewControllerEventNickname){
        //the viewControllerEventNickname represents the nickname received from the client

        boolean correctNicknameFound = false;
        //set nickname
        if(viewControllerEventNickname!=null) {
            if (ModelGate.model.getPlayerList().getPlayer(viewControllerEventNickname.getNickname()) != null || viewControllerEventNickname.getNickname().equals("Terminator")) {
                correctNicknameFound = false;
            } else {
                RmiVirtualView.newPlayer.setNickname(viewControllerEventNickname.getNickname());

                //TODO
                //set everything Rmi-related in the Player p
                //esempio:
                //      p."setInterfacciaDelClient"(this.client)
                //      ...

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
