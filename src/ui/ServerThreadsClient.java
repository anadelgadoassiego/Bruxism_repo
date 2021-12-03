/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThreadsClient implements Runnable {

    int byteRead;
    Socket socket;
    static char caracter;
    
    

    public ServerThreadsClient(Socket socket) {
        this.socket = socket;
    }

   
    
    @Override
    public void run() {
        
        InputStream inputStream = null;
        DataInputStream din;
        OutputStream outputStream = null;
        DataOutputStream don;
        try {
            inputStream = socket.getInputStream();
            din= new DataInputStream(inputStream);
            outputStream = socket.getOutputStream();
            don = new DataOutputStream(outputStream);
            int choice= din.readInt();
            System.out.println(choice);
        switch (choice) {
                case 1:
                   String response = din.readUTF();
                   
                    try {
                        ui.Main.newUserPatient(response);
                    } catch (Exception ex) {
                        Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
                    break;
                case 2:
                    String response_login = din.readUTF();
                    String okay = null;
                    try {
                        okay = ui.Main.login(response_login);
                    } catch (Exception ex) {
                        Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (okay == "Wrong credentials, please try again!") {
                        
                    } // We check the role
                    else if (okay == "Welcome patient !" ) {
                       don.writeUTF(okay);
                       //menuPatient();
                       
                    }  else {
                        System.out.println("Invalid role");
                    }
                    break;
                case 0:
                    dbManager.disconnect();
                    userManager.disconnect();
                    return;
                default:
                    break;
            }
            //We read until is finished the connection or character 'x'
            while (((byteRead = inputStream.read()) != -1) || (byteRead != 'x')) {
                caracter = (char) byteRead;
                
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("Character reception finished");
            releaseResourcesClient(inputStream, socket);
        }
        

    }

    private static void releaseResourcesClient(InputStream inputStream, Socket socket) {
        try {
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerReceiveCharactersViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerReceiveCharactersViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
