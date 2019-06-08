package it.polimi.se2019.controller;

import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.PlayersList;
import it.polimi.se2019.networkHandler.RMI.RMINetworkHandler;
import it.polimi.se2019.networkHandler.Socket.SocketNetworkHandler;
import it.polimi.se2019.view.GUIstarter;
import it.polimi.se2019.view.components.View;
import it.polimi.se2019.virtualView.RMI.RMIVirtualView;
import it.polimi.se2019.virtualView.Socket.SocketVirtualView;
import org.omg.PortableInterceptor.INACTIVE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;


public class  Controller{

    public static SocketVirtualView SVV;
    public static RMIVirtualView RMIVV;

    public static SocketNetworkHandler SNH;
    public static RMINetworkHandler RMINH;

    public static ViewControllerEventHandlerContext VCEHC;

    public static View V;

    public static void startServerWithRMIAndSocket(){
        System.out.println("<SERVER> Creating the Game.");
        ModelGate.model = new Game();
        System.out.println("<SERVER> Creating a PlayerList.");
        PlayersList pl = new PlayersList();
        ModelGate.model.setPlayerList(pl);

        //Setting the state pattern
        VCEHC = new ViewControllerEventHandlerContext();

        //Starting the Server Socket
        SVV = new SocketVirtualView(VCEHC);
        try {
            SVV.startServer();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        ViewControllerEventHandlerContext.socketVV = SVV;

        //Startin the Server as RMI
        try {
            RMIVV=new RMIVirtualView(VCEHC);
            RMIVV.startServer();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ViewControllerEventHandlerContext.RMIVV = RMIVV;

        //Registering the VirtualView as an observer of the model so it can receive the MVEs
        System.out.println("<SERVER> Registering the VirtualViews (RMI and Socket) as observers of the Model");
        ModelGate.model.setVirtualView(ViewControllerEventHandlerContext.socketVV, ViewControllerEventHandlerContext.RMIVV);
        ModelGate.model.registerVirtualView();

    }

    public static void startClientSocketOrRMIWithGUI(){
        GUIstarter.begin();
    }

    public static boolean connect(String networkConnection, String userInterface, String IP, String Port){
        if(networkConnection.equalsIgnoreCase("RMI")){
            if(userInterface.equalsIgnoreCase("GUI")){
                V = new View("RMI", "GUI");
            }
            else {
                V = new View("RMI", "CLI");
            }
            try {
                RMINH=new RMINetworkHandler(IP, Integer.parseInt(Port), V);
            } catch (RemoteException e) {
                System.err.println(e.getMessage());
                return false;
            } catch (NotBoundException e) {
                System.err.println(e.getMessage());
                return false;
            } catch (UnknownHostException e) {
                System.err.println(e.getMessage());
                return false;
            }
        }
        else {
            if(userInterface.equalsIgnoreCase("GUI")){
                V = new View("SOCKET", "GUI");
            }
            else {
                V = new View("SOCKET", "CLI");
            }
            try {
                SNH = new SocketNetworkHandler(InetAddress.getByName(IP), Integer.parseInt(Port) , V);
            } catch (UnknownHostException e) {
                System.err.println(e.getMessage());
                return false;
            }
        }
        return true;
    }


    public static void startClientSocketOrRMIWithCLI() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("<CLIENT> Do you want to play with:");
        System.out.println("         RMI");
        System.out.println("         SOCKET");
        String networkConnectionChoice ="";
        networkConnectionChoice = br.readLine();

        /*TODO
        System.out.println("<CLIENT> Are you trying to reconnect to a Game? [Y/N]");
        String reconection ="";
        try {
            userInterface = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(reconection.equals("Y")){
            this.reconnect(networkConnectionChoice, userInterface);
            return;
        }
        */

        String IP = null;

        String port = null;

        String userInterface = "CLI";

        if(networkConnectionChoice.equalsIgnoreCase("RMI")){
            System.out.println("<CLIENT> Starting Client with RMI connection");

            //ask for IP and PORT
            System.out.println("<CLIENT> Insert Server's IP:");
            IP = br.readLine();

            port = "1099";
        }
        else {
            System.out.println("<CLIENT> Starting Client with Socket connection");

            System.out.println("<CLIENT> Insert Server IP:");
            IP = br.readLine();
            System.out.println("<CLIENT> Insert Port:");
            port = br.readLine();
        }

        br.close();

        connect(networkConnectionChoice, userInterface, IP, port);
    }

    /*TODO
    public void reconnect(String networkConnection, String userInterface){

        if(userInterface.equals("CLI")) {
            System.out.println("<CLIENT> TRYING TO RECONNECT TO SERVER.");
            if(networkConnection.equalsIgnoreCase("SOCKET")) {
                //creating the View for the user who holds the server
                if(userInterface.equalsIgnoreCase("GUI")){
                    this.V = new View("SOCKET", "GUI");
                }
                else {
                    this.V = new View("SOCKET", "CLI");
                }

                BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("<CLIENT> Insert Server IP:");
                String address = null;
                try {
                    address = buff.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("<CLIENT> Insert Port:");
                String port = null;
                try {
                    port = buff.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //creating the Client for the user who holds the server and connecting it to the server
                try {
                    this.SNH = new SocketNetworkHandler(InetAddress.getByName(address), Integer.parseInt(port) , this.V);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
            else{
                //creating the View for the user who holds the server
                if(userInterface.equalsIgnoreCase("GUI")){
                    this.V = new View("RMI", "GUI");
                }
                else {
                    this.V = new View("RMI", "CLI");
                }
                Scanner scanner=new Scanner(System.in);

                //ask for IP and PORT
                System.out.println("<CLIENT> Insert Server's IP:");
                String address = scanner.nextLine();
                System.out.println("<CLIENT> Insert Port:");
                int port = scanner.nextInt();

                try {
                    this.RMINH=new RMINetworkHandler(address, port, V);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }
        else{

        }
    }
    */

    /////////////////////////////////////////////////////////////////
    ///////////////////////    RMI    ///////////////////////////////
    /////////////////////////////////////////////////////////////////

    public void startGameWithRMIAsServer() throws IOException, NotBoundException {
        //Setting the state pattern
        this.VCEHC = new ViewControllerEventHandlerContext();

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
        this.RMINH=new RMINetworkHandler(this.RMIVV.getAddress(), this.RMIVV.getPort(),this.V);

    }

    public void startGameWithRMIAsClient() throws IOException, NotBoundException{
        //creating the View for the user who holds the server
        this.V = new View("RMI", "CLI");
        Scanner scanner=new Scanner(System.in);

        //ask for IP and PORT
        System.out.println("<CLIENT>Insert Server's IP:");
        String address = scanner.nextLine();
        System.out.println("<CLIENT>Insert Port:");
        int port = scanner.nextInt();

        this.RMINH=new RMINetworkHandler(address, port, V);

    }


    //////////////////////////////////////////////////////////////////
    //////////////////////   SOCKET    ///////////////////////////////
    //////////////////////////////////////////////////////////////////

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
