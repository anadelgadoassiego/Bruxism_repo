/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.sqlite;

import db.interfaces.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import pojos.Ecg;
//import pojos.Patient;

public class SQLiteEcgManager implements EcgManager {

    private Connection c;

    public SQLiteEcgManager(Connection c) {
        this.c = c;
    }

    @Override
    public List<Ecg> searchByName(String name_ecg) {
        List<Ecg> ecgsList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ecg WHERE name_ecg LIKE ?";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setString(1, "%" + name_ecg + "%");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String ecgName = rs.getString("name_ecg");
                int patient_id = rs.getInt("patient_id");

                Ecg newecg = new Ecg(id, ecgName, patient_id);
                ecgsList.add(newecg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ecgsList;
    }

    @Override
    public void add(Ecg ecg) {
        try {
            String sql = "INSERT INTO ecg (name_ecg, patient_id, ecg_array, ecg_form) "
                    + "VALUES (?,?,?,?)";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setString(1, ecg.getName_ecg());
            prep.setInt(2, ecg.getPatient_id());
            prep.setBytes(3, ecg.getPatient_ecg());
            prep.setBytes(4, ecg.getForm());
            prep.executeUpdate();
            prep.close();
        } catch (SQLException e) {
        }
    }

    @Override
    public void delete(Integer ecg_id) {
        try {
            String sql = "DELETE FROM ecg WHERE id=?";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setInt(1, ecg_id);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException e) {
        }
    }
    
    public List<Ecg> getECGpatient(Integer patient_id){
        List<Ecg> ecgsList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ecg WHERE patient_id LIKE ?";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setString(1, "%" + patient_id + "%");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String ecgName = rs.getString("name_ecg");
                byte[] ecg_array = rs.getBytes("ecg_array");
                byte[] ecg_form = rs.getBytes("ecg_form");
                Ecg newecg = new Ecg(id, ecgName, patient_id,ecg_array,ecg_form);
                ecgsList.add(newecg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ecgsList;
    }

    

    @Override
    public void addForm(Ecg ecg){
        byte[] form = null;
        try {
            String sql = "UPDATE ecg SET ecg_form=? WHERE id=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setBytes(1, ecg.getForm());
            s.setInt(2, ecg.getId());
            s.executeUpdate();
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
