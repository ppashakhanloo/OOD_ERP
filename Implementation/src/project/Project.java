package project;

import database.*;
import report.ProjectRequirement;
import resource.HumanResource;
import resource.Resource;
import unit.Unit;
import utility.Identifiable;

import java.util.ArrayList;
import java.util.Date;

public class Project extends Identifiable {
    //    private String ID;
    private String name;
    private Date developmentStart;
    private Date developmentEnd;
    private String customerName;
    private int usersCount;


    public Project() {
        setID(generateNDigitID(ID_LENGTH));
    }

    public Project(String iD, String name, Date developmentStart,
                   Date developmentEnd, String customerName, int usersCount) {
        super();
        setID(iD);
        this.name = name;
        this.developmentStart = developmentStart;
        this.developmentEnd = developmentEnd;
        this.customerName = customerName;
        this.usersCount = usersCount;
    }

    public static void main(String[] args) {
//        Project project = new Project("OOD", new Date(), new Date(), "Dr. Ramsin", 10);
//        ProjectRequirementCatalogue.
    }

    public boolean addSystem(System system) {
        return SystemDAO.getInstance().add(system, getID());
    }

    public System getSystem(String id) {
        return SystemDAO.getInstance().get(id);
    }

    public ArrayList<System> getSystems() {
        return SystemDAO.getInstance().getByProjectID(getID());
    }

    public ArrayList<Module> getModules() { return ProjectDAO.getInstance().getAllModules(getID()); }

    public ArrayList<Unit> getInvolvedUnits() {
        return ProjectDAO.getInstance().getUnitsByProjectID(getID());
    }

    public boolean setProjectManager(HumanResource hr) {
        return ProjectDAO.getInstance().setProjectManager(getID(), hr.getID());
    }

    public HumanResource getProjectManager() {
        return ProjectDAO.getInstance().getProjectManager(getID());
    }

    public boolean addTechnology(Technology technology) {
        return ProjectDAO.getInstance().addTechnology(technology, getID());
    }

    public ArrayList<Technology> getTechnologies() {
        return ProjectDAO.getInstance().getTechnologiesByProject(getID());
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

    public Date getDevelopmentEnd() {
        return developmentEnd;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getUsersCount() {
        return usersCount;
    }

    public boolean setUsersCount(int usersCount) {
        this.usersCount = usersCount;
        return ProjectDAO.getInstance().update(new Project(getID(), getName(),
                getDevelopmentStart(), getDevelopmentEnd(), getCustomerName(),
                usersCount));
    }

    @Override
    public String toString() {
//        return "ID=" + getID() + ", name=" + name + ", developmentStart="
//                + (developmentStart == null ? "" : developmentStart.toString())
//                + ", developmentEnd="
//                + (developmentEnd == null ? "" : developmentEnd.toString())
//                + ", customerName=" + customerName + ", usersCount="
//                + Integer.toString(usersCount);
        return name + "(" + getID() + ")";
    }

    public ArrayList<ProjectRequirement> getRequirements() {
        return ProjectRequirementDAO.getInstance().getRequirementByProjectID(getID());
    }

    public ArrayList<ProjectRequirement> getAllRequirements() {
        return ProjectRequirementDAO.getInstance().getAllRequirementByProjectID(getID());
    }

    public ArrayList<Resource> getResources() {
        ArrayList<Resource> resources = new ArrayList<>(HumanResourceDAO.getInstance().getResourcesByProjectID(getID()));
        resources.addAll(InformationResourceDAO.getInstance().getResourcesByProjectID(getID()));
        resources.addAll(PhysicalResourceDAO.getInstance().getResourcesByProjectID(getID()));
        resources.addAll(MonetaryResourceDAO.getInstance().getResourcesByProjectID(getID()));
        return resources;
    }
}
