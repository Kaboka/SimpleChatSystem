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

    public static final int PORT = 5000;
    private static ServerSocket serverSocket;
    private Map<String, SimpleChatClientHandler> clients;

    public synchronized void online() {

        String online = "ONLINE#";
        for (String user : clients.keySet()) {
            online += user + ",";
        }
        for (SimpleChatClientHandler handler : clients.values()) {
            handler.send(online);
        }
    }
    
    public synchronized void message(String msg){
        String reciever = msg.substring(0, msg.indexOf("#"));
         for (String key : clients.keySet()) {
             if(key.equals(reciever))
                clients.get(key).send(msg);
                
                System.out.println(clients.get(key));
        }
    }
    
    public void close(String msg){
        String reciever = msg.substring(0, msg.indexOf("#"));
        for (String key : clients.keySet()) {
             if(key.equals(reciever))
                clients.remove(key);
        }
    }

    public synchronized void addClient(String name, SimpleChatClientHandler handler) {
        clients.put(name, handler);
    }

    public void listen() {
        System.out.println("Server is started");
        clients = new HashMap<>();
        try {
//            clients.put("HANS", new SimpleChatClientHandler());
//            clients.put("Ole", new SimpleChatClientHandler());
//            clients.put("Erik", new SimpleChatClientHandler());
//            clients.put("Sigurd", new SimpleChatClientHandler());

            serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                new SimpleChatClientHandler(this, socket).start();
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
