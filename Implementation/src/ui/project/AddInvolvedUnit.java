package ui.project;

import business_logic_facade.OperationFacade;
import business_logic_facade.ProjectFacade;
import project.Project;
import ui.MainDialog;
import ui.utilities.FormUtility;
import unit.Unit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class AddInvolvedUnit extends MainDialog {

    private OperationFacade operationFacade;
    private ProjectFacade projectFacade;

    private ArrayList<ProjectObserver> observers;

    AddInvolvedUnit(Project project) {
        operationFacade = new OperationFacade();
        projectFacade = new ProjectFacade();
        observers = new ArrayList<>();
        prepareGUI(project);
    }

    public void attach(ProjectObserver observer) {
        observers.add(observer);
    }

    private void notifyAllObservers() {
        observers.forEach(ProjectObserver::update);
    }

    private void prepareGUI(Project project) {
        super.getMainDialog().setTitle("افزودن واحد درگیر");
        JPanel form = new JPanel(new GridBagLayout());

        super.getMainDialog().getContentPane().setLayout(new BorderLayout());
        super.getMainDialog().getContentPane().add(form, BorderLayout.NORTH);

        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        ArrayList<Unit> units = operationFacade.getUnits();
        JComboBox<Unit> unitsCombo = new JComboBox<>();
        for (Unit unit : units)
            unitsCombo.addItem(unit);

        formUtility.addLabel("واحد ", form);
        formUtility.addLastField(unitsCombo, form);

        JButton submit = new JButton("افزودن");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectFacade.addInvolvedUnit(((Unit) unitsCombo.getSelectedItem()).getID(), project.getID());
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
//        AddNewTechnology addNewTechnology = new AddNewTechnology();
//        addNewTechnology.setVisible(true);
    }
}
