package project;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

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
	int ID_LENGTH = 6;

	public Project() {
		setID(generateNDigitID(ID_LENGTH));
	}

	public Project(String iD, String name, Date developmentStart,
			Date developmentEnd, String customerName, int usersCount) {
		super();
		ID = iD;
		this.name = name;
		this.developmentStart = developmentStart;
		this.developmentEnd = developmentEnd;
		this.customerName = customerName;
		this.usersCount = usersCount;
	}

	private String generateNDigitID(int n) {
		Random random = new Random();
		return Integer.toString((int) (Math.pow(10, n - 1) + random.nextFloat()
				* 9 * Math.pow(10, n - 1)));
	}

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

	public boolean addTechnology(Technology technology) {
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		return projectDAO.addTechnology(technology, getID());
	}

	public ArrayList<Technology> getTechnologies() {
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		return projectDAO.getTechnologiesByProject(getID());
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
