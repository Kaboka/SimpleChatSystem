/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicklas Hemmingsen
 */
public class SimpleChatClient {
    
    private static final int NOTCONNECTED = 0;
    private static final int CONNECTED = 1;
    private static final int CLOSING = 2;
    
    private int state = NOTCONNECTED;

    private Socket socket;
    private PrintWriter output;
    private Scanner input;
    
    
    public void connect(String ip, int port, String userName) throws IOException{
        if(state != NOTCONNECTED){
            throw new IllegalStateException();
        }else{
            socket = new Socket(ip,port);
            output = new PrintWriter(socket.getOutputStream(),true);
            input = new Scanner(socket.getInputStream());
            output.println("CONNECT#"+userName);
//            output.flush();
            state = CONNECTED;
        }
    }
    
    public void disconnect(){
        
    }
    
    public void sendMessage(String reciver,String msg){
        
    }
    
    public void addMessageArrivedEventListener(){
        
    }
    
    public void removeMessageArrivedEventListener(){
        
    }
    public static void main(String[] args) {
        SimpleChatClient client = new SimpleChatClient();
        try {
            client.connect("localhost", 1234, "lol");
        } catch (IOException ex) {
            Logger.getLogger(SimpleChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
