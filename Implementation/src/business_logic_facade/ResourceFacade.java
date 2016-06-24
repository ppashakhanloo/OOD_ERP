package business_logic_facade;

import access.AccessLevelFactory;
import resource.*;
import unit.Unit;
import unit.UnitCatalogue;

import java.util.ArrayList;

/**
 * Created by ppash on 6/24/2016.
 */
public class ResourceFacade {

    public ArrayList<Resource> getHumanResources() {
        return ResourceCatalogue.getInstance().getAll(ResourceType.HUMAN);
    }

    public ArrayList<Resource> getInformationResources() {
        return ResourceCatalogue.getInstance().getAll(ResourceType.INFORMATION);
    }

    public ArrayList<Resource> getPhysicalResources() {
        return ResourceCatalogue.getInstance().getAll(ResourceType.PHYSICAL);
    }

    public ArrayList<Resource> getMonetaryResources() {
        return ResourceCatalogue.getInstance().getAll(ResourceType.MONETARY);
    }

    public ArrayList<Unit> getUnits() {
        return UnitCatalogue.getInstance().getAll();
    }

    public void addNewHumanResource(String firstName, String lastName, String expertise, String password, String unitID) {
        // by default, set accessLevel to 3
        HumanResource humanResource = new HumanResource(firstName, lastName, expertise, password, (new AccessLevelFactory()).getAccessLevel("3"));
        ResourceCatalogue.getInstance().add(humanResource, unitID, "");
    }

}
