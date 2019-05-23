package it.polimi.se2019.networkHandler.RMI;
//just sketch
import java.rmi.*;
import java.util.*;
public class clientside{

    public void createRMIClient(){
        String answer;
        try{
            Scanner scanner;
            System.out.println("plis insert an input");
            scanner=new Scanner(System.in);
            interfaces access=(interfaces)Naming.lookup("rmi://localhost:56678"+"/Adrenaline");
            answer=access.lookforinput(scanner.next());


            System.out.println("Article on"+answer+"ludo");



        }catch(Exception e) {e.printStackTrace();}


    }



}