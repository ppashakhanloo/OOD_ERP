package business_logic_facade;

import project.Project;
import project.ProjectCatalogue;
import project.Technology;
import resource.HumanResource;
import unit.Unit;

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

    public void addNewTechnology(String name, String reason, String pid) {
        ProjectCatalogue.getInstance().get(pid).addTechnology(new Technology(name, reason));
    }

    public void addInvolvedUnit(String uid, String pid) {
//        ProjectCatalogue.getInstance().get(pid).
    }
}
