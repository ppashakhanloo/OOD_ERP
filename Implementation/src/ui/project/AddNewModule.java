package ui.project;

import business_logic_facade.ProjectFacade;
import business_logic_facade.UserFacade;
import project.System;
import resource.HumanResource;
import resource.Resource;
import ui.MainDialog;
import ui.utilities.FormUtility;
import utility.Identifiable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

class AddNewModule extends MainDialog {

    private UserFacade userFacade;


    AddNewModule(UserFacade userFacade, String pid) {
        this.userFacade = userFacade;
        prepareGUI(pid);
    }

    public static void main(String[] args) {
//        UserFacade userFacade = new UserFacade();
//        userFacade.login("478837", "888");
//        AddNewModule addNewProject = new AddNewModule();
//        addNewProject.setVisible(true);
    }

    private void prepareGUI(String pid) {
        super.getMainDialog().setTitle("افزودن ماژول جدید");
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
        systems.forEach(systemsCombo::addItem);
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

        JButton submit = new JButton("افزودن");
        submit.addActionListener(e -> {
            ArrayList<String> developers = devList.getSelectedValuesList().stream().map(Identifiable::getID).collect(Collectors.toCollection(ArrayList::new));
            ProjectFacade.getInstance().addNewModule(name.getText(), ((System) systemsCombo.getSelectedItem()).getID(), pid, developers);
            new ViewSingleProject(userFacade, pid).setVisible(true);
        });

        JButton cancel = new JButton("انصراف");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new ViewSingleProject(userFacade, pid).setVisible(true);
            }
        });
        formUtility.addLastField(submit, form);
        formUtility.addLastField(cancel, form);

        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        super.getMainDialog().pack();
        super.getMainDialog().setLocationRelativeTo(null);
    }
}
