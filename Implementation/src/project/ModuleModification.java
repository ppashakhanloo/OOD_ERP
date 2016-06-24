package project;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import resource.HumanResource;
import database.ModuleModificationDAO;

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

	public ArrayList<HumanResource> getModifiers(){
		ModuleModificationDAO modDAO = ModuleModificationDAO.getInstance();
		return modDAO.getModifiers(getID());
	}
	
	public boolean addModifier(HumanResource modifier){
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

	public void setModificationType(String modificationType) {
		this.modificationType = modificationType;
	}

	public Date getModificationStart() {
		return modificationStart;
	}

	public void setModificationStart(Date modificationStart) {
		this.modificationStart = modificationStart;
	}

	public Date getModificationEnd() {
		return modificationEnd;
	}

	public void setModificationEnd(Date modificationEnd) {
		this.modificationEnd = modificationEnd;
	}

}
