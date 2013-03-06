/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicklas Hemmingsen
 */
public class SimpleChatServer {

    public static final int PORT = 1234;
    private static ServerSocket serverSocket;
    private Map<String,SimpleChatClientHandler> clients;

//    public String online(){
//        String online = "ONLINE#";
//        for(String user: clients.keySet()){
//            online += user+",";
//        }
//        return online;
//    }
    
    public void listen(){
                System.out.println("Server is started");
                clients = new HashMap<>();
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                SimpleChatClientHandler ch = new SimpleChatClientHandler(this, socket);
                ch.start();
                System.out.println("Got a new client");
            }
        } catch (IOException ex) {
            Logger.getLogger(SimpleChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        SimpleChatServer server = new SimpleChatServer();
        server.listen();
    }
}
