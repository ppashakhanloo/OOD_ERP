package ui.project;

import business_logic_facade.ProjectFacade;
import business_logic_facade.UserFacade;
import project.Project;
import resource.Resource;
import ui.MainFrame;
import ui.Visibility;

import javax.swing.*;
import java.awt.*;

public class ViewProjectResources extends ProjectObserver implements Visibility {

    private MainFrame mainFrame;


    private AddNewProject addNewProject;

    private DefaultListModel<Resource> listModel;
    private JList<Resource> resourceList;
    private JScrollPane jScrollPane;

    private Project project;

    public ViewProjectResources(UserFacade currentUser, Project project) {
        mainFrame = new MainFrame(currentUser);
        addNewProject = new AddNewProject();
        this.project = project;
        addNewProject.attach(this);
        prepareGUI();
    }

    public static void main(String[] args) {
//        UserFacade userFacade = new UserFacade();
//        userFacade.login("158481", "y");
//        ViewProjectResources viewProjects = new ViewProjectResources(userFacade);
//        viewProjects.setVisible(true);
    }

    private void prepareGUI() {
        mainFrame.getMainFrame().setTitle("مشاهده منابع پروژه");
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        /////////////////////////////////////////////
        listModel = new DefaultListModel<>();
        for (Resource resource : ProjectFacade.getInstance().getProjectResources(project.getID()))
            listModel.addElement(resource);
        resourceList = new JList<>(listModel);
        jScrollPane = new JScrollPane(resourceList);
        mainFrame.getMainFrame().add(jScrollPane, BorderLayout.CENTER);
        //////////////////////////////////////////

        mainFrame.getMainFrame().setResizable(true);
        mainFrame.getMainFrame().pack();
        mainFrame.getMainFrame().setLocationRelativeTo(null);
    }

    @Override
    public void setVisible(boolean visible) {
        mainFrame.getMainFrame().setVisible(visible);
    }

    @Override
    public void update() {
        mainFrame.getMainFrame().remove(jScrollPane);
        listModel = new DefaultListModel<>();
        for (Resource resource : ProjectFacade.getInstance().getProjectResources(project.getID()))
            listModel.addElement(resource);
        resourceList = new JList<>(listModel);
        jScrollPane = new JScrollPane(resourceList);
        mainFrame.getMainFrame().add(jScrollPane, BorderLayout.CENTER);
        mainFrame.getMainFrame().repaint();
        mainFrame.getMainFrame().revalidate();
    }
}