package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by mitesh on 3/31/16.
 */
public class ChatClient {
    private static final int PORT = 8182;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ChatClient(){
        System.out.print("2c");
    }

    public void connect(String serverAdd){
        System.out.print("3c");
        try {
            this.socket = new Socket(serverAdd, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        String message = null;
                        try {
                            message = in.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        updateMessageWindow(message);
                    }
                }
            })
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateMessageWindow(String message) {

    }

    public void sendMessage(String message){
        System.out.print("4c");
        out.println(message);
    }
}
