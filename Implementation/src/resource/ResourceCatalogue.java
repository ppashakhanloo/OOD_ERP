package resource;

import java.util.ArrayList;

import database.HumanResourceDAO;
import database.ResourceDAO;

public class ResourceCatalogue {
	// private ArrayList<Resource> resourceList;
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
		ResourceDAO resourceDAO = ResourceDAO.getInstance();
		return resourceDAO.add(resource, unitID, projectID);
	}

	public void remove(Resource resource) {
		ResourceDAO resourceDAO = ResourceDAO.getInstance();
		resourceDAO.remove(resource.getID());
	}

	public ArrayList<Resource> getAll() {
		ResourceDAO resourceDAO = ResourceDAO.getInstance();
		ArrayList<Resource> realResources = new ArrayList<>();
		for (Resource resource : resourceDAO.list()) {
			if (resource.isAvailable)
				realResources.add(resource);
		}
		return realResources;
	}

	public ArrayList<Resource> getAvailableResources() {
		ArrayList<Resource> realResources = getAll();
		ArrayList<Resource> availableResources = new ArrayList<>();
		for (Resource resource : realResources) {
			if (resource.resourceStatus == ResourceStatus.IDLE)
				availableResources.add(resource);
		}
		return realResources;
	}

	public Resource get(String ID) {
		ResourceDAO resourceDAO = ResourceDAO.getInstance();
		return resourceDAO.get(ID);
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

	public boolean humanResourceLogin(String ID, String password) {
		HumanResourceDAO humanResourceDAO = HumanResourceDAO.getInstance();
		return humanResourceDAO.login(ID, password);
	}

	public void humanResourceLogout(String ID) {
		HumanResourceDAO humanResourceDAO = HumanResourceDAO.getInstance();
		humanResourceDAO.logout(ID);
	}
}
