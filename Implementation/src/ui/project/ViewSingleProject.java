package ui.project;

import business_logic_facade.OperationFacade;
import business_logic_facade.ProjectFacade;
import business_logic_facade.UserFacade;
import project.Module;
import project.System;
import project.Technology;
import ui.MainFrame;
import ui.Visibility;
import unit.Unit;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.*;
import java.util.ArrayList;

public class ViewSingleProject extends ProjectObserver implements Visibility {
    private MainFrame mainFrame;
    //    private OperationFacade operationFacade;
    private ProjectFacade projectFacade;
    private EditProject editProject;
    private AddNewTechnology addNewTechnology;
    private UserFacade userFacade;

    private DefaultListModel<Technology> listModelTech;
    private JList<Technology> techList;

    private String pid;

    public ViewSingleProject(UserFacade currentUser, String pid) {
        mainFrame = new MainFrame(currentUser);
//        operationFacade = new OperationFacade();
        projectFacade = new ProjectFacade();
        this.userFacade = currentUser;
//        this.project = project;
        addNewTechnology = new AddNewTechnology(userFacade, projectFacade.getProject(pid));
//        editProject = new EditProject(project, addNewTechnology, currentUser);
//        editProject.attach(this);
        addNewTechnology.attach(this);
        this.pid = pid;

        prepareGUI();
    }

    private void prepareGUI() {

        JLabel nameLbl = new JLabel("نام پروژه");
        JTextField name = new JTextField(projectFacade.getProject(pid).getName());
        name.setEditable(false);

        JLabel managerLbl = new JLabel("مدیر پروژه");
        JTextField managerName = new JTextField((projectFacade.getProjectManager(pid) == null)
                ? "تعیین نشده"
                : projectFacade.getProjectManager(pid).getFirstName() + " " + projectFacade.getProjectManager(pid).getLastName());
        managerName.setEditable(false);

        JLabel usersCountLbl = new JLabel("تعداد کاربران");
        JSpinner usersCount = new JSpinner();
        usersCount.setValue(projectFacade.getProject(pid).getUsersCount());
        usersCount.setEnabled(false);


        JPanel structPanel = new JPanel();
        JScrollPane structTreeScroll = new JScrollPane();
        JTree structTree = new JTree();
        structTree.addMouseListener(new PopClickListener());


        JPanel techPanel = new JPanel();
        JScrollPane techListScroll = new JScrollPane();
        listModelTech = new DefaultListModel<>();
        projectFacade.getProject(pid).getTechnologies().forEach(listModelTech::addElement);
        techList = new JList(listModelTech);


        JPanel unitsPanel = new JPanel();
        DefaultListModel<Unit> listModelUnit = new DefaultListModel<>();
        JScrollPane unitListScroll = new JScrollPane();
        projectFacade.getProject(pid).getInvolvedUnits().forEach(listModelUnit::addElement);
        JList<Unit> unitList = new JList(listModelUnit);


        JButton viewRes = new JButton("مشاهده منابع");
        viewRes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                (new ViewProjectResources(userFacade, projectFacade.getProject(pid))).setVisible(true);
            }
        });


        JButton viewReqs = new JButton("مشاهده نیازمندی‌ها");
        viewReqs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                (new ViewProjectRequirements(userFacade, projectFacade.getProject(pid))).setVisible(true);
            }
        });

        JButton edit = new JButton("ویرایش");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getMainFrame().setVisible(false);
                editProject = new EditProject(projectFacade.getProject(pid), addNewTechnology, userFacade);
                editProject.setVisible(true);
            }
        });


        structPanel.setBorder(BorderFactory.createTitledBorder("ساختار پروژه"));
        structTreeScroll.setViewportView(structTree);

        GroupLayout structPanelLayout = new GroupLayout(structPanel);
        structPanel.setLayout(structPanelLayout);
        structPanelLayout.setHorizontalGroup(
                structPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(structPanelLayout.createSequentialGroup()
                                .addComponent(structTreeScroll, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        structPanelLayout.setVerticalGroup(
                structPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(structPanelLayout.createSequentialGroup()
                                .addComponent(structTreeScroll, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(projectFacade.getProject(pid));
        // create the system nodes
        ArrayList<System> systems = projectFacade.getProject(pid).getSystems();
        DefaultMutableTreeNode[] systemNodes = new DefaultMutableTreeNode[systems.size()];
        if (systems != null) {
            for (int i = 0; i < systems.size(); i++) {
                systemNodes[i] = new DefaultMutableTreeNode(systems.get(i));
                ArrayList<Module> modules = systems.get(i).getModules();
                DefaultMutableTreeNode[] moduleNodes = new DefaultMutableTreeNode[modules.size()];
                for (int j = 0; j < modules.size(); j++) {
                    moduleNodes[j] = new DefaultMutableTreeNode(modules.get(j));
                    systemNodes[i].add(moduleNodes[j]);
                }
                root.add(systemNodes[i]);
            }
            DefaultTreeModel defaultTreeModel = new DefaultTreeModel(root);
            structTree.setModel(defaultTreeModel);
        }


        techPanel.setBorder(BorderFactory.createTitledBorder("تکنولوژی‌ها"));

        techListScroll.setViewportView(techList);

        GroupLayout techPanelLayout = new GroupLayout(techPanel);
        techPanel.setLayout(techPanelLayout);
        techPanelLayout.setHorizontalGroup(
                techPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(techListScroll, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
        );
        techPanelLayout.setVerticalGroup(
                techPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(techListScroll, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
        );

        unitsPanel.setBorder(BorderFactory.createTitledBorder("واحدهای درگیر"));

        unitListScroll.setViewportView(unitList);

        GroupLayout unitsPanelLayout = new GroupLayout(unitsPanel);
        unitsPanel.setLayout(unitsPanelLayout);
        unitsPanelLayout.setHorizontalGroup(
                unitsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(unitListScroll, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        unitsPanelLayout.setVerticalGroup(
                unitsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(unitListScroll, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
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

    }

    @Override
    public void setVisible(boolean visible) {
        mainFrame.setVisible(true);
    }

    class AddSystemModule extends JPopupMenu {
        private JMenuItem addSystem;
        private JMenuItem addModule;

        public AddSystemModule(UserFacade userFacade, String pid) {
            addSystem = new JMenuItem("افزودن سیستم جدید");
            addSystem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AddNewSystem addNewSystem = new AddNewSystem(userFacade, pid);
                    java.lang.System.out.println("I WANT TO ADD SYSTEM TO PROJECT " + pid);
                    addNewSystem.setVisible(true);
                    mainFrame.getMainFrame().setVisible(false);
                }
            });
            addModule = new JMenuItem("افزودن ماژول جدید");
            addModule.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AddNewModule addNewModule = new AddNewModule(userFacade, pid);
                    addNewModule.setVisible(true);
                    java.lang.System.out.println("I WANT TO ADD MODULE TO PROJECT " + pid);
                    mainFrame.getMainFrame().setVisible(false);
                }
            });

            add(addSystem);
            add(addModule);
        }
    }

    class PopClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (e.isPopupTrigger())
                doPop(e);
        }

        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger())
                doPop(e);
        }

        private void doPop(MouseEvent e) {
            AddSystemModule menu = new AddSystemModule(userFacade, pid);
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}

