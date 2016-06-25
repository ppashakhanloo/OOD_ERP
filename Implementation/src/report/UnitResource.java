package report;

import java.util.Date;
import java.util.Random;

import resource.Resource;
import unit.Unit;
import database.UnitResourceDAO;

public class UnitResource {
	String ID;
	int ID_LENGTH = 6;
	Date additionDate;
	Date removeDate;

	public UnitResource() {
		setID(generateNDigitID(ID_LENGTH));
	}

	private String generateNDigitID(int n) {
		Random random = new Random();
		return Integer.toString((int) (Math.pow(10, n - 1) + random.nextFloat()
				* 9 * Math.pow(10, n - 1)));
	}

	public UnitResource(String iD, Date additionDate, Date removeDate) {
		setID(iD);
		this.additionDate = additionDate;
		this.removeDate = removeDate;
	}

	public String getID() {
		return ID;
	}

	private void setID(String iD) {
		ID = iD;
	}

	public Date getAdditionDate() {
		return additionDate;
	}

	public boolean setAdditionDate(Date additionDate) {
		this.additionDate = additionDate;
		UnitResourceDAO dao = UnitResourceDAO.getInstance();
		return dao
				.update(new UnitResource(null, additionDate, getRemoveDate()));
	}

	public Date getRemoveDate() {
		return removeDate;
	}

	public boolean setRemoveDate(Date removeDate) {
		this.removeDate = removeDate;
		UnitResourceDAO dao = UnitResourceDAO.getInstance();
		return dao
				.update(new UnitResource(null, getAdditionDate(), removeDate));
	}

	public Resource getResource() {
		UnitResourceDAO dao = UnitResourceDAO.getInstance();
		return dao.getResource(getID());
	}

	public Unit getUnit() {
		UnitResourceDAO dao = UnitResourceDAO.getInstance();
		return dao.getUnit(getID());
	}

}
