/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

import java.io.Serializable;
import java.sql.Date;

public class Emg implements Serializable {

    private Integer id;
    private String name_emg;
    private Integer patient_id;

    public Emg(String name_emg, Integer patient_id) {
        this.name_emg = name_emg;
        this.patient_id = patient_id;
    }

    public Emg(Integer id, String name_emg, Integer patient_id) {
        this.id = id;
        this.name_emg = name_emg;
        this.patient_id = patient_id;
    }
    
    
    public Integer getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Integer patient_id) {
        this.patient_id = patient_id;
    }
    
    

    public Emg(Integer id, String name_emg) {
        this.id = id;
        this.name_emg = name_emg;
    }

   
    public Emg(Integer id, String name_emg, Date start_date, Date finish_date) {
        this.id = id;
        this.name_emg = name_emg;
        
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName_emg() {
        return name_emg;
    }

    public void setName_emg(String name_emg) {
        this.name_emg = name_emg;
    }

    /*
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.name_emg);
        hash = 67 * hash + Objects.hashCode(this.start_date);
        hash = 67 * hash + Objects.hashCode(this.finish_date);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Emg other = (Emg) obj;
        if (!Objects.equals(this.name_emg, other.name_emg)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.start_date, other.start_date)) {
            return false;
        }
        if (!Objects.equals(this.finish_date, other.finish_date)) {
            return false;
        }
        return true;
    }*/

    @Override
    public String toString() {
        return "Emg{" + "id=" + id + ", name_emg=" + name_emg + ", patient_id=" + patient_id + '}';
    }
  

}
