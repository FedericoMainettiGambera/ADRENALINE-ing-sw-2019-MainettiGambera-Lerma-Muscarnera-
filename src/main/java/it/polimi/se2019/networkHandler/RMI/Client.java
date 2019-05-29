package it.polimi.se2019.networkHandler.RMI;


import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.virtualView.RMI.RMIInterface;
import it.polimi.se2019.virtualView.RMI.Message;
import it.polimi.se2019.virtualView.RMI.NumberOfConnection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.rmi.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteObjectInvocationHandler;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;
import java.util.Observable;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements RMIInterface, Runnable {
    private static final long serialVersionUID = 1L;
    private RMIInterface server;
    private int rmiIdentifier;
    boolean chkExit = true;
    RMIObsHandler rmiObsHandler;


    public void setRmiObsHandler(RMIObsHandler rmiObsHandler){
        this.rmiObsHandler=rmiObsHandler;
    }


    protected Client(RMIInterface chatinterface,int clientname) throws RemoteException {

        this.server = chatinterface;
        this.rmiIdentifier = clientname;
        System.out.println("<SEVERINO> " + "hola guapa") ;

    }


    public RMIInterface returnInterface(){return this.server;}
    @Override
    public void sendAllClient(Object o) throws RemoteException {}

    @Override
    public void sendToClient(int RmiIdentifier, Object o) throws RemoteException {

        Event E = (Event)o;
        this.rmiObsHandler.notify(E);

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
        return rmiIdentifier;
    }

    @Override
    public void setRmiIdentifier() throws RemoteException {

    }

    @Override
    public void createPlayer(RMIInterface rmiInterface) throws RemoteException {

    }

    @Override
    public RMIInterface getClient(int rmiIdentifier) throws RemoteException {
        return null;
    }

    @Override
    public void sendToServer(Object o) throws RemoteException{
    }


    public void run(){



        Scanner scanner = new Scanner(System.in);
        Message message=new Message();

        try {
            server.createPlayer(server);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


       /* while(chkExit) {
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
        }*/


    }


    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, ServerNotActiveException {


        Scanner scanner = new Scanner(System.in);
        String clientName = "";
        Client client;


        System.out.println("<PLAYERINO>~~ Welcome To RMI Adrenaline Server~~"+"  Ready to Play? Cool! just a few steps before!");
        System.out.print("<PLAYERINO>Inserisci un nickname: ");
        clientName = scanner.nextLine();
        System.out.println("<Playerino>Connecting To RMI Server...");

        System.setProperty("java.rmi.http//AdrenalineServer", "192.168.x.x");
        Registry reg ;
try {


    System.out.println("Insert IP:");
    Scanner scanner1=new Scanner(System.in);


    reg = LocateRegistry.getRegistry(scanner.nextLine(), 1099);
    RMIInterface chatinterface = (RMIInterface) reg.lookup("http://AdrenalineServer:1099");


        if( chatinterface.numberOfConnection().getNumber()<5   )
        { client= new Client(chatinterface, chatinterface.getRmiIdentifier());
            chatinterface.setRmiIdentifier();
            chatinterface.addClientToList(client);
            chatinterface.numberOfConnection().addNumber();
            new Thread(client).start();



        }

        else {System.out.println("<PlYAERINO> Sorry you cant play we are full, "+"number of connection is already"+chatinterface.numberOfConnection());}

    }catch (Exception e){System.out.println("Error occured in connection");}



    }

}
