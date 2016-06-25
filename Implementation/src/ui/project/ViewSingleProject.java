package ui.project;

import business_logic_facade.OperationFacade;
import business_logic_facade.ProjectFacade;
import business_logic_facade.UserFacade;
import project.Project;
import project.Technology;
import ui.MainFrame;
import ui.Visibility;

import javax.swing.*;

public class ViewSingleProject extends ProjectObserver implements Visibility {
    private MainFrame mainFrame;
    private OperationFacade operationFacade;
    private ProjectFacade projectFacade;
    private Project project;
    private EditProject editProject;

    private JButton viewRes;
    private JButton viewReqs;
    private JButton edit;
    private JList<String> jList1;
    private JScrollPane treePanel;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JTree jTree1;
    private JLabel managerLbl;
    private JTextField managerName;
    private JTextField name;
    private JLabel nameLbl;
    private JPanel structPanel;
    private JTree structTree;
    private JList<Technology> techList;
    private JPanel techPanel;
    private JScrollPane unitList;
    private JPanel unitsPanel;
    private JSpinner usersCount;
    private JLabel usersCountLbl;

    public ViewSingleProject(UserFacade currentUser, Project project) {
        mainFrame = new MainFrame(currentUser);
        operationFacade = new OperationFacade();
        projectFacade = new ProjectFacade();
        this.project = project;
        editProject = new EditProject();
        editProject.attach(this);
        prepareGUI();
    }

    private void prepareGUI() {
        treePanel = new JScrollPane();
        jTree1 = new JTree();

        nameLbl = new JLabel("نام پروژه");
        name = new JTextField(project.getName());
        name.setEditable(false);

        managerLbl = new JLabel("مدیر پروژه");
        managerName = new JTextField((projectFacade.getProjectManager(project.getID()) == null)
                ? "تعیین نشده"
                : projectFacade.getProjectManager(project.getID()).getFirstName() + " " + projectFacade.getProjectManager(project.getID()).getLastName());
        managerName.setEditable(false);

        usersCountLbl = new JLabel("تعداد کاربران");
        usersCount = new JSpinner();
        usersCount.setValue(new Integer(project.getUsersCount()));
        usersCount.setEnabled(false);


        structPanel = new JPanel();
        jScrollPane2 = new JScrollPane();
        structTree = new JTree();


        techPanel = new JPanel();
        jScrollPane3 = new JScrollPane();
        DefaultListModel<Technology> listModel = new DefaultListModel<>();
        project.getTechnologies().forEach(listModel::addElement);
        techList = new JList(listModel);


        unitsPanel = new JPanel();
        unitList = new JScrollPane();
        jList1 = new JList<>();
        viewRes = new JButton("مشاهده منابع");
        viewReqs = new JButton("مشاهده نیازمندی‌ها");
        edit = new JButton("ویرایش");

        treePanel.setViewportView(jTree1);

        structPanel.setBorder(BorderFactory.createTitledBorder("ساختار پروژه"));

        jScrollPane2.setViewportView(structTree);

        GroupLayout structPanelLayout = new GroupLayout(structPanel);
        structPanel.setLayout(structPanelLayout);
        structPanelLayout.setHorizontalGroup(
                structPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(structPanelLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        structPanelLayout.setVerticalGroup(
                structPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(structPanelLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        techPanel.setBorder(BorderFactory.createTitledBorder("تکنولوژی‌ها"));

        jScrollPane3.setViewportView(techList);

        GroupLayout techPanelLayout = new GroupLayout(techPanel);
        techPanel.setLayout(techPanelLayout);
        techPanelLayout.setHorizontalGroup(
                techPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
        );
        techPanelLayout.setVerticalGroup(
                techPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
        );

        unitsPanel.setBorder(BorderFactory.createTitledBorder("واحدهای درگیر"));

        unitList.setViewportView(jList1);

        GroupLayout unitsPanelLayout = new GroupLayout(unitsPanel);
        unitsPanel.setLayout(unitsPanelLayout);
        unitsPanelLayout.setHorizontalGroup(
                unitsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(unitList, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        unitsPanelLayout.setVerticalGroup(
                unitsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(unitList, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        GroupLayout layout = new GroupLayout(mainFrame.getMainFrame().getContentPane());
        mainFrame.getMainFrame().getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(structPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(techPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(unitsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(managerName, GroupLayout.Alignment.LEADING)
                                                        .addComponent(name, GroupLayout.Alignment.LEADING)
                                                        .addComponent(usersCount, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                                                        .addComponent(edit))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(managerLbl)
                                                        .addComponent(usersCountLbl)
                                                        .addComponent(nameLbl)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(viewReqs, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(viewRes, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(structPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(techPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(unitsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(edit)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(nameLbl)
                                                        .addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(managerName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(managerLbl))
                                                .addGap(12, 12, 12)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                        .addComponent(usersCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(usersCountLbl))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(viewRes)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(viewReqs)
                                                .addGap(0, 9, Short.MAX_VALUE)))
                                .addContainerGap())
        );

        mainFrame.getMainFrame().pack();
    }

    @Override
    public void update() {
        // TODO
    }

    @Override
    public void setVisible(boolean visible) {
        mainFrame.setVisible(true);
    }

}
