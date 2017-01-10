package sample;

/**
 * Created by mitesh on 3/30/16.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * Idea behind this class.
 * ======================
 *
 * Makes this application a server to which clients can connect.
 *
 * Keeps listening for clients using sockets.
 * If socket tries to connect, accept it and push to a new thread.
 * This keeps the connection alive.
 *
 * The newly created thread deals with everything else.
 * This class ONLY listens and creates the threads.
 *
 * The private inner class ChatHandler handles the functions of each
 * client thread created.
 */

public class ChatServer {
    private static final int PORT = 8182;
    private static Set<PrintWriter> writerSet = new HashSet<PrintWriter>();

    public static void start(){
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            int i = 0;
            while (true){
                System.out.print(i);
                Socket clientSocket = serverSocket.accept();
                new ChatHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ChatHandler extends Thread{
        /**
         * Handles chat connections of each client connected.
         * Sends message to every other client.
         */
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;

        public ChatHandler(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            writerSet.add(out);
        }

        @Override
        public void run(){
            try{
                while (true){
                    System.out.print("1");
                    String message = in.readLine();
                    for(PrintWriter writerMember : writerSet){
                        writerMember.println(clientSocket.getInetAddress() + ": " + message);
                    }
                }
            } catch (IOException e){
                System.out.println(e);
            } finally {
                writerSet.remove(out);
                try {
                    this.clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
