package business_logic_facade;

import access.AccessLevelFactory;
import resource.*;
import unit.Unit;
import unit.UnitCatalogue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<QuantityUnit> getQuantityUnits() { return Arrays.asList(QuantityUnit.values()); }

    public void addNewHumanResource(String firstName, String lastName, String expertise, String password, String unitID) {
        // by default, set accessLevel to 3
        HumanResource humanResource = new HumanResource(firstName, lastName, expertise, password, (new AccessLevelFactory()).getAccessLevel("3"));
        ResourceCatalogue.getInstance().add(humanResource, unitID, "");
    }

    public void addNewPhysicalResource(String name, String model, String location, String unitID) {
        PhysicalResource physicalResource = new PhysicalResource(name, model, location);
        ResourceCatalogue.getInstance().add(physicalResource, unitID, "");
    }

    public void addNewInformationResource(String name, String description, String unitID) {
        InformationResource informationResource = new InformationResource(name, description);
        ResourceCatalogue.getInstance().add(informationResource, unitID, "");
    }

    public void addNewMonetaryResource(String monetaryType, String location, String accountNumber, Integer amount, String quantityUnit, String unitID) {
        MonetaryResource monetaryResource = new MonetaryResource(monetaryType.equals("CASH") ? MonetaryType.CASH : MonetaryType.NON_CASH,
                location, Integer.parseInt(accountNumber),
                new Quantity(amount, quantityUnit.equals("DOLLAR") ? QuantityUnit.DOLLAR : QuantityUnit.RIAL));
        ResourceCatalogue.getInstance().add(monetaryResource, unitID, "");
    }
}
