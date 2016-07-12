package business_logic_facade;

import access.AccessLevelFactory;
import access.AccessLevelType;
import project.Project;
import project.ProjectCatalogue;
import project.Technology;
import report.ProjectRequirementCatalogue;
import resource.*;
import unit.Requirement;
import unit.Unit;
import unit.UnitCatalogue;

import java.util.*;

public class OperationFacade {

    private static OperationFacade operationFacade;

    private OperationFacade() {
    }

    public static OperationFacade getInstance() {
        if (operationFacade == null)
            operationFacade = new OperationFacade();
        return operationFacade;
    }

    public ArrayList<Resource> getResources() {
        return ResourceCatalogue.getInstance().getAll();
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

    public Map<Unit, ArrayList<Resource>> getAvailableResources() {
        Map<Unit, ArrayList<Resource>> availableResources = new HashMap<Unit, ArrayList<Resource>>();
        ArrayList<Unit> units = getUnits();

        for (Unit unit : units) {
            ArrayList<Resource> thisUnitsAvailableResources = new ArrayList<>();
            thisUnitsAvailableResources = unit.getAvailableResources();

            availableResources.put(unit, thisUnitsAvailableResources);
        }

        return availableResources;
    }

    public ArrayList<Requirement> getUnitRequirements(String uid) {
        return UnitCatalogue.getInstance().get(uid).getRequirements();
    }

    public boolean addUnitRequirement(String uid, Requirement unitRequirement, Resource resource) {
        return UnitCatalogue.getInstance().get(uid).addRequirement(unitRequirement, resource.getID());
    }

    public ArrayList<Technology> getTechnologies() {
        return ProjectCatalogue.getInstance().getTechnologies();
    }

    public ArrayList<Project> search(Technology tech, int userCount, int devCount, int modCount) {
        return ProjectCatalogue.getInstance().search(tech, userCount, devCount, modCount);
    }

    public ArrayList<String> getFlowReport(Date start, Date end, List<Resource> resources) {
        if (start == null || end == null)
            return ProjectRequirementCatalogue.getInstance().getUnBoundedUsageFlowReport(resources);
        else
            return ProjectRequirementCatalogue.getInstance().getBoundedUsageFlowReport(start, end, resources);
    }

    public ArrayList<QuantityUnit> getQuantityUnits() {
        ArrayList<QuantityUnit> quantityUnits = new ArrayList<>();
        Collections.addAll(quantityUnits, QuantityUnit.values());
        return quantityUnits;
    }

    public boolean addNewHumanResource(String firstName, String lastName, String expertise, String password, AccessLevelType type, String unitID) {
        return ResourceCatalogue.getInstance().add(new HumanResource(firstName, lastName, expertise, password, (new AccessLevelFactory()).getAccessLevel(type)), unitID, "");
    }

    public boolean addNewPhysicalResource(String name, String model, String location, String unitID) {
        return ResourceCatalogue.getInstance().add(new PhysicalResource(name, model, location), unitID, "");
    }

    public boolean addNewInformationResource(String name, String description, String unitID) {
        return ResourceCatalogue.getInstance().add(new InformationResource(name, description), unitID, "");
    }

    public boolean addNewUnit(String name) {
        return UnitCatalogue.getInstance().add(new Unit(name));
    }

    public boolean addNewMonetaryResource(String monetaryType, String location, String accountNumber, Integer amount, String quantityUnit, String unitID) {
        try {
            return ResourceCatalogue.getInstance().add(new MonetaryResource(MonetaryType.valueOf(monetaryType),
                    location, Integer.parseInt(accountNumber),
                    new Quantity(amount, QuantityUnit.valueOf(quantityUnit))), unitID, "");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //    public HumanResource(String id, String firstName, String lastName, String expertise, String password,
//                         AccessLevel accessLevel) {
    public boolean updateHumanResource(String id, String firstName, String lastName, String expertise, AccessLevelType accessLevelType, ConfirmStatus confirmStatus, Unit unit) {
        HumanResource humanResource = new HumanResource(
                id,
                firstName,
                lastName,
                expertise,
                ((HumanResource) ResourceCatalogue.getInstance().get(id)).getPassword(),
                new AccessLevelFactory().getAccessLevel(accessLevelType));
        humanResource.setConfirmStatus(confirmStatus);
        return ResourceCatalogue.getInstance().update(humanResource);
    }

    public boolean updateInformationResource(String id, String name, String description) {
        return ResourceCatalogue.getInstance().update(new InformationResource(id, name, description));
    }

    public boolean updateMonetaryResource(String id, MonetaryType monetaryType, Integer amount, QuantityUnit quantityUnit,
                                          String location, String accountNumber, Unit unit) {
        return ResourceCatalogue.getInstance().update(new MonetaryResource(id, monetaryType, location, Integer.parseInt(accountNumber), new Quantity(amount, quantityUnit)));
    }

    public boolean updatePhysicalResource(String id, String name, String model, String location, Unit unit) {
        return ResourceCatalogue.getInstance().update(new PhysicalResource(id, name, model, location));
    }

//    public Unit getResourceUnit(String rID) {
    // TODO after Maryam added this to somewhere!

//    }
}
