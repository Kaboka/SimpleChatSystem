package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SimpleChatClientHandler extends Thread {


    private PrintWriter output;
    private Scanner input;
    private boolean keepRunning = true;
    private SimpleChatServer server;

    public SimpleChatClientHandler(SimpleChatServer server, Socket socket) throws IOException {
        output = new PrintWriter(socket.getOutputStream(), true);
        input = new Scanner(socket.getInputStream());
        this.server = server;
    }

    public void run() {

        while(keepRunning){
            String msg = input.nextLine();
            System.out.println(msg);
            while (input.hasNext()) {
                output.println(msg);
                msg = input.nextLine();
                
            }
        }
    }
}
