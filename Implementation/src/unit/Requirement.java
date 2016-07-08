package unit;

import database.RequirementDAO;
import resource.Resource;
import utility.Identifiable;

import java.util.Date;

public class Requirement extends Identifiable {

    private String description;
    private Date provideDate;

    public Requirement() {
        setID(generateNDigitID(ID_LENGTH));
    }

    public Requirement(String description, Date provideDate) {
        setID(generateNDigitID(ID_LENGTH));
        this.description = description;
        this.provideDate = provideDate;
    }

    public Requirement(String iD, String description, Date provideDate) {
        setID(iD);
        this.description = description;
        this.provideDate = provideDate;
    }

    public Resource getResource() {
        RequirementDAO reqDAO = RequirementDAO.getInstance();
        return reqDAO.getResource(getID());
    }

    public void setResource(String resID) {
        RequirementDAO reqDAO = RequirementDAO.getInstance();
        reqDAO.setResource(getID(), resID);
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

	
    @Override
    public String toString() {
            return "ID=" + getID() + ", "
                    + "provideDate=" + (provideDate == null ? "" : provideDate.toString())
                    + "\n, resource=" + (getResource() == null ? "" : getResource().toString());
    }
}
