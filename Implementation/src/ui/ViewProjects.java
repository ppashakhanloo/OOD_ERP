package ui;

import access.PermissionType;
import business_logic_facade.OperationFacade;
import business_logic_facade.UserFacade;
import project.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ppash on 6/24/2016.
 */
public class ViewProjects extends ProjectObserver implements Visiblity {

    private MainFrame mainFrame;
    private OperationFacade operationFacade;

    private AddNewProject addNewProject;

    private DefaultListModel<Project> listModel;
    private JList<Project> projectList;
    private JScrollPane jScrollPane;


    public ViewProjects(UserFacade currentUser) {
        mainFrame = new MainFrame(currentUser);
        operationFacade = new OperationFacade();
        addNewProject = new AddNewProject();
        addNewProject.attach(this);
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame.getMainFrame().setTitle("مشاهده پروژه‌های سازمان ");
        JPanel addProjectsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;

        JButton addNew = new JButton("افزودن پروژه جدید");
        if (mainFrame.getCurrentUser().getCurrentUserPermissions().get(PermissionType.canAddProject))
            addNew.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addNewProject.setVisible(true);
                }
            });
        else
            addNew.setEnabled(false);

        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        addProjectsPanel.add(addNew, cs);
        mainFrame.getMainFrame().getContentPane().add(addProjectsPanel, BorderLayout.NORTH);

        ////////////////////
        listModel = new DefaultListModel<>();
        for (Project project : operationFacade.getProjects())
            listModel.addElement(project);
        projectList = new JList<>(listModel);
        ////////////////////
        jScrollPane = new JScrollPane(projectList);
        mainFrame.getMainFrame().add(jScrollPane, BorderLayout.CENTER);
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
        for (Project project : operationFacade.getProjects())
            listModel.addElement(project);
        projectList = new JList<>(listModel);
        jScrollPane = new JScrollPane(projectList);
        mainFrame.getMainFrame().add(jScrollPane, BorderLayout.CENTER);
        mainFrame.getMainFrame().repaint();
        mainFrame.getMainFrame().revalidate();
    }

    public static void main(String[] args) {
        UserFacade userFacade = new UserFacade();
        userFacade.login("158481", "y");
        ViewProjects viewProjects = new ViewProjects(userFacade);
        viewProjects.setVisible(true);
    }
}