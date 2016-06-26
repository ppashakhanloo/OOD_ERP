package ui.project;

import business_logic_facade.OperationFacade;
import business_logic_facade.ProjectFacade;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import resource.*;
import ui.MainDialog;
import ui.utilities.FormUtility;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;

public class AddNewProjectRequirement extends MainDialog {
    private OperationFacade operationFacade;
    private ProjectFacade projectFacade;

    AddNewProjectRequirement(String pid) {
        operationFacade = new OperationFacade();
        projectFacade = new ProjectFacade();
        prepareGUI(pid);
    }

    private void prepareGUI(String pid) {
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

        JCheckBox isEssential = new JCheckBox();
        JLabel isEssentialLbl = formUtility.addLabel("نیازمندی ضروری است؟ ", form);
        formUtility.addLastField(isEssential, form);

        UtilDateModel dateModel = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());


        datePicker.setBounds(220, 350, 120, 30);
        JLabel dateLbl = formUtility.addLabel("آخرین مهلت تخصیص یافتن ", form);
        formUtility.addLastField(datePicker, form);

        JSpinner lengthOfPossession = new JSpinner(); // also for information
        JLabel lopLbl = formUtility.addLabel("تعداد روز موردنیاز ", form);
        formUtility.addLastField(lengthOfPossession, form);


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
                getMainDialog().repaint();
                getMainDialog().revalidate();
            }
        });

//boolean isEssential, String criticalProvideDate, String lengthOfPossession, String pid, Resource resource
        JButton submit = new JButton("افزودن");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, datePicker.getModel().getYear());
                cal.set(Calendar.MONTH, datePicker.getModel().getMonth());
                cal.set(Calendar.DAY_OF_MONTH, datePicker.getModel().getDay());
                if (monetaryCash.isSelected()) {
                    Resource resource = new MonetaryResource(MonetaryType.CASH, location.getText(), -1, new Quantity((Integer) amount.getValue(), QuantityUnit.valueOf(quantityUnitsCombo.getSelectedItem().toString())));
                    projectFacade.addRequirementToProject(isEssential.isSelected(), cal.getTime(), lengthOfPossession.getValue().toString(), pid, resource);
                } else if (monetaryNonCash.isSelected()) {
                    Resource resource = new MonetaryResource(MonetaryType.NON_CASH, location.getText(), -1, new Quantity((Integer) amount.getValue(), QuantityUnit.valueOf(quantityUnitsCombo.getSelectedItem().toString())));
                    projectFacade.addRequirementToProject(isEssential.isSelected(), cal.getTime(), lengthOfPossession.getValue().toString(), pid, resource);
                } else if (human.isSelected()) {
                    Resource resource = new HumanResource();
                    ((HumanResource) resource).setExpertise(exp.getText());
                    projectFacade.addRequirementToProject(isEssential.isSelected(), cal.getTime(), lengthOfPossession.getValue().toString(), pid, resource);
                } else if (information.isSelected()) {
                    Resource resource = new InformationResource(name.getText(), "");
                    projectFacade.addRequirementToProject(isEssential.isSelected(), cal.getTime(), lengthOfPossession.getValue().toString(), pid, resource);
                } else if (physical.isSelected()) {
                    Resource resource = new PhysicalResource(name.getText(), model.getText(), "");
                    projectFacade.addRequirementToProject(isEssential.isSelected(), cal.getTime(), lengthOfPossession.getValue().toString(), pid, resource);
                }
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

class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }
}


//    public ProjectRequirement() {
//super();
//        setEssential(false);
//        setID(generateNDigitID(ID_LENGTH));
//        }
