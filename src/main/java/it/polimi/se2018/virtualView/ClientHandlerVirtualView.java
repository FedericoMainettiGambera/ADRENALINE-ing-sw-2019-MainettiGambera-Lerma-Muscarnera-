package it.polimi.se2018.virtualView;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandlerVirtualView extends Thread {

    private Socket socket;

    private ObjectOutputStream oos;

    private ObjectInputStream ois;

    ClientHandlerVirtualView(Socket socket, ObjectOutputStream oos, ObjectInputStream ois){
        this.socket = socket;
        this.oos = oos;
        this.ois = ois;
    }

    @Override
    public void run(){

        //TODO

    }

}
