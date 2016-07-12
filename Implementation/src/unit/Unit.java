package unit;

import database.RequirementDAO;
import database.UnitResourceDAO;
import resource.*;
import utility.Identifiable;

import java.util.ArrayList;

public class Unit extends Identifiable {

    private String name;

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

    public ArrayList<Resource> getResources() {
        return UnitResourceDAO.getInstance().getResourceByUnitID(getID());
    }

    public ArrayList<Resource> getAvailableResources() {
        return UnitResourceDAO.getInstance().getAvailableResourceByUnitID(getID());
    }

    public boolean addRequirement(Requirement req, String resourceID) {
        return RequirementDAO.getInstance().add(req, resourceID, getID());
    }

    public String getName() {
        return name;
    }


    public ArrayList<Resource> getResources(ResourceType resType) {
        ArrayList<Resource> resources = new ArrayList<>();
        ArrayList<Resource> allUnitResource = UnitResourceDAO.getInstance().getAvailableResourceByUnitID(getID());
        for (Resource resource : allUnitResource) {
            if ((resource instanceof HumanResource) && resType.equals(ResourceType.HUMAN)) {
                resources.add(resource);
            } else if ((resource instanceof PhysicalResource) && resType.equals(ResourceType.PHYSICAL)) {
                resources.add(resource);
            } else if ((resource instanceof InformationResource) && resType.equals(ResourceType.INFORMATION)) {
                resources.add(resource);
            } else if ((resource instanceof MonetaryResource) && resType.equals(ResourceType.MONETARY)) {
                resources.add(resource);
            }
        }
        return resources;
    }


    @Override
    public String toString() {
        return name;
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
