package it.polimi.se2019.controller;

import it.polimi.se2019.networkHandler.Socket.SocketNetworkHandler;
import it.polimi.se2019.view.components.View;
import it.polimi.se2019.virtualView.Socket.SocketVirtualView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class  Controller{

    private SocketVirtualView SVV;

    private SocketNetworkHandler SNH;

    private ViewControllerEventHandlerContext VCEHC;

    private View V;



    public void startGameWithSocketAsServer(){

        //Setting the state pattern
        this.VCEHC = new ViewControllerEventHandlerContext();


        //Starting the Server
        this.SVV = new SocketVirtualView(this.VCEHC);
        try {
            this.SVV.startServer();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        //creating the View for the user who holds the server
        this.V = new View();

        //creating the Client for the user who holds the server and connecting it to the server
        this.SNH = new SocketNetworkHandler(this.SVV.getServerSocket().getInetAddress(), this.SVV.getServerSocket().getLocalPort(), this.V);

    }

    public void startGameWithSocketAsClient() throws IOException {
        //creating the View for the user who holds the server
        this.V = new View();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("<CLIENT>Insert Server IP:");
        String address = br.readLine();
        System.out.println("<CLIENT>Insert Port:");
        String port = br.readLine();

        //creating the Client for the user who holds the server and connecting it to the server
        this.SNH = new SocketNetworkHandler(InetAddress.getByName(address), Integer.parseInt(port) , this.V);

    }
}
