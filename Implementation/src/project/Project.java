package project;

import database.*;
import report.ProjectRequirement;
import resource.HumanResource;
import resource.PhysicalResource;
import resource.Resource;
import unit.Unit;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

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

	public Project(String name, Date developmentStart, Date developmentEnd,
			String customerName, int usersCount) {
		super();
		setID(generateNDigitID(ID_LENGTH));
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
		return projectDAO.setProjectManager(getID(), hr.getID());
	}

	public HumanResource getProjectManager() {
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		return projectDAO.getProjectManager(getID());
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

	public boolean setName(String name) {
		this.name = name;
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		return projectDAO.update(new Project(getID(), name,
				getDevelopmentStart(), getDevelopmentEnd(), getCustomerName(),
				getUsersCount()));
	}

	public Date getDevelopmentStart() {
		return developmentStart;
	}

	public boolean setDevelopmentStart(Date developmentStart) {
		this.developmentStart = developmentStart;
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		return projectDAO.update(new Project(getID(), getName(),
				developmentStart, getDevelopmentEnd(), getCustomerName(),
				getUsersCount()));
	}

	public Date getDevelopmentEnd() {
		return developmentEnd;
	}

	public boolean setDevelopmentEnd(Date developmentEnd) {
		this.developmentEnd = developmentEnd;
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		return projectDAO.update(new Project(getID(), getName(),
				getDevelopmentStart(), developmentEnd, getCustomerName(),
				getUsersCount()));
	}

	public String getCustomerName() {
		return customerName;
	}

	public boolean setCustomerName(String customerName) {
		this.customerName = customerName;
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		return projectDAO.update(new Project(getID(), getName(),
				getDevelopmentStart(), getDevelopmentEnd(), customerName,
				getUsersCount()));
	}

	public int getUsersCount() {
		return usersCount;
	}

	public boolean setUsersCount(int usersCount) {
		this.usersCount = usersCount;
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		return projectDAO.update(new Project(getID(), getName(),
				getDevelopmentStart(), getDevelopmentEnd(), getCustomerName(),
				usersCount));
	}

	@Override
	public String toString() {
		return "ID=" + ID + ", name=" + name + ", developmentStart="
				+ (developmentStart == null ? "" : developmentStart.toString())
				+ ", developmentEnd="
				+ (developmentEnd == null ? "" : developmentEnd.toString())
				+ ", customerName=" + customerName + ", usersCount="
				+ Integer.toString(usersCount);
	}

	public ArrayList<ProjectRequirement> getRequirements() {
		return ProjectRequirementDAO.getInstance().getRequirementByProjectID(getID());
	}

	public ArrayList<Resource> getResources() {
		ArrayList<Resource> resources = new ArrayList<>(HumanResourceDAO.getInstance().getResourcesByProjectID(getID()));
		resources.addAll(InformationResourceDAO.getInstance().getResourcesByProjectID(getID()));
		resources.addAll(PhysicalResourceDAO.getInstance().getResourcesByProjectID(getID()));
		resources.addAll(MonetaryResourceDAO.getInstance().getResourcesByProjectID(getID()));
		return resources;
	}
}
