package ui.resource;

import business_logic_facade.OperationFacade;
import business_logic_facade.UserFacade;
import database.MonetaryResourceDAO;
import resource.*;
import ui.MainDialog;
import ui.Visibility;
import ui.utilities.FormUtility;
import unit.Unit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SingleMonetaryResource extends MainDialog implements Visibility {

    private JTextField id;
    private JTextField monetaryType;
    private JSpinner quantityAmount;
    private JComboBox<QuantityUnit> quantityUnit;
    private JTextField location;
    private JTextField accountNumber;
    private JComboBox<Unit> unitsCombo;


    public SingleMonetaryResource(UserFacade userFacade, String rID) {
        prepareGUI(userFacade, rID);

    }

    private void prepareGUI(UserFacade userFacade, String rID) {

        System.out.println("RES:: " + ((MonetaryResource) MonetaryResourceDAO.getInstance().get(rID)).getMonetaryType().toString());

        super.getMainDialog().setTitle("منبع مالی");
        JPanel form = new JPanel(new GridBagLayout());

        super.getMainDialog().getContentPane().setLayout(new BorderLayout());
        super.getMainDialog().getContentPane().add(form, BorderLayout.NORTH);

        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        id = new JTextField(MonetaryResourceDAO.getInstance().get(rID).getID());
        formUtility.addLabel("شناسه ", form);
        formUtility.addLastField(id, form);
        id.setEditable(false);

        monetaryType = new JTextField(((MonetaryResource) MonetaryResourceDAO.getInstance().get(rID)).getMonetaryType().toString());
        monetaryType.setEditable(false);
        formUtility.addLabel("نوع منبع مالی ", form);
        formUtility.addLastField(monetaryType, form);

        quantityAmount = new JSpinner();
        quantityAmount.setValue(((MonetaryResource) MonetaryResourceDAO.getInstance().get(rID)).getQuantity().getAmount());
        formUtility.addLabel("مبلغ ", form);
        formUtility.addLastField(quantityAmount, form);

        ArrayList<QuantityUnit> quantityUnits = OperationFacade.getInstance().getQuantityUnits();
        quantityUnit = new JComboBox<>();
        for (QuantityUnit type : quantityUnits)
            quantityUnit.addItem(type);
        quantityUnit.setSelectedItem(((MonetaryResource) MonetaryResourceDAO.getInstance().get(rID)).getQuantity().getQuantityUnit());
        formUtility.addLabel("نوع واحد مالی ", form);
        formUtility.addLastField(quantityUnit, form);

        accountNumber = new JTextField("0");
        accountNumber.setVisible(false);
        if (((MonetaryResource) MonetaryResourceDAO.getInstance().get(rID)).getMonetaryType() == MonetaryType.CASH) {
            accountNumber = new JTextField(((MonetaryResource) MonetaryResourceDAO.getInstance().get(rID)).getAccountNumber());
            formUtility.addLabel("شماره حساب ", form);
            formUtility.addLastField(accountNumber, form);
            accountNumber.setVisible(true);
        }

        location = new JTextField(((MonetaryResource) MonetaryResourceDAO.getInstance().get(rID)).getLocation());
        formUtility.addLabel("محل ", form);
        formUtility.addLastField(location, form);


        // TODO
        ArrayList<Unit> units = OperationFacade.getInstance().getUnits();
        unitsCombo = new JComboBox<>();
        for (Unit unit : units)
            unitsCombo.addItem(unit);
// TODO        unitsCombo.setSelectedItem(OperationFacade.getInstance().getResourceUnit(rID));
        formUtility.addLabel("واحد ", form);
        formUtility.addLastField(unitsCombo, form);

        JButton submit = new JButton("ذخیره تغییرات");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OperationFacade.getInstance().updateMonetaryResource(
                        id.getText(), MonetaryType.valueOf(monetaryType.getText()),
                        (Integer) quantityAmount.getValue(), (QuantityUnit) quantityUnit.getSelectedItem(),
                        location.getText(), (accountNumber.getText().equals("") ? "0" : accountNumber.getText()),
                        (Unit) unitsCombo.getSelectedItem());
                setVisible(false);
            }
        });
        JButton cancel = new JButton("انصراف و بازگشت");
        formUtility.addLastField(submit, form);
        formUtility.addLastField(cancel, form);

        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        super.getMainDialog().pack();
    }

    @Override
    public void setVisible(boolean visible) {
        getMainDialog().setVisible(visible);
    }

    public static void main(String[] args) {
        UserFacade userFacade = new UserFacade();
        userFacade.login("100824", "888");
        SingleMonetaryResource singleMonetaryResource = new SingleMonetaryResource(userFacade, "165536");
        singleMonetaryResource.setVisible(true);
    }

}
