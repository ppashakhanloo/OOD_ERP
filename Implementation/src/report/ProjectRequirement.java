package report;

import database.ProjectRequirementDAO;
import database.UnitResourceDAO;
import project.Project;
import resource.Resource;
import unit.Unit;
import utility.Identifiable;

import java.util.Date;


public class ProjectRequirement extends Identifiable {

    private Date provideDate;
    private Date releaseDate;
    private boolean isEssential;
    private Date criticalProvideDate;
    private int lengthOfPossession;

    public ProjectRequirement() {
        super();
        setEssential(false);
        setID(generateNDigitID(ID_LENGTH));
    }

    public ProjectRequirement(String iD, Date provideDate, Date releaseDate,
                              boolean isEssential, Date criticalProvideDate,
                              int lengthOfPossession) {
        super();
        setID(iD);
        this.provideDate = provideDate;
        this.releaseDate = releaseDate;
        this.isEssential = isEssential;
        this.criticalProvideDate = criticalProvideDate;
        this.lengthOfPossession = lengthOfPossession;
    }

    public Date getProvideDate() {
        return provideDate;
    }

    public boolean setProvideDate(Date provideDate) {
        this.provideDate = provideDate;
        ProjectRequirementDAO prjReqDAO = ProjectRequirementDAO.getInstance();
        return prjReqDAO.update(new ProjectRequirement(getID(), provideDate,
                getReleaseDate(), isEssential(), getCriticalProvideDate(),
                getLengthOfPossession()));
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public boolean setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
        ProjectRequirementDAO prjReqDAO = ProjectRequirementDAO.getInstance();
        return prjReqDAO.update(new ProjectRequirement(getID(),
                getProvideDate(), releaseDate, isEssential(),
                getCriticalProvideDate(), getLengthOfPossession()));
    }

    public boolean isEssential() {
        return isEssential;
    }

    public boolean setEssential(boolean isEssential) {
        this.isEssential = isEssential;
        return ProjectRequirementDAO.getInstance().update(new ProjectRequirement(getID(),
                getProvideDate(), getReleaseDate(), isEssential,
                getCriticalProvideDate(), getLengthOfPossession()));
    }

    public Date getCriticalProvideDate() {
        return criticalProvideDate;
    }

    public boolean setCriticalProvideDate(Date criticalProvideDate) {
        this.criticalProvideDate = criticalProvideDate;
        return ProjectRequirementDAO.getInstance().update(new ProjectRequirement(getID(),
                getProvideDate(), getReleaseDate(), isEssential(),
                criticalProvideDate, getLengthOfPossession()));
    }

    public int getLengthOfPossession() {
        return lengthOfPossession;
    }

    public boolean setLengthOfPossession(int lengthOfPossession) {
        this.lengthOfPossession = lengthOfPossession;
        return ProjectRequirementDAO.getInstance().update(new ProjectRequirement(getID(),
                getProvideDate(), getReleaseDate(), isEssential(),
                getCriticalProvideDate(), lengthOfPossession));
    }

    public Resource getResource() {
        return ProjectRequirementDAO.getInstance().getResource(getID());
    }

    public boolean setResource(Resource res) {
        return ProjectRequirementDAO.getInstance().setResource(getID(), res);
    }

    public Project getProject() {
        return ProjectRequirementDAO.getInstance().getProject(getID());
    }


    public Unit getUnit() {
        ProjectRequirementDAO dao = ProjectRequirementDAO.getInstance();
        return UnitResourceDAO.getInstance().getUnitByResourceID(
                dao.get(getID()).getResource().getID());
    }

    @Override
    public String toString() {
        return "ID=" + getID() + ", "
                + "provideDate=" + (provideDate == null ? "" : provideDate.toString())
                + ", releaseDate=" + (releaseDate == null ? "" : releaseDate.toString())
                + ", isEssential=" + isEssential
                + ", criticalProvideDate=" + (criticalProvideDate == null ? "" : criticalProvideDate.toString())
                + ", lengthOfPossession=" + Integer.toString(lengthOfPossession)
                + "\n, resource=" + (getResource() == null ? "" : getResource().toString());
    }
}
