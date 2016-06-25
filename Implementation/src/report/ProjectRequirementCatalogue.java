package report;

import java.util.ArrayList;

import database.ProjectRequirementDAO;

public class ProjectRequirementCatalogue {
	public ArrayList<ProjectRequirement> getAll(){
		ProjectRequirementDAO dao = ProjectRequirementDAO.getInstance();
		return dao.list();
	}
}
