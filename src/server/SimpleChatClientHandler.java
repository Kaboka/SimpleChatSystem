package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleChatClientHandler extends Thread {


    private PrintWriter output;
    private Scanner input;
    private boolean keepRunning = true;
    private SimpleChatServer server;
    private String userName;
    private Socket socket;
    private final int NOTCONNECTED = 0;
    private final int CONNECTED = 1;
    private final int CLOSED = 2;
    private int state = NOTCONNECTED;

    public SimpleChatClientHandler(){
        
    }
    public SimpleChatClientHandler(SimpleChatServer server, Socket socket) throws IOException {
        output = new PrintWriter(socket.getOutputStream(), true);
        input = new Scanner(socket.getInputStream());
        this.socket = socket;
        this.server = server;

    }

    @Override
    public void run() {
        String command;
        while(keepRunning){
            String msg = "";
            while (input.hasNext()) {
                msg = input.nextLine();
                System.out.println(msg);
                command = msg.substring(0,msg.indexOf("#"));
                if(state == NOTCONNECTED && command.equals("CONNECT")){
                    System.out.println(msg.substring(msg.indexOf("#")+1));
                    server.addClient(msg.substring(msg.indexOf("#")+1), this);
                    userName = msg.substring(msg.indexOf("#")+1);
                    server.online();
                    state = CONNECTED;
                }else if(state == CONNECTED && command.equals("SEND" )){
                    msg = msg.substring(msg.indexOf("#")+1);
                    server.message(msg,userName);
                }else if(state == CONNECTED && command.equals("CLOSE"))
                {
                    server.close(userName);
                    state = CLOSED;
                }else{
                    throw new IllegalStateException();
                }
                
            }
        }
    }
    
    public void send(String msg){
        output.println(msg);
          try {
        if(msg.equals("CLOSE#")){
            socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(SimpleChatClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
}
