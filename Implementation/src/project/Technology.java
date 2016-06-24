package project;

import database.ProjectDAO;

public class Technology {
	String name;
	String reason;

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public String getReason() {
		return reason;
	}

	public boolean setReason(String reason) {
		this.reason = reason;
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		return projectDAO.updateTechnology(new Technology(getName(), reason));
	}

	public Technology(String name, String reason) {
		super();
		this.name = name;
		this.reason = reason;
	}

}
