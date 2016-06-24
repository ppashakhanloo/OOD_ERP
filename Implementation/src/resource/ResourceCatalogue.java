package resource;

import java.util.ArrayList;

import database.*;

public class ResourceCatalogue {
    // private ArrayList<Resource> resourceList;

    private HumanResourceDAO humanResourceDAO;
    private MonetaryResourceDAO monetaryResourceDAO;
    private InformationResourceDAO informationResourceDAO;
    private PhysicalResourceDAO physicalResourceDAO;

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
            return humanResourceDAO.getInstance().add(resource, unitID, projectID);
        else if (resource instanceof PhysicalResource)
            return physicalResourceDAO.getInstance().add(resource, unitID, projectID);
        else if (resource instanceof InformationResource)
            return informationResourceDAO.getInstance().add(resource, unitID, projectID);
        else if (resource instanceof MonetaryResource)
            return monetaryResourceDAO.getInstance().add(resource, unitID, projectID);
        return false;
    }

    public void remove(Resource resource) {
        if (resource instanceof HumanResource)
            humanResourceDAO.getInstance().remove(resource.getID());
        else if (resource instanceof PhysicalResource)
            physicalResourceDAO.getInstance().remove(resource.getID());
        else if (resource instanceof InformationResource)
            informationResourceDAO.getInstance().remove(resource.getID());
        else if (resource instanceof MonetaryResource)
            monetaryResourceDAO.getInstance().remove(resource.getID());
    }

    public ArrayList<Resource> getAll() {
        ArrayList<Resource> allResources = new ArrayList<>();
        allResources.addAll(humanResourceDAO.getInstance().list());
        allResources.addAll(physicalResourceDAO.getInstance().list());
        allResources.addAll(informationResourceDAO.getInstance().list());
        allResources.addAll(monetaryResourceDAO.getInstance().list());

        ArrayList<Resource> realResources = new ArrayList<>();

        for (Resource resource : allResources) {
            if (resource.isAvailable()) {
                System.out.println("hi!!!!!!!!!!!!!!!!!!!!!!");
                realResources.add(resource);
            }
        }
        System.out.println("res=" + realResources);
        return realResources;
    }

    public ArrayList<Resource> getAvailableResources() {
        ArrayList<Resource> realResources = getAll();
        ArrayList<Resource> availableResources = new ArrayList<>();
        for (Resource resource : realResources) {
            if (resource.resourceStatus.equals(ResourceStatus.IDLE))
                availableResources.add(resource);
        }
        return availableResources;
    }

    public Resource get(String ID) {
        ResourceDAO resourceDAO = ResourceDAO.getInstance();
        System.out.println("ID: " + ID);
        System.out.println("IN RESOURCE CATALOG: " + resourceDAO.get(ID));
        return resourceDAO.get(ID);
    }

    public ArrayList<Resource> getAll(ResourceType resourceType) {
        ArrayList<Resource> realResources = getAll();
        System.out.println("***************here: " + realResources);
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
        HumanResourceDAO humanResourceDAO = HumanResourceDAO.getInstance();
        if (humanResourceDAO.login(ID, password))
            return (HumanResource) humanResourceDAO.get(ID);
        return null;
    }

    public void humanResourceLogout(String ID) {
        HumanResourceDAO humanResourceDAO = HumanResourceDAO.getInstance();
        humanResourceDAO.logout(ID);
    }
}
