/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import java.util.List;
import pojos.Patient;

public interface PatientManager {

    public List<Patient> searchByName(String name);

    public void add(Patient patient);

    public void delete(Integer patient_id);

    public void updateUsername(String username, String newUsername);

    public List<String> getUsernames();
    
    public Integer searchByUsername(String username);

    public Patient getPatient(int patient_id);
    
    public void addForm(Patient patient);
}
