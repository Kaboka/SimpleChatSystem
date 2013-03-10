/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
        Logger.getLogger(SimpleChatServer.class.getName()).log(Level.INFO, String.format("Sendt the message: %s  ", online));
    }

    public synchronized void message(String msg, String userName) {
//        System.out.println(msg);
        String receiverString = msg.substring(0, msg.indexOf("#"));
        String[] receivers = receiverString.split(",");
        String finalMessage = msg.substring(msg.indexOf("#") + 1);
        if (!receiverString.equals("*")) {
            for (String key : clients.keySet()) {
                for (int i = 0; i < receivers.length; i++) {
                    if (key.equals(receivers[i])) {
                        clients.get(key).send("MESSAGE#" + userName + "#" + finalMessage);
                        System.out.println(clients.get(key));
                    }
                    Logger.getLogger(SimpleChatServer.class.getName()).log(Level.INFO, String.format("Sendt the message: %s  ", msg));
                }
            }
        } else {
            for (String key : clients.keySet()) {
                clients.get(key).send("MESSAGE#" + userName + "#" + finalMessage);
                System.out.println("All clients: " + clients.get(key));
                Logger.getLogger(SimpleChatServer.class.getName()).log(Level.INFO, String.format("Sendt the message: %s  ", msg));
            }
        }
    }

    public void close(String userName) {
        clients.get(userName).send("CLOSE#");
        clients.remove(userName);
        online();
        Logger.getLogger(SimpleChatServer.class.getName()).log(Level.INFO, "Sendt the message CLOSE");
        Logger.getLogger(SimpleChatServer.class.getName()).log(Level.INFO, String.format("Removed the client: %s from list of clients ", userName));
    }

    public synchronized void addClient(String name, SimpleChatClientHandler handler) {
        clients.put(name, handler);
    }

    public void listen() {
        System.out.println("Server is started");
        clients = new HashMap<>();
        Logger.getLogger(SimpleChatServer.class.getName()).log(Level.INFO, "Started the server: " + new Date().toString());
        try {

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
        try {
            Logger logger = Logger.getLogger(SimpleChatServer.class.getName());
            FileHandler fileTxt;
            fileTxt = new FileHandler("Logging.txt");
            java.util.logging.Formatter formatterTxt = new SimpleFormatter();
            fileTxt.setFormatter(formatterTxt);
            logger.addHandler(fileTxt);
            SimpleChatServer server = new SimpleChatServer();
            server.listen();
        } catch (IOException ex) {
            Logger.getLogger(SimpleChatServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(SimpleChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
