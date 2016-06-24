package unit;

import java.util.ArrayList;
import java.util.Random;

import resource.Resource;
import database.RequirementDAO;
import database.UnitDAO;
import database.UnitResourceDAO;

public class Unit {

	int ID_LENGTH = 6;
	String ID;
	String name;

	public Unit() {
		setID(generateNDigitID(ID_LENGTH));
	}

	public Unit(String iD, String name) {
		setID(iD);
		this.name = name;
	}

	private String generateNDigitID(int n) {
		Random random = new Random();
		return Integer.toString((int) (Math.pow(10, n - 1) + random.nextFloat()
				* 9 * Math.pow(10, n - 1)));
	}

	public ArrayList<Requirement> getRequirements() {
		UnitDAO unitDAO = UnitDAO.getInstance();
		return unitDAO.getRequirements(getID());
	}

	public void removeRequirement(Requirement req) {
		UnitDAO unitDAO = UnitDAO.getInstance();
		unitDAO.removeRequirement(req);
	}

	public ArrayList<Resource> getResources() {
		UnitResourceDAO urDAO = UnitResourceDAO.getInstance();
		return urDAO.getResourceByUnitID(getID());
	}

	public ArrayList<Resource> getAvailableResources() {
		UnitResourceDAO urDAO = UnitResourceDAO.getInstance();
		return urDAO.getAvailableResourceByUnitID(getID());
	}

	public boolean addRequirement(Requirement req, String resourceID) {
		RequirementDAO reqDAO = RequirementDAO.getInstance();
		return reqDAO.add(req, resourceID, getID());
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

	public void setName(String name) {
		this.name = name;
	}

}
