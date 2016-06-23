package project;

import java.util.Date;

public class Module {
	
	String ID;
	String name;
	Date developmentStart;
	Date developmentEnd;
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
