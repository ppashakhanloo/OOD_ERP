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

	public void setID(String ID) {
		this.ID = ID;
	}

	private String generateNDigitID(int n) {
		Random random = new Random();
		return Integer.toString((int) (Math.pow(10, n - 1) + random.nextFloat() * 9 * Math.pow(10, n - 1)));
	}

	public Resource() {
		this.setID(generateNDigitID(ID_LENGTH));
		this.setResourceStatus(ResourceStatus.IDLE);
		this.setAvailable(true);
	}

	public Resource(String iD, ResourceStatus resourceStatus, boolean isAvailable) {
		super();
		this.resourceStatus = resourceStatus;
		this.isAvailable = isAvailable;
		ID = iD;
	}

	public void edit(Resource resource) {
		this.resourceStatus = resource.getResourceStatus();
		this.isAvailable = resource.isAvailable();
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

	@Override
	public String toString() {
		return "ID=" + ID + ", resourceStatus=" + resourceStatus + ", isAvailable=" + isAvailable;
	}
}
