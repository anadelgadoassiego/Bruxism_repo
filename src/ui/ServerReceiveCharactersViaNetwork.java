package ui;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerReceiveCharactersViaNetwork {
    
     public static void executeMenu(){ 
        new Main();
        try {
            Main.Menu();
        } catch (Exception ex) {
            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String args[]) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        try {
            while (true) {
                executeMenu();
                //This executes when we have a client
                Socket socket = serverSocket.accept();
                //new ServerThreadsClient(socket).executeMenu();
                //executeMenu();
                new Thread(new ServerThreadsClient(socket)).start();
                
                
            }
        } finally {
            releaseResourcesServer(serverSocket);
        }
    }

    private static void releaseResourcesServer(ServerSocket serverSocket) {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
