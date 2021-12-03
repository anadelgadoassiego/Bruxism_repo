/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

import java.io.Serializable;
import java.util.List;

public class Patient implements Serializable {

    private static final long serialVersionUID = 6891296751142184360L;

    private Integer id;

    private String full_name;

    private Integer age;

    private Float weight;

    private Float height;

    private String gender;

    private List<Doctor> doctors;

    private List<Ecg> ecg;

    private List<Emg> emg;

    private String name_user;

    private byte[] patient_form;

    public Patient(Integer id, String full_name, Integer age, Float weight, Float height, String gender, List<Ecg> ecg, List<Emg> emg) {
        this.id = id;
        this.full_name = full_name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.ecg = ecg;
        this.emg = emg;
    }

    public Patient(Integer id, String full_name, Integer age, Float weight, Float height, String gender) {
        this.id = id;
        this.full_name = full_name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Patient(String full_name, Integer age, Float weight, Float height, String gender) {
        this.full_name = full_name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
    }

    public Patient(String full_name) {
        this.full_name = full_name;

    }
    public Patient(){
        
    }
    public Integer getId() {
        return id;
    }

    public String getFull_name() {
        return full_name;
    }

    public Integer getAge() {
        return age;
    }

    public Float getWeight() {
        return weight;
    }

    public Float getHeight() {
        return height;
    }

    public String getGender() {
        return gender;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public List<Ecg> getEcg() {
        return ecg;
    }

    public List<Emg> getEmg() {
        return emg;
    }

    public byte[] getPatient_form() {
        return this.patient_form;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public void setEcg(List<Ecg> ecg) {
        this.ecg = ecg;
    }

    public void setEmg(List<Emg> emg) {
        this.emg = emg;
    }

    public String getNameuser() {
        return name_user;
    }

    public void setNameuser(String name_user) {
        this.name_user = name_user;
    }

    public void setPatient_form(byte[] patient_form) {
        this.patient_form = patient_form;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
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
        Patient other = (Patient) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Patient{" + "id=" + id + ", Fullname=" + full_name + ", age=" + age + ", weight=" + weight + ", height=" + height + ", gender=" + gender + ", doctors=" + doctors + '}';
    }

}
