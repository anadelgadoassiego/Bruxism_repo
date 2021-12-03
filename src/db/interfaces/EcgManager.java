/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import java.sql.Date;
import java.util.List;
import pojos.Ecg;
import pojos.Emg;

public interface EcgManager {

    public List<Ecg> searchByName(String name_ecg);

    public List<Ecg> searchByStartDate(Date start_date);

    public void add(Ecg ecg);

    public void delete(Integer ecg_id);
    
    public List<Ecg> getECGpatient(Integer patient_id);


}
