package it.polimi.se2019.controller;

import it.polimi.se2019.model.Game;
import it.polimi.se2019.model.GameConstant;
import it.polimi.se2019.model.PlayersList;
import it.polimi.se2019.networkHandler.RMIREDO.RmiNetworkHandler;
import it.polimi.se2019.networkHandler.Socket.SocketNetworkHandler;
import it.polimi.se2019.view.GUIstarter;
import it.polimi.se2019.view.components.View;
import it.polimi.se2019.view.components.ViewModelGate;
import it.polimi.se2019.view.selector.CLISelector;
import it.polimi.se2019.virtualView.RMIREDO.RmiVirtualView;
import it.polimi.se2019.virtualView.Socket.SocketVirtualView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class  Controller{

    private Controller(){
        //empty
    }

    private static final Logger logger= Logger.getLogger(Controller.class.getName());
    private static PrintWriter out= new PrintWriter(System.out, true);

    /**the class for rmi that manages connections from the client side*/
    private static RmiNetworkHandler rmiNetworkHandler;

    /**@return  rmiNetworkHandler*/
    public static RmiNetworkHandler getRmiNetworkHandler() {
        return rmiNetworkHandler;
    }

    /**@param rmiNetworkHandler, above mentioned*/
    private static void setRmiNetworkHandler(RmiNetworkHandler rmiNetworkHandler) {
        Controller.rmiNetworkHandler = rmiNetworkHandler;
    }

    /**indicates what kind of connection the user is using*/
    private static String networkConnection;

    /**@return networkConnection, above mentioned*/
    public static String getNetworkConnection() {
        return networkConnection;
    }

    /**@param networkConnection, above mentioned*/
    private static void setNetworkConnection(String networkConnection) {
        Controller.networkConnection = networkConnection;
    }

    /**indicates what kind of user interface the client decided to use, GUI or CLI */
    private static String userInterface;
    /**@return  GUI or CLI */
    public static String getUserInterface() {
        return userInterface;
    }

    /**indicates the user's ip address */
    private static String ip;

    /**@param ip address */
    private static void setIp(String ip) {
        Controller.ip = ip;
    }
    /**@return  ip address */
    public static String getIp() {
        return ip;
    }

    /**indicate the port of the client on which the connection is opened*/
    private static String port;
    /**@return the port of the client on which the connection is opened*/
    public static String getPort() {
        return port;
    }

    /**@param port of the client on which the connection is opened*/
    public static void setPort(String port) {
        Controller.port = port;
    }

    /**function that starts the server with the possibility to connect to it either with rmi or socket underlying network*/
    static void startServerWithRMIAndSocket(){

        //initialize model
        out.println("<SERVER> Creating the Game.");
        ModelGate.setModel(new Game());
        out.println("<SERVER> Creating a PlayerList.");
        PlayersList pl = new PlayersList();
        ModelGate.getModel().setPlayerList(pl);

        //Setting the state pattern
         ViewControllerEventHandlerContext viewControllerEventHandlerContext = new ViewControllerEventHandlerContext();

        //Starting the Server socket
         SocketVirtualView socketVirtualView = new SocketVirtualView(viewControllerEventHandlerContext);
        socketVirtualView.startServer();
        ViewControllerEventHandlerContext.socketVV = socketVirtualView;


        //-----------------NEW-----------------//
        //starting the Server as RMi
         RmiVirtualView rmiVirtualView = new RmiVirtualView(viewControllerEventHandlerContext);
        try {
            rmiVirtualView.startRMI();
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, " EXCEPTION ", e);
        }
        ViewControllerEventHandlerContext.RMIVV= rmiVirtualView;

        //Registering the VirtualView as an observer of the model so it can receive the MVEs
        out.println("<SERVER> Registering the VirtualViews (rmi and socket) as observers of the Model");
        ModelGate.getModel().setVirtualView(ViewControllerEventHandlerContext.socketVV, ViewControllerEventHandlerContext.RMIVV);
        ModelGate.getModel().registerVirtualView();
    }

    /** start the game with the GUI interface, once opened it the user will be given the possibility to switch to CLI mode*/
     static void startClientSocketOrRMIWithGUI(){
        GUIstarter.begin();
    }

    /**possibility to generate random choices*/
    private static boolean randomGame = false;

    /**@return randomGame*/
    public static boolean isRandomGame() {
        return randomGame;
    }
    /**@param randomGame above mentioned*/
    public static void setRandomGame(boolean randomGame) {
        Controller.randomGame = randomGame;
    }
    /**creates the right view according to the user's will,
     * @param ip needed to initialize both socket and rmi networkhandler
     * @param networkConnection  needed to create the view and initialize the right NetworkHandler
     * @param port needed to initialize the socket network handler
     * @param userInterface needed to create the view and initialize both of the networkhandler
     * @return boolean value that indicates if the connection attempt was successful*/
    public static boolean connect(String networkConnection, String userInterface, String ip, String port){

        setNetworkConnection(networkConnection);
        Controller.setIp(ip);
        Controller.setPort(port);
        Controller.userInterface = userInterface;
        View view=new View(networkConnection, userInterface);

        if(networkConnection.equalsIgnoreCase("rmi")){
            try {
                 setRmiNetworkHandler(new RmiNetworkHandler(ip, Integer.parseInt(port), view));
            } catch (IOException e) {
                logger.log(Level.SEVERE, "EXCEPTION", e);
                return false;
            }
        }
        else {

            try{
                new SocketNetworkHandler(InetAddress.getByName(ip), Integer.parseInt(port), view);
            }
            catch (NumberFormatException|  IOException e){
                if(userInterface.equalsIgnoreCase("GUI")){
                    GUIstarter.showError("Controller"," CONNECTION WASN'T POSSIBLE ", e);
                }else{
                    logger.severe("CONNECTION WASN'T POSSIBLE"+e.getMessage());
                }
                return false;
            }
        }
        return true;
    }



    /**allows the first interaction with the server in CLI,
     * sending asked information in order to
     * create a client*/
    public static void startClientSocketOrRMIWithCLI() {
        Scanner br = new Scanner(System.in);

        out.println("\n\n");
        out.println(GameConstant.ADRENALINE_TITLE_7);
        out.println("\n\n\n");
        out.println("\nWhat's your Nickname?");

        String nickname = br.nextLine();
        ViewModelGate.setMe(nickname);

        out.println("\n<CLIENT> Do you want to play with:");

        CLISelector.showListOfRequests(Arrays.asList("SOCKET","rmi"));
        String networkConnectionChoice;
        int choice = CLISelector.askNumber(0,1);
        if(choice == 1){
            networkConnectionChoice = "rmi";
        }
        else{
            networkConnectionChoice = "SOCKET" ;
        }

        String ip;

        String port;

        String userInterface = "CLI";

        if (networkConnectionChoice.equalsIgnoreCase("rmi")) {
            out.println("<CLIENT> Starting Client with rmi connection");

            //ask for IP and PORT
            out.println("\n<CLIENT> Insert Server's IP:");
            ip = br.nextLine();

            port = "1099";
        } else {
            out.println("<CLIENT> Starting Client with socket connection");

            out.println("\n<CLIENT> Insert Server IP:");
            ip = br.nextLine();
            out.println("\n<CLIENT> Insert Port:");
            port = br.nextLine();
        }

        out.println("\n<CLIENT> DO YOU WANT TO PLAY WITH AUTOMATIC RANDOM CHOICES?");
        CLISelector.showListOfRequests(Arrays.asList("HELL YES !!","nope, thanks."));
        Controller.setRandomGame(false);
        choice = CLISelector.askNumber(0,1);
        if(choice == 0){
            Controller.setRandomGame(true);
        }

        if (!connect(networkConnectionChoice, userInterface, ip, port)) {
            startClientSocketOrRMIWithCLI();
        }
    }

}
