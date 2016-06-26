package ui.project;

import business_logic_facade.OperationFacade;
import business_logic_facade.ProjectFacade;
import business_logic_facade.UserFacade;
import project.Module;
import project.Project;
import project.System;
import project.Technology;
import resource.HumanResource;
import resource.Resource;
import ui.MainDialog;
import unit.Unit;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditProject extends MainDialog {

    private ArrayList<ProjectObserver> observers;
    private ProjectFacade projectFacade;
    private OperationFacade operationFacade;
    private UserFacade userFacade;

    public EditProject(Project project, AddNewTechnology addNewTechnology, UserFacade userFacade) {
        observers = new ArrayList<>();
        projectFacade = new ProjectFacade();
        operationFacade = new OperationFacade();
        this.userFacade = userFacade;
        prepareGUI(project, addNewTechnology);
    }

    private void prepareGUI(Project project, AddNewTechnology addNewTechnology) {

        getMainDialog().setTitle("ویرایش اطلاعات پروژه");
        JLabel nameLbl = new JLabel("نام پروژه");
        JTextField name = new JTextField(project.getName());

        JLabel managerLbl = new JLabel("مدیر پروژه");
        ArrayList<Resource> humanResources = operationFacade.getHumanResources();
        JComboBox<HumanResource> manCombo = new JComboBox<>();
        for (Resource resource : humanResources) manCombo.addItem((HumanResource) resource);

        JLabel usersCountLbl = new JLabel("تعداد کاربران");
        JSpinner usersCount = new JSpinner();
        usersCount.setValue(project.getUsersCount());


        JButton addTech = new JButton("افزودن تکنولوژی جدید");
        addTech.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewTechnology.setVisible(true);
            }
        });

//        JButton addUnit = new JButton("افزودن واحد جدید درگیر");
//        addUnit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                addUnit.setVisible(true);
//            }
//        });


        JButton edit = new JButton("ذخیره تغییرات");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.lang.System.out.println("SAVE CHANGES: " + projectFacade);
                java.lang.System.out.println("NAME: " + name.getText());
                java.lang.System.out.println("MANAGER ID: " + ((HumanResource) manCombo.getSelectedItem()).getID());
                java.lang.System.out.println("USER COUNT: " + (int) usersCount.getValue());
                java.lang.System.out.println("PROJECT ID: " + project.getID());

                projectFacade.updateProject(name.getText(),
                        ((HumanResource) manCombo.getSelectedItem()).getID(),
                        (int) usersCount.getValue(),
                        project.getID());
                java.lang.System.out.println("END");
                ViewSingleProject viewSingleProject = new ViewSingleProject(userFacade, project.getID());
                viewSingleProject.setVisible(true);
                getMainDialog().setVisible(false);
            }
        });

        GroupLayout layout = new GroupLayout(getMainDialog().getContentPane());
        getMainDialog().getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                .addGap(12, 12, 12)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(21, 21, 21)
                                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                                                .addComponent(manCombo, GroupLayout.Alignment.LEADING)
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
                                                                        .addComponent(addTech, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(layout.createSequentialGroup()
                                                                                .addGap(18, 18, 18)
//                                                        .addComponent(addUnit, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
                                                                )
                                                )

                                )));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(edit)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                                        .addComponent(nameLbl)
                                                                                        .addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                                        .addComponent(manCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(managerLbl))
                                                                                .addGap(12, 12, 12)
                                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                                                        .addComponent(usersCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(usersCountLbl))
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(addTech)
                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//                                                                        .addComponent(addUnit)
                                                                                .addGap(0, 9, Short.MAX_VALUE)))
                                                )))));
        getMainDialog().pack();
    }

    private void notifyAllObservers() {
        observers.forEach(ProjectObserver::update);
    }

    public void attach(ProjectObserver observer) {
        observers.add(observer);
    }

}
