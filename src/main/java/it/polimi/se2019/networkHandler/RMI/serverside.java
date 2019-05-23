package it.polimi.se2019.networkHandler.RMI;

//just sketch
import java.rmi.*;
import java.rmi.registry.*;

public class serverside{


    public void creatreRMIRegistry(){
        try{

            interfaces obj=new interfacesinput();
            LocateRegistry.createRegistry(56678);
            Naming.rebind("rmi://localhost:56678"+"/Adrenaline",obj);

        }catch(Exception e){

            e.printStackTrace();
        }




    }
}
