package ui;

import business_logic_facade.OperationFacade;
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
public class AddNewHumanResource extends MainDialog {

    private OperationFacade operationFacade;

    ArrayList<HumanResourceObserver> observers;

    public AddNewHumanResource() {
        operationFacade = new OperationFacade();
        observers = new ArrayList<>();
        prepareGUI();
    }

    public void attach(HumanResourceObserver observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (HumanResourceObserver observer : observers) {
            observer.update();
        }
    }

    private void prepareGUI() {
        super.getMainDialog().setTitle("افزودن منبع انسانی");
        JPanel form = new JPanel(new GridBagLayout());

        super.getMainDialog().getContentPane().setLayout(new BorderLayout());
        super.getMainDialog().getContentPane().add(form, BorderLayout.NORTH);

        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        JTextField firstName = new JTextField(20);
        formUtility.addLabel("نام ", form);
        formUtility.addLastField(firstName, form);

        JTextField lastName = new JTextField(20);
        formUtility.addLabel("نام خانوادگی ", form);
        formUtility.addLastField(lastName, form);

        JTextField expertise = new JTextField(20);
        formUtility.addLabel("تخصص‌ها ", form);
        formUtility.addLastField(expertise, form);


        JTextField password = new JTextField(20);
        formUtility.addLabel("رمز ", form);
        formUtility.addLastField(password, form);

        // JTextField ID

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
                operationFacade.addNewHumanResource(firstName.getText(), lastName.getText(), expertise.getText(), password.getText(), ((Unit)unitsCombo.getSelectedItem()).getID());
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
        AddNewHumanResource addNewHumanResource = new AddNewHumanResource();
        addNewHumanResource.setVisible(true);
    }
}
