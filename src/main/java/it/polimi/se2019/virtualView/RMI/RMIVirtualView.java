package it.polimi.se2019.virtualView.RMI;

import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.events.modelViewEvents.ModelViewEvent;
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
    protected NumberOfConnection numberOfConnection = new NumberOfConnection();
    private int port=6799;
    private String name="//rmi:localhost:";
    int rmiIdentifier=0;


    public RMIVirtualView() throws RemoteException {
        clientList = new ArrayList<>();

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

            System.out.println(client.getName()+" is connected");
            numberOfConnection.addNumber();
            System.out.println("number of connection is\n"+ numberOfConnection.getNumber());}
        else System.out.println("sorry we are full, number of connection is\n"+numberOfConnection.getNumber());

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


    public void startServer()throws RemoteException{


        RMIInterface RMIS= new RMIVirtualView();


           RMIInterface stub = (RMIInterface) UnicastRemoteObject.exportObject(RMIS, port);
           Registry reg = LocateRegistry.createRegistry(port);
           reg.rebind(name+port,stub);
           System.out.println("Ciao\n"+"sei connesso al server Rmi di Adrenaline! Benvenuto!\n");

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
