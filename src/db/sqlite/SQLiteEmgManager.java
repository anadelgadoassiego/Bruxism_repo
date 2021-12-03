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
/*
    @Override
    public List<Emg> searchByStartDate(Date start_date) {
        List<Emg> emgsList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM emg WHERE sart_date LIKE ?";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setString(1, "%" + start_date + "%");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String emgName = rs.getString("name_emg");
                Date emgStart_date = rs.getDate("start_date");
                Date emgFinish_date = rs.getDate("finish_date");

                Emg newemg = new Emg(id, emgName, emgStart_date,
                        emgFinish_date);
                emgsList.add(newemg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emgsList;
    }
*/
    @Override
    public void add(Emg emg) {
        try {
            String sql = "INSERT INTO emg (name_emg, patient_id "
                    + ") "
                    + "VALUES (?,?,?,?)";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setString(1, emg.getName_emg());
            prep.setInt(2, emg.getPatient_id());
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

                Emg newemg = new Emg(id, emgName, patient_id);
                emgsList.add(newemg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emgsList;
    }

    @Override
    public List<Emg> searchByStartDate(Date start_date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
