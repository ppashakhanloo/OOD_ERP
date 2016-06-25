package project;

import java.util.ArrayList;
import database.ProjectDAO;

public class ProjectCatalogue {

	private static ProjectCatalogue projectCatalogue;

	private ProjectCatalogue() {
	}

	public static ProjectCatalogue getInstance() {
		if (projectCatalogue == null)
			projectCatalogue = new ProjectCatalogue();
		return projectCatalogue;
	}

	public boolean add(Project project, String uid) {
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		return projectDAO.add(project, uid);
	}

	public void remove(Project project) {
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		projectDAO.remove(project.getID());
	}

	public Project get(String ID) {
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		return projectDAO.get(ID);
	}

	public ArrayList<Project> list() {
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		return projectDAO.list();
	}

	public ArrayList<Project> search(Technology tech, int userCount,
			int devCount) {
		ProjectDAO projectDAO = ProjectDAO.getInstance();
		ArrayList<Project> result = new ArrayList<>();
		ArrayList<Project> userCountProjects = projectDAO
				.getByUsersCount(userCount);
		ArrayList<Project> devCounsProjects = projectDAO
				.getByDevelopersCount(devCount);
		ArrayList<Project> techProjects = projectDAO.getByTechnology(tech);
		if (userCount >= 0) {
			if (devCount >= 0) {
				if (tech == null) {
					for (Project tproject : userCountProjects) {
						for (Project dproject : devCounsProjects) {
							if (tproject.getID().equals(dproject.getID())) {
								result.add(tproject);
								break;
							}
						}
					}
				} else {
					for (Project tproject : techProjects) {
						for (Project dproject : devCounsProjects) {
							for (Project uproject : userCountProjects) {
								if (tproject.getID().equals(dproject.getID())
										&& tproject.getID().equals(
												uproject.getID())) {
									result.add(tproject);
									break;
								}
							}
						}
					}
				}
			} else {
				for (Project project : userCountProjects) {
					for (Project tproject : techProjects) {
						if (tproject.getID().equals(project.getID())) {
							result.add(project);
							break;
						}
					}
				}
			}
		} else {
			if (devCount >= 0) {
				if (tech == null) {
					result = devCounsProjects;
				} else {
					for (Project tproject : techProjects) {
						for (Project dproject : devCounsProjects) {
							if (tproject.getID().equals(dproject.getID())) {
								result.add(tproject);
								break;
							}
						}
					}
				}
			} else {
				result = techProjects;
			}
		}
		return result;
	}
}
