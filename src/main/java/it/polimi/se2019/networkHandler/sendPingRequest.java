package it.polimi.se2019.networkHandler;

import java.io.IOException;
import java.net.UnknownHostException;
import java.io.*;
import java.net.*;

public class sendPingRequest{

    public static boolean sendPingRequest(String IP)throws IOException{
        InetAddress Server=InetAddress.getByName(IP);
        if(Server.isReachable(5000)){
           return true;
        }
        else{
           return false;
        }
    }


    public static boolean available(int port) {
        if (port < 49152&&port!=1099 || port > 65535 ) {
            throw new IllegalArgumentException("Invalid start port: " + port);
        }

        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                }
            }
        }

        return false;
    }

}
