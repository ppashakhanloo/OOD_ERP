package unit;

public class Unit {
	String ID;
	String name;
	
	public Unit(String iD, String name) {
		setID(iD);
		this.name = name;
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
