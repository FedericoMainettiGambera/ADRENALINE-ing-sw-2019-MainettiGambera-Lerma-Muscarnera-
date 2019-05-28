package it.polimi.se2019.controller;

import it.polimi.se2019.networkHandler.RMI.RMINetworkHandler;
import it.polimi.se2019.networkHandler.Socket.SocketNetworkHandler;
import it.polimi.se2019.view.components.View;
import it.polimi.se2019.virtualView.RMI.RMIVirtualView;
import it.polimi.se2019.virtualView.Socket.SocketVirtualView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class  Controller{

    private SocketVirtualView SVV;
    private RMIVirtualView RMIVV;

    private SocketNetworkHandler SNH;

    private ViewControllerEventHandlerContext VCEHC;

    private View V;
    private RMINetworkHandler RMINH;


    /////////////////////////////////////////////////////////////////
    ///////////////////////    RMI    ///////////////////////////////
    /////////////////////////////////////////////////////////////////


    public void startGameWithRMIAsServer() throws RemoteException, NotBoundException {
        //Setting the state pattern
        this.VCEHC = new ViewControllerEventHandlerContext();
        ViewControllerEventHandlerContext.networkConnection="RMI";

        //start the server

        try {
            RMIVV=new RMIVirtualView(VCEHC);
            RMIVV.startServer();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        ViewControllerEventHandlerContext.RMIVV = this.RMIVV;


        //creating the View for the user who holds the server
        this.V = new View("RMI", "CLI");

        //start the client for the user who holds the server and connecting it to the server
        RMINH=new RMINetworkHandler(this.RMIVV.getServerName(), this.RMIVV.getPort(),this.V);

    }

    public void startGameWithRMIAsClient() throws IOException {
        //creating the View for the user who holds the server
        this.V = new View("RMI", "CLI");

        //ask for IP and PORT
        /*
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("<CLIENT>Insert Server's IP:");
        String address = br.readLine();
        System.out.println("<CLIENT>Insert Port:");
        String port = br.readLine();
        */

        //create the Client for the user who holds the server and connecting it to the server

    }


    //////////////////////////////////////////////////////////////////
    //////////////////////   SOCKET    ///////////////////////////////
    //////////////////////////////////////////////////////////////////

    public void startGameWithSocketAsServer(){

        //Setting the state pattern
        this.VCEHC = new ViewControllerEventHandlerContext();

        ViewControllerEventHandlerContext.networkConnection = "SOCKET";

        //Starting the Server
        this.SVV = new SocketVirtualView(this.VCEHC);
        try {
            this.SVV.startServer();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        ViewControllerEventHandlerContext.socketVV = this.SVV;

        //creating the View for the user who holds the server
        this.V = new View("SOCKET", "CLI");

        //creating the Client for the user who holds the server and connecting it to the server
        this.SNH = new SocketNetworkHandler(this.SVV.getServerSocket().getInetAddress(), this.SVV.getServerSocket().getLocalPort(), this.V);

    }

    public void startGameWithSocketAsClient() throws IOException {
        //creating the View for the user who holds the server
        this.V = new View("SOCKET", "CLI");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("<CLIENT>Insert Server IP:");
        String address = br.readLine();
        System.out.println("<CLIENT>Insert Port:");
        String port = br.readLine();

        //creating the Client for the user who holds the server and connecting it to the server
        this.SNH = new SocketNetworkHandler(InetAddress.getByName(address), Integer.parseInt(port) , this.V);

    }
}
