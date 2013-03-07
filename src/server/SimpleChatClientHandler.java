package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleChatClientHandler extends Thread {


    private PrintWriter output;
    private Scanner input;
    private boolean keepRunning = true;
    private SimpleChatServer server;
    private String userName;

    public SimpleChatClientHandler(){
        
    }
    public SimpleChatClientHandler(SimpleChatServer server, Socket socket) throws IOException {
        output = new PrintWriter(socket.getOutputStream(), true);
        input = new Scanner(socket.getInputStream());
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
                if(command.equals("CONNECT")){
                    System.out.println(msg.substring(msg.indexOf("#")+1));
                    server.addClient(msg.substring(msg.indexOf("#")+1), this);
                    userName = msg.substring(msg.indexOf("#")+1);
                    server.online();
                }else if(command.equals("SEND")){
                    msg = msg.substring(msg.indexOf("#")+1);
                    server.message(msg,userName);
                }else if(command.equals("CLOSE"))
                {
                    server.close(userName);
                }
                
            }
        }
    }
    
    public void send(String msg){
        output.println(msg);
    }
}
