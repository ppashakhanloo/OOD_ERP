package report;

import java.util.Date;

public class UnitResource {
	String ID;
	Date additionDate;
	Date removeDate;

	public UnitResource() {

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

	public void setAdditionDate(Date additionDate) {
		this.additionDate = additionDate;
	}

	public Date getRemoveDate() {
		return removeDate;
	}

	public void setRemoveDate(Date removeDate) {
		this.removeDate = removeDate;
	}

}
