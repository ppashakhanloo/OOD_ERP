package resource;

import access.AccessLevel;

public class HumanResource extends Resource {
    private String firstName;
    private String lastName;
    private String expertise;
    private String password;
    private ConfirmStatus confirmStatus;
    private AccessLevel accessLevel;

    public HumanResource() {
        super();
        setConfirmStatus(ConfirmStatus.PENDING);
    }

    public HumanResource(String id, String firstName, String lastName, String expertise, String password,
                         AccessLevel accessLevel) {
        setID(id);
        this.confirmStatus = ConfirmStatus.PENDING;
        this.firstName = firstName;
        this.lastName = lastName;
        this.expertise = expertise;
        this.password = password;
        this.accessLevel = accessLevel;
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

    public String getLastName() {
        return lastName;
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

    public ConfirmStatus getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(ConfirmStatus confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    @Override
    public String toString() {
//        return super.toString() + ",\n" + "firstName=" + firstName + ", lastName=" + lastName + ", expertise="
//                + expertise + ", password=" + password + ", confirmStatus=" + confirmStatus.toString()
//                + ", accessLevelID=" + accessLevel.getAccessLevelType().toString();

        return firstName + " " + lastName + " [" + expertise + "] [" + "سطح دسترسی" + "->" + accessLevel.getAccessLevelType().toString() + "\n" + super.toString();
    }


}
