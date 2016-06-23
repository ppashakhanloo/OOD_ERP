package project;

import java.util.ArrayList;
import java.util.Date;

import resource.HumanResource;
import unit.Unit;
import database.ProjectDAO;
import database.SystemDAO;

public class Project {
	String ID;
	String name;
	Date developmentStart;
	Date developmentEnd;
	String customerName;
	int usersCount;

	public boolean addSystem(System system) {
		SystemDAO systemDAO = SystemDAO.getInstance();
		return systemDAO.add(system, ID);
	}

	public void removeSystem(System system) {
		SystemDAO systemDAO = SystemDAO.getInstance();
		systemDAO.remove(system.getID());
	}

	public System getSystem(String id) {
		SystemDAO systemDAO = SystemDAO.getInstance();
		return systemDAO.get(id);
	}

	public ArrayList<System> getSystems() {
		SystemDAO systemDAO = SystemDAO.getInstance();
		return systemDAO.getByProjectID(getID());
	}

	public void edit(Project project) {
		this.setName(project.getName());
		this.setCustomerName(project.getCustomerName());
		this.setDevelopmentEnd(project.getDevelopmentEnd());
		this.setDevelopmentStart(project.getDevelopmentStart());
		this.setUsersCount(project.getUsersCount());
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		projectDAO.update(project);
	}

	public ArrayList<Unit> getInvolvedUnits() {
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		return projectDAO.getUnitsByProjectID(getID());
	}

	public boolean setProjectManager(HumanResource hr) {
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		return projectDAO.setProjectManagers(getID(), hr.getID());
	}

	public HumanResource getProjectManager() {
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		return projectDAO.getProjectManagers(getID());
	}

	public String getID() {
		return ID;
	}

	private void setID(String iD) {
		this.ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDevelopmentStart() {
		return developmentStart;
	}

	public void setDevelopmentStart(Date developmentStart) {
		this.developmentStart = developmentStart;
	}

	public Date getDevelopmentEnd() {
		return developmentEnd;
	}

	public void setDevelopmentEnd(Date developmentEnd) {
		this.developmentEnd = developmentEnd;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getUsersCount() {
		return usersCount;
	}

	public void setUsersCount(int usersCount) {
		this.usersCount = usersCount;
	}

}