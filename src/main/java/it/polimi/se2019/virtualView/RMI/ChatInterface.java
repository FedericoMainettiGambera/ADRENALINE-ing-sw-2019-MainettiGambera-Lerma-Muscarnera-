package it.polimi.se2019.virtualView.RMI;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

public interface ChatInterface extends java.rmi.Remote {
    void broadcastMessage(String name, Message message) throws RemoteException;
    void sendMessageToClient(String message) throws RemoteException;
    public void addClientToList(ChatInterface chat)throws RemoteException;
    public String getName()throws RemoteException;
    public int numberOfConnection()throws RemoteException;

}
