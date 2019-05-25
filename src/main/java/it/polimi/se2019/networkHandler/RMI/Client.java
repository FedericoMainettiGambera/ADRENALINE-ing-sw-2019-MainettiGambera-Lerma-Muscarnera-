package it.polimi.se2019.networkHandler.RMI;


import com.sun.tools.javac.util.Abort;
import it.polimi.se2019.virtualView.RMI.RMIInterface;
import it.polimi.se2019.virtualView.RMI.Message;
import it.polimi.se2019.virtualView.RMI.NumberOfConnection;
import it.polimi.se2019.virtualView.RMI.RMIInterface;
import sun.rmi.registry.RegistryImpl_Stub;

import java.rmi.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements RMIInterface, Runnable {
    private static final long serialVersionUID = 1L;
    private RMIInterface server;
    private int rmiIdentifier;
    boolean chkExit = true;



    protected Client(RMIInterface chatinterface,int clientname) throws RemoteException {

        this.server = chatinterface;
        this.rmiIdentifier = clientname;
        System.out.println("hola") ;

    }


    @Override
    public void sendAllClient(Object o) throws RemoteException {}

    @Override
    public void sendToClient(int RmiIdentifier, Object o) throws RemoteException {

    }

    public void sendMessageToClient(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public void addClientToList(RMIInterface client) throws RemoteException {

    }

    @Override
    public int getName() throws RemoteException {
        return this.rmiIdentifier;
    }

    @Override
    public NumberOfConnection numberOfConnection() {
       NumberOfConnection numberOfConnection=new NumberOfConnection();
        return numberOfConnection;
    }

    @Override
    public int getRmiIdentifier() throws RemoteException {
        return 0;
    }

    @Override
    public void setRmiIdentifier() throws RemoteException {

    }

    public void run(){

        System.out.println("Successfully Connected To RMI Adrenaline Server");
        System.out.println("NOTE : Type LOGOUT to Exit From The Service");
        System.out.println("Now Your Online To Play!\nPlease have fun!");
        Scanner scanner = new Scanner(System.in);
        Message message=new Message();
        RMIInterface chatInterface;





        while(chkExit) {
            message.setString(scanner.nextLine());
            if(message.getString().equals("LOGOUT")) {
                chkExit = false;
            }
            else {
                try {
                    server.sendAllClient(new Object ());

                }
                catch(RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("\nSuccessfully Logout From The RMI");
        try {
            server.numberOfConnection().lessNumber();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) throws MalformedURLException,RemoteException,NotBoundException {


        Scanner scanner = new Scanner(System.in);
        String clientName = "";
        Client client;


        System.out.println("\n~~ Welcome To RMI Adrenaline Server~~\n"+"Ready to Play?\n Cool!\njust a few steps before!");
        System.out.print("Inserisci un nickname: ");
        clientName = scanner.nextLine();
        System.out.println("\nConnecting To RMI Server...\n");

        Registry reg=LocateRegistry.getRegistry("localhost", 6799);
        RMIInterface chatinterface = (RMIInterface) reg.lookup("rmi://localhost:6799");

        if( chatinterface.numberOfConnection().getNumber()+1<3    )
        { client= new Client(chatinterface, chatinterface.getRmiIdentifier());
            chatinterface.setRmiIdentifier();
            chatinterface.addClientToList(client);
            new Thread(client).start();


        }

        else {System.out.println("Sorry you cant play we are full\n"+"number of connection is already\n"+chatinterface.numberOfConnection());}




    }

}
