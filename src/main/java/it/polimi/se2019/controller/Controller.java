package it.polimi.se2019.controller;

import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.GameConstant;
import it.polimi.se2019.model.PlayersList;
import it.polimi.se2019.networkHandler.RMI.RMINetworkHandler;
import it.polimi.se2019.networkHandler.Socket.SocketNetworkHandler;
import it.polimi.se2019.networkHandler.sendPingRequest;
import it.polimi.se2019.view.GUIstarter;
import it.polimi.se2019.view.components.View;
import it.polimi.se2019.view.components.ViewModelGate;
import it.polimi.se2019.view.selector.CLISelector;
import it.polimi.se2019.virtualView.RMI.RMIVirtualView;
import it.polimi.se2019.virtualView.Socket.SocketVirtualView;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class  Controller{

    private static final Logger logger= Logger.getLogger(Controller.class.getName());
    private static PrintWriter out= new PrintWriter(System.out, true);

    public static SocketVirtualView socketVirtualView;
    public static RMIVirtualView rmiVirtualView;

    public static SocketNetworkHandler SNH;
    public static RMINetworkHandler RMINH;

    public static ViewControllerEventHandlerContext viewControllerEventHandlerContext;

    private static View view;
    public static String networkConnection;
    public static String ip;
    public static String port;

    public static void startServerWithRMIAndSocket(){

        //inizializza il model
        out.println("<SERVER> Creating the Game.");
        ModelGate.model = new Game();
        out.println("<SERVER> Creating a PlayerList.");
        PlayersList pl = new PlayersList();
        ModelGate.model.setPlayerList(pl);

        //Setting the state pattern
        viewControllerEventHandlerContext = new ViewControllerEventHandlerContext();

        //Starting the Server Socket
        socketVirtualView = new SocketVirtualView(viewControllerEventHandlerContext);
        try {
            socketVirtualView.startServer();
        }
        catch (IOException e){
            logger.log(Level.SEVERE, "EXCEPTION", e);
        }
        ViewControllerEventHandlerContext.socketVV = socketVirtualView;

        //Starting the Server as RMI
        try {
            rmiVirtualView = new RMIVirtualView(viewControllerEventHandlerContext);
            rmiVirtualView.startServer();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "EXCEPTION", e);
        }
        ViewControllerEventHandlerContext.RMIVV = rmiVirtualView;

        //Registering the VirtualView as an observer of the model so it can receive the MVEs
        out.println("<SERVER> Registering the VirtualViews (RMI and Socket) as observers of the Model");
        ModelGate.model.setVirtualView(ViewControllerEventHandlerContext.socketVV, ViewControllerEventHandlerContext.RMIVV);
        ModelGate.model.registerVirtualView();
    }

    public static void startClientSocketOrRMIWithGUI(){
        GUIstarter.begin();
    }

    public static boolean randomGame = false;

    public static boolean connect(String networkConnection, String userInterface, String ip, String port){
        Controller.networkConnection = networkConnection;
        Controller.ip = ip;
        Controller.port = port;
        if(networkConnection.equalsIgnoreCase("RMI")){
            if(userInterface.equalsIgnoreCase("GUI")){
                view = new View("RMI", "GUI");
            }
            else {
                view = new View("RMI", "CLI");
            }
            try {
                if(sendPingRequest.sendPingRequest(ip)) {
                    try {
                        RMINH = new RMINetworkHandler(ip, Integer.parseInt(port), view);
                    }
                    catch (NumberFormatException e){
                        logger.severe("error in rmi network handler occurred"+ e.getCause());
                        return false;
                    }
                }
            } catch (NotBoundException|IOException e ) {
                logger.severe("error in rmi starting server occurred"+ e.getCause());
                return false;
            }
        }
        else {
            if(userInterface.equalsIgnoreCase("GUI")){
                view = new View("SOCKET", "GUI");
            }
            else {
                view = new View("SOCKET", "CLI");
            }

            try{
                    SNH = new SocketNetworkHandler(InetAddress.getByName(ip), Integer.parseInt(port) , view);
                }
                catch (NumberFormatException|  IOException e){
                logger.severe("Socket network handler error occurred"+e.getMessage());
                    return false;
                }
             }
        return true;
    }


    public static void startClientSocketOrRMIWithCLI() {
        Scanner br = new Scanner(System.in);

        out.println("\n\n");
        out.println(GameConstant.AdrenalineTitle4);
        out.println("\n\n\n");
        out.println("\nWhat's your Nickname?");

        String nickname = br.nextLine();
        ViewModelGate.setMe(nickname);

        out.println("\n<CLIENT> Do you want to play with:");

        CLISelector.showListOfRequests(Arrays.asList("SOCKET","RMI"));
        String networkConnectionChoice = "";
        int choice = CLISelector.askNumber(0,1);
        if(choice == 1){
            networkConnectionChoice = "RMI";
        }
        else{
            networkConnectionChoice = "SOCKET" ;
        }

        String ip = null;

        String port = null;

        String userInterface = "CLI";

        if (networkConnectionChoice.equalsIgnoreCase("RMI")) {
            out.println("<CLIENT> Starting Client with RMI connection");

            //ask for IP and PORT
            out.println("\n<CLIENT> Insert Server's IP:");
            ip = br.nextLine();

            port = "1099";
        } else {
            out.println("<CLIENT> Starting Client with Socket connection");

            out.println("\n<CLIENT> Insert Server IP:");
            ip = br.nextLine();
            out.println("\n<CLIENT> Insert Port:");
            port = br.nextLine();
        }

        out.println("\n<CLIENT> DO YOU WANT TO PLAY WITH AUTOMATIC RANDOM CHOICES?");
        CLISelector.showListOfRequests(Arrays.asList("HELL YES !!","nope, thanks."));
        randomGame = false;
        choice = CLISelector.askNumber(0,1);
        if(choice == 0){
            randomGame = true;
        }

        if (!connect(networkConnectionChoice, userInterface, ip, port)) {
            startClientSocketOrRMIWithCLI();
        }
    }


    /*TODO public void reconnect(String networkConnection, String userInterface){

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

    /*
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
    */


    //////////////////////////////////////////////////////////////////
    //////////////////////   SOCKET    ///////////////////////////////
    //////////////////////////////////////////////////////////////////

    /*
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
    */
    /*
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
    */
}
