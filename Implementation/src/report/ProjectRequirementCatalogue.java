package report;

import java.util.ArrayList;

import project.Project;
import resource.Resource;
import database.ProjectRequirementDAO;

public class ProjectRequirementCatalogue {
	public ArrayList<ProjectRequirement> getAll() {
		ProjectRequirementDAO dao = ProjectRequirementDAO.getInstance();
		return dao.list();
	}

	public void remove(ProjectRequirement prjReq) {
		ProjectRequirementDAO dao = ProjectRequirementDAO.getInstance();
		dao.remove(prjReq.getID());
	}

	public ArrayList<Project> getProjectsWithEssentialResource(Resource res) {
		ProjectRequirementDAO dao = ProjectRequirementDAO.getInstance();
		return dao.getProjectsWithEssentialResource(res.getID());
	}

	public ArrayList<Resource> getRequiredResources(String pid) {
		ProjectRequirementDAO dao = ProjectRequirementDAO.getInstance();
		return dao.getRequiredResources(pid);
	}
}
