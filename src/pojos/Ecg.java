/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

import java.io.Serializable;
import java.sql.Date;

public class Ecg implements Serializable {

    private Integer id;
    private String name_ecg;
    private Integer patient_id;

    public Integer getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Integer patient_id) {
        this.patient_id = patient_id;
    }

    public Ecg(String name_ecg, Integer patient_id) {
        this.name_ecg = name_ecg;
        this.patient_id = patient_id;
    }

    public Ecg(Integer id, String name_ecg, Integer patient_id) {
        this.id = id;
        this.name_ecg = name_ecg;
        this.patient_id = patient_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName_ecg() {
        return name_ecg;
    }

    public void setName_ecg(String name_ecg) {
        this.name_ecg = name_ecg;
    }

    /*
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.name_ecg);
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
        final Ecg other = (Ecg) obj;
        if (!Objects.equals(this.name_ecg, other.name_ecg)) {
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
        return "Ecg{" + "id=" + id + ", name_ecg=" + name_ecg + ", patient_id=" + patient_id + '}';
    }
  

}
