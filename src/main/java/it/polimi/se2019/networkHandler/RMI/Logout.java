package it.polimi.se2019.networkHandler.RMI;

import java.util.Scanner;

public class Logout extends Thread{

    private boolean checkExit;

   public Logout(){
       this.checkExit=false;
   }

   @Override
    public void run(){


       while(!checkExit){
           Scanner scanner = new Scanner(System.in);

           if(scanner.nextLine().equalsIgnoreCase("logout")){
               checkExit=true;
           }

       }
   }



}
