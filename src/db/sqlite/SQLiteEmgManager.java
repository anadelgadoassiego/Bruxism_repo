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
import pojos.Emg;

public class SQLiteEmgManager implements EmgManager {

    private Connection c;

    public SQLiteEmgManager(Connection c) {
        this.c = c;
    }

    @Override
    public List<Emg> searchByName(String name_emg) {
        List<Emg> emgsList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM emg WHERE name_emg LIKE ?";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setString(1, "%" + name_emg + "%");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String emgName = rs.getString("name_emg");
                int patient_id = rs.getInt("patient_id");
                

                Emg newemg = new Emg(id, emgName, patient_id);
                emgsList.add(newemg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emgsList;
    }

    @Override
    public void add(Emg emg) {
        try {
            String sql = "INSERT INTO emg (name_emg, patient_id, emg_array) "
                    + "VALUES (?,?,?)";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setString(1, emg.getName_emg());
            prep.setInt(2, emg.getPatient_id());
            prep.setBytes(3, emg.getPatient_emg());
            prep.executeUpdate();
            prep.close();
        } catch (SQLException e) {
        }
    }

    @Override
    public void delete(Integer emg_id) {
        try {
            String sql = "DELETE FROM emg WHERE id=?";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setInt(1, emg_id);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException e) {
        }
    }
    
    public List<Emg> getEMGpatient(Integer patient_id){
        List<Emg> emgsList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM emg WHERE patient_id LIKE ?";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setString(1, "%" + patient_id + "%");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String emgName = rs.getString("name_emg");
                byte[] emg_array = rs.getBytes("emg_array");
                byte[] emg_form = rs.getBytes("emg_form");
                Emg newemg = new Emg(id, emgName, patient_id,emg_array,emg_form);
                emgsList.add(newemg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emgsList;
    }

    

    @Override
    public void addForm(Emg emg){
        byte[] form = null;
        try {
            String sql = "UPDATE emg SET emg_form=? WHERE id=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setBytes(1, emg.getForm());
            s.setInt(2, emg.getId());
            s.executeUpdate();
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
