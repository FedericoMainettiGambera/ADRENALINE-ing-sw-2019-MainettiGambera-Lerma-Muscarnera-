package it.polimi.se2018.virtualView;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketVirtualView extends VirtualView {

    private ServerSocket serverSocket;

    private Socket socket;

    private int port;

    public void SocketVirtualView(){
        try{
            serverSocket = new ServerSocket(port);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void StartServer(){
        while(true){
            try{
                socket = serverSocket.accept();

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                Thread t = new ClientHandlerVirtualView(socket, oos, ois);

                t.start();
            }
            catch(Exception e){
                e.printStackTrace();
                try {
                    socket.close();
                }
                catch(IOException a) {
                    a.printStackTrace();
                }
            }
        }
    }

}
