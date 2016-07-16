package ui.unit;

import business_logic_facade.OperationFacade;
import ui.MainDialog;
import ui.utilities.FormUtility;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class AddNewUnit extends MainDialog {

    private ArrayList<UnitObserver> observers;
    private JTextField name;

    AddNewUnit() {

        observers = new ArrayList<>();
        prepareGUI();
    }

    public static void main(String[] args) {
//        UserFacade userFacade = new UserFacade();
//        userFacade.login("478837", "888");
        AddNewUnit addNewUnit = new AddNewUnit();
        addNewUnit.setVisible(true);
    }

    public void attach(UnitObserver observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        observers.forEach(UnitObserver::update);
    }

    private void prepareGUI() {
        super.getMainDialog().setTitle("افزودن واحد جدید");
        JPanel form = new JPanel(new GridBagLayout());

        super.getMainDialog().getContentPane().setLayout(new BorderLayout());
        super.getMainDialog().getContentPane().add(form, BorderLayout.NORTH);

        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        name = new JTextField(20);
        formUtility.addLabel("نام واحد ", form);
        formUtility.addLastField(name, form);

        JButton submit = new JButton("افزودن");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (OperationFacade.getInstance().addNewUnit(name.getText())) {
                    notifyAllObservers();
                    setVisible(false);
                } else
                    JOptionPane.showMessageDialog(null,
                            "ورودی را بررسی کنید.",
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

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        name.setText("");
    }
}
