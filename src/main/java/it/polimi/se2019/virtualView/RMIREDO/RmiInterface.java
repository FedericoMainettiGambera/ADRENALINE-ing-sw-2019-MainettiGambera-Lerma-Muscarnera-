package it.polimi.se2019.virtualView.RMIREDO;

import java.rmi.RemoteException;

/**remote methods to be called*/
public interface RmiInterface extends java.rmi.Remote{

     void send(Object o) throws RemoteException;

     void connect(RmiInterface client) throws RemoteException;


}
