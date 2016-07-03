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

    public ArrayList<QuantityUnit> getQuantityUnits() {
        ArrayList<QuantityUnit> quantityUnits = new ArrayList<>();
        for (QuantityUnit quantityUnit : QuantityUnit.values())
            quantityUnits.add(quantityUnit);
        return quantityUnits;
    }

    public ArrayList<MonetaryType> getMonetaryTypes() {
        ArrayList<MonetaryType> monetaryTypes = new ArrayList<>();
        for (MonetaryType monetaryType : MonetaryType.values())
            monetaryTypes.add(monetaryType);
        return monetaryTypes;
    }

    public boolean addNewHumanResource(String firstName, String lastName, String expertise, String password, String unitID) {
        try {
            HumanResource humanResource = new HumanResource(firstName, lastName, expertise, password, (new AccessLevelFactory()).getAccessLevel(AccessLevelType.High));
            ResourceCatalogue.getInstance().add(humanResource, unitID, "");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean addNewPhysicalResource(String name, String model, String location, String unitID) {
        try {
            ResourceCatalogue.getInstance().add(new PhysicalResource(name, model, location), unitID, "");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean addNewInformationResource(String name, String description, String unitID) {
        try {
            ResourceCatalogue.getInstance().add(new InformationResource(name, description), unitID, "");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void addNewUnit(String name) {
        UnitCatalogue.getInstance().add(new Unit(name));
    }

    public boolean addNewMonetaryResource(String monetaryType, String location, String accountNumber, Integer amount, String quantityUnit, String unitID) {
        try {
            MonetaryResource monetaryResource = new MonetaryResource(MonetaryType.valueOf(monetaryType),
                    location, Integer.parseInt(accountNumber),
                    new Quantity(amount, QuantityUnit.valueOf(quantityUnit)));
            ResourceCatalogue.getInstance().add(monetaryResource, unitID, "");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //    public HumanResource(String id, String firstName, String lastName, String expertise, String password,
//                         AccessLevel accessLevel) {
    public void updateHumanResource(String id, String firstName, String lastName, String expertise, AccessLevelType accessLevelType, ConfirmStatus confirmStatus, Unit unit) {
        HumanResource humanResource = new HumanResource(
                id,
                firstName,
                lastName,
                expertise,
                ((HumanResource) ResourceCatalogue.getInstance().get(id)).getPassword(),
                new AccessLevelFactory().getAccessLevel(accessLevelType));
        humanResource.setConfirmStatus(confirmStatus);
        ResourceCatalogue.getInstance().update(humanResource);
    }

    public void updateInformationResource(String id, String name, String description) {
        InformationResource informationResource = new InformationResource(id, name, description);
        ResourceCatalogue.getInstance().update(informationResource);
    }

    public void updateMonetaryResource(String id, MonetaryType monetaryType, Integer amount, QuantityUnit quantityUnit,
                                       String location, String accountNumber, Unit unit) {
        MonetaryResource monetaryResource = new MonetaryResource(id, monetaryType, location, Integer.parseInt(accountNumber), new Quantity(amount, quantityUnit));
        ResourceCatalogue.getInstance().update(monetaryResource);
    }

    public void updatePhysicalResource(String id, String name, String model, String location, Unit unit) {
        PhysicalResource physicalResource = new PhysicalResource(id, name, model, location);
        ResourceCatalogue.getInstance().update(physicalResource);
    }

//    public Unit getResourceUnit(String rID) {
    // TODO after Maryam added this to somewhere!

//    }
}
