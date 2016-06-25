package report;

import database.ProjectRequirementDAO;
import project.Project;
import resource.Resource;

import java.util.Date;
import java.util.Random;

public class ProjectRequirement {
	String ID;
	int ID_LENGTH = 6;
	Date provideDate;
	Date releaseDate;
	boolean isEssential;
	Date criticalProvideDate;
	int lengthOfPossession;

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

	private String generateNDigitID(int n) {
		Random random = new Random();
		return Integer.toString((int) (Math.pow(10, n - 1) + random.nextFloat()
				* 9 * Math.pow(10, n - 1)));
	}

	public String getID() {
		return ID;
	}

	private void setID(String iD) {
		ID = iD;
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
		ProjectRequirementDAO prjReqDAO = ProjectRequirementDAO.getInstance();
		return prjReqDAO.update(new ProjectRequirement(getID(),
				getProvideDate(), getReleaseDate(), isEssential,
				getCriticalProvideDate(), getLengthOfPossession()));
	}

	public Date getCriticalProvideDate() {
		return criticalProvideDate;
	}

	public boolean setCriticalProvideDate(Date criticalProvideDate) {
		this.criticalProvideDate = criticalProvideDate;
		ProjectRequirementDAO prjReqDAO = ProjectRequirementDAO.getInstance();
		return prjReqDAO.update(new ProjectRequirement(getID(),
				getProvideDate(), getReleaseDate(), isEssential(),
				criticalProvideDate, getLengthOfPossession()));
	}

	public int getLengthOfPossession() {
		return lengthOfPossession;
	}

	public boolean setLengthOfPossession(int lengthOfPossession) {
		this.lengthOfPossession = lengthOfPossession;
		ProjectRequirementDAO prjReqDAO = ProjectRequirementDAO.getInstance();
		return prjReqDAO.update(new ProjectRequirement(getID(),
				getProvideDate(), getReleaseDate(), isEssential(),
				getCriticalProvideDate(), lengthOfPossession));
	}

	public Resource getResource() {
		ProjectRequirementDAO dao = ProjectRequirementDAO.getInstance();
		return dao.getResource(getID());
	}

	public boolean setResource(Resource res) {
		ProjectRequirementDAO dao = ProjectRequirementDAO.getInstance();
		return dao.setResource(getID(), res);
	}

	public Project getProject() {
		ProjectRequirementDAO dao = ProjectRequirementDAO.getInstance();
		return dao.getProject(getID());
	}
}
