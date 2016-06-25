package ui;

import business_logic_facade.OperationFacade;
import project.Project;
import ui.utilities.FormUtility;
import unit.Unit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by ppash on 6/24/2016.
 */
public class AddNewProject extends MainDialog {

    private OperationFacade operationFacade;

    ArrayList<ProjectObserver> observers;

    public AddNewProject() {
        operationFacade = new OperationFacade();
        observers = new ArrayList<>();
        prepareGUI();
    }

    public void attach(ProjectObserver observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (ProjectObserver observer : observers) {
            observer.update();
        }
    }

    private void prepareGUI() {
        super.getMainDialog().setTitle("افزودن پروژه جدید");
        JPanel form = new JPanel(new GridBagLayout());

        super.getMainDialog().getContentPane().setLayout(new BorderLayout());
        super.getMainDialog().getContentPane().add(form, BorderLayout.NORTH);

        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        JTextField firstName = new JTextField(20);
        formUtility.addLabel("نام ", form);
        formUtility.addLastField(firstName, form);


        // واحدهای درگیر
        DefaultListModel<Unit> listModel;
        JList<Unit> unitList;
        JScrollPane jScrollPane;
        //////////////////////////////////////////////////// TODO
        listModel = new DefaultListModel<>();
        operationFacade.getUnits().forEach(listModel::addElement);
        unitList = new JList<>(listModel);
        ////////////////////
        jScrollPane = new JScrollPane(unitList);
        getMainDialog().add(jScrollPane, BorderLayout.CENTER);

        formUtility.addLabel("واحد ", form);
        formUtility.addLastField(unitList, form);
        //////////////////////////////////////////////////////


        JButton submit = new JButton("افزودن");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operationFacade.addNewProject(firstName.getText(), lastName.getText(), expertise.getText(), password.getText(), ((Unit) unitsCombo.getSelectedItem()).getID());
                notifyAllObservers();
                setVisible(false);
            }
        });
        JButton cancel = new JButton("صرف‌نظر");
        formUtility.addLastField(submit, form);
        formUtility.addLastField(cancel, form);

        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        super.getMainDialog().pack();
    }

    public static void main(String[] args) {
//        UserFacade userFacade = new UserFacade();
//        userFacade.login("478837", "888");
        AddNewProject addNewProject = new AddNewProject();
        addNewProject.setVisible(true);
    }
}
