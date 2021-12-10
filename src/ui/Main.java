/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import db.interfaces.*;
import db.jpa.JPAUserManager;
import pojos.*;
import db.sqlite.SQLiteManager;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import pojos.users.Role;
import pojos.users.User;
import static utils.InputOutput.getIntFromKeyboard1to10;

public class Main {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Each time dates are added
    private static BufferedReader reader; // To read from the console
    private static DBManager dbManager;
    private static PatientManager patientManager;
    private static DoctorManager doctorManager;
    private static EmgManager emgManager;
    private static EcgManager ecgManager;
    private static UserManager userManager;
    private static String doctorName = "";
    private static String patientName = "";

    public static void Menu() throws Exception {

        // In order to connect with the DB
        dbManager = new SQLiteManager();
        dbManager.connect();
        patientManager = dbManager.getPatientManager();
        doctorManager = dbManager.getDoctorManager();
        emgManager = dbManager.getEmgManager();
        ecgManager = dbManager.getEcgManager();
        dbManager.createTables();
        userManager = new JPAUserManager();
        userManager.connect();

        //To initialize the bufferedReader
        reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to our database!");
        newRole();

    }

    private static void newRole() throws Exception {
        String roleName = "patient";
        Role role = new Role(roleName);
        userManager.createRole(role);
        roleName = "doctor";
        role = new Role(roleName);
        userManager.createRole(role);
        System.out.println("Roles Created!!");
    }

    public static void newUserDoctor(String response) throws Exception {

        String name, username, password;
        String totalText[] = response.split(",");
        name = totalText[0];
        username = totalText[1];
        password = totalText[2];
        int roleId = Integer.parseInt(totalText[3]);

        // Create the password's hash
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] hash = md.digest();
        // Show all the roles and let the user choose one
        Role chosenRole = userManager.getRole(roleId);
        // Create the user and store it
        User user = new User(username, hash, chosenRole);
        userManager.createUser(user);
        Doctor doctor = new Doctor(name, username);
        doctorManager.add(doctor);

    }

    public static void newUserPatient(String response) throws Exception {
        String name, gender, username, password;
        String totalText[] = response.split(",");
        name = totalText[0];
        int age = Integer.parseInt(totalText[1]);
        float weight = Float.parseFloat(totalText[2]);
        float height = Float.parseFloat(totalText[3]);
        gender = totalText[4];
        username = totalText[5];
        password = totalText[6];
        int roleId = Integer.parseInt(totalText[7]);

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] hash = md.digest();

        Role chosenRole = userManager.getRole(roleId);
        // Create the user and store it
        User user = new User(username, hash, chosenRole);

        userManager.createUser(user);

        Patient patient = new Patient(name, age, weight, height, gender);
        patient.setNameuser(username);
        patientManager.add(patient);

    }

    public static String login(String response_login) throws Exception {
        String username, password, okay = null;
        String totalText[] = response_login.split(",");
        username = totalText[0];
        password = totalText[1];
        System.out.println(password);

        User user = userManager.checkPassword(username, password);
        // We check if the user/password combination was OK
        if (user == null) {
            okay = "Wrong credentials, please try again!";
        } // We check the role
        else if (user.getRole().getRole().equalsIgnoreCase("doctor")) {
            okay = "Welcome doctor !";
            doctorName = username;
            //doctorMenu();
            // We check the role
        } else if (user.getRole().getRole().equalsIgnoreCase("patient")) {
            okay = "Welcome patient !";
            patientName = username;
            //patientMenu();
        } else {
            okay = "Invalid role";
        }
        return okay;
    }

   
    public static String changeUsernamePatient(String newName) {
        //si da tiempo comprobar que va bien
        String response = "";
        System.out.println(newName);
        System.out.println(patientName);

        userManager.updateUsername(patientName, newName);
        patientManager.updateUsername(patientName, newName);
        patientName = newName;
        response = "Action Completed";
        return response;
    }
    
        public static String changeUsernameDoctor(String newName) {
        //si da tiempo comprobar que va bien
        String response = "";
        userManager.updateUsername(doctorName, newName);
        doctorManager.updateUsername(doctorName, newName);
        doctorName= newName;
        response = "Action Completed";
        return response;
    }
        public static String BossUser(String username,String password){
        User user = userManager.checkPassword(username, password);
        String okay="";
        if (user == null) {
            okay = "Wrong credentials, please try again!";
        } // We check the role
        else if (user.getRole().getRole().equalsIgnoreCase("doctor")) {
            okay = "Welcome doctor !";
            doctorName = username;
            //doctorMenu();
            // We check the role
        } else if (user.getRole().getRole().equalsIgnoreCase("patient")) {
            okay = "Welcome patient !";
            patientName = username;
            //patientMenu();
        } else {
            okay = "Invalid role";
        }
        return okay;
    }
    public static void GoodBye() {
        dbManager.disconnect();
        userManager.disconnect();
    }

    public static String changePasswordPatient(String password) {
        String response = "";
        userManager.updatePassword(patientName, password);
        response = "Action Completed";
        return response;
    }
    
        public static String changePasswordDoctor(String password) {
        String response = "";
        userManager.updatePassword(doctorName, password);
        response = "Action Completed";
        return response;
    }

    public static String completeForm(String response_form) throws Exception {
        String responseServer = "";
        String q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15, q16, q17, q18, q19, q20;
        String totalText[] = response_form.split(",");
        q1 = totalText[0];
        q2 = totalText[1];
        q3 = totalText[2];
        q4 = totalText[3];
        q5 = totalText[4];
        q6 = totalText[5];
        q7 = totalText[6];
        q8 = totalText[7];
        q9 = totalText[8];
        q10 = totalText[9];
        q11 = totalText[10];
        q12 = totalText[11];
        q13 = totalText[12];
        q14 = totalText[13];
        q15 = totalText[14];
        q16 = totalText[15];
        q17 = totalText[16];
        q18 = totalText[17];
        q19 = totalText[18];
        q20 = totalText[19];

        Integer patient_id = patientManager.searchByUsername(patientName);
        Patient patient = patientManager.getPatient(patient_id);

        String nameForm = patientName + ("_form.txt");
        File file = new File(nameForm);
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file);
            printWriter.print("1. Do you have difficulty or pain when opening your mouth, for example, when yawning? -> " + q1);
            printWriter.print("\n2. Do you feel your jaw sticking, locking or popping out? -> " + q2);
            printWriter.print("\n3. Do you have difficulty or pain when you chew, speak, or use your jaws? -> " + q3);
            printWriter.print("\n4. Have you noticed noises in your jaw joints? -> " + q4);
            printWriter.print("\n5. Do your jaws regularly feel stiff or clenched? -> " + q5);
            printWriter.print("\n6. Do you have pain around your ears, temples, or cheeks? -> " + q6);
            printWriter.print("\n7. Do you have frequent headaches or neck pain? -> " + q7);
            printWriter.print("\n8. Have you had a recent injury or trauma to your head, neck, or jaw? -> " + q8);
            printWriter.print("\n9. Have you noticed or felt any recent change in your bite? -> " + q9);
            printWriter.print("\n10. Have you ever been treated for a jaw joint problem? -> " + q10);
            printWriter.print("\n11. Have you noticed that you grind or clench your teeth frequently during sleep? -> " + q11);
            printWriter.print("\n12. Has anyone heard you grind your teeth at night? -> " + q12);
            printWriter.print("\n13. Did your jaw feel sore or fatigued when you woke up in the morning? -> " + q13);
            printWriter.print("\n14. Do you ever have a momentary headache when you wake up in the morning? -> " + q14);
            printWriter.print("\n15. Have you noticed that you grind/clench your teeth during the day? -> " + q15);
            printWriter.print("\n16. Do you have difficulty opening your mouth wide when you wake up? -> " + q16);
            printWriter.print("\n17. Do you feel pain in your teeth when they come in contact with cold air or liquids? -> " + q17);
            printWriter.print("\n18. Do you feel your jaw joint lock or make a clicking sound when you move it? -> " + q18);
            printWriter.print("\n19. Do your teeth or gums feel sore when you wake up in the morning? -> " + q19);
            printWriter.print("\n20. Have you noticed that you have considerable wear on your teeth? -> " + q20 +"\n");

        } catch (IOException ex) {
            responseServer = "There was an error while saving";

        } finally {
            if (printWriter != null) {
                printWriter.close();
            }

        }
        String filePath = nameForm;
        byte[] patient_form = Files.readAllBytes(Paths.get(filePath));
        System.out.println(patient_form);
        patient.setPatient_form(patient_form);
        patientManager.addForm(patient);
        responseServer = "Form saved successfully";
        return responseServer;

    }

    public static void addEMG_addECG(String response_EMG_ECG, List<Integer> arrayEMG, List<Integer> arrayECG, String form_ecg_emg) throws IOException {
        String responseServer = "";
        String q1, q2, q3, q4, q5, q6, q7;
        String totalText[] = form_ecg_emg.split(",");
        q1 = totalText[0];
        q2 = totalText[1];
        q3 = totalText[2];
        q4 = totalText[3];
        q5 = totalText[4];
        q6 = totalText[5];
        q7 = totalText[6];
        
        Integer patient_id = patientManager.searchByUsername(patientName);
        Patient patient = patientManager.getPatient(patient_id);

        String namePatient = patient.getFull_name();
        String nameForm_ecg_emg = patientName+ "_form_ECG_EMG.txt";
        
        File file = new File(nameForm_ecg_emg);
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file);
            printWriter.print("1. Level of laboral concerns: " + q1);
            printWriter.print("\n2. Level of personal concerns: " + q2);
            printWriter.print("\n3. Have you been stress today?: " + q3);
            printWriter.print("\n4. Do you feel tired?: " + q4);
            printWriter.print("\n5. Have you noticed that you grind/clench your teeth during the day?: " + q5);
            printWriter.print("\n6. Additional important information you want to share: " + q6+"\n");

        } catch (IOException ex) {
            responseServer = "There was an error while saving";

        } finally {
            if (printWriter != null) {
                printWriter.close();
            }

        }
        String filePath = nameForm_ecg_emg;
        byte[] patient_form_ecg_emg = Files.readAllBytes(Paths.get(filePath));
        
        
        
        
        String name_emg = ("EMG_") + response_EMG_ECG + (".txt");
        String name_ecg = ("ECG_") + response_EMG_ECG + (".txt");

        File file_emg = new File(name_emg);
        File file_ecg = new File(name_ecg);

        PrintWriter printWriter2 = null;
        try {
            printWriter2 = new PrintWriter(file_emg);
            printWriter2.print(namePatient+" "+q7 + "\n");
            for (int i = 0; i < arrayEMG.size(); i++) {
                printWriter2.print(arrayEMG.get(i) + "\n");
            }
        } catch (IOException ex) {
            //responseServer = "There was an error while saving";

        } finally {
            if (printWriter2 != null) {
                printWriter2.close();
            }

        }

        PrintWriter printWriter3 = null;
        try {
            printWriter3 = new PrintWriter(file_ecg);
            printWriter3.print(namePatient+" "+q7 + "\n");
            for (int i = 0; i < arrayECG.size(); i++) {
                printWriter3.print(arrayECG.get(i) + "\n");
            }
        } catch (IOException ex) {
            //responseServer = "There was an error while saving";

        } finally {
            if (printWriter3 != null) {
                printWriter3.close();
            }

        }
        String filePath_emg = name_emg;
        String filePath_ecg = name_ecg;

        byte[] patient_emg = Files.readAllBytes(Paths.get(filePath_emg));
        byte[] patient_ecg = Files.readAllBytes(Paths.get(filePath_ecg));

        Emg emg2 = new Emg(name_emg, patient_id, patient_emg, patient_form_ecg_emg);
        Ecg ecg2 = new Ecg(name_ecg, patient_id, patient_ecg, patient_form_ecg_emg);

        //System.out.println("probando: "+patient_emg);
        emgManager.add(emg2);
        ecgManager.add(ecg2);
        
        /*emg2.setForm(patient_form_ecg_emg);
        ecg2.setForm(patient_form_ecg_emg);
        emgManager.addForm(emg2);
        ecgManager.addForm(ecg2);*/
    }

    public static List<Ecg> searchECGByName() throws Exception {
        Integer patient_id = patientManager.searchByUsername(patientName);
        List<Ecg> ecgList = ecgManager.getECGpatient(patient_id);
        return ecgList;
    }
    
    public static List<Ecg> searchECG(int id) throws Exception {
 
        List<Ecg> ecgList = ecgManager.getECGpatient(id);
        return ecgList;
    }
    public static List<Emg> searchEMG(int id) throws Exception {

        List<Emg> emgList = emgManager.getEMGpatient(id);
        return emgList;
    }


    public static List<Emg> searchEMGByName() throws Exception {
       Integer patient_id = patientManager.searchByUsername(patientName);
        List<Emg> emgList = emgManager.getEMGpatient(patient_id);
        return emgList;
    }

    public static void deletePatient(int id) throws Exception {
        Patient patient = patientManager.getPatient(id);
        String name = patient.getNameuser();
        userManager.deletePatient(name);
        patientManager.delete(id);
    }

    public static List<Patient> searchPatientByName() throws Exception {
        int doctorId = doctorManager.getId(doctorName);
        List<Patient> patientList = doctorManager.getPatientsOfDoctor(doctorId);
        return patientList;
    }
    
    public static Patient searchForm(int id) throws Exception {
        byte[] form = patientManager.searchForm(id);
        Patient patient = new Patient();
        patient.setPatient_form(form);
        return patient;
    }
    
    public static List<Doctor> availableDoctors() throws Exception {
        List<Doctor> doctorList = doctorManager.getDoctors();
        return doctorList;
    }

    public static void chooseDoctor(int doctor_id) throws Exception {
        int patient_id = patientManager.searchByUsername(patientName);
        doctorManager.asign(doctor_id, patient_id);
    }




}
