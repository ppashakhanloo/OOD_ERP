package business_logic_facade;

import project.Project;
import project.ProjectCatalogue;
import resource.HumanResource;

import java.util.ArrayList;

public class ProjectFacade {

    public void addNewProject(String name, ArrayList<String> involvedUnits) {
        // by default, set accessLevel to 3
        Project project = new Project();
        project.setName(name);
        ProjectCatalogue.getInstance().add(project, involvedUnits);
    }

    public ArrayList<Project> getProjects() {
        return ProjectCatalogue.getInstance().list();
    }


    public HumanResource getProjectManager(String pid){
        return ProjectCatalogue.getInstance().get(pid).getProjectManager();
    }
}
