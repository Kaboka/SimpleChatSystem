
package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleChatClientHandler extends Thread {
    
    public static void handleClient (Socket socket) throws IOException{
                //Don't forget to turn on autoFlush
        PrintWriter output = new PrintWriter(socket.getOutputStream(),true);
        Scanner input = new Scanner(socket.getInputStream());
        //Important: This is a blocking call.
        String msg = input.nextLine();
        System.out.println(msg);
        while(input.hasNext()){
            output.println(msg);
            msg = input.nextLine();
        }
        socket.close();
    }
    
    public void run(){
        
    }
}
