package business_logic_facade;

import access.AccessLevelFactory;
import access.AccessLevelType;
import project.Project;
import project.ProjectCatalogue;
import project.Technology;
import resource.*;
import unit.Unit;
import unit.UnitCatalogue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OperationFacade {

    private OperationFacade() {
    }

    private static OperationFacade operationFacade;

    public static OperationFacade getInstance() {
        if (operationFacade == null)
            operationFacade = new OperationFacade();
        return operationFacade;
    }

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
        return UnitCatalogue.getInstance().list();
    }

    public ArrayList<Technology> getTechnologies() {
        return ProjectCatalogue.getInstance().getTechnologies();
    }

    public ArrayList<Project> search(Technology tech, int userCount, int devCount) {
        return ProjectCatalogue.getInstance().search(tech, userCount, devCount);
    }

    public List<QuantityUnit> getQuantityUnits() {
        return Arrays.asList(QuantityUnit.values());
    }

    public void addNewHumanResource(String firstName, String lastName, String expertise, String password, String unitID) {
        // by default, set accessLevel to 3
        HumanResource humanResource = new HumanResource(firstName, lastName, expertise, password, (new AccessLevelFactory()).getAccessLevel(AccessLevelType.High));
        ResourceCatalogue.getInstance().add(humanResource, unitID, "");
    }

    public void addNewPhysicalResource(String name, String model, String location, String unitID) {
        ResourceCatalogue.getInstance().add(new PhysicalResource(name, model, location), unitID, "");
    }

    public void addNewInformationResource(String name, String description, String unitID) {
        ResourceCatalogue.getInstance().add(new InformationResource(name, description), unitID, "");
    }

    public void addNewUnit(String name) {
        UnitCatalogue.getInstance().add(new Unit(name));
    }

    public void addNewMonetaryResource(String monetaryType, String location, String accountNumber, Integer amount, String quantityUnit, String unitID) {
        MonetaryResource monetaryResource = new MonetaryResource(monetaryType.equals("CASH") ? MonetaryType.CASH : MonetaryType.NON_CASH,
                location, Integer.parseInt(accountNumber),
                new Quantity(amount, quantityUnit.equals("DOLLAR") ? QuantityUnit.DOLLAR : QuantityUnit.RIAL));
        ResourceCatalogue.getInstance().add(monetaryResource, unitID, "");
    }
}
