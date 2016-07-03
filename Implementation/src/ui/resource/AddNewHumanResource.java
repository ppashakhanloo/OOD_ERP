package ui.resource;

import business_logic_facade.OperationFacade;
import ui.MainDialog;
import ui.utilities.FormUtility;
import unit.Unit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class AddNewHumanResource extends MainDialog {

    private ArrayList<HumanResourceObserver> observers;

    private JTextField firstName;
    private JTextField lastName;
    private JTextField expertise;
    private JTextField password;
    private JComboBox<Unit> unitsCombo;

    AddNewHumanResource() {
        observers = new ArrayList<>();
        prepareGUI();
    }

    public void attach(HumanResourceObserver observer) {
        observers.add(observer);
    }

    private void notifyAllObservers() {
        for (HumanResourceObserver observer : observers) {
            observer.update();
        }
    }

    @Override
    public void setVisible(boolean visible) {
        firstName.setText("");
        lastName.setText("");
        expertise.setText("");
        password.setText("");
        super.setVisible(visible);
    }

    private void prepareGUI() {
        super.getMainDialog().setTitle("افزودن منبع انسانی");
        JPanel form = new JPanel(new GridBagLayout());

        super.getMainDialog().getContentPane().setLayout(new BorderLayout());
        super.getMainDialog().getContentPane().add(form, BorderLayout.NORTH);

        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        firstName = new JTextField(20);
        formUtility.addLabel("نام ", form);
        formUtility.addLastField(firstName, form);

        lastName = new JTextField(20);
        formUtility.addLabel("نام خانوادگی ", form);
        formUtility.addLastField(lastName, form);

        expertise = new JTextField(20);
        formUtility.addLabel("تخصص‌ها ", form);
        formUtility.addLastField(expertise, form);

        password = new JTextField(20);
        formUtility.addLabel("رمز ", form);
        formUtility.addLastField(password, form);

        // JTextField ID

        ArrayList<Unit> units = OperationFacade.getInstance().getUnits();
        unitsCombo = new JComboBox<>();
        for (Unit unit : units)
            unitsCombo.addItem(unit);

        formUtility.addLabel("واحد ", form);
        formUtility.addLastField(unitsCombo, form);

        JButton submit = new JButton("افزودن");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                OperationFacade.getInstance().addNewHumanResource(firstName.getText(), lastName.getText(), expertise.getText(), password.getText(), ((Unit) unitsCombo.getSelectedItem()).getID());
                notifyAllObservers();
                setVisible(false);
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
        AddNewHumanResource addNewHumanResource = new AddNewHumanResource();
        addNewHumanResource.setVisible(true);
    }
}
