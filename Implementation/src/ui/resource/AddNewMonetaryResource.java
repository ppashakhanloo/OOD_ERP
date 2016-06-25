package ui.resource;

import business_logic_facade.OperationFacade;
import resource.QuantityUnit;
import ui.MainDialog;
import ui.utilities.FormUtility;
import unit.Unit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

class AddNewMonetaryResource extends MainDialog {

    private OperationFacade operationFacade;

    private ArrayList<MonetaryResourceObserver> observers;

    AddNewMonetaryResource() {
        operationFacade = new OperationFacade();
        observers = new ArrayList<>();
        prepareGUI();
    }

    public void attach(MonetaryResourceObserver observer) {
        observers.add(observer);
    }

    private void notifyAllObservers() {
        for (MonetaryResourceObserver observer : observers) {
            observer.update();
        }
    }

    private void prepareGUI() {
        super.getMainDialog().setTitle("افزودن منبع مالی");
        JPanel form = new JPanel(new GridBagLayout());

        super.getMainDialog().getContentPane().setLayout(new BorderLayout());
        super.getMainDialog().getContentPane().add(form, BorderLayout.NORTH);

        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        JRadioButton cash = new JRadioButton("نقدی");
        JRadioButton nonCash = new JRadioButton("غیرنقدی");

        ButtonGroup buttonGroup = new ButtonGroup();
        cash.setSelected(true);
        buttonGroup.add(cash);
        buttonGroup.add(nonCash);

        formUtility.addLabel("نوع ", form);
        formUtility.addLastField(cash, form);
        formUtility.addLabel("", form);
        formUtility.addLastField(nonCash, form);

        JTextField accountNumber = new JTextField(20);
        JLabel accountLabel = formUtility.addLabel("شماره حساب ", form);
        formUtility.addLastField(accountNumber, form);

        cash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountNumber.setVisible(true);
                accountLabel.setVisible(true);
                getMainDialog().repaint();
                getMainDialog().revalidate();
            }
        });

        nonCash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountNumber.setVisible(false);
                accountLabel.setVisible(false);
                getMainDialog().repaint();
                getMainDialog().revalidate();
            }
        });

        JTextField location = new JTextField(20);
        formUtility.addLabel("محل ", form);
        formUtility.addLastField(location, form);

        JSpinner amount = new JSpinner();
        formUtility.addLabel("مقدار", form);
        formUtility.addLastField(amount, form);

        java.util.List<QuantityUnit> quantityUnits = operationFacade.getQuantityUnits();
        JComboBox<QuantityUnit> quantityUnitsCombo = new JComboBox<>();
        quantityUnits.forEach(quantityUnitsCombo::addItem);
        formUtility.addLabel("واحد پول ", form);
        formUtility.addLastField(quantityUnitsCombo, form);

        ArrayList<Unit> units = operationFacade.getUnits();
        JComboBox<Unit> unitsCombo = new JComboBox<>();

        units.forEach(unitsCombo::addItem);

        formUtility.addLabel("واحد ", form);
        formUtility.addLastField(unitsCombo, form);

        JButton submit = new JButton("افزودن");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operationFacade.addNewMonetaryResource(getSelectedButtonText(buttonGroup), location.getText(),
                        accountNumber.getText().equals("") ? "0" : accountNumber.getText(), (Integer) amount.getValue(), quantityUnitsCombo.getSelectedItem().toString(), ((Unit) unitsCombo.getSelectedItem()).getID());
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

    private String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    public static void main(String[] args) {
//        UserFacade userFacade = new UserFacade();
//        userFacade.login("478837", "888");
        AddNewMonetaryResource addNewMonetaryResource = new AddNewMonetaryResource();
        addNewMonetaryResource.setVisible(true);
    }
}
