package it.polimi.se2019.virtualView.RMIREDO;

import java.rmi.RemoteException;

public interface RmiInterface extends java.rmi.Remote{

    public void send(Object o) throws RemoteException;

    public void connect(RmiInterface client) throws RemoteException;


}
