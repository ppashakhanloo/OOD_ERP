package resource;

import database.*;
import unit.Unit;

import java.util.ArrayList;

public class ResourceCatalogue {

    private static ResourceCatalogue resourceCatalogue;

    private ResourceCatalogue() {
        // resourceList = new ArrayList<>();
    }

    public static ResourceCatalogue getInstance() {
        if (resourceCatalogue == null) {
            resourceCatalogue = new ResourceCatalogue();
        }
        return resourceCatalogue;
    }

    public boolean add(Resource resource, String unitID, String projectID) {
        if (resource instanceof HumanResource)
            return HumanResourceDAO.getInstance().add(resource, unitID, projectID);
        else if (resource instanceof PhysicalResource)
            return PhysicalResourceDAO.getInstance().add(resource, unitID, projectID);
        else if (resource instanceof InformationResource)
            return InformationResourceDAO.getInstance().add(resource, unitID, projectID);
        else if (resource instanceof MonetaryResource)
            return MonetaryResourceDAO.getInstance().add(resource, unitID, projectID);
        return false;
    }

    public void remove(Resource resource) {
        if (resource instanceof HumanResource)
            HumanResourceDAO.getInstance().remove(resource.getID());
        else if (resource instanceof PhysicalResource)
            PhysicalResourceDAO.getInstance().remove(resource.getID());
        else if (resource instanceof InformationResource)
            InformationResourceDAO.getInstance().remove(resource.getID());
        else if (resource instanceof MonetaryResource)
            MonetaryResourceDAO.getInstance().remove(resource.getID());
    }

    public ArrayList<Resource> getAll() {
        ArrayList<Resource> allResources = new ArrayList<>();
        allResources.addAll(HumanResourceDAO.getInstance().list());
        allResources.addAll(PhysicalResourceDAO.getInstance().list());
        allResources.addAll(InformationResourceDAO.getInstance().list());
        allResources.addAll(MonetaryResourceDAO.getInstance().list());

        ArrayList<Resource> realResources = new ArrayList<>();

        for (Resource resource : allResources) {
            if (resource.isAvailable()) {
                realResources.add(resource);
            }
        }
        return realResources;
    }

    public ArrayList<Resource> getAvailableResources() {
        ArrayList<Resource> realResources = getAll();
        ArrayList<Resource> availableResources = new ArrayList<>();
        for (Resource resource : realResources) {
            if (resource.getResourceStatus().equals(ResourceStatus.IDLE))
                availableResources.add(resource);
        }
        return availableResources;
    }

    public Resource get(String ID) {
        Resource resource = HumanResourceDAO.getInstance().get(ID);
        if (resource == null)
            resource = InformationResourceDAO.getInstance().get(ID);
        if (resource == null)
            resource = PhysicalResourceDAO.getInstance().get(ID);
        if (resource == null)
            resource = MonetaryResourceDAO.getInstance().get(ID);
        return resource;
    }

    public ArrayList<Resource> getAll(ResourceType resourceType) {
        ArrayList<Resource> realResources = getAll();
        ArrayList<Resource> resources = new ArrayList<>();

        switch (resourceType) {
            case HUMAN:
                for (Resource resource : realResources) {
                    if (resource instanceof HumanResource)
                        resources.add(resource);
                }
                break;
            case INFORMATION:
                for (Resource resource : realResources) {
                    if (resource instanceof InformationResource)
                        resources.add(resource);
                }
                break;
            case MONETARY:
                for (Resource resource : realResources) {
                    if (resource instanceof MonetaryResource)
                        resources.add(resource);
                }
                break;
            case PHYSICAL:
                for (Resource resource : realResources) {
                    if (resource instanceof PhysicalResource)
                        resources.add(resource);
                }
                break;
        }
        return resources;
    }

    public HumanResource humanResourceLogin(String ID, String password) {
        if (HumanResourceDAO.getInstance().login(ID, password))
            return (HumanResource) HumanResourceDAO.getInstance().get(ID);
        return null;
    }

    public void humanResourceLogout(String ID) {
        HumanResourceDAO.getInstance().logout(ID);
    }
}
