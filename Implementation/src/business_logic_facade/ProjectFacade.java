package business_logic_facade;

import project.*;
import project.System;
import report.ProjectRequirement;
import report.ProjectRequirementCatalogue;
import resource.HumanResource;
import resource.Resource;
import resource.ResourceCatalogue;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class ProjectFacade {

    private static ProjectFacade projectFacade;

    private ProjectFacade() {
    }

    public static ProjectFacade getInstance() {
        if (projectFacade == null)
            projectFacade = new ProjectFacade();
        return projectFacade;
    }

    public boolean addNewProject(String name, ArrayList<String> involvedUnits) {
        Project project = new Project();
        project.setName(name);
        return ProjectCatalogue.getInstance().add(project, involvedUnits);
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

    public boolean addNewTechnology(String name, String reason, String pid) {
        return ProjectCatalogue.getInstance().get(pid).addTechnology(new Technology(name, reason));
    }

    public ArrayList<Resource> getProjectResources(String pid) {
        return ProjectCatalogue.getInstance().get(pid).getResources();
    }

    public ArrayList<ProjectRequirement> getProjectRequirements(String pid) {
        return ProjectCatalogue.getInstance().get(pid).getRequirements();
    }

    public boolean updateProject(String name, String managerID, int usersCount, String pid) {
        boolean setProjectManagerSuccessful = false;
        if (ProjectCatalogue.getInstance().get(pid).getProjectManager() == null)
            setProjectManagerSuccessful = ProjectCatalogue.getInstance().get(pid).setProjectManager((HumanResource) ResourceCatalogue.getInstance().get(managerID));
        else if (!managerID.equals(ProjectCatalogue.getInstance().get(pid).getProjectManager().getID()))
            setProjectManagerSuccessful = ProjectCatalogue.getInstance().get(pid).setProjectManager((HumanResource) ResourceCatalogue.getInstance().get(managerID));
        boolean setNameSuccessful = ProjectCatalogue.getInstance().get(pid).setName(name);
        boolean setUsersCountSuccessful = ProjectCatalogue.getInstance().get(pid).setUsersCount(usersCount);
        return setNameSuccessful && setUsersCountSuccessful && setProjectManagerSuccessful;
    }

    public boolean addNewSystem(String name, String pid) {
        return ProjectCatalogue.getInstance().get(pid).addSystem(new System(name));
    }

    public boolean addNewModule(String name, String sid, String pid, ArrayList<String> resourceID, ArrayList<String> developersID) {
        Module newModule = new Module();
        boolean addModuleSuccessful = ProjectCatalogue.getInstance().get(pid).getSystem(sid).addModule(newModule);
        boolean setNameSuccessful = newModule.setName(name);
        for (String id : developersID) {
            if (!newModule.addDeveloper((HumanResource) ResourceCatalogue.getInstance().get(id)))
                return false;
        }
        // TODO add resources
        return addModuleSuccessful && setNameSuccessful;
    }

    //    public boolean addProjectRequirement(ProjectRequirement item, String projectID,
//                                         String resourceID) {
    public boolean addRequirementToProject(boolean isEssential, java.util.Date criticalProvideDate, String lengthOfPossession, String pid, Resource resource) {
        ProjectRequirement projectRequirement = new ProjectRequirement();
        //
        projectRequirement.setEssential(isEssential);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        projectRequirement.setCriticalProvideDate(criticalProvideDate);
        projectRequirement.setLengthOfPossession(Integer.valueOf(lengthOfPossession));

        boolean add1Successful = ResourceCatalogue.getInstance().add(resource, "", pid);
        boolean add2Successful = ProjectRequirementCatalogue.getInstance().addProjectRequirement(projectRequirement, pid, resource.getID());

        return add1Successful && add2Successful;
    }

    public boolean addNewModuleModification(String mid, String modificationType, Date start, Date end, ArrayList<Resource> resources, ArrayList<Resource> developers) {
        ModuleModification moduleModification = new ModuleModification(modificationType, start, end);
        boolean moduleModificationSuccessful = ProjectCatalogue.getInstance().getModule(mid).addModification(moduleModification);
        for (Resource humanResourceID : developers)
            if (!moduleModification.addModifier((HumanResource) humanResourceID))
                return false;
// TODO
//        for (Resource resource : resources)
//            moduleModification.addResource(resource);

        return moduleModificationSuccessful;
    }


//
//    public boolean addModification(ModuleModification mod) {
//        return ModuleModificationDAO.getInstance().add(mod, getID());
//    }
}
