package ui.report;

import business_logic_facade.OperationFacade;
import business_logic_facade.UserFacade;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import resource.Resource;
import ui.MainFrame;
import ui.Visibility;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

public class UsageFlowReport implements Visibility {

    private MainFrame mainFrame;

    public UsageFlowReport(UserFacade currentUser) {
        mainFrame = new MainFrame(currentUser);
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame.getMainFrame().setTitle("گزارش جریان چرخشی منابع");

        JPanel resourcesPanel = new JPanel();
        resourcesPanel.setBorder(BorderFactory.createTitledBorder("منابع و بازه ی زمانی"));

        JPanel reportPanel = new JPanel();
        reportPanel.setBorder(BorderFactory.createTitledBorder("گزارش جریان چرخشی منابع"));

        JLabel resourcesLbl = new JLabel("منابع موردنظر خود را انتخاب کنید:");

        DefaultListModel<Resource> resListModel = new DefaultListModel<>();
        JList<Resource> resList = new JList(resListModel);
        JScrollPane resScrollPane = new JScrollPane(resList);
        ArrayList<Resource> resources = OperationFacade.getInstance().getResources();
        resources.forEach(resListModel::addElement);

        JLabel timeIntervalTypeLbl = new JLabel("نوع بازه ی زمانی را انتخاب کنید:");

        JRadioButton specificRb = new JRadioButton("بازه ی زمانی مشخص");
        JRadioButton unspecificRb = new JRadioButton("بازه ی زمانی نامحدود");

        JLabel timeIntervalLbl = new JLabel("بازه ی زمانی را مشخص کنید:");

        UtilDateModel dateModel = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        JLabel dateLbl = new JLabel("از ");

        UtilDateModel dateModel2 = new UtilDateModel();
        Properties p2 = new Properties();
        p2.put("text.today", "Today");
        p2.put("text.month", "Month");
        p2.put("text.year", "Year");
        JDatePanelImpl datePanel2 = new JDatePanelImpl(dateModel2, p2);
        JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
        JLabel dateLbl2 = new JLabel("تا ");

        JButton report = new JButton("دریافت گزارش");

        Object[] columnNames = {"تا", "از", "پروژه", "منبع"};

        DefaultTableModel flowReportTableModel = new DefaultTableModel(columnNames, 4);
        JTable flowReportTable = new JTable(flowReportTableModel);
        JScrollPane flowReportScrollPane = new JScrollPane(flowReportTable);

        GroupLayout resourcesPanelLayout = new GroupLayout(resourcesPanel);
        resourcesPanel.setLayout(resourcesPanelLayout);
        resourcesPanelLayout.setAutoCreateGaps(true);
        resourcesPanelLayout.setAutoCreateContainerGaps(true);
        resourcesPanelLayout.setHorizontalGroup(resourcesPanelLayout.createSequentialGroup()
                .addGroup(resourcesPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(resScrollPane)
                        .addComponent(report))
                .addGroup(resourcesPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(unspecificRb)
                        .addComponent(dateLbl2)
                        .addComponent(datePicker2))
                .addGroup(resourcesPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(specificRb)
                        .addComponent(dateLbl)
                        .addComponent(datePicker))
                .addGroup(resourcesPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(resourcesLbl)
                        .addComponent(timeIntervalTypeLbl)
                        .addComponent(timeIntervalLbl))
        );
        resourcesPanelLayout.setVerticalGroup(resourcesPanelLayout.createSequentialGroup()
                .addGroup(resourcesPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(resourcesLbl)
                        .addComponent(resScrollPane))
                .addGroup(resourcesPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(timeIntervalTypeLbl)
                        .addComponent(specificRb)
                        .addComponent(unspecificRb))
                .addGroup(resourcesPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(timeIntervalLbl)
                        .addComponent(dateLbl)
                        .addComponent(datePicker)
                        .addComponent(dateLbl2)
                        .addComponent(datePicker2))
                .addGroup(resourcesPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(report))
        );

        GroupLayout reportPanelLayout = new GroupLayout(reportPanel);
        reportPanel.setLayout(reportPanelLayout);
        reportPanelLayout.setAutoCreateGaps(true);
        reportPanelLayout.setAutoCreateContainerGaps(true);
        reportPanelLayout.setHorizontalGroup(reportPanelLayout.createSequentialGroup()
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(flowReportScrollPane))
        );
        reportPanelLayout.setVerticalGroup(reportPanelLayout.createSequentialGroup()
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(flowReportScrollPane))
        );

        GroupLayout mainLayout = new GroupLayout(mainFrame.getMainFrame().getContentPane());
        mainFrame.getMainFrame().getContentPane().setLayout(mainLayout);
        mainLayout.setAutoCreateGaps(true);
        mainLayout.setAutoCreateContainerGaps(true);
        mainLayout.setHorizontalGroup(mainLayout.createSequentialGroup()
                .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(resourcesPanel)
                        .addComponent(reportPanel))
        );
        mainLayout.setVerticalGroup(mainLayout.createSequentialGroup()
                .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(resourcesPanel))
                .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(reportPanel))
        );

        report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });

        mainFrame.getMainFrame().pack();
        mainFrame.getMainFrame().setLocationRelativeTo(null);
    }

    @Override
    public void setVisible(boolean visible) {
        mainFrame.setVisible(visible);
    }

    public static void main(String[] args) {
        UserFacade userFacade = new UserFacade();
        userFacade.login("100824", "888");
        UsageFlowReport ufr = new UsageFlowReport(userFacade);
        ufr.setVisible(true);
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
