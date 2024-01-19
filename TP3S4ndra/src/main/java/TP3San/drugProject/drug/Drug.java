package TP3San.drugProject.drug;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Drug {

	@Id
	private int drugnumber;
	private String dci;
	private int catnumber;
    private String dosage;
    private String form;
    private String reference;
    private int cases;
	private int posts;
	private int centers;
	private int eps1;
	private int eps2;
	private int eps3;
	private String createdby;
	private String lastupdatedby;
	private int status;

	public int getDrugnumber() {
		return drugnumber;
	}

	public String getDci() {
		return dci;
	}
	public void setDci(String dci) {
		this.dci = dci;
	}

	public int getCatnumber() {
		return catnumber;
	}
	public void setCatnumber(int catnumber) {
		this.catnumber = catnumber;
	}

	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}

	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}

	public int getCases() {
		return cases;
	}
	public void setCases(int cases) {
		this.cases = cases;
	}

	public int getPosts() {
		return posts;
	}
	public void setPosts(int posts) {
		this.posts = posts;
	}

	public int getCenters() {
		return centers;
	}
	public void setCenters(int centers) {
		this.centers = centers;
	}

	public int getEps1() {
		return eps1;
	}
	public void setEps1(int eps1) {
		this.eps1 = eps1;
	}

	public int getEps2() {
		return eps2;
	}
	public void setEps2(int eps2) {
		this.eps2 = eps2;
	}

	public int getEps3() {
		return eps3;
	}
	public void setEps3(int eps3) {
		this.eps3 = eps3;
	}

	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getLastupdatedby() {
		return lastupdatedby;
	}
	public void setLastupdatedby(String lastupdatedby) {
		this.lastupdatedby = lastupdatedby;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

    
    public Drug() {}
    
    public Drug(int drugnumber, String dci) {
    	this.drugnumber = drugnumber;
    	this.dci = dci;
    }
    
    public Drug(int drugnumber, String dci, int catnumber, String dosage, String form, String reference, int cases,
			int posts, int centers, int eps1, int eps2, int eps3, String createdby,
			String lastupdatedby, int status) {
		this.catnumber = catnumber;
		this.drugnumber = drugnumber;
		this.dci = dci;
		this.dosage = dosage;
		this.form = form;
		this.reference = reference;
		this.cases = cases;
		this.posts = posts;
		this.centers = centers;
		this.eps1 = eps1;
		this.eps2 = eps2;
		this.eps3 = eps3;
		this.createdby = createdby;
		this.lastupdatedby = lastupdatedby;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Drug [catnumber=" + catnumber + ", drugnumber=" + drugnumber + ", dci=" + dci + ", dosage=" + dosage + ", form=" + form + ", reference=" + reference + ", cases="
				+ cases + ", posts=" + posts + ", centers=" + centers + ", eps1=" + eps1 + ", eps2=" + eps2 + ", eps3="
				+ eps3 + ", createdby=" + createdby + ", lastupdatedby=" + lastupdatedby + ", status=" + status + "]";
	}
}