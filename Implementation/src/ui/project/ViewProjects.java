package ui.project;

import access.PermissionType;
import business_logic_facade.ProjectFacade;
import business_logic_facade.UserFacade;
import project.Project;
import ui.MainDialog;
import ui.Visibility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ViewProjects extends ProjectObserver implements Visibility {

    private MainDialog mainDialog;

    private AddNewProject addNewProject;

    private DefaultListModel<Project> listModel;
    private JList<Project> projectList;
    private JScrollPane jScrollPane;

    private ViewSingleProject viewSingleProject;

    private UserFacade userFacade;

    public ViewProjects(UserFacade userFacade) {
        mainDialog = new MainDialog();
        addNewProject = new AddNewProject();
        addNewProject.attach(this);
        this.userFacade = userFacade;
        prepareGUI();
    }

    private void prepareGUI() {
        mainDialog.getMainDialog().setTitle("مشاهده پروژه‌های سازمان ");
        JPanel addProjectsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;

        JButton addNewProject = new JButton("افزودن پروژه جدید");
        if (userFacade.getCurrentUserPermissions().get(PermissionType.canAddProject))
            addNewProject.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ViewProjects.this.addNewProject.setVisible(true);
                }
            });
        else
            addNewProject.setEnabled(false);

        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        addProjectsPanel.add(addNewProject, cs);
        mainDialog.getMainDialog().getContentPane().add(addProjectsPanel, BorderLayout.NORTH);

        ////////////////////
        listModel = new DefaultListModel<>();
        for (Project project : ProjectFacade.getInstance().getProjects())
            listModel.addElement(project);
        projectList = new JList<>(listModel);

        projectList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {
                    // Double-click detected
                    viewSingleProject = new ViewSingleProject(userFacade, ((Project) list.getSelectedValue()).getID());
                    viewSingleProject.setVisible(true);
                }
            }
        });

        ////////////////////
        jScrollPane = new JScrollPane(projectList);
        mainDialog.getMainDialog().add(jScrollPane, BorderLayout.CENTER);
        mainDialog.getMainDialog().setResizable(true);
        mainDialog.getMainDialog().pack();
        mainDialog.getMainDialog().setLocationRelativeTo(null);
    }

    @Override
    public void setVisible(boolean visible) {
        mainDialog.getMainDialog().setVisible(visible);
    }

    @Override
    public void update() {
        mainDialog.getMainDialog().remove(jScrollPane);
        listModel = new DefaultListModel<>();
        for (Project project : ProjectFacade.getInstance().getProjects())
            listModel.addElement(project);
        projectList = new JList<>(listModel);

        projectList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {
                    // Double-click detected
                    viewSingleProject = new ViewSingleProject(userFacade, ((Project) list.getSelectedValue()).getID());
                    viewSingleProject.setVisible(true);
                }
            }
        });

        jScrollPane = new JScrollPane(projectList);
        mainDialog.getMainDialog().add(jScrollPane, BorderLayout.CENTER);
        mainDialog.getMainDialog().repaint();
        mainDialog.getMainDialog().revalidate();
    }

    public static void main(String[] args) {
        UserFacade userFacade = new UserFacade();
        userFacade.login("100824", "888");
        ViewProjects viewProjects = new ViewProjects(userFacade);
        viewProjects.setVisible(true);
    }
}