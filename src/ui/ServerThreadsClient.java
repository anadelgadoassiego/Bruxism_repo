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
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojos.Doctor;
import pojos.Ecg;
import pojos.Emg;

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
        } catch (Exception ex) {
            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

    private static void menuPatient() throws IOException, Exception{
        InputStream inputStream2; 
        DataInputStream din2;
        OutputStream outputStream2;
        DataOutputStream don2;
        ObjectOutputStream objectOutputStream = null;
        try {
            inputStream2 = socket.getInputStream();
            din2= new DataInputStream(inputStream2);
            outputStream2 = socket.getOutputStream();
            don2 = new DataOutputStream(outputStream2);
            objectOutputStream = new ObjectOutputStream(outputStream2);
            while(true) {
                int choice= din2.readInt();
                System.out.println(choice);
                switch (choice) {
                    case 1:
                       String response_form = din2.readUTF();
                       String okay="";

                        try {
                            okay= ui.Main.completeForm(response_form);
                        } catch (Exception ex) {
                            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if ((okay == "There was an error while saving") || (okay == "")) {

                        } // We check the role
                        else if (okay == "Form saved successfully" ) {
                           don2.writeUTF(okay);
                        }
                        break;
                    case 2:
                        String response_EMG_ECG = din2.readUTF();
                        
                        int EMG_value, ECG_value;
                        List <Integer> arrayEMG = new ArrayList <Integer>();
                        List <Integer> arrayECG = new ArrayList <Integer>();
                        
                        
                        while((EMG_value=din2.readInt()) != -10000){
                            //System.out.println("EMG he llegado: "+EMG_value);
                            arrayEMG.add(EMG_value);
                        }
                        
                        while((ECG_value=din2.readInt()) != -20000){
                            //System.out.println("He llegado ECG: "+ECG_value);
                            arrayECG.add(ECG_value);
                        }
                        
                        ui.Main.addEMG_addECG(response_EMG_ECG,arrayEMG,arrayECG);
                        break;
                    case 3:
                        List<Emg> emgList = new ArrayList <Emg>();;
                        emgList = ui.Main.searchEMGByName();
                        for(int i = 0; i < emgList.size(); i++) {
                            objectOutputStream.writeObject(emgList.get(i));
                        }
                        objectOutputStream.writeObject(null);
                        break;
                    case 4:
                        List<Ecg> ecgList = new ArrayList <Ecg>();;
                        ecgList = ui.Main.searchECGByName();
                        for(int i = 0; i < ecgList.size(); i++) {
                            objectOutputStream.writeObject(ecgList.get(i));
                        }
                        objectOutputStream.writeObject(null);
                        break;
                    case 5:
                        String newName = din2.readUTF();
                        try {
                            okay = ui.Main.changeUsername(newName);
                            don2.writeUTF(okay);
                        } catch (Exception ex) {
                            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case 6:
                        String newPassword = din2.readUTF();
                        try {
                            okay = ui.Main.changePassword(newPassword);
                            don2.writeUTF(okay);
                        } catch (Exception ex) {
                            Logger.getLogger(ServerThreadsClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case 7:
                        List<Doctor> doctorList = new ArrayList <Doctor>();;
                        doctorList = ui.Main.availableDoctors();
                        for(int i = 0; i < doctorList.size(); i++) {
                            objectOutputStream.writeObject(doctorList.get(i));
                        }
                        objectOutputStream.writeObject(null);
                        int doctor_id = din2.readInt();
                        ui.Main.chooseDoctor(doctor_id);
                        break;
                    case 8:
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
