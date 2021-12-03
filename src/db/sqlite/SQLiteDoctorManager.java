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
import java.util.List;
import pojos.Doctor;

public class SQLiteDoctorManager implements DoctorManager {

    private Connection c;

    public SQLiteDoctorManager(Connection c) {
        this.c = c;
    }

    @Override
    public List<Doctor> searchByName(String name) {
        List<Doctor> doctorsList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM doctors WHERE Fullname LIKE ?";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setString(1, "%" + name + "%");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String doctorName = rs.getString("Fullname");

                Doctor newdoctor = new Doctor(id, doctorName);
                doctorsList.add(newdoctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctorsList;
    }

    @Override
    public void add(Doctor doctor) {
        try {
            String sql = "INSERT INTO doctors (Fullname,nameuser) "
                    + "VALUES (?,?)";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setString(1, doctor.getFull_name());
            prep.setString(2, doctor.getNameuser());
            prep.executeUpdate();
            prep.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer doctor_id) {
        try {
            String sql = "DELETE FROM doctors WHERE id=?";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setInt(1, doctor_id);
            prep.executeUpdate();
            prep.close();
        } catch (SQLException e) {
        }
    }

    @Override
    public void updateUsername(String username, String newUsername) {
        try {
            String sql = "UPDATE doctors SET nameuser=? WHERE nameuser=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(2, username);
            s.setString(1, newUsername);
            s.executeUpdate();
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void asign(int doctorId, int patientId) {
		// Link Product and Component
		try {
			String sql = "INSERT INTO doctorPatients (doctorId,patientId) " + "VALUES (?,?)";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, doctorId);
			prep.setInt(2, patientId);
			prep.executeUpdate();
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    @Override
    public int getId (String name) {
        int doctorId = 0 ;
        try {
            String sql = "SELECT id FROM doctors WHERE nameuser LIKE ?";
            PreparedStatement prep = c.prepareStatement(sql);
            prep.setString(1, "%" + name + "%");
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                doctorId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctorId;
    }


}
