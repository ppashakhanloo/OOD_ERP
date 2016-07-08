package ui.project;

import access.PermissionType;
import business_logic_facade.ProjectFacade;
import business_logic_facade.UserFacade;
import project.Project;
import report.ProjectRequirement;
import ui.MainFrame;
import ui.Visibility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewProjectRequirements extends ProjectObserver implements Visibility {

    private MainFrame mainFrame;
    private DefaultListModel<ProjectRequirement> listModel;
    private JList<ProjectRequirement> resourceList;
    private JScrollPane jScrollPane;

    private Project project;

    public ViewProjectRequirements(UserFacade currentUser, Project project) {
        mainFrame = new MainFrame(currentUser);
        this.project = project;
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame.getMainFrame().setTitle("مشاهده نیازمندی‌های پروژه");
        JPanel addProjectsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;

        JButton addNew = new JButton("افزودن نیازمندی جدید");
        if (mainFrame.getCurrentUser().hasPermission(PermissionType.canAddRemReq))
            addNew.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AddNewProjectRequirement addNewProjectRequirement = new AddNewProjectRequirement(ViewProjectRequirements.this, project.getID());
                    addNewProjectRequirement.setVisible(true);
                }
            });
        else
            addNew.setEnabled(false);

        JButton satisfyRequirement = new JButton("رفع نیازمندی");

        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        addProjectsPanel.add(addNew, cs);
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        addProjectsPanel.add(satisfyRequirement, cs);
        mainFrame.getMainFrame().getContentPane().add(addProjectsPanel, BorderLayout.NORTH);

        /////////////////////////////////////////////
        listModel = new DefaultListModel<>();
        for (ProjectRequirement pr : ProjectFacade.getInstance().getProjectRequirements(project.getID())) {
            if (pr.getProvideDate() == null)
                listModel.addElement(pr);
        }
        resourceList = new JList<>(listModel);
        jScrollPane = new JScrollPane(resourceList);
        mainFrame.getMainFrame().add(jScrollPane, BorderLayout.CENTER);
        //////////////////////////////////////////

        satisfyRequirement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProjectRequirement selectedRequirement = resourceList.getSelectedValue();
                ProjectFacade.getInstance().satisfyProjectRequirement(selectedRequirement);
                JOptionPane.showMessageDialog(null, "نیازمندی با موفّقیّت رفع گردید.");
                update();
            }
        });

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
        for (ProjectRequirement pr : ProjectFacade.getInstance().getProjectRequirements(project.getID())) {
            if (pr.getProvideDate() == null)
                listModel.addElement(pr);
        }
        resourceList = new JList<>(listModel);
        jScrollPane = new JScrollPane(resourceList);
        mainFrame.getMainFrame().add(jScrollPane, BorderLayout.CENTER);
        mainFrame.getMainFrame().repaint();
        mainFrame.getMainFrame().revalidate();
    }

    public static void main(String[] args) {
        UserFacade userFacade = new UserFacade();
        userFacade.login("100824", "888");
        ViewProjectRequirements viewProjectReqs = new ViewProjectRequirements(userFacade, ProjectFacade.getInstance().getProject("450629"));
        viewProjectReqs.setVisible(true);
    }
}