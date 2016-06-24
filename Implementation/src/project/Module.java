package project;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import resource.HumanResource;
import database.ModuleDAO;
import database.ModuleModificationDAO;

public class Module {

	int ID_LENGTH = 6;
	String ID;
	String name;
	Date developmentStart;
	Date developmentEnd;

	public Module() {
		setID(generateNDigitID(ID_LENGTH));
	}

	public Module(String iD, String name, Date developmentStart,
			Date developmentEnd) {
		ID = iD;
		this.name = name;
		this.developmentStart = developmentStart;
		this.developmentEnd = developmentEnd;
	}

	private String generateNDigitID(int n) {
		Random random = new Random();
		return Integer.toString((int) (Math.pow(10, n - 1) + random.nextFloat()
				* 9 * Math.pow(10, n - 1)));
	}

	public ArrayList<ModuleModification> getModuleModifications() {
		ModuleModificationDAO modDAO = ModuleModificationDAO.getInstance();
		return modDAO.getByModuleID(getID());
	}

	public boolean addModification(ModuleModification mod) {
		ModuleModificationDAO modDAO = ModuleModificationDAO.getInstance();
		return modDAO.add(mod, getID());
	}

	public ArrayList<HumanResource> getDevelopers() {
		ModuleDAO modDAO = ModuleDAO.getInstance();
		return modDAO.getDevelopers(getID());
	}

	public boolean addDeveloper(HumanResource developer) {
		ModuleDAO modDAO = ModuleDAO.getInstance();
		return modDAO.addDeveloper(getID(), developer);
	}

	public String getID() {
		return ID;
	}

	private void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public boolean setName(String name) {
		this.name = name;
		ModuleDAO modDAO = ModuleDAO.getInstance();
		return modDAO.update(new Module(getID(), name, getDevelopmentStart(),
				getDevelopmentEnd()));
	}

	public Date getDevelopmentStart() {
		return developmentStart;
	}

	public boolean setDevelopmentStart(Date developmentStart) {
		this.developmentStart = developmentStart;
		ModuleDAO modDAO = ModuleDAO.getInstance();
		return modDAO.update(new Module(getID(), getName(), developmentStart,
				getDevelopmentEnd()));
	}

	public Date getDevelopmentEnd() {
		return developmentEnd;
	}

	public boolean setDevelopmentEnd(Date developmentEnd) {
		this.developmentEnd = developmentEnd;
		ModuleDAO modDAO = ModuleDAO.getInstance();
		return modDAO.update(new Module(getID(), getName(),
				getDevelopmentStart(), developmentEnd));
	}

}
