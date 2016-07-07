package ui.user;

import access.AccessLevelType;
import business_logic_facade.OperationFacade;
import business_logic_facade.ProjectFacade;
import ui.MainDialog;
import ui.utilities.FormUtility;
import unit.Unit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class RegisterForm extends MainDialog {

    RegisterForm() {
        prepareGUI();
    }

    private void prepareGUI() {
        super.getMainDialog().setTitle("ثبت‌نام نیروی انسانی جدید");
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

        AccessLevelType[] accessLevelTypes = AccessLevelType.values();
        JComboBox<AccessLevelType> accessCombo = new JComboBox<>();
        for (AccessLevelType type : accessLevelTypes)
            accessCombo.addItem(type);

        formUtility.addLabel("سطح دسترسی ", form);
        formUtility.addLastField(accessCombo, form);

        ArrayList<Unit> units = OperationFacade.getInstance().getUnits();
        JComboBox<Unit> unitsCombo = new JComboBox<>();
        for (Unit unit : units)
            unitsCombo.addItem(unit);

        formUtility.addLabel("واحد ", form);
        formUtility.addLastField(unitsCombo, form);

        JButton submit = new JButton("ثبت‌نام");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OperationFacade.getInstance().addNewHumanResource(firstName.getText(), lastName.getText(), expertise.getText(), password.getText(), (AccessLevelType) accessCombo.getSelectedItem(), ((Unit) unitsCombo.getSelectedItem()).getID());
                JOptionPane.showMessageDialog(null, "ثبت‌نام شما با موفقیت انجام شد. منتظر تایید باشید.");
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
        RegisterForm registerForm = new RegisterForm();
        registerForm.setVisible(true);
    }
}
