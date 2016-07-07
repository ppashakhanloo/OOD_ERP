package project;

import database.ModuleDAO;
import database.ProjectDAO;

import java.util.ArrayList;

public class ProjectCatalogue {

    private static ProjectCatalogue projectCatalogue;

    private ProjectCatalogue() {
    }

    public static ProjectCatalogue getInstance() {
        if (projectCatalogue == null)
            projectCatalogue = new ProjectCatalogue();
        return projectCatalogue;
    }

    public Module getModule(String id) {
        return ModuleDAO.getInstance().get(id);
    }

    public boolean add(Project project, ArrayList<String> uid) {
        return ProjectDAO.getInstance().add(project, uid);
    }

    public void remove(Project project) {
        ProjectDAO.getInstance().remove(project.getID());
    }

    public Project get(String ID) {
        return ProjectDAO.getInstance().get(ID);
    }

    public boolean update(Project project) {
        return ProjectDAO.getInstance().update(project);
    }

    public ArrayList<Technology> getTechnologies() {
        return ProjectDAO.getInstance().getAllTechnologies();
    }

    public ArrayList<Project> list() {
        return ProjectDAO.getInstance().list();
    }

    public ArrayList<Project> search(Technology tech, int userCount,
                                     int devCount) {
//                                     int devCount, int ModCount) {
        ProjectDAO projectDAO = ProjectDAO.getInstance();
        ArrayList<Project> result = new ArrayList<>();
        ArrayList<Project> userCountProjects = projectDAO
                .getByUsersCount(userCount);
        ArrayList<Project> devCountsProjects = projectDAO
                .getByDevelopersCount(devCount);
//        ArrayList<Project> oneTechProjects = new ArrayList<>();
//        ArrayList<Project> techProjects = new ArrayList<>();
//        for (Technology tech : techs) {
//            oneTechProjects = projectDAO.getByTechnology(tech);
//            oneTechProjects.forEach(project -> techProjects.add(project));
//        }
        ArrayList<Project> techProjects = projectDAO.getByTechnology(tech);
        if (userCount >= 0) {
            if (devCount >= 0) {
                if (tech == null) {
                    for (Project tProject : userCountProjects) {
                        for (Project dProject : devCountsProjects) {
                            if (tProject.getID().equals(dProject.getID())) {
                                result.add(tProject);
                                break;
                            }
                        }
                    }
                } else {
                    for (Project tProject : techProjects) {
                        for (Project dProject : devCountsProjects) {
                            for (Project uProject : userCountProjects) {
                                if (tProject.getID().equals(dProject.getID())
                                        && tProject.getID().equals(
                                        uProject.getID())) {
                                    result.add(tProject);
                                    break;
                                }
                            }
                        }
                    }
                }
            } else {
                for (Project project : userCountProjects) {
                    for (Project tProject : techProjects) {
                        if (tProject.getID().equals(project.getID())) {
                            result.add(project);
                            break;
                        }
                    }
                }
            }
        } else {
            if (devCount >= 0) {
                if (tech == null) {
                    result = devCountsProjects;
                } else {
                    for (Project tproject : techProjects) {
                        for (Project dproject : devCountsProjects) {
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
