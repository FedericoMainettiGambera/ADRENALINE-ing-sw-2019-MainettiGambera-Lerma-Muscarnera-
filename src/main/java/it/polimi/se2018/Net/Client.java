package it.polimi.se2018.Net;
import java.net.*;
import java.io.*;

public class Client {



    Socket mySocket;
    int port=9333;
    DataInputStream in;
    DataOutputStream out;

    public Socket connect() {
        try {

            System.out.println("0.provo a connettermi al server");
            Socket server = new Socket(InetAddress.getLocalHost(), port); //connessione alla socket, in questo caso il server è su localhost


            System.out.println("1.connesso!");
            in= new DataInputStream(mySocket.getInputStream());
            out=new DataOutputStream(mySocket.getOutputStream());


        }
        catch (UnknownHostException e) {
            System.err.println("unknown host");
                         }

        catch(Exception e) {

              System.err.println("Impossibile stabilire connessione");
        }


      return mySocket;
    }

    }


