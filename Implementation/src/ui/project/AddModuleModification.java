package ui.project;

import business_logic_facade.ProjectFacade;
import business_logic_facade.UserFacade;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import project.Module;
import project.System;
import resource.HumanResource;
import resource.Resource;
import ui.MainDialog;
import ui.utilities.FormUtility;
import unit.Unit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

class AddModuleModification extends MainDialog {

    private UserFacade userFacade;
    private ArrayList<ProjectObserver> observers;


    AddModuleModification(UserFacade userFacade, String pid) {
        this.userFacade = userFacade;
        observers = new ArrayList<>();
        prepareGUI(pid);
    }

    public void attach(ProjectObserver observer) {
        observers.add(observer);
    }

    private void prepareGUI(String pid) {
        super.getMainDialog().setTitle("ثبت تغییر ماژول");
        JPanel form = new JPanel(new GridBagLayout());

        super.getMainDialog().getContentPane().setLayout(new BorderLayout());
        super.getMainDialog().getContentPane().add(form, BorderLayout.NORTH);

        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        //////////////////////////////////////////////////////
        ArrayList<System> systems = ProjectFacade.getInstance().getProject(pid).getSystems();
        ArrayList<Module> modules = new ArrayList<>();
        for (System system : systems)
            modules.addAll(system.getModules());
        JComboBox<Module> moduleJComboBox = new JComboBox<>();
        for (Module module : modules) {
            moduleJComboBox.addItem(module);
        }
        formUtility.addLabel("ماژول موردنظر ", form);
        formUtility.addLastField(moduleJComboBox, form);
        ///////////////////////////////////////////////////////
        JTextField modificationType = new JTextField(30);
        formUtility.addLabel("نوع تغییر ", form);
        formUtility.addLastField(modificationType, form);
        //////////////////////////////////////////////////////
        UtilDateModel dateModel = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setBounds(220, 350, 120, 30);
        JLabel dateLbl = formUtility.addLabel("آغاز زمان تغییر ", form);
        formUtility.addLastField(datePicker, form);
        ///////////////////////////////////////////////////////
        UtilDateModel dateModel2 = new UtilDateModel();
        Properties p2 = new Properties();
        p2.put("text.today", "Today");
        p2.put("text.month", "Month");
        p2.put("text.year", "Year");
        JDatePanelImpl datePanel2 = new JDatePanelImpl(dateModel2, p2);
        JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
        datePicker.setBounds(220, 350, 120, 30);
        JLabel dateLbl2 = formUtility.addLabel("پایان زمان تغییر ", form);
        formUtility.addLastField(datePicker2, form);
        //////////////////////////////////////////////////////
        DefaultListModel<Resource> devListModel = new DefaultListModel<>();
        for (Unit unit : ProjectFacade.getInstance().getProject(pid).getInvolvedUnits())
            for (Resource resource : unit.getResources())
                if (resource instanceof HumanResource)
                    devListModel.addElement(resource);

        JList<Resource> devList = new JList<>(devListModel);
        devList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        devList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        getMainDialog().add(new JScrollPane(devList), BorderLayout.CENTER);
        formUtility.addLabel("تغییردهنده(ها) ", form);
        formUtility.addLastField(devList, form);
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
            try {
                ArrayList<Resource> developers = new ArrayList<>();
                for (Resource dev : devList.getSelectedValuesList())
                    developers.add(dev);
                ArrayList<Resource> resources = new ArrayList<>();
                for (Resource res : resList.getSelectedValuesList())
                    resources.add(res);

                Calendar calStart = Calendar.getInstance();
                calStart.set(Calendar.YEAR, datePicker.getModel().getYear());
                calStart.set(Calendar.MONTH, datePicker.getModel().getMonth());
                calStart.set(Calendar.DAY_OF_MONTH, datePicker.getModel().getDay());

                Calendar calEnd = Calendar.getInstance();
                calEnd.set(Calendar.YEAR, datePicker2.getModel().getYear());
                calEnd.set(Calendar.MONTH, datePicker2.getModel().getMonth());
                calEnd.set(Calendar.DAY_OF_MONTH, datePicker2.getModel().getDay());

                ProjectFacade.getInstance().addNewModuleModification(
                        ((Module) moduleJComboBox.getSelectedItem()).getID(),
                        modificationType.getText(),
                        calStart.getTime(), calEnd.getTime(), resources, developers);
                new ViewSingleProject(userFacade, pid).setVisible(true);
                setVisible(false);
            } catch (Exception exp) {
                JOptionPane.showMessageDialog(null,
                        "خطایی در افزودن تغییرات ماژول رخ داده است. ورودی را بررسی کنید.",
                        "خطا",
                        JOptionPane.ERROR_MESSAGE);
            }
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
