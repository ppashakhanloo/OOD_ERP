package ui.project;

import access.PermissionType;
import business_logic_facade.OperationFacade;
import business_logic_facade.ProjectFacade;
import business_logic_facade.UserFacade;
import project.Project;
import report.ProjectRequirement;
import resource.Resource;
import ui.MainFrame;
import ui.Visibility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewProjectRequirements extends ProjectObserver implements Visibility {

    private MainFrame mainFrame;
    private OperationFacade operationFacade;
    private ProjectFacade projectFacade;

    private DefaultListModel<ProjectRequirement> listModel;
    private JList<ProjectRequirement> resourceList;
    private JScrollPane jScrollPane;

    private Project project;

    public ViewProjectRequirements(UserFacade currentUser, Project project) {
        mainFrame = new MainFrame(currentUser);
        operationFacade = new OperationFacade();
        projectFacade = new ProjectFacade();
        this.project = project;
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame.getMainFrame().setTitle("مشاهده نیازمندی‌های پروژه");
        JPanel addProjectsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        System.out.println("**************HI*************");
        JButton addNew = new JButton("افزودن نیازمندی جدید");
        if (mainFrame.getCurrentUser().getCurrentUserPermissions().get(PermissionType.canAddRemReq))
            addNew.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AddNewProjectRequirement addNewProjectRequirement = new AddNewProjectRequirement(project.getID());
                    addNewProjectRequirement.setVisible(true);
                }
            });
        else
            addNew.setEnabled(false);
        System.out.println("**************HI*************");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        addProjectsPanel.add(addNew, cs);
        mainFrame.getMainFrame().getContentPane().add(addProjectsPanel, BorderLayout.NORTH);
        System.out.println("**************HI*************");
        /////////////////////////////////////////////
        listModel = new DefaultListModel<>();
        for (ProjectRequirement pr : projectFacade.getProjectRequirements(project.getID()))
            listModel.addElement(pr);
        resourceList = new JList<>(listModel);
        jScrollPane = new JScrollPane(resourceList);
        mainFrame.getMainFrame().add(jScrollPane, BorderLayout.CENTER);
        //////////////////////////////////////////
        System.out.println("**************END taghriban*************");
        mainFrame.getMainFrame().setResizable(true);
        System.out.println("**************END taghriban*************");
        mainFrame.getMainFrame().pack();
        System.out.println("**************END taghriban*************");
    }

    @Override
    public void setVisible(boolean visible) {
        mainFrame.getMainFrame().setVisible(visible);
    }

    @Override
    public void update() {
        mainFrame.getMainFrame().remove(jScrollPane);
        listModel = new DefaultListModel<>();
        for (ProjectRequirement pr : projectFacade.getProjectRequirements(project.getID()))
            listModel.addElement(pr);
        resourceList = new JList<>(listModel);
        jScrollPane = new JScrollPane(resourceList);
        mainFrame.getMainFrame().add(jScrollPane, BorderLayout.CENTER);
        mainFrame.getMainFrame().repaint();
        mainFrame.getMainFrame().revalidate();
    }

    public static void main(String[] args) {
//        UserFacade userFacade = new UserFacade();
//        userFacade.login("158481", "y");
//        ViewProjectResources viewProjects = new ViewProjectResources(userFacade);
//        viewProjects.setVisible(true);
    }
}