package it.polimi.se2019.networkHandler.Socket;

import it.polimi.se2019.model.events.ViewControllerEvent;
import it.polimi.se2019.model.events.ViewControllerEventGameSetUp;
import it.polimi.se2019.networkHandler.NetworkHandler;

import java.io.*;
import java.net.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class SocketNetworkHandler extends NetworkHandler implements Observer{

    private Socket socket;

    private int port;

    private InetAddress inetAddress;

    private ObjectOutputStream oos;

    private ObjectInputStream ois;

    private Observer view;

    public SocketNetworkHandler(InetAddress inetAddress, int port, Observer view){
        this.view = view;

        this.port = port;
        this.inetAddress = inetAddress;

        try {
            System.out.println("<CLIENT>New Client with IP: " + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e ){
            e.printStackTrace();
        }
        System.out.println("<CLIENT>Trying to connect to: " + this.inetAddress.getHostAddress() + ":" + this.port);
        try {
            this.socket = new Socket(this.inetAddress, this.port);
            System.out.println("<CLIENT>Connected to: " + this.inetAddress.getHostAddress() + ":" + this.port);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        try {
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.ois = new ObjectInputStream(this.socket.getInputStream());

            ServerListenerNetworkHandler sl = new ServerListenerNetworkHandler(socket, ois, view);

            new Thread(sl).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //da cancellare quando scriveremo la view.
        this.testingGameWithFakeInputs();
    }


    public void testingGameWithFakeInputs(){
        Scanner br = new Scanner(System.in);

        String gameMode;
        String mapChoice;
        int numberOFSkulls;
        boolean isFinalFrenzy;
        boolean isBot;

        System.out.println("<CLIENT>Insert gameMode:");
        gameMode = br.next();
        System.out.println("<CLIENT>Insert mapChoice:");
        mapChoice = br.next();
        System.out.println("<CLIENT>Insert numberOfStartingSKulls:");
        numberOFSkulls = br.nextInt();
        System.out.println("<CLIENT>Insert Final frazy:");
        isFinalFrenzy = br.nextBoolean();
        System.out.println("<CLIENT>Insert bot:");
        isBot = br.nextBoolean();

        ViewControllerEvent VCE = new ViewControllerEventGameSetUp(gameMode,mapChoice,numberOFSkulls,isFinalFrenzy,isBot);
        try {
            this.oos.writeObject(VCE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        ViewControllerEvent VCE = (ViewControllerEvent) arg;

        try {
            this.oos.writeObject(VCE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
