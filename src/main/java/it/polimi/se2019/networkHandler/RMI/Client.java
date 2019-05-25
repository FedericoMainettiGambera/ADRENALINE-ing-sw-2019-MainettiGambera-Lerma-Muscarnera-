package it.polimi.se2019.networkHandler.RMI;


import it.polimi.se2019.virtualView.RMI.ChatInterface;
import it.polimi.se2019.virtualView.RMI.Message;
import sun.rmi.registry.RegistryImpl_Stub;

import java.rmi.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements ChatInterface, Runnable {
    private static final long serialVersionUID = 1L;
    private ChatInterface server;
    private String ClientName;
    boolean chkExit = true;



    protected Client(ChatInterface chatinterface,String clientname) throws RemoteException {
        this.server = chatinterface;
        this.ClientName = clientname;
        System.out.println("hola") ;

    }


    public void sendMessageToClient(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public void addClientToList(ChatInterface client) throws RemoteException {

    }

    @Override
    public String getName() throws RemoteException {
        return this.ClientName;
    }

    @Override
    public int numberOfConnection() {
        return 0;
    }


    public void broadcastMessage(String clientname, Message message) throws RemoteException {}

    public void run(){

        System.out.println("Successfully Connected To RMI Server");
        System.out.println("NOTE : Type LOGOUT to Exit From The Service");
        System.out.println("Now Your Online To Chat\n");
        Scanner scanner = new Scanner(System.in);
        Message message=new Message();




        while(chkExit) {
            message.setString(scanner.nextLine());
            if(message.getString().equals("LOGOUT")) {
                chkExit = false;
            }
            else {
                try {
                    server.broadcastMessage(ClientName , message);

                }
                catch(RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("\nSuccessfully Logout From The RMI");
    }


    public static void main(String[] args) throws MalformedURLException,RemoteException,NotBoundException {


        Scanner scanner = new Scanner(System.in);
        String clientName = "";
        Client client;


        System.out.println("\n~~ Welcome To RMI Chat Program ~~\n");
        System.out.print("Enter The Name : ");
        clientName = scanner.nextLine();
        System.out.println("\nConnecting To RMI Server...\n");

        Registry reg=LocateRegistry.getRegistry("localhost", 6799);
        ChatInterface chatinterface = (ChatInterface)reg.lookup("rmi://localhost:6799");

        if( chatinterface.numberOfConnection()+1<3    )
        { client= new Client(chatinterface, clientName );
            chatinterface.addClientToList(client);
            new Thread(client).start();   }
        else {System.out.println("Sorry you cant play we are full\n"+"number of connection is already\n"+chatinterface.numberOfConnection());}




    }

}
