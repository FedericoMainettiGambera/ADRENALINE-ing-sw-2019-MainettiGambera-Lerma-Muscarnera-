package it.polimi.se2019;

import it.polimi.se2019.networkHandler.Socket.ServerListenerNetworkHandler;
import it.polimi.se2019.view.View;
import org.junit.Test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TestServerListenerNH{

    Socket socket;
    ObjectInputStream oos;
    View view;
    ServerListenerNetworkHandler SLNH=new ServerListenerNetworkHandler(socket,oos, view );

    @Test
    public void TestServer(){

    }


}
