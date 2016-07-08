package ui.project;

import access.PermissionType;
import business_logic_facade.ProjectFacade;
import business_logic_facade.UserFacade;
import project.Module;
import project.System;
import project.Technology;
import ui.MainDialog;
import ui.Visibility;
import unit.Unit;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ViewSingleProject extends ProjectObserver implements Visibility {
    private MainDialog mainDialog;
    private EditProject editProject;
    private AddNewTechnology addNewTechnology;
    private UserFacade userFacade;

    private DefaultListModel<Technology> listModelTech;
    private JList<Technology> techList;

    private String pid;

    public ViewSingleProject(UserFacade userFacade, String pid) {
        mainDialog = new MainDialog();
        this.userFacade = userFacade;
        addNewTechnology = new AddNewTechnology(ProjectFacade.getInstance().getProject(pid));
        addNewTechnology.attach(this);
        this.pid = pid;
        prepareGUI();
    }

    private void prepareGUI() {

        mainDialog.getMainDialog().setTitle("مشاهده مشخصات پروژه");

        JLabel nameLbl = new JLabel("نام پروژه");
        JTextField name = new JTextField(ProjectFacade.getInstance().getProject(pid).getName());
        name.setEditable(false);

        JLabel managerLbl = new JLabel("مدیر پروژه");
        JTextField managerName = new JTextField((ProjectFacade.getInstance().getProjectManager(pid) == null)
                ? "تعیین نشده"
                : ProjectFacade.getInstance().getProjectManager(pid).getFirstName() + " " + ProjectFacade.getInstance().getProjectManager(pid).getLastName());
        managerName.setEditable(false);

        JLabel usersCountLbl = new JLabel("تعداد کاربران");
        JSpinner usersCount = new JSpinner();
        usersCount.setValue(ProjectFacade.getInstance().getProject(pid).getUsersCount());
        usersCount.setEnabled(false);


        JPanel structPanel = new JPanel();
        JScrollPane structTreeScroll = new JScrollPane();
        JTree structTree = new JTree();
        structTree.addMouseListener(new PopClickListener());


        JPanel techPanel = new JPanel();
        JScrollPane techListScroll = new JScrollPane();
        listModelTech = new DefaultListModel<>();
        ProjectFacade.getInstance().getProject(pid).getTechnologies().forEach(listModelTech::addElement);
        techList = new JList(listModelTech);


        JPanel unitsPanel = new JPanel();
        DefaultListModel<Unit> listModelUnit = new DefaultListModel<>();
        JScrollPane unitListScroll = new JScrollPane();
        ProjectFacade.getInstance().getProject(pid).getInvolvedUnits().forEach(listModelUnit::addElement);
        JList<Unit> unitList = new JList(listModelUnit);


        JButton viewRes = new JButton("مشاهده منابع");
        viewRes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                (new ViewProjectResources(userFacade, ProjectFacade.getInstance().getProject(pid))).setVisible(true);
            }
        });


        JButton viewReqs = new JButton("مشاهده نیازمندی‌ها");
        viewReqs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                (new ViewProjectRequirements(userFacade, ProjectFacade.getInstance().getProject(pid))).setVisible(true);
            }
        });

        JButton edit = new JButton("ویرایش");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainDialog.getMainDialog().setVisible(false);
                editProject = new EditProject(ProjectFacade.getInstance().getProject(pid), addNewTechnology, userFacade);
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

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(ProjectFacade.getInstance().getProject(pid));
        // create the system nodes
        ArrayList<System> systems = ProjectFacade.getInstance().getProject(pid).getSystems();
        DefaultMutableTreeNode[] systemNodes = new DefaultMutableTreeNode[systems.size()];
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

        GroupLayout layout = new GroupLayout(mainDialog.getMainDialog().getContentPane());
        mainDialog.getMainDialog().getContentPane().setLayout(layout);
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

        mainDialog.getMainDialog().pack();
        mainDialog.getMainDialog().setLocationRelativeTo(null);
    }

    @Override
    public void update() {

    }

    @Override
    public void setVisible(boolean visible) {
        mainDialog.setVisible(true);
    }

    class AddSystemModule extends JPopupMenu {
        private JMenuItem addSystem;
        private JMenuItem addModule;
        private JMenuItem addModuleModification;
        private JMenuItem viewModuleModifications;

        public AddSystemModule(UserFacade userFacade, String pid) {
            addSystem = new JMenuItem("افزودن سیستم جدید");
            if (userFacade.getCurrentUserPermissions().get(PermissionType.canAddRemSysMod))
                addSystem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AddNewSystem addNewSystem = new AddNewSystem(userFacade, pid);
                        addNewSystem.setVisible(true);
                        mainDialog.getMainDialog().setVisible(false);
                    }
                });
            else
                addSystem.setEnabled(false);

            addModule = new JMenuItem("افزودن ماژول جدید");
            if (userFacade.getCurrentUserPermissions().get(PermissionType.canAddRemSysMod))
                addModule.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AddNewModule addNewModule = new AddNewModule(userFacade, pid);
                        addNewModule.setVisible(true);
                        mainDialog.getMainDialog().setVisible(false);
                    }
                });
            else
                addModule.setEnabled(false);

            addModuleModification = new JMenuItem("ثبت تغییر ماژول");
            addModuleModification.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AddModuleModification addModuleModification = new AddModuleModification(userFacade, pid);
                    addModuleModification.setVisible(true);
                    mainDialog.getMainDialog().setVisible(false);
                }
            });
            viewModuleModifications = new JMenuItem("مشاهده تغییرات ماژول‌ها");
            viewModuleModifications.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ViewModuleModifications viewModuleModifications = new ViewModuleModifications(userFacade, pid);
                    viewModuleModifications.setVisible(true);
                    mainDialog.getMainDialog().setVisible(false);
                }
            });

            add(addSystem);
            add(addModule);
            add(addModuleModification);
            add(viewModuleModifications);
        }
    }

    private class PopClickListener extends MouseAdapter {
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

