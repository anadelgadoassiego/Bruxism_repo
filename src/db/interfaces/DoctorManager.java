/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import java.util.List;
import pojos.Doctor;

public interface DoctorManager {

    public List<Doctor> searchByName(String name);

    public void add(Doctor doctor);

    public void delete(Integer doctor_id);

    public void updateUsername(String doctorName, String userName);
    
    public void asign( int doctorId, int patientId);
    
    public int getId(String username);
}
