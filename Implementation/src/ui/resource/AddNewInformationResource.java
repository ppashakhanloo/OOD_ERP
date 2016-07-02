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

class AddNewInformationResource extends MainDialog {

    private ArrayList<InformationResourceObserver> observers;

    private JTextField name;
    private JTextArea description;

    AddNewInformationResource() {
        observers = new ArrayList<>();
        prepareGUI();
    }

    @Override
    public void setVisible(boolean visible) {
        name.setText("");
        description.setText("");
        super.setVisible(visible);
    }

    public void attach(InformationResourceObserver observer) {
        observers.add(observer);
    }

    private void notifyAllObservers() {
        for (InformationResourceObserver observer : observers) {
            observer.update();
        }
    }

    private void prepareGUI() {
        super.getMainDialog().setTitle("افزودن منبع اطلاعاتی");
        JPanel form = new JPanel(new GridBagLayout());

        super.getMainDialog().getContentPane().setLayout(new BorderLayout());
        super.getMainDialog().getContentPane().add(form, BorderLayout.NORTH);

        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        name = new JTextField(20);
        formUtility.addLabel("نام ", form);
        formUtility.addLastField(name, form);

        description = new JTextArea();
        formUtility.addLabel("توضیح ", form);
        formUtility.addLastField(new JScrollPane(description), form);

        // JTextField ID

        ArrayList<Unit> units = OperationFacade.getInstance().getUnits();
        JComboBox<Unit> unitsCombo = new JComboBox<>();
        for (Unit unit : units)
            unitsCombo.addItem(unit);

        formUtility.addLabel("واحد ", form);
        formUtility.addLastField(unitsCombo, form);

        JButton submit = new JButton("افزودن");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OperationFacade.getInstance().addNewInformationResource(name.getText(), description.getText(), ((Unit) unitsCombo.getSelectedItem()).getID());
                notifyAllObservers();
                setVisible(false);
            }
        });
        JButton cancel = new JButton("صرف‌نظر");
        formUtility.addLastField(submit, form);
        formUtility.addLastField(cancel, form);

        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        super.getMainDialog().pack();
        super.getMainDialog().setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
//        UserFacade userFacade = new UserFacade();
//        userFacade.login("478837", "888");
        AddNewInformationResource addNewInformationResource = new AddNewInformationResource();
        addNewInformationResource.setVisible(true);
    }
}
