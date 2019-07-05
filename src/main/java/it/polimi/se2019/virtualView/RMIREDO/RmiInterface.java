package it.polimi.se2019.virtualView.RMIREDO;

import java.rmi.RemoteException;

public interface RmiInterface extends java.rmi.Remote{

     void send(Object o) throws RemoteException;

     void connect(RmiInterface client) throws RemoteException;


}
