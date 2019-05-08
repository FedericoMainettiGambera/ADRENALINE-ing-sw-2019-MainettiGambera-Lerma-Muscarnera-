package it.polimi.se2019.networkHandler.RMI;
//just sketch
import java.rmi.*;
import java.rmi.server.*;


public class interfacesinput extends UnicastRemoteObject implements interfaces {


    interfacesinput() throws RemoteException {

        super();
    }

    public String lookforinput(String string) throws RemoteException {


        return string;

    }
}