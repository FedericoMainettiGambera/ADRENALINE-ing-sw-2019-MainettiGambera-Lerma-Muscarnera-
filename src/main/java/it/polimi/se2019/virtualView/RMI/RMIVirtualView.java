package it.polimi.se2019.virtualView.RMI;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.controller.ViewControllerEventHandlerContext;
import it.polimi.se2019.controller.statePattern.GameSetUpState;
import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.GameConstant;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.PlayersList;
import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
import it.polimi.se2019.model.events.viewControllerEvents.ViewControllerEvent;
import it.polimi.se2019.virtualView.VirtualView;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Observable;

public class RMIVirtualView extends VirtualView implements RMIInterface{

    private static final long serialVersionUID = 1L;
    protected static ArrayList<RMIInterface> clientList;
    public static NumberOfConnection numberOfConnection = new NumberOfConnection();
    private int port=6799;
    private String name="rmi://localhost:";
    int rmiIdentifier=1;

    private RMIObsVirtualView RmiObsVirtualView;

    private ViewControllerEventHandlerContext controller;


    public RMIVirtualView(ViewControllerEventHandlerContext controller) throws RemoteException {
        clientList = new ArrayList<>();
        this.controller = controller;
        this.RmiObsVirtualView = new RMIObsVirtualView(controller);
    }

    @Override
    public void sendAllClient(Object o) throws RemoteException{
        for(int i=0; i<clientList.size(); i++) {
            //TODO INOLTRARE IL MESSAGGIO A TUTTI
            // clientList.get(i).sendMessageToClient();
        }
    }

    @Override
    public void sendToClient(int RmiIdentifier, Object o) throws RemoteException {

    }

    @Override
    public void addClientToList(RMIInterface client) throws RemoteException {

        if(numberOfConnection.getNumber()+1<3)
        {   clientList.add(client);

            System.out.println("<SEVERINO> " + client.getName()+" is connected");
            numberOfConnection.addNumber();
            System.out.println("<SEVERINO> " + "number of connection is\n"+ numberOfConnection.getNumber());}
        else System.out.println("<SEVERINO> " + "sorry we are full, number of connection is\n"+numberOfConnection.getNumber());

    }

    @Override
    public int getName() throws RemoteException {
        return 0;
    }

    @Override
    public NumberOfConnection numberOfConnection() throws RemoteException {
        return numberOfConnection;
    }

    @Override
    public int getRmiIdentifier() throws RemoteException {
        return this.rmiIdentifier;
    }

    @Override
    public void setRmiIdentifier() throws RemoteException {

        this.rmiIdentifier=rmiIdentifier+1;

    }

    @Override
    public void createPlayer(RMIInterface server) throws RemoteException {

        System.out.println("<SERVER> Creating a Player.");
        Player p = new Player();
        System.out.println("<SEVERINO> " + "rmiIdenfier is:"+rmiIdentifier);
        p.setNickname("User"+rmiIdentifier);
        System.out.println("<SEVERINO> players's nickname is : " + p.getNickname());
        p.setRmiInterface(server);
        p.setRmiIdentifier(this.rmiIdentifier);
        System.out.println("<SERVER> Adding Player (" + p.getNickname() + ") to the PlayerList.");
        ModelGate.model.getPlayerList().addPlayer(p);
        System.out.println("<SERVER> current number of connection is: "+numberOfConnection.getNumber());


        if(numberOfConnection.getNumber()>GameConstant.maxNumberOfPlayerPerGame-1){
            System.out.println("<SERVER> total number of connection reached, starting the state pattern.");
            System.out.println("does player exist?" +ModelGate.model.getPlayerList().getPlayer("User1").getNickname());
            ViewControllerEventHandlerContext.setNextState(new GameSetUpState());
            ViewControllerEventHandlerContext.state.askForInput(ModelGate.model.getPlayerList().getPlayer("User1"));
        }

    }

    @Override
    public RMIInterface getClient(int rmiIdentifier) throws RemoteException{

        for(int i=0;i< clientList.size(); i++){
            if(clientList.get(i).getRmiIdentifier()==rmiIdentifier)
            return clientList.get(i);
        }
        return null;
    }

    @Override
    public void sendToServer(Object o) throws RemoteException {
        ViewControllerEvent VCE= (ViewControllerEvent)o;
        this.RmiObsVirtualView.notify(VCE);
    }


    public void startServer()throws RemoteException{

         System.out.println("<SERVER>Creating the Game.");
         ModelGate.model = new Game();
         System.out.println("<SERVER>Creating a PlayerList.");
         PlayersList pl = new PlayersList();
         ModelGate.model.setPlayerList(pl);

         RMIInterface RMIS= new RMIVirtualView(controller);


         RMIInterface stub = (RMIInterface) UnicastRemoteObject.exportObject(RMIS, port);
         Registry reg = LocateRegistry.createRegistry(port);
         reg.rebind(name+port,stub);
         System.out.println("<SEVERINO>Ciao,"+"sei connesso al server Rmi di Adrenaline! Benvenuto!\n");


        //System.setProperty("java.rmi.Server.hostname", "192.168.x.x");

    }

    public int getPort(){
        return port;
    }

    public String getServerName(){
        return name;
    }

    @Override
    public void update(Observable o, Object arg){
        ModelViewEvent MVE= (ModelViewEvent)arg;
        try {
            this.sendAllClient(MVE);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
