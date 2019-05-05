package it.polimi.se2018.networkHandler.RMI;
import java.rmi.*;
import java.lang.*;
//just sketch
public interface interfaces extends Remote{

    //method protoype
    public String lookforinput(String string) throws RemoteException;

}
