package it.polimi.se2018.networkHandler.RMI;

//just sketch
import java.rmi.*;
import java.rmi.registry.*;

public class serverside{


    public static void main(String arg[]){

        try{


            interfaces obj=new interfacesinput();

            LocateRegistry.createRegistry(56678);

            Naming.rebind("rmi://localhost:56678"+"/ludo",obj);


        }catch(Exception e)
        {e.printStackTrace();}




    }
}
