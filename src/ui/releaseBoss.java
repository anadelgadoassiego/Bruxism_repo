/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static ui.ServerReceiveCharactersViaNetwork.number_users_conected;
import static utils.InputOutput.getStringFromKeyboard;

/**
 *
 * @author RAQUEL
 */
public class releaseBoss implements Runnable  {
    ServerSocket socket;
     public releaseBoss(ServerSocket socket) {
        this.socket = socket;
    }
 
    public void run() {
    /* String answer = getStringFromKeyboard("¿Do you want to disconect the server?(yes/no)");
                if(answer.equals("yes")){
                    
                    if(number_users_conected==0){
                        boolean desconexion = true;
                        String user = "BOSS_SERVER";
                        String password = getStringFromKeyboard("Please introduce the password");
                        String correct = ui.Main.BossUser(user,password);
                        if(correct.equals("Welcome doctor !")){
                            ui.Main.GoodBye();
                            releaseResourcesServer(socket);
                        }else{
                            System.out.println("WRONG PASSWORD");
                        }
                        
                    }else{
                        System.out.println("There are clients conected");
                    }
                }else{
                    
                }*/
    ui.clouse.main();
    }
    
    public static void releaseResourcesServer(ServerSocket serverSocket) {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
