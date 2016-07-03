package ui.project;

import business_logic_facade.ProjectFacade;
import business_logic_facade.UserFacade;
import project.System;
import resource.HumanResource;
import resource.Resource;
import ui.MainDialog;
import ui.utilities.FormUtility;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class AddNewModule extends MainDialog {

    private UserFacade userFacade;
    private ArrayList<ProjectObserver> observers;


    AddNewModule(UserFacade userFacade, String pid) {
        this.userFacade = userFacade;
        observers = new ArrayList<>();
        prepareGUI(pid);
    }

    public void attach(ProjectObserver observer) {
        observers.add(observer);
    }

    private void prepareGUI(String pid) {
        super.getMainDialog().setTitle("افزودن پروژه جدید");
        JPanel form = new JPanel(new GridBagLayout());

        super.getMainDialog().getContentPane().setLayout(new BorderLayout());
        super.getMainDialog().getContentPane().add(form, BorderLayout.NORTH);

        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        JTextField name = new JTextField(20);
        formUtility.addLabel("نام ماژول ", form);
        formUtility.addLastField(name, form);

        ArrayList<System> systems = ProjectFacade.getInstance().getProject(pid).getSystems();
        JComboBox<System> systemsCombo = new JComboBox<>();
        for (System system : systems) systemsCombo.addItem(system);
        formUtility.addLabel("سیستم موردنظر ", form);
        formUtility.addLastField(systemsCombo, form);

        //////////////////////////////////////////////////////
        DefaultListModel<Resource> devListModel = new DefaultListModel<>();
        for (Resource resource : ProjectFacade.getInstance().getProjectResources(pid))
            if (resource instanceof HumanResource)
                devListModel.addElement(resource);
        JList<Resource> devList = new JList<>(devListModel);
        devList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        devList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        getMainDialog().add(new JScrollPane(devList), BorderLayout.CENTER);
        formUtility.addLabel("ایجادکننده(ها) ", form);
        formUtility.addLastField(devList, form);
        //////////////////////////////////////////////////////

        //////////////////////////////////////////////////////
        DefaultListModel<Resource> resListModel = new DefaultListModel<>();
        for (Resource resource : ProjectFacade.getInstance().getProjectResources(pid))
            resListModel.addElement(resource);
        JList<Resource> resList = new JList<>(resListModel);
        resList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        resList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        getMainDialog().add(new JScrollPane(resList), BorderLayout.CENTER);
        formUtility.addLabel("منابع  ", form);
        formUtility.addLastField(resList, form);
        //////////////////////////////////////////////////////

        JButton submit = new JButton("افزودن");
        submit.addActionListener(e -> {
            ArrayList<String> developers = new ArrayList<>();
            for (Resource dev : devList.getSelectedValuesList())
                developers.add(dev.getID());
            ArrayList<String> resources = new ArrayList<>();
            for (Resource res : resList.getSelectedValuesList())
                resources.add(res.getID());

            ProjectFacade.getInstance().addNewModule(name.getText(), ((System) systemsCombo.getSelectedItem()).getID(), pid, resources, developers);

            new ViewSingleProject(userFacade, pid).setVisible(true);
            setVisible(false);
        });

        JButton cancel = new JButton("انصراف");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        formUtility.addLastField(submit, form);
        formUtility.addLastField(cancel, form);

        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        super.getMainDialog().pack();
        super.getMainDialog().setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
//        UserFacade userFacade = new UserFacade();
//        userFacade.login("478837", "888");
//        AddNewModule addNewProject = new AddNewModule();
//        addNewProject.setVisible(true);
    }
}
