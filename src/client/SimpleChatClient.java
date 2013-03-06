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
public class SimpleChatClient extends Thread {
    
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
            state = CONNECTED;
            start();
        }
    }
    
    @Override
    public void run(){
        boolean keepRunning = true;
        while(keepRunning){
            String msg = input.nextLine();
            System.out.println(msg);
        }
    }
    
    public void disconnect(){
        
    }
    
    public void sendMessage(String reciver,String msg){
        String message = "SEND#"+reciver+"#"+msg;
        output.println(message);
        
    }
    
    public void addMessageArrivedEventListener(){
        
    }
    
    public void removeMessageArrivedEventListener(){
        
    }
    public static void main(String[] args) throws InterruptedException {
        SimpleChatClient client = new SimpleChatClient();
        SimpleChatClient client2 = new SimpleChatClient();
        try {
            client.connect("localhost", 5000, "lol");
 //           Thread.sleep(1000);
            client2.connect("localhost", 5000, "WOOP");
 //           Thread.sleep(1000);
            client.sendMessage("WOOP", "LOLOLOLOLOLOL");
        } catch (IOException ex) {
            Logger.getLogger(SimpleChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
