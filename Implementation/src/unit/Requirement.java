package unit;

import java.util.Date;
import java.util.Random;

import database.RequirementDAO;
import resource.Resource;

public class Requirement {

    private int ID_LENGTH = 6;
    private String ID;
    private String description;
    private Date provideDate;

    public Requirement() {
        setID(generateNDigitID(ID_LENGTH));
    }

    public Requirement(String iD, String description, Date provideDate) {
        setID(iD);
        this.description = description;
        this.provideDate = provideDate;
    }

    private String generateNDigitID(int n) {
        Random random = new Random();
        return Integer.toString((int) (Math.pow(10, n - 1) + random.nextFloat()
                * 9 * Math.pow(10, n - 1)));
    }

    public Resource getResource() {
        RequirementDAO reqDAO = RequirementDAO.getInstance();
        return reqDAO.getResource(getID());
    }

    public void setResource(String resID) {
        RequirementDAO reqDAO = RequirementDAO.getInstance();
        reqDAO.setResource(getID(), resID);
    }

    public String getID() {
        return ID;
    }

    private void setID(String iD) {
        ID = iD;
    }

    public String getDescription() {
        return description;
    }

    public boolean setDescription(String description) {
        this.description = description;
        RequirementDAO reqDAO = RequirementDAO.getInstance();
        return reqDAO.update(new Requirement(getID(), description,
                getProvideDate()));
    }

    public Date getProvideDate() {
        return provideDate;
    }

    public boolean setProvideDate(Date provideDate) {
        this.provideDate = provideDate;
        RequirementDAO reqDAO = RequirementDAO.getInstance();
        return reqDAO.update(new Requirement(getID(), getDescription(),
                provideDate));
    }
}
