package net.lehre_online.bw;

public class Immobilie {
	
	private int id;
	
	private String name;
	
	private String department;
	
	private String email;
	
	public Immobilie() {
	
	}
	
	public Immobilie(int id, String name, String department, String email) {
		this.id = id;
		this.name = name;
		this.department = department;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
