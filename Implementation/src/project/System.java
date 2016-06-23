package project;

import java.util.Random;

import resource.ResourceStatus;

public class System {
	int ID_LENGTH = 6;
	String ID;
	String name;

	public System(){
		this.setID(generateNDigitID(ID_LENGTH));
	}
	
	public System(String id, String name){
		this.setID(id);
		this.setName(name);
	}

	private String generateNDigitID(int n) {
		Random random = new Random();
		return Integer.toString((int) (Math.pow(10, n) + random.nextFloat() * 9 * Math.pow(10, n)));
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
