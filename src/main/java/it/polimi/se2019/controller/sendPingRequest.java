package it.polimi.se2019.controller;

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
}
