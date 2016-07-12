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

    public Project get(String ID) {
        return ProjectDAO.getInstance().get(ID);
    }

    public ArrayList<Technology> getTechnologies() {
        return ProjectDAO.getInstance().getAllTechnologies();
    }

    public ArrayList<Project> list() {
        return ProjectDAO.getInstance().list();
    }

    public ArrayList<Project> search(Technology tech, int userCount,
                                     int devCount, int modCount) {
        ProjectDAO projectDAO = ProjectDAO.getInstance();
        ArrayList<Project> result = new ArrayList<>();
        ArrayList<Project> userCountProjects = projectDAO
                .getByUsersCount(userCount);
        ArrayList<Project> devCountProjects = projectDAO
                .getByDevelopersCount(devCount);
        ArrayList<Project> modCountProjects = projectDAO
                .getByModulesCount(modCount);
//        ArrayList<Project> oneTechProjects = new ArrayList<>();
//        ArrayList<Project> techProjects = new ArrayList<>();
//        for (Technology tech : techs) {
//            oneTechProjects = projectDAO.getByTechnology(tech);
//            oneTechProjects.forEach(project -> techProjects.add(project));
//        }
        ArrayList<Project> techProjects = projectDAO.getByTechnology(tech);

        for (Project uProject : userCountProjects) {
            for (Project dProject : devCountProjects) {
                for (Project mProject : modCountProjects) {
                    for (Project tProject : techProjects) {
                        if (uProject.getID().equals(dProject.getID()) && dProject.getID().equals(mProject.getID()) && mProject.getID().equals(tProject.getID()))
                            result.add(uProject);
                    }
                }
            }
        }
        return result;
    }
}
