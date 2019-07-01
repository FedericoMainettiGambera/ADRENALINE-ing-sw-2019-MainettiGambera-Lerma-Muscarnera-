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

    private static RmiNetworkHandler rmiNetworkHandler;

    public static RmiNetworkHandler getRmiNetworkHandler() {
        return rmiNetworkHandler;
    }

    private static void setRmiNetworkHandler(RmiNetworkHandler rmiNetworkHandler) {
        Controller.rmiNetworkHandler = rmiNetworkHandler;
    }

    private static String networkConnection;

    public static String getNetworkConnection() {
        return networkConnection;
    }

    private static void setNetworkConnection(String networkConnection) {
        Controller.networkConnection = networkConnection;
    }

    private static String userInterface;

    public static String getUserInterface() {
        return userInterface;
    }

    private static String ip;

    private static void setIp(String ip) {
        Controller.ip = ip;
    }

    public static String getIp() {
        return ip;
    }

    private static String port;

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        Controller.port = port;
    }

    static void startServerWithRMIAndSocket(){

        //initialize model
        out.println("<SERVER> Creating the Game.");
        ModelGate.model = new Game();
        out.println("<SERVER> Creating a PlayerList.");
        PlayersList pl = new PlayersList();
        ModelGate.model.setPlayerList(pl);

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
        ModelGate.model.setVirtualView(ViewControllerEventHandlerContext.socketVV, ViewControllerEventHandlerContext.RMIVV);
        ModelGate.model.registerVirtualView();
    }

     static void startClientSocketOrRMIWithGUI(){
        GUIstarter.begin();
    }

    private static boolean randomGame = false;

    public static boolean isRandomGame() {
        return randomGame;
    }

    public static void setRandomGame(boolean randomGame) {
        Controller.randomGame = randomGame;
    }

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



    public static void startClientSocketOrRMIWithCLI() {
        Scanner br = new Scanner(System.in);

        out.println("\n\n");
        out.println(GameConstant.AdrenalineTitle4);
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
