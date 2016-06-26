package business_logic_facade;

import project.*;
import project.System;
import report.ProjectRequirement;
import report.ProjectRequirementCatalogue;
import resource.HumanResource;
import resource.Resource;
import resource.ResourceCatalogue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Properties;

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

    public Project getProject(String pid) {
        return ProjectCatalogue.getInstance().get(pid);
    }

    public HumanResource getProjectManager(String pid) {
        return ProjectCatalogue.getInstance().get(pid).getProjectManager();
    }

    public void addNewTechnology(String name, String reason, String pid) {
        ProjectCatalogue.getInstance().get(pid).addTechnology(new Technology(name, reason));
    }

    public ArrayList<Resource> getProjectResources(String pid) {
        return ProjectCatalogue.getInstance().get(pid).getResources();
    }

    public ArrayList<ProjectRequirement> getProjectRequirements(String pid) {
        return ProjectCatalogue.getInstance().get(pid).getRequirements();
    }

    public void updateProject(String name, String managerID, int usersCount, String pid) {
        if (ProjectCatalogue.getInstance().get(pid).getProjectManager() == null)
            ProjectCatalogue.getInstance().get(pid).setProjectManager((HumanResource) ResourceCatalogue.getInstance().get(managerID));
        else if (!managerID.equals(ProjectCatalogue.getInstance().get(pid).getProjectManager().getID()))
            ProjectCatalogue.getInstance().get(pid).setProjectManager((HumanResource) ResourceCatalogue.getInstance().get(managerID));
        ProjectCatalogue.getInstance().get(pid).setName(name);
        ProjectCatalogue.getInstance().get(pid).setUsersCount(usersCount);
    }

    public void addNewSystem(String name, String pid) {
        ProjectCatalogue.getInstance().get(pid).addSystem(new System(name));
    }

    public void addNewModule(String name, String sid, String pid, ArrayList<String> resourceID, ArrayList<String> developersID) {
        Module newModule = new Module();
        ProjectCatalogue.getInstance().get(pid).getSystem(sid).addModule(newModule);
        newModule.setName(name);
        for (String id : developersID)
            newModule.addDeveloper((HumanResource) ResourceCatalogue.getInstance().get(id));
        // TODO add resources
    }

    //    public boolean addProjectRequirement(ProjectRequirement item, String projectID,
//                                         String resourceID) {
    public void addRequirementToProject(boolean isEssential, java.util.Date criticalProvideDate, String lengthOfPossession, String pid, Resource resource) {
        ProjectRequirement projectRequirement = new ProjectRequirement();
        //
        projectRequirement.setEssential(isEssential);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        projectRequirement.setCriticalProvideDate(criticalProvideDate);
        projectRequirement.setLengthOfPossession(Integer.valueOf(lengthOfPossession));

        ResourceCatalogue.getInstance().add(resource, "", pid);

        ProjectRequirementCatalogue.getInstance().addProjectRequirement(projectRequirement, pid, resource.getID());
    }
}
