package resource;

import access.AccessLevel;

public class HumanResource extends Resource {
	String firstName;
	String lastName;
	String expertise;
	String password;
	ConfirmStatus confirmStatus;

	AccessLevel accessLevel;

	public HumanResource() {
		super();
		setConfirmStatus(ConfirmStatus.PENDING);
	}

	public HumanResource(String firstName, String lastName, String expertise, String password,
			AccessLevel accessLevel) {
		super();
		this.confirmStatus = ConfirmStatus.PENDING;
		this.firstName = firstName;
		this.lastName = lastName;
		this.expertise = expertise;
		this.password = password;
		this.accessLevel = accessLevel;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ConfirmStatus getConfirmStatus() {
		return confirmStatus;
	}

	private void setConfirmStatus(ConfirmStatus confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public AccessLevel getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(AccessLevel accessLevel) {
		this.accessLevel = accessLevel;
	}

	public void confirm() {
		setConfirmStatus(ConfirmStatus.CONFIRMED);
	}

	@Override
	public String toString() {
		return super.toString() + ",\n" + "firstName=" + firstName + ", lastName=" + lastName + ", expertise="
				+ expertise + ", password=" + password + ", confirmStatus=" + confirmStatus.toString()
				+ ", accessLevelID=" + accessLevel.getID();
	}

}
