/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import interfaces.MessageArrivedEvent;
import interfaces.MessageArrivedListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.event.EventListenerList;

/**
 *
 * 
 * 
 * @author Nicklas Hemmingsen
 */

public class SimpleChatClient extends Thread {
    
    private static final int NOTCONNECTED = 0;
    private static final int CONNECTED = 1;
    private static final int CLOSING = 2;
    private static final int CLOSED = 3;
    
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
        while(state == CONNECTED){
            String msg = input.nextLine();
                fireMessageArrivedEventEvent(new MessageArrivedEvent(this,msg.substring(0,
                        msg.indexOf("#")), msg.substring(msg.indexOf("#")+1)));
            System.out.println(msg);
        }
        while(state == CLOSING){
            String msg = input.nextLine();
            if(msg.equals("CLOSE#")){
                state = CLOSED;
            }
        }
    }
    
    public void disconnect(){    
        if(state != CONNECTED){
            throw new IllegalStateException();
        }else{
            state = CLOSING;
            output.println("CLOSE#");
        }
    }
    
    public void sendMessage(String receiver,String msg){
        if(state != CONNECTED)
        {
            throw new IllegalStateException();
        }else{
            String message = "SEND#"+receiver+"#"+msg;
            output.println(message);
        }
        
    }
  
  protected EventListenerList listenerList = new EventListenerList();
    
  public void addMessageArrivedListener(MessageArrivedListener listener)
  {
    listenerList.add(MessageArrivedListener.class, listener);
  }

  public void removeMessageArrivedListener(MessageArrivedListener listener)
  {
    listenerList.remove(MessageArrivedListener.class, listener);
  }

  private void fireMessageArrivedEventEvent(MessageArrivedEvent evt)
  {
    MessageArrivedListener[] listeners = listenerList.getListeners(MessageArrivedListener.class);
    for (MessageArrivedListener listener : listeners) {
      listener.MessageArrived(evt);
    }
  }
    
    public static void main(String[] args) {
        
        
//        SimpleChatClient client = new SimpleChatClient();
//        SimpleChatClient client2 = new SimpleChatClient();
//        try {
//            client.connect("localhost", 5000, "userA");
// //           Thread.sleep(1000);
//            client2.connect("localhost", 5000, "UserB");
// //           Thread.sleep(1000);
//            client.sendMessage("UserB", "Hello");
//              client.disconnect();

//        } catch (IOException ex) {
//            Logger.getLogger(SimpleChatClient.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
