package project;

import java.util.Date;
import java.util.Random;

public class Module {

	int ID_LENGTH = 6;
	String ID;
	String name;
	Date developmentStart;
	Date developmentEnd;

	public Module() {
		this.ID = generateNDigitID(ID_LENGTH);
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

	public Date getDevelopmentStart() {
		return developmentStart;
	}

	public void setDevelopmentStart(Date developmentStart) {
		this.developmentStart = developmentStart;
	}

	public Date getDevelopmentEnd() {
		return developmentEnd;
	}

	public void setDevelopmentEnd(Date developmentEnd) {
		this.developmentEnd = developmentEnd;
	}

}
