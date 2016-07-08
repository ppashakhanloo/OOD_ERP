package ui.unit;


import business_logic_facade.OperationFacade;
import resource.*;
import ui.MainDialog;
import ui.utilities.FormUtility;
import unit.Requirement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNewUnitRequirement extends MainDialog {

    ViewUnitRequirements unitRequirementsViewer;

    AddNewUnitRequirement(String uid, ViewUnitRequirements vurs) {
        unitRequirementsViewer = vurs;
        prepareGUI(uid);
    }

    private void prepareGUI(String uid) {
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
        JSpinner amount = new JSpinner();
        JLabel amountLabel = formUtility.addLabel("مبلغ ", form);
        formUtility.addLastField(amount, form);
        amount.setVisible(true);
        amountLabel.setVisible(true);
        //////////////////////////
        java.util.List<QuantityUnit> quantityUnits = OperationFacade.getInstance().getQuantityUnits();
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
        JLabel expLabel = formUtility.addLabel("تخصص‌ها ", form);
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

        JTextArea description = new JTextArea(5, 20);
        JLabel descLabel = formUtility.addLabel("توضیحات ", form);
        formUtility.addLastField(description, form);

        monetaryCash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                amount.setVisible(true);
                amountLabel.setVisible(true);
                unitLbl.setVisible(true);
                quantityUnitsCombo.setVisible(true);
                location.setVisible(false);
                locationLabel.setVisible(false);
                name.setVisible(false);
                nameLabel.setVisible(false);
                model.setVisible(false);
                modelLabel.setVisible(false);
                exp.setVisible(false);
                expLabel.setVisible(false);
                getMainDialog().pack();
                getMainDialog().setLocationRelativeTo(null);
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
                name.setVisible(false);
                nameLabel.setVisible(false);
                model.setVisible(false);
                modelLabel.setVisible(false);
                exp.setVisible(false);
                expLabel.setVisible(false);
                getMainDialog().pack();
                getMainDialog().setLocationRelativeTo(null);
                getMainDialog().repaint();
                getMainDialog().revalidate();

            }
        });

        physical.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                amount.setVisible(false);
                amountLabel.setVisible(false);
                unitLbl.setVisible(false);
                quantityUnitsCombo.setVisible(false);
                location.setVisible(false);
                locationLabel.setVisible(false);
                name.setVisible(true);
                nameLabel.setVisible(true);
                model.setVisible(true);
                modelLabel.setVisible(true);
                exp.setVisible(false);
                expLabel.setVisible(false);
                getMainDialog().pack();
                getMainDialog().setLocationRelativeTo(null);
                getMainDialog().repaint();
                getMainDialog().revalidate();
            }
        });

        human.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                amount.setVisible(false);
                amountLabel.setVisible(false);
                unitLbl.setVisible(false);
                quantityUnitsCombo.setVisible(false);
                location.setVisible(false);
                locationLabel.setVisible(false);
                name.setVisible(false);
                nameLabel.setVisible(false);
                model.setVisible(false);
                modelLabel.setVisible(false);
                exp.setVisible(true);
                expLabel.setVisible(true);
                getMainDialog().pack();
                getMainDialog().setLocationRelativeTo(null);
                getMainDialog().repaint();
                getMainDialog().revalidate();
            }
        });

        information.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                amount.setVisible(false);
                amountLabel.setVisible(false);
                unitLbl.setVisible(false);
                quantityUnitsCombo.setVisible(false);
                location.setVisible(false);
                locationLabel.setVisible(false);
                name.setVisible(true);
                nameLabel.setVisible(true);
                model.setVisible(false);
                modelLabel.setVisible(false);
                exp.setVisible(false);
                expLabel.setVisible(false);
                getMainDialog().pack();
                getMainDialog().setLocationRelativeTo(null);
                getMainDialog().repaint();
                getMainDialog().revalidate();
            }
        });

//boolean isEssential, String criticalProvideDate, String lengthOfPossession, String pid, Resource resource
        JButton submit = new JButton("افزودن");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (monetaryCash.isSelected()) {
                    Resource resource = new MonetaryResource(MonetaryType.CASH, location.getText(), -1, new Quantity((Integer) amount.getValue(), QuantityUnit.valueOf(quantityUnitsCombo.getSelectedItem().toString())));
                    resource.setAvailable(false);
                    ResourceCatalogue.getInstance().add(resource, uid, null);
                    Requirement requirement = new Requirement(description.getText(), null);
                    OperationFacade.getInstance().addUnitRequirement(uid, requirement, resource);
                } else if (monetaryNonCash.isSelected()) {
                    Resource resource = new MonetaryResource(MonetaryType.NON_CASH, location.getText(), -1, new Quantity((Integer) amount.getValue(), QuantityUnit.valueOf(quantityUnitsCombo.getSelectedItem().toString())));
                    resource.setAvailable(false);
                    ResourceCatalogue.getInstance().add(resource, uid, null);
                    Requirement requirement = new Requirement(description.getText(), null);
                    OperationFacade.getInstance().addUnitRequirement(uid, requirement, resource);
                } else if (human.isSelected()) {
                    Resource resource = new HumanResource();
                    ((HumanResource) resource).setExpertise(exp.getText());
                    resource.setAvailable(false);
                    ResourceCatalogue.getInstance().add(resource, uid, null);
                    Requirement requirement = new Requirement(description.getText(), null);
                    OperationFacade.getInstance().addUnitRequirement(uid, requirement, resource);
                } else if (information.isSelected()) {
                    Resource resource = new InformationResource(name.getText(), "");
                    resource.setAvailable(false);
                    ResourceCatalogue.getInstance().add(resource, uid, null);
                    Requirement requirement = new Requirement(description.getText(), null);
                    OperationFacade.getInstance().addUnitRequirement(uid, requirement, resource);
                } else if (physical.isSelected()) {
                    Resource resource = new PhysicalResource(name.getText(), model.getText(), "");
                    resource.setAvailable(false);
                    ResourceCatalogue.getInstance().add(resource, uid, null);
                    Requirement requirement = new Requirement(description.getText(), null);
                    OperationFacade.getInstance().addUnitRequirement(uid, requirement, resource);
                }

                JOptionPane.showMessageDialog(null, "نیازمندی با موفّقیّت ثبت شد.");
                unitRequirementsViewer.update();

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
}
