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
    private static Socket socket;
    static char caracter;
    
    

    public ServerThreadsClient(Socket socket) {
        this.socket = socket;
    }

    
    
    @Override
    public void run() {
        InputStream inputStream; 
        DataInputStream din;
        OutputStream outputStream;
        DataOutputStream don;
        try {
            inputStream = socket.getInputStream();
            din= new DataInputStream(inputStream);
            outputStream = socket.getOutputStream();
            don = new DataOutputStream(outputStream);
            while(true){
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
                        System.out.println(response_login);
                        String okay = "";
                        try {
                            okay = ui.Main.login(response_login);
                            System.out.println(okay);
                        } catch (Exception ex) {
                            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if ((okay == "Wrong credentials, please try again!") || (okay == "Invalid role")) {
                            don.writeUTF(okay);
                        } // We check the role
                        else if (okay == "Welcome patient !" ) {
                           don.writeUTF(okay);
                           menuPatient();
                        }
                        break;
                    case 0:
                        ui.Main.GoodBye();
                        return;
                    default:
                        break;
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

    private static void menuPatient() throws IOException{
        InputStream inputStream; 
        DataInputStream din;
        OutputStream outputStream;
        DataOutputStream don;
        try {
            inputStream = socket.getInputStream();
            din= new DataInputStream(inputStream);
            outputStream = socket.getOutputStream();
            don = new DataOutputStream(outputStream);
            while(true) {
                int choice= din.readInt();
                System.out.println(choice);
                switch (choice) {
                    case 1:
                       String response_form = din.readUTF();
                       String okay="";

                        try {
                            okay= ui.Main.completeForm(response_form);
                        } catch (Exception ex) {
                            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if ((okay == "There was an error while saving") || (okay == "")) {

                        } // We check the role
                        else if (okay == "Form saved successfully" ) {
                           don.writeUTF(okay);
                        }
                        break;
                    case 2:
                        String response_login = din.readUTF();
                        okay = "";
                        try {
                            okay = ui.Main.login(response_login);
                        } catch (Exception ex) {
                            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if ((okay == "Wrong credentials, please try again!") || (okay == "Invalid role")) {

                        } // We check the role
                        else if (okay == "Welcome patient !" ) {
                           don.writeUTF(okay);

                           menuPatient();

                        }
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        String newName = din.readUTF();
                        try {
                            okay = ui.Main.changeUsername(newName);
                            don.writeUTF(okay);
                        } catch (Exception ex) {
                            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case 6:
                        String newPassword = din.readUTF();
                        try {
                            okay = ui.Main.changePassword(newPassword);
                            don.writeUTF(okay);
                        } catch (Exception ex) {
                            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case 7:
                        //releaseResourcesClient(inputStream, outputStream, din, don);
                        return;
                    default:
                        break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private static void releaseResourcesClient(InputStream inputStream, OutputStream outputStream, DataInputStream din, DataOutputStream don) {
        try {
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerReceiveCharactersViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            outputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerReceiveCharactersViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            din.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerReceiveCharactersViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            don.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerReceiveCharactersViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
