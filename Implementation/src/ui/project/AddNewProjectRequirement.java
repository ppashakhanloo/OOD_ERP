package ui.project;

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
import java.util.Enumeration;

public class AddNewProjectRequirement extends MainDialog {
    private OperationFacade operationFacade;

    AddNewProjectRequirement() {
        operationFacade = new OperationFacade();
        prepareGUI();
    }

    private void prepareGUI() {
        super.getMainDialog().setTitle("افزودن نیازمندی جدید");
        JPanel form = new JPanel(new GridBagLayout());

        super.getMainDialog().getContentPane().setLayout(new BorderLayout());
        super.getMainDialog().getContentPane().add(form, BorderLayout.NORTH);

        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        JRadioButton monetaryCash = new JRadioButton("مالی نقدی");
        JRadioButton monetaryNonCash = new JRadioButton("مالی غیرنقدی");
        JRadioButton human = new JRadioButton("انسانی");
        JRadioButton physical = new JRadioButton("فیزیکی");
        JRadioButton information = new JRadioButton("اطلاعاتی");

        ButtonGroup buttonGroup = new ButtonGroup();
        monetaryCash.setSelected(true);
        buttonGroup.add(monetaryCash);
        buttonGroup.add(monetaryNonCash);
        buttonGroup.add(human);
        buttonGroup.add(physical);
        buttonGroup.add(information);

        formUtility.addLabel("نوع منبع موردنیاز ", form);
        formUtility.addLastField(monetaryCash, form);
        formUtility.addLabel("", form);
        formUtility.addLastField(monetaryNonCash, form);
        formUtility.addLabel("", form);
        formUtility.addLastField(human, form);
        formUtility.addLabel("", form);
        formUtility.addLastField(physical, form);
        formUtility.addLabel("", form);
        formUtility.addLastField(information, form);


        ///////////////////////// money - cash, non cash
        JTextField amount = new JTextField(20);
        JLabel amountLabel = formUtility.addLabel("مبلغ ", form);
        formUtility.addLastField(amount, form);
        amount.setVisible(true);
        amountLabel.setVisible(true);
        //////////////////////////
        java.util.List<QuantityUnit> quantityUnits = operationFacade.getQuantityUnits();
        JComboBox<QuantityUnit> quantityUnitsCombo = new JComboBox<>();
        quantityUnits.forEach(quantityUnitsCombo::addItem);
        JLabel unitLbl = formUtility.addLabel("واحد پول ", form);
        formUtility.addLastField(quantityUnitsCombo, form);

        quantityUnitsCombo.setVisible(true);
        unitLbl.setVisible(true);
        ////////////////////////////

        //////////// money - non cash
        JTextField location = new JTextField(20);
        JLabel locationLabel = formUtility.addLabel("محل ", form);
        formUtility.addLastField(location, form);
        location.setVisible(false);
        locationLabel.setVisible(false);
        /////////////////////////////

        ///////////////// human
        JTextField exp = new JTextField(20);
        JLabel expLabel = formUtility.addLabel("محل ", form);
        formUtility.addLastField(exp, form);
        exp.setVisible(false);
        expLabel.setVisible(false);
        ///////////////////////

        ///////////////// physical
        JTextField name = new JTextField(20); // also for information
        JLabel nameLabel = formUtility.addLabel("نام ", form);
        formUtility.addLastField(name, form);
        name.setVisible(false);
        nameLabel.setVisible(false);

        JTextField model = new JTextField(20);
        JLabel modelLabel = formUtility.addLabel("مدل ", form);
        formUtility.addLastField(model, form);
        model.setVisible(false);
        modelLabel.setVisible(false);
        ///////////////////////

        monetaryCash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                amount.setVisible(true);
                amountLabel.setVisible(true);
                unitLbl.setVisible(true);
                quantityUnitsCombo.setVisible(true);
                getMainDialog().repaint();
                getMainDialog().revalidate();
            }
        });

        monetaryNonCash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                amount.setVisible(true);
                amountLabel.setVisible(true);
                unitLbl.setVisible(true);
                quantityUnitsCombo.setVisible(true);
                location.setVisible(true);
                locationLabel.setVisible(true);
                getMainDialog().repaint();
                getMainDialog().revalidate();
            }
        });

        physical.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name.setVisible(true);
                nameLabel.setVisible(true);
                model.setVisible(true);
                modelLabel.setVisible(true);
                getMainDialog().repaint();
                getMainDialog().revalidate();
            }
        });

        human.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exp.setVisible(true);
                expLabel.setVisible(true);
                getMainDialog().repaint();
                getMainDialog().revalidate();
            }
        });

        information.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name.setVisible(true);
                nameLabel.setVisible(true);
                getMainDialog().repaint();
                getMainDialog().revalidate();
            }
        });


        JButton submit = new JButton("افزودن");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                if (monetaryCash.isSelected())
                    operationFacade.addNewMonetaryResource(getSelectedButtonText(buttonGroup), location.getText(),
                            accountNumber.getText().equals("") ? "0" : accountNumber.getText(), (Integer) amount.getValue(), quantityUnitsCombo.getSelectedItem().toString(), ((Unit) unitsCombo.getSelectedItem()).getID());
                else if (monetaryNonCash.isSelected())
                else if (human.isSelected())
                else if (information.isSelected())
                else if (physical.isSelected())
//                notifyAllObservers();
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
}

//    public ProjectRequirement() {
//super();
//        setEssential(false);
//        setID(generateNDigitID(ID_LENGTH));
//        }
