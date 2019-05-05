package it.polimi.se2018.networkHandler.RMI;
//just sketch
import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;


public class interfacesinput extends UnicastRemoteObject implements interfaces {


    interfacesinput() throws RemoteException {

        super();
    }

    public String lookforinput(String string) throws RemoteException {


        return string;

    }
}