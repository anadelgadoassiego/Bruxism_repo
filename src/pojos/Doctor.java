/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

/**
 *
 * @author delga
 */

import java.io.Serializable;
import java.util.List;

public class Doctor implements Serializable {
    private static final long serialVersionUID = 6891296751142184360L;

    private Integer id;
    private String full_name;
    private String nameuser;
    private List<Patient> patients;

    public Doctor() {
        super();
    }

    public Doctor(Integer id, String full_name) {
        this.id = id;
        this.full_name = full_name;

    }

    public Doctor(String full_name, List<Patient> patients) {
        super();
        this.full_name = full_name;
        this.patients = patients;

    }

    public Doctor(String full_name, String nameuser) {
        this.full_name = full_name;
        this.nameuser = nameuser;
    }

    

    
    /*
    public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pharmacy other = (Pharmacy) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
    
    
    /*
    public static void main(String[] args) {
        Doctor doctor = new Doctor(1, "my first doctor", new LinkedList());
        System.out.println("doct: " + doctor);
    }
     */

    public String getNameuser() {
        return nameuser;
    }

    public void setNameuser(String nameuser) {
        this.nameuser = nameuser;
    }
}
