package it.polimi.se2019.virtualView.RMI;

import it.polimi.se2019.virtualView.VirtualView;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class RMIVirtualView extends VirtualView implements ChatInterface{

    private static final long serialVersionUID = 1L;
    protected static ArrayList<ChatInterface> clientList;
    protected int numberOfConnection=0;


    public RMIVirtualView() throws RemoteException {
        clientList = new ArrayList<>();


    }
    @Override
    public void broadcastMessage(String clientname, Message message) throws RemoteException{
        for(int i=0; i<clientList.size(); i++) {
        clientList.get(i).sendMessageToClient(clientname.toUpperCase() + " : "+ message.getString());
    }
    }

    @Override
    public void sendMessageToClient(String message) throws RemoteException {

    }

    @Override
    public void addClientToList(ChatInterface client) throws RemoteException {

        if(numberOfConnection+1<3)
        {   clientList.add(client);

            System.out.println(client.getName()+" is connected");
            numberOfConnection++;
            System.out.println("number of connection is\n"+ numberOfConnection);}
        else System.out.println("sorry we are full, number of connection is\n"+numberOfConnection);

    }

    @Override
    public String getName() throws RemoteException {
        return null;
    }

    @Override
    public int numberOfConnection() throws RemoteException {
        return numberOfConnection;
    }

    public void startServer()throws RemoteException, MalformedURLException{

        ChatInterface RMIS= new RMIVirtualView();
      // try {
           ChatInterface stub = (ChatInterface) UnicastRemoteObject.exportObject(RMIS, 6799);
           Registry reg = LocateRegistry.createRegistry(6799);
           reg.rebind("rmi://localhost:6799",stub);
           System.out.println("ciao");
     //  }catch (RemoteException e){}
        //System.setProperty("java.rmi.Server.hostname", "192.168.x.x");

    }

/*public static void main(String[] rgs) throws RemoteException, MalformedURLException{

    LocateRegistry.createRegistry(6799);
    // ChatInterface RMIS= new RMIVirtualView();
    // ChatInterface stub=(ChatInterface)UnicastRemoteObject.exportObject(RMIS, 0);
    // Registry reg=LocateRegistry.getRegistry();
    //System.setProperty("java.rmi.Server.hostname", "192.168.x.x");
     Naming.rebind("rmi://localhost:6799"+"/chat", new RMIVirtualView());

}
*/

}
