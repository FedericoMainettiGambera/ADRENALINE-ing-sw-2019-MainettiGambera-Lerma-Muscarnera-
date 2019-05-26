package it.polimi.se2019.virtualView.RMI;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

public interface RMIInterface extends java.rmi.Remote {
    void sendAllClient(Object o) throws RemoteException;
    void sendToClient(int RmiIdentifier, Object o) throws RemoteException;
    public void addClientToList(RMIInterface chat)throws RemoteException;
    public int getName()throws RemoteException;
    public NumberOfConnection numberOfConnection()throws RemoteException;
    public int getRmiIdentifier()throws RemoteException;
    public void setRmiIdentifier()throws RemoteException;
    public void createPlayer(RMIInterface rmiInterface)throws RemoteException;
    public RMIInterface getClient(int rmiIdentifier)throws RemoteException;
    public void sendToServer(Object o)throws  RemoteException;

}
