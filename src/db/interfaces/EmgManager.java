/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import java.sql.Date;
import java.util.List;
import pojos.Emg;

public interface EmgManager {

    public List<Emg> searchByName(String name_emg);

    public List<Emg> searchByStartDate(Date start_date);

    public void add(Emg emg);

    public void delete(Integer emg_id);
    
    public List<Emg> getEMGpatient(Integer patient_id);

}
