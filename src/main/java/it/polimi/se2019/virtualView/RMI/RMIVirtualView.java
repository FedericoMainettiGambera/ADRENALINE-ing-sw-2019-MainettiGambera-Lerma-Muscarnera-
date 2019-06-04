package it.polimi.se2019.virtualView.RMI;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.statePattern.GameSetUpState;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.GameConstant;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PlayersList;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.virtualView.VirtualView;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Observable;

//import sun.net.util.IPAddressUtil;
//import sun.security.x509.IPAddressName;

public class RMIVirtualView extends VirtualView implements RMIInterface {

    private static final long serialVersionUID = 1L;
    protected static ArrayList<RMIInterface> clientList;

    //public static NumberOfConnection numberOfConnection = new NumberOfConnection();

    private int port = 1099;
    private String name = "http://AdrenalineServer:";
    int rmiIdentifier = 1;
    String address;

    private RMIObsVirtualView RmiObsVirtualView;

    private ViewControllerEventHandlerContext controller;


    public RMIVirtualView(ViewControllerEventHandlerContext controller) throws RemoteException {
        clientList = new ArrayList<>();
        this.controller = controller;
        this.RmiObsVirtualView = new RMIObsVirtualView(controller);
    }

    @Override
    //TODO: EXCEPT AFK PLAYERS...
    public void sendAllClient(Object o) throws RemoteException {
        int i=1;

        if(ModelGate.model.getPlayerList()!=null && ModelGate.model.getPlayerList().getPlayers()!=null){
            for (Player p : ModelGate.model.getPlayerList().getPlayers()) {
                if(p.getRmiInterface()!=null){
                   p.getRmiInterface().getClient(i).sendToClient(i,o);
                    i++;
                }
            }
        }
    }

    @Override
    public void sendToClient(int RmiIdentifier, Object o) throws RemoteException {

    }

    @Override
    public void addClientToList(RMIInterface client) throws RemoteException {

        //if (numberOfConnection.getNumber() + 1 < 3) {
        if(ModelGate.model.getNumberOfClientsConnected() <= GameConstant.maxNumberOfPlayerPerGame){
            clientList.add(client);

            System.out.println("<SEVERINO-rmi> " + client.getName() + " is connected");
            //numberOfConnection.addNumber();
            ModelGate.model.setNumberOfClientsConnected(ModelGate.model.getNumberOfClientsConnected()+1);
            //System.out.println("<SEVERINO> " + "number of connection is\n" + numberOfConnection.getNumber());
            System.out.println("<SEVERINO-rmi> " + "number of connection is" + ModelGate.model.getNumberOfClientsConnected());
        } else {
            //System.out.println("<SEVER> " + "sorry we are full, number of connection is" + numberOfConnection.getNumber());
            System.out.println("<SEVER-rmi> " + "sorry we are full, number of connection is" + ModelGate.model.getNumberOfClientsConnected());
        }

    }

    @Override
    public int getName() throws RemoteException {
        return 0;
    }

    //@Override
    //public NumberOfConnection numberOfConnection() throws RemoteException {
    //    return numberOfConnection;
    //}
    @Override
    public int numberOfConnection(){
        return ModelGate.model.getNumberOfClientsConnected();
    }

    //@Override
    //public void addConnection() {
    //    ModelGate.model.setNumberOfClientsConnected(ModelGate.model.getNumberOfClientsConnected()+1);
    //}

    @Override
    public int getRmiIdentifier() throws RemoteException {
        return this.rmiIdentifier;
    }

    @Override
    public void setRmiIdentifier() throws RemoteException {

        this.rmiIdentifier = rmiIdentifier + 1;

    }

    @Override
    public void createPlayer(RMIInterface server) throws RemoteException {

        System.out.println("<SERVER-rmi> Creating a Player.");
        Player p = new Player();
        System.out.println("<SEVERINO-rmi> " + "rmiIdenfier is:" + rmiIdentifier);
        p.setNickname("User" + ModelGate.model.getNumberOfClientsConnected());
        System.out.println("<SERVER-rmi> players's nickname is : " + p.getNickname());
        p.setRmiInterface(server);
        p.setRmiIdentifier(this.rmiIdentifier);
        System.out.println("<SERVER-rmi> Adding Player (" + p.getNickname() + ") to the PlayerList.");
        ModelGate.model.getPlayerList().addPlayer(p);
        //System.out.println("<SERVER-rmi> current number of connection is: " + numberOfConnection.getNumber());
        System.out.println("<SERVER-rmi> current number of connection is: " + ModelGate.model.getNumberOfClientsConnected());


        //if (numberOfConnection.getNumber() > GameConstant.maxNumberOfPlayerPerGame - 1) {
        if (ModelGate.model.getNumberOfClientsConnected() > GameConstant.maxNumberOfPlayerPerGame - 1) {
            System.out.println("<SERVER-rmi> total number of connection reached, starting the state pattern.");
            // (MOVED TO THE GAME CLASS IN THE NUMBEROFCONNECTION STUFF... setNumberOfClientsConnected()
            //ViewControllerEventHandlerContext.setNextState(new GameSetUpState());
            //ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getPlayerList().getPlayer("User1"));
        }

    }

    @Override
    public RMIInterface getClient(int rmiIdentifier) throws RemoteException {

        for (int i = 0; i < clientList.size(); i++) {
            if (clientList.get(i).getRmiIdentifier() == rmiIdentifier)
                return clientList.get(i);
        }
        return null;
    }

    @Override
    public void sendToServer(Object o) throws RemoteException {
        ViewControllerEvent VCE = (ViewControllerEvent) o;
        this.RmiObsVirtualView.notify(VCE);
    }

    @Override
    public void removeClient(int rmiIdentifier) throws RemoteException{

        clientList.remove(rmiIdentifier);
        for (RMIInterface client: clientList) {
            System.out.println("still playing: "+client.getName());
        }

    }


    public void startServer() throws IOException {

        RMIInterface RMIS = new RMIVirtualView(controller);



        RMIInterface stub = (RMIInterface) UnicastRemoteObject.exportObject(RMIS, port);
        Registry reg = LocateRegistry.createRegistry(port);
        reg.rebind(name+port, stub);
        address=new String();


        try {
             address= InetAddress.getLocalHost().getHostAddress();

        }catch(Exception e){
            e.printStackTrace();
        };

        System.out.println("<SERVER-rmi> FOR RMI CLIENTS. Server running on: "+ address + " (:1099) port is always 1099.");


    }

    public int getPort() {
        return port;
    }

    public String getServerName() {
        return name;
    }

    public String getAddress(){
        return address;
}

    @Override
    public void update(Observable o, Object arg)
    {
        //System.out.println("                                        <SERVER-rmi> SENDING MVE FROM: " +o.getClass());

        try {
            this.sendAllClient(arg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}



