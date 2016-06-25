package project;

import database.ModuleModificationDAO;
import resource.HumanResource;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ModuleModification {
	int ID_LENGTH = 6;
	String ID;
	String modificationType;
	Date modificationStart;
	Date modificationEnd;

	public ModuleModification() {
		this.ID = generateNDigitID(ID_LENGTH);
	}

	private String generateNDigitID(int n) {
		Random random = new Random();
		return Integer.toString((int) (Math.pow(10, n - 1) + random.nextFloat()
				* 9 * Math.pow(10, n - 1)));
	}

	public ModuleModification(String iD, String modificationType,
			Date modificationStart, Date modificationEnd) {
		super();
		ID = iD;
		this.modificationType = modificationType;
		this.modificationStart = modificationStart;
		this.modificationEnd = modificationEnd;
	}

	public ArrayList<HumanResource> getModifiers() {
		ModuleModificationDAO modDAO = ModuleModificationDAO.getInstance();
		return modDAO.getModifiers(getID());
	}

	public boolean addModifier(HumanResource modifier) {
		ModuleModificationDAO modDAO = ModuleModificationDAO.getInstance();
		return modDAO.addModifier(getID(), modifier);
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getModificationType() {
		return modificationType;
	}

	public boolean setModificationType(String modificationType) {
		this.modificationType = modificationType;
		ModuleModificationDAO modDAO = ModuleModificationDAO.getInstance();
		return modDAO.update(new ModuleModification(getID(), modificationType,
				getModificationStart(), getModificationEnd()));
	}

	public Date getModificationStart() {
		return modificationStart;
	}

	public boolean setModificationStart(Date modificationStart) {
		this.modificationStart = modificationStart;
		ModuleModificationDAO modDAO = ModuleModificationDAO.getInstance();
		return modDAO
				.update(new ModuleModification(getID(), getModificationType(),
						modificationStart, getModificationEnd()));
	}

	public Date getModificationEnd() {
		return modificationEnd;
	}

	public boolean setModificationEnd(Date modificationEnd) {
		this.modificationEnd = modificationEnd;
		ModuleModificationDAO modDAO = ModuleModificationDAO.getInstance();
		return modDAO
				.update(new ModuleModification(getID(), getModificationType(),
						getModificationStart(), modificationEnd));
	}

}
