package report;

import database.ProjectRequirementDAO;
import project.Project;
import resource.Resource;

import java.util.ArrayList;
import java.util.Date;

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

    public void remove(ProjectRequirement prjReq) {
        ProjectRequirementDAO.getInstance().remove(prjReq.getID());
    }

    public ArrayList<Project> getProjectsWithEssentialResource(Resource res) {
        return ProjectRequirementDAO.getInstance().getProjectsWithEssentialResource(res.getID());
    }

    public ArrayList<Resource> getRequiredResources(String pid) {
        return ProjectRequirementDAO.getInstance().getRequiredResources(pid);
    }

    public boolean addProjectRequirement(ProjectRequirement item, String projectID,
                                         String resourceID) {

        return ProjectRequirementDAO.getInstance().add(item, projectID, resourceID);
    }
    
    public ArrayList<String> getBoundedUsageFlowReport(Date startDate, Date endDate, ArrayList<String> resourceIDs){
    	ProjectRequirementDAO dao = ProjectRequirementDAO.getInstance();
    	return dao.getFlowReport(startDate, endDate, resourceIDs);
    	
    }
    
    public ArrayList<String> getUnBoundedUsageFlowReport(ArrayList<String> resourceIDs){
    	ProjectRequirementDAO dao = ProjectRequirementDAO.getInstance();
    	return dao.getFlowReport(null, null, resourceIDs);
    	
    }

}
