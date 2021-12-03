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
/*
    @Override
    public List<Ecg> searchByStartDate(Date start_date) {
        List<Ecg> ecgsList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ecg WHERE sart_date LIKE ?";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setString(1, "%" + start_date + "%");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String ecgName = rs.getString("name_ecg");
                Date ecgStart_date = rs.getDate("start_date");
                Date ecgFinish_date = rs.getDate("finish_date");

                Ecg newecg = new Ecg(id, ecgName, ecgStart_date,
                        ecgFinish_date);
                ecgsList.add(newecg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ecgsList;
    }
*/
    @Override
    public void add(Ecg ecg) {
        try {
            String sql = "INSERT INTO ecg (name_ecg, patient_id "
                    + ") "
                    + "VALUES (?,?,?,?)";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setString(1, ecg.getName_ecg());
            prep.setInt(2, ecg.getPatient_id());
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
            String sql = "SELECT * FROM emg WHERE patient_id LIKE ?";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setString(1, "%" + patient_id + "%");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String ecgName = rs.getString("name_emg");

                Ecg newecg = new Ecg(id, ecgName, patient_id);
                ecgsList.add(newecg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ecgsList;
    }

    @Override
    public List<Ecg> searchByStartDate(Date start_date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
