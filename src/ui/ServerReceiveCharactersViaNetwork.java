package ui;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static utils.InputOutput.getStringFromKeyboard;


public class ServerReceiveCharactersViaNetwork {
    static int number_users_conected=0;
    static boolean desconexion=false;
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
        
        new Thread((Runnable) new releaseBoss(serverSocket)).start();
        try {
            while (true) {
                executeMenu();
                //This executes when we have a client
        
                Socket socket = serverSocket.accept();
                
                number_users_conected++;
                new Thread(new ServerThreadsClient(socket)).start();
               
                
            }
        }catch(IOException ex){
            System.out.println("¡Goodbye! Server and database dssconnected");
        }finally {
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
