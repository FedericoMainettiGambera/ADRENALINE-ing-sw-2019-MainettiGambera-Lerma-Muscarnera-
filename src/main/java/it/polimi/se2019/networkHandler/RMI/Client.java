/**package it.polimi.se2019.networkHandler.RMI;


import it.polimi.se2019.controller.ModelGate;
import it.polimi.se2019.model.Player;
import it.polimi.se2019.model.events.Event;
import it.polimi.se2019.virtualView.RMI.RMIInterface;
import it.polimi.se2019.virtualView.RMI.Message;
import it.polimi.se2019.virtualView.RMI.NumberOfConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.*;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
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
        System.out.println("<SERVER-rmi> " + "hola guapa (ehi Ludo, can I delete this??)");


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

    //@Override
    //public NumberOfConnection numberOfConnection() {
    //   NumberOfConnection numberOfConnection=new NumberOfConnection();
    //    return numberOfConnection;
    //}
    @Override
    public int numberOfConnection() throws Exception {
        throw new Exception("EXCEPTION MADE BY FEDE: shouldn't use this method i guess?? Actually it'd be better ask Ludo.");
    }

    //@Override
    //public void addConnection() {
    //    //do nothing
    //}

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

    @Override
    public void removeClient(int rmiIdentifier) throws RemoteException {

    }


    public void run(){



        Scanner scanner = new Scanner(System.in);
        Message message=new Message();

        try {
            server.createPlayer(server);
            server.setRmiIdentifier();

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
 /**   }


    public static void main(String[] args){


        Scanner scanner = new Scanner(System.in);
        String clientName = "";
        Client client;


        System.out.println("<CLIENT> ~~ Welcome To RMI Adrenaline Server~~"+"  Ready to Play? Cool! just a few steps before!");
        System.out.print("<CLIENT> Inserisci un nickname: ");
        clientName = scanner.nextLine();
        System.out.println("<CLIENT> Connecting To RMI Server...");

        System.setProperty("java.rmi.http://AdrenalineServer", "192.168.x.x");
        Registry reg ;
    try {


        System.out.println("<CLIENT> Insert IP:");
        Scanner scanner1=new Scanner(System.in);
        String IP =scanner1.nextLine();
        System.out.println("<CLIENT> Insert port:");
        int port= scanner1.nextInt();


        reg = LocateRegistry.getRegistry(port);
        RMIInterface chatinterface = (RMIInterface) reg.lookup("http://AdrenalineServer:1099");

        // RMISocketFactory.getDefaultSocketFactory().createSocket(IP, port);

            //if(chatinterface.numberOfConnection().getNumber()< 5  )
        if(chatinterface.numberOfConnection()< 5  ) {
                client= new Client(chatinterface, chatinterface.getRmiIdentifier());

                chatinterface.setRmiIdentifier();
                chatinterface.addClientToList(client);
                //chatinterface.numberOfConnection().addNumber();
                //chatinterface.addConnection();
                System.out.println("NOTA DA FEDE NOTA DA FEDE NOTA DA FEDE: PER SISTEMARE LE COSE DI RMI E SOCKET ASSIEME HO MODIFICATO classe CLIENT riga 199.");
                System.out.println("HO TOLTO DELLE COSE CHE SEMBRANO DOVER AGGIORNARE IL NUMERO DI CONNESSIONI.");

                Thread thread=new Thread(client);
                thread.start();

               // Logout logout=new Logout(client, thread);
               // new Thread(logout).start();

        }
        else {
            System.out.println("<CLIENT> Sorry you cant play we are full, "+"number of connection is already"+chatinterface.numberOfConnection());
        }

    }catch (Exception e){
        e.printStackTrace();
    }

    }

}
**/