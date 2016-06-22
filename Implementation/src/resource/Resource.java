package resource;

import java.util.Random;

public class Resource {
	int ID_LENGTH = 6;
	ResourceStatus resourceStatus;
	boolean isAvailable;
	String ID;

	public String getID() {
		return ID;
	}

	private void setID(String ID) {
		this.ID = ID;
	}

	private String generateNDigitID(int n) {
		Random random = new Random();
		return Integer.toString((int) (Math.pow(10, n) + random.nextFloat() * 9 * Math.pow(10, n)));
	}

	public Resource() {
		this.setID(generateNDigitID(ID_LENGTH));
		this.setResourceStatus(ResourceStatus.IDLE);
	}

	public void edit(Resource resource) {
	}

	public ResourceStatus getResourceStatus() {
		return resourceStatus;
	}

	public void setResourceStatus(ResourceStatus resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	// TODO
	// public boolean logout() {
	//
	// }
}
