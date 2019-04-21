package it.polimi.se2018.Net;

import java.io.*;
import java.net.*;

public class Server {


    ServerSocket server=null;
    Socket socketClient=null;
    int porta=9333; //porta server

    DataInputStream in;
    DataOutputStream out;

public Socket waitRequest()
{
try {
    server = new ServerSocket(porta);
    socketClient = server.accept(); //aspetto richieste sulla porta 9333 e in caso ci siano inizliazzo socketclient con la richiesta in entrata
   // server.close();//evitare connessioni multiple //

    in=new DataInputStream(socketClient.getInputStream());
    out=new DataOutputStream(socketClient.getOutputStream());

}

catch(IOException e)
{e.printStackTrace();}

return socketClient;


}


}
