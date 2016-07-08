package report;

import database.ProjectRequirementDAO;
import project.Project;
import project.ProjectCatalogue;
import resource.*;
import unit.Unit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectRequirementCatalogue {

    private static ProjectRequirementCatalogue projectRequirementCatalogue;

    private ProjectRequirementCatalogue() {
    }

    public static ProjectRequirementCatalogue getInstance() {
        if (projectRequirementCatalogue == null)
            projectRequirementCatalogue = new ProjectRequirementCatalogue();
        return projectRequirementCatalogue;
    }

    public ArrayList<ProjectRequirement> getAll() {
        return ProjectRequirementDAO.getInstance().list();
    }

    public ProjectRequirement get(String key) {
        return ProjectRequirementDAO.getInstance().get(key);
    }

    public void remove(ProjectRequirement prjReq) {
        ProjectRequirementDAO.getInstance().remove(prjReq.getID());
    }

    public ArrayList<Project> getProjectsWithEssentialResource(Resource res) {
        return ProjectRequirementDAO.getInstance()
                .getProjectsWithEssentialResource(res.getID());
    }

    public ArrayList<Resource> getRequiredResources(String pid) {
        return ProjectRequirementDAO.getInstance().getRequiredResources(pid);
    }

    public boolean addProjectRequirement(ProjectRequirement item,
                                         String projectID, String resourceID) {

        return ProjectRequirementDAO.getInstance().add(item, projectID,
                resourceID);
    }

    public ArrayList<String> getBoundedUsageFlowReport(Date startDate,
                                                       Date endDate, List<Resource> resources) {
        ProjectRequirementDAO dao = ProjectRequirementDAO.getInstance();
        return dao.getFlowReport(startDate, endDate, resources);

    }

    public ArrayList<String> getUnBoundedUsageFlowReport(
            List<Resource> resources) {
        ProjectRequirementDAO dao = ProjectRequirementDAO.getInstance();
        return dao.getFlowReport(null, null, resources);

    }

    public boolean add(ProjectRequirement prjReq, String projectID,
                       String unitID, Resource resource) {
        ProjectRequirementDAO dao = ProjectRequirementDAO.getInstance();

        resource = getSimilarResourceToReq(prjReq, unitID, projectID, resource);

        prjReq.setResource(resource);

        if (resource.isAvailable()
                && resource.getResourceStatus().equals(ResourceStatus.IDLE)) {
            prjReq.setProvideDate(new Date(System.currentTimeMillis()));
            resource.setResourceStatus(ResourceStatus.BUSY);
            ResourceCatalogue.getInstance().update(
                    new Resource(resource.getID(), ResourceStatus.BUSY,
                            resource.isAvailable()));
        }

        return dao.add(prjReq, projectID, resource.getID());
    }

    public Resource getSimilarResourceToReq(ProjectRequirement prjReq,
                                            String unitID, String projectID, Resource resource) {
        ArrayList<Unit> involvedUnits = ProjectCatalogue.getInstance()
                .get(projectID).getInvolvedUnits();
        Resource result = null;

        ArrayList<Resource> allSpecialResources = new ArrayList<>();
        for (Unit unit : involvedUnits) {

            if (resource instanceof HumanResource) {
                allSpecialResources.addAll(unit
                        .getResources(ResourceType.HUMAN));
            } else if (resource instanceof PhysicalResource) {
                allSpecialResources.addAll(unit
                        .getResources(ResourceType.PHYSICAL));
            } else if (resource instanceof MonetaryResource) {
                allSpecialResources.addAll(unit
                        .getResources(ResourceType.MONETARY));
            } else if (resource instanceof InformationResource) {
                allSpecialResources.addAll(unit
                        .getResources(ResourceType.INFORMATION));
            }
        }
        Date minReleaseDate = null;
        for (Resource resource2 : allSpecialResources) {
            if (isSimilar(resource, resource2)) {
                System.out.println(resource2.getID() + ":"
                        + isSimilar(resource, resource2));
                if (resource2.isAvailable()
                        && resource2.getResourceStatus().equals(
                        ResourceStatus.IDLE)) {
                    result = resource2;
                    if ((resource instanceof MonetaryResource)) {
                        MonetaryResource MonRes1 = (MonetaryResource) resource;
                        MonetaryResource MonRes = (MonetaryResource) resource2;
                        if (MonRes1.getMonetaryType().equals(MonetaryType.CASH)) {
                            MonRes.getQuantity()
                                    .setAmount(
                                            MonRes.getQuantity().getAmount()
                                                    - MonRes1.getQuantity()
                                                    .getAmount());
                            ResourceCatalogue.getInstance().update(MonRes);
                            MonetaryResource newMonRes = new MonetaryResource(
                                    MonRes.getMonetaryType(),
                                    MonRes.getLocation(),
                                    MonRes.getAccountNumber(),
                                    MonRes1.getQuantity());
                            ResourceCatalogue.getInstance().add(newMonRes,
                                    MonRes.getUnitID(), projectID);
                            result = newMonRes;
                        }
                    }
                } else if (resource2.isAvailable()
                        && !resource2.getResourceStatus().equals(
                        ResourceStatus.IDLE)) {
                    if (minReleaseDate == null) {
                        for (ProjectRequirement req : getAll()) {
                            if (req.getResource().getID()
                                    .equals(resource2.getID())
                                    && req.getReleaseDate().before(
                                    prjReq.getCriticalProvideDate())) {
                                result = resource2;
                                minReleaseDate = req.getReleaseDate();
                                break;
                            }
                        }
                    } else {
                        for (ProjectRequirement req : getAll()) {
                            if (req.getResource().getID()
                                    .equals(resource2.getID())
                                    && req.getReleaseDate().before(
                                    minReleaseDate)) {
                                result = resource2;
                                minReleaseDate = req.getReleaseDate();
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (result == null) {
            resource.setAvailable(false);
            ResourceCatalogue.getInstance().add(resource, unitID, null);
            result = resource;
        }
        return result;
    }

    private boolean isSimilar(Resource res1, Resource res2) {
        Boolean bool = false;
        if (res1 instanceof HumanResource) {
            HumanResource hres1 = (HumanResource) res1;
            HumanResource hres2 = (HumanResource) res2;
            bool = (hres1.getFirstName() == null ? true : (hres1.getFirstName()
                    .equals(hres2.getFirstName())))
                    && ((hres1.getLastName() == null ? true : (hres1
                    .getLastName().equals(hres2.getLastName()))))
                    && (hres1.getExpertise() == null ? true : (hres1
                    .getExpertise().equals(hres2.getExpertise())));
        } else if (res1 instanceof PhysicalResource) {
            PhysicalResource hres1 = (PhysicalResource) res1;
            PhysicalResource hres2 = (PhysicalResource) res2;
            bool = (hres1.getName() == null ? true : (hres1.getName()
                    .equals(hres2.getName())))
                    && ((hres1.getModel() == null ? true : (hres1.getModel()
                    .equals(hres2.getModel()))))
                    && (hres1.getLocation() == null ? true : (hres1
                    .getLocation().equals(hres2.getLocation())));
        } else if (res1 instanceof InformationResource) {
            InformationResource hres1 = (InformationResource) res1;
            InformationResource hres2 = (InformationResource) res2;
            bool = (hres1.getName() == null ? true : (hres1.getName()
                    .equals(hres2.getName())));
        } else if (res1 instanceof MonetaryResource) {
            MonetaryResource hres1 = (MonetaryResource) res1;
            MonetaryResource hres2 = (MonetaryResource) res2;
            bool = ((!(hres1.getAccountNumber() >= 0) ? true : (hres1
                    .getAccountNumber() == hres2.getAccountNumber()))
                    && (hres1.getMonetaryType() == null ? true : (hres1
                    .getMonetaryType().equals(hres2.getMonetaryType())))
                    && (hres1.getQuantity().getAmount() > 0 ? (hres1
                    .getQuantity().getAmount() < hres2.getQuantity()
                    .getAmount()) : true)
                    && (hres1.getQuantity().getQuantityUnit() != null ? (hres1
                    .getQuantity().getQuantityUnit().equals(hres2.getQuantity()
                            .getQuantityUnit())) : true)
                    && (hres1.getLocation() == null ? true
                    : (hres1.getLocation().equals(hres2.getLocation()))));
        }
        return bool;
    }

    public void satisfyRequirement(ProjectRequirement req) {
        req.getResource().setAvailable(true);
        req.getResource().setProjectID(req.getProject().getID());
        req.setProvideDate(new Date(System.currentTimeMillis()));
        req.setReleaseDate(new Date(System.currentTimeMillis()
                + req.getLengthOfPossession() * 24 * 60 * 60 * 1000));
        req.getResource().setResourceStatus(ResourceStatus.BUSY);
        ResourceCatalogue.getInstance().update(
                new Resource(req.getResource().getID(), ResourceStatus.BUSY,
                        req.getResource().isAvailable()));
        ProjectRequirementDAO.getInstance().update(req);
    }

}
