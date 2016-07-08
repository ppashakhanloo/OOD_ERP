package resource;

import database.*;
import report.UnitResource;
import unit.UnitCatalogue;

import java.util.ArrayList;
import java.util.Date;

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
        boolean add1 = false;
        if (resource instanceof HumanResource) {
            add1 = HumanResourceDAO.getInstance().add(resource, projectID);
        } else if (resource instanceof PhysicalResource) {
            add1 = PhysicalResourceDAO.getInstance().add(resource, projectID);
        } else if (resource instanceof InformationResource) {
            add1 = InformationResourceDAO.getInstance().add(resource, projectID);
        } else if (resource instanceof MonetaryResource) {
            add1 = MonetaryResourceDAO.getInstance().add(resource, projectID);
        }

        boolean add2 = UnitResourceDAO.getInstance().add(new UnitResource(new Date(), null, UnitCatalogue.getInstance().get(unitID)), resource.getID());
        return add1 && add2;
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
                for (Resource resource : realResources)
                    if (resource instanceof HumanResource)
                        resources.add(resource);
                return resources;
            case INFORMATION:
                for (Resource resource : realResources)
                    if (resource instanceof InformationResource)
                        resources.add(resource);
                return resources;
            case MONETARY:
                for (Resource resource : realResources)
                    if (resource instanceof MonetaryResource)
                        resources.add(resource);
                return resources;
            case PHYSICAL:
                for (Resource resource : realResources)
                    if (resource instanceof PhysicalResource)
                        resources.add(resource);
                return resources;
        }
        return null;
    }

    public boolean update(Resource resource) {
        if (resource instanceof HumanResource)
            return HumanResourceDAO.getInstance().update(resource);
        if (resource instanceof InformationResource)
            return InformationResourceDAO.getInstance().update(resource);
        if (resource instanceof PhysicalResource)
            return PhysicalResourceDAO.getInstance().update(resource);
        if (resource instanceof MonetaryResource)
            return MonetaryResourceDAO.getInstance().update(resource);
        return resource != null && ResourceDAO.getInstance().update(resource);
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
