package ui.project;

import access.PermissionType;
import business_logic_facade.OperationFacade;
import business_logic_facade.ProjectFacade;
import business_logic_facade.UserFacade;
import project.Project;
import resource.Resource;
import ui.MainFrame;
import ui.Visibility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private void prepareGUI() {
        mainFrame.getMainFrame().setTitle("مشاهده منابع پروژه");
        JPanel addProjectsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;

        JButton addNew = new JButton("تخصیص منبع جدید");
        if (mainFrame.getCurrentUser().getCurrentUserPermissions().get(PermissionType.canAddRemReq))
            addNew.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
//                    addNewProject.setVisible(true);
                }
            });
        else
            addNew.setEnabled(false);

        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        addProjectsPanel.add(addNew, cs);
        mainFrame.getMainFrame().getContentPane().add(addProjectsPanel, BorderLayout.NORTH);

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

    public static void main(String[] args) {
//        UserFacade userFacade = new UserFacade();
//        userFacade.login("158481", "y");
//        ViewProjectResources viewProjects = new ViewProjectResources(userFacade);
//        viewProjects.setVisible(true);
    }
}