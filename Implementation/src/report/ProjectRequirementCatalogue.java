package report;

import java.util.ArrayList;

import database.ProjectRequirementDAO;

public class ProjectRequirementCatalogue {
	public ArrayList<ProjectRequirement> getAll(){
		ProjectRequirementDAO dao = ProjectRequirementDAO.getInstance();
		return dao.list();
	}
	
	public void remove(ProjectRequirement prjReq){
		ProjectRequirementDAO dao = ProjectRequirementDAO.getInstance();
		dao.remove(prjReq.getID());
	}
	
	
}
