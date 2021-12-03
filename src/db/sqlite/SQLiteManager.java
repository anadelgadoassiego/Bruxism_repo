/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.sqlite;

import db.interfaces.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteManager implements DBManager {

    private Connection c;
    private PatientManager patient;
    private DoctorManager doctor;
    private EmgManager emg;
    private EcgManager ecg;

    public SQLiteManager() {
        super();
    }

    @Override
    public void connect() {
        try {

            Class.forName("org.sqlite.JDBC");
            this.c = DriverManager.getConnection("jdbc:sqlite:./db/Telemedicina.db");
            c.createStatement().execute("PRAGMA foreign_keys=ON");

            patient = new SQLitePatientManager(c);

            doctor = new SQLiteDoctorManager(c);

            emg = new SQLiteEmgManager(c);

            ecg = new SQLiteEcgManager(c);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void disconnect() {
        try {
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTables() {
        Statement stmt1;
        try {
            stmt1 = c.createStatement();
            String sql1 = "CREATE TABLE doctors " + "(id     INTEGER  PRIMARY KEY AUTOINCREMENT,"
                    + " Fullname   TEXT   NOT NULL," + " nameuser TEXT NOT NULL UNIQUE)";
            stmt1.executeUpdate(sql1);
            stmt1 = c.createStatement();
            String sql2 = "CREATE TABLE patients " + "(id     INTEGER  PRIMARY KEY AUTOINCREMENT,"
                    + " Fullname   TEXT   NOT NULL," + "age INTEGER NOT NULL," + "weight FLOAT NOT NULL,"
                    + "height FLOAT NOT NULL," + " gender TEXT NOT NULL," + " nameuser TEXT NOT NULL UNIQUE,"
                    + "form BYTES )";
            stmt1.executeUpdate(sql2);
            stmt1 = c.createStatement();
            String sql3 = "CREATE TABLE doctorPatients " + "(doctorId     INTEGER  REFERENCES doctors(id) ON UPDATE CASCADE ON DELETE SET NULL, "
                    + "patientId     INTEGER  REFERENCES patients(id) ON UPDATE CASCADE ON DELETE SET NULL, " + "PRIMARY KEY(doctorid,patientId))";
            stmt1.executeUpdate(sql3);
            stmt1 = c.createStatement();
            String sql4 = "CREATE TABLE emg " + "(id     INTEGER  PRIMARY KEY AUTOINCREMENT,"
                    + " name_emg   TEXT   NOT NULL," + " patient_id INTEGER REFERENCES patients(id) ON UPDATE CASCADE ON DELETE SET NULL"
                    + ")";
            stmt1.executeUpdate(sql4);
            stmt1 = c.createStatement();
            String sql5 = "CREATE TABLE ecg " + "(id     INTEGER  PRIMARY KEY AUTOINCREMENT,"
                    + " name_ecg   TEXT   NOT NULL," + "patient_id INTEGER REFERENCES patients(id) ON UPDATE CASCADE ON DELETE SET NULL"
                    + ")";
            stmt1.executeUpdate(sql5);

            stmt1.close();
        } catch (SQLException e) {
            if (e.getMessage().contains("already exists")) {
            } else {
                e.printStackTrace();
            }
        }
    }

    @Override
    public PatientManager getPatientManager() {
        return patient;
    }

    @Override
    public DoctorManager getDoctorManager() {
        return doctor;
    }

    @Override
    public EmgManager getEmgManager() {
        return emg;
    }

    @Override
    public EcgManager getEcgManager() {
        return ecg;
    }

    public PatientManager getPatient() {
        return patient;
    }

    public void setPatient(PatientManager patient) {
        this.patient = patient;
    }

    public DoctorManager getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorManager doctor) {
        this.doctor = doctor;
    }

    public EmgManager getEmg() {
        return emg;
    }

    public void setEmg(EmgManager emg) {
        this.emg = emg;
    }
    @Override
    public int getLastId() {
            int result = 0;
            try {
                    String query = "SELECT last_insert_rowid() AS lastId";
                    PreparedStatement p = c.prepareStatement(query);
                    ResultSet rs = p.executeQuery();
                    result = rs.getInt("lastId");
            } catch (SQLException e) {
                    e.printStackTrace();
            }
            return result;
    }
}
