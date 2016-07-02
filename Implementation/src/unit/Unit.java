package unit;

import database.RequirementDAO;
import database.UnitDAO;
import database.UnitResourceDAO;
import resource.Resource;
import utility.Identifiable;

import java.util.ArrayList;
import java.util.Random;

public class Unit extends Identifiable {

    private String ID;
    private String name;

    public Unit() {
        setID(generateNDigitID(ID_LENGTH));
    }

    public Unit(String iD, String name) {
        setID(iD);
        this.name = name;
    }

    public Unit(String name) {
        setID(generateNDigitID(ID_LENGTH));
        this.name = name;
    }

    public ArrayList<Requirement> getRequirements() {
        return RequirementDAO.getInstance().getRequirementsByUnitID(getID());
    }

    public void removeRequirement(Requirement req) {
        RequirementDAO.getInstance().remove(req.getID());
    }

    public ArrayList<Resource> getResources() {
        return UnitResourceDAO.getInstance().getResourceByUnitID(getID());
    }

    public ArrayList<Resource> getAvailableResources() {
        return UnitResourceDAO.getInstance().getAvailableResourceByUnitID(getID());
    }

    public boolean addRequirement(Requirement req, String resourceID) {
        return RequirementDAO.getInstance().add(req, resourceID, getID());
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

    public boolean setName(String name) {
        this.name = name;
        UnitDAO unitDAO = UnitDAO.getInstance();
        return unitDAO.update(new Unit(ID, name));
    }

    @Override
    public String toString() {
        return "ID=" + ID + ", name=" + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unit)) return false;

        Unit unit = (Unit) o;

        if (getID() != null ? !getID().equals(unit.getID()) : unit.getID() != null) return false;
        return getName() != null ? getName().equals(unit.getName()) : unit.getName() == null;

    }

    @Override
    public int hashCode() {
        int result = getID() != null ? getID().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
