package ui.report;

import business_logic_facade.ProjectFacade;
import business_logic_facade.UserFacade;
import project.Project;
import ui.MainFrame;
import ui.Visibility;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Mozhdeh on 7/3/2016.
 */
public class RequiredResourcesReport implements Visibility {

    private MainFrame mainFrame;

    public RequiredResourcesReport(UserFacade currentUser) {
        mainFrame = new MainFrame(currentUser);
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame.getMainFrame().setTitle("گزارش منابع مورد نیاز");

        JPanel projectsPanel = new JPanel();
        projectsPanel.setBorder(BorderFactory.createTitledBorder("پروژه های سازمان"));

        JPanel reportPanel = new JPanel();
        reportPanel.setBorder(BorderFactory.createTitledBorder("گزارش منابع مورد نیاز"));

        JPanel cashMonetaryPanel = new JPanel();
        cashMonetaryPanel.setBorder(BorderFactory.createTitledBorder("منابع مالی نقدی"));

        JPanel nonCashMonetaryPanel = new JPanel();
        nonCashMonetaryPanel.setBorder(BorderFactory.createTitledBorder("منابع مالی غیرنقدی"));

        JPanel humanPanel = new JPanel();
        humanPanel.setBorder(BorderFactory.createTitledBorder("منابع انسانی"));

        JPanel informationPanel = new JPanel();
        informationPanel.setBorder(BorderFactory.createTitledBorder("منابع اطلاعاتی"));

        JPanel physicalPanel = new JPanel();
        physicalPanel.setBorder(BorderFactory.createTitledBorder("منابع فیزیکی"));

        JLabel projectsLbl = new JLabel("پروژه های موردنظر خود را انتخاب کنید:");

        DefaultListModel<Project> projListModel = new DefaultListModel<>();
        JList<Project> projList = new JList(projListModel);
        JScrollPane projScrollPane = new JScrollPane(projList);
        ArrayList<Project> projects = ProjectFacade.getInstance().getProjects();
        projects.forEach(projListModel::addElement);

        JButton report = new JButton("دریافت گزارش");

        Object[] columnNames = {"منبع", "پروژه"};

        DefaultTableModel cashMonetaryTableModel = new DefaultTableModel(columnNames, 2);
        JTable cashMonetaryTable = new JTable(cashMonetaryTableModel);
        JScrollPane cashMonetaryScrollPane = new JScrollPane(cashMonetaryTable);

        DefaultTableModel nonCashMonetaryTableModel = new DefaultTableModel(columnNames, 2);
        JTable nonCashMonetaryTable = new JTable(nonCashMonetaryTableModel);
        JScrollPane nonCashMonetaryScrollPane = new JScrollPane(nonCashMonetaryTable);

        DefaultTableModel humanTableModel = new DefaultTableModel(columnNames, 2);
        JTable humanTable = new JTable(humanTableModel);
        JScrollPane humanScrollPane = new JScrollPane(humanTable);

        DefaultTableModel informationTableModel = new DefaultTableModel(columnNames, 2);
        JTable informationTable = new JTable(informationTableModel);
        JScrollPane informationScrollPane = new JScrollPane(informationTable);

        DefaultTableModel physicalTableModel = new DefaultTableModel(columnNames, 2);
        JTable physicalTable = new JTable(physicalTableModel);
        JScrollPane physicalScrollPane = new JScrollPane(physicalTable);

        GroupLayout projectsPanelLayout = new GroupLayout(projectsPanel);
        projectsPanel.setLayout(projectsPanelLayout);
        projectsPanelLayout.setAutoCreateGaps(true);
        projectsPanelLayout.setAutoCreateContainerGaps(true);
        projectsPanelLayout.setHorizontalGroup(projectsPanelLayout.createSequentialGroup()
                .addGroup(projectsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(projScrollPane)
                        .addComponent(report))
                .addGroup(projectsPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(projectsLbl))
        );
        projectsPanelLayout.setVerticalGroup(projectsPanelLayout.createSequentialGroup()
                .addGroup(projectsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(projectsLbl)
                        .addComponent(projScrollPane))
                .addGroup(projectsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(report))
        );

        GroupLayout cashMonetaryPanelLayout = new GroupLayout(cashMonetaryPanel);
        cashMonetaryPanel.setLayout(cashMonetaryPanelLayout);
        cashMonetaryPanelLayout.setAutoCreateGaps(true);
        cashMonetaryPanelLayout.setAutoCreateContainerGaps(true);
        cashMonetaryPanelLayout.setHorizontalGroup(cashMonetaryPanelLayout.createSequentialGroup()
                .addGroup(cashMonetaryPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(cashMonetaryScrollPane))
        );
        cashMonetaryPanelLayout.setVerticalGroup(cashMonetaryPanelLayout.createSequentialGroup()
                .addGroup(cashMonetaryPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(cashMonetaryScrollPane))
        );

        GroupLayout nonCashMonetaryPanelLayout = new GroupLayout(nonCashMonetaryPanel);
        nonCashMonetaryPanel.setLayout(nonCashMonetaryPanelLayout);
        nonCashMonetaryPanelLayout.setAutoCreateGaps(true);
        nonCashMonetaryPanelLayout.setAutoCreateContainerGaps(true);
        nonCashMonetaryPanelLayout.setHorizontalGroup(nonCashMonetaryPanelLayout.createSequentialGroup()
                .addGroup(nonCashMonetaryPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nonCashMonetaryScrollPane))
        );
        nonCashMonetaryPanelLayout.setVerticalGroup(nonCashMonetaryPanelLayout.createSequentialGroup()
                .addGroup(nonCashMonetaryPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(nonCashMonetaryScrollPane))
        );

        GroupLayout humanPanelLayout = new GroupLayout(humanPanel);
        humanPanel.setLayout(humanPanelLayout);
        humanPanelLayout.setAutoCreateGaps(true);
        humanPanelLayout.setAutoCreateContainerGaps(true);
        humanPanelLayout.setHorizontalGroup(humanPanelLayout.createSequentialGroup()
                .addGroup(humanPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(humanScrollPane))
        );
        humanPanelLayout.setVerticalGroup(humanPanelLayout.createSequentialGroup()
                .addGroup(humanPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(humanScrollPane))
        );

        GroupLayout informationPanelLayout = new GroupLayout(informationPanel);
        informationPanel.setLayout(informationPanelLayout);
        informationPanelLayout.setAutoCreateGaps(true);
        informationPanelLayout.setAutoCreateContainerGaps(true);
        informationPanelLayout.setHorizontalGroup(informationPanelLayout.createSequentialGroup()
                .addGroup(informationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(informationScrollPane))
        );
        informationPanelLayout.setVerticalGroup(informationPanelLayout.createSequentialGroup()
                .addGroup(informationPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(informationScrollPane))
        );

        GroupLayout physicalPanelLayout = new GroupLayout(physicalPanel);
        physicalPanel.setLayout(physicalPanelLayout);
        physicalPanelLayout.setAutoCreateGaps(true);
        physicalPanelLayout.setAutoCreateContainerGaps(true);
        physicalPanelLayout.setHorizontalGroup(physicalPanelLayout.createSequentialGroup()
                .addGroup(physicalPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(physicalScrollPane))
        );
        physicalPanelLayout.setVerticalGroup(physicalPanelLayout.createSequentialGroup()
                .addGroup(physicalPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(physicalScrollPane))
        );

        GroupLayout reportPanelLayout = new GroupLayout(reportPanel);
        reportPanel.setLayout(reportPanelLayout);
        reportPanelLayout.setAutoCreateGaps(true);
        reportPanelLayout.setAutoCreateContainerGaps(true);
        reportPanelLayout.setHorizontalGroup(reportPanelLayout.createSequentialGroup()
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(cashMonetaryPanel)
                        .addComponent(nonCashMonetaryPanel)
                        .addComponent(humanPanel)
                        .addComponent(informationPanel)
                        .addComponent(physicalPanel))
        );
        reportPanelLayout.setVerticalGroup(reportPanelLayout.createSequentialGroup()
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(cashMonetaryPanel))
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(nonCashMonetaryPanel))
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(humanPanel))
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(informationPanel))
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(physicalPanel))
        );

        GroupLayout mainLayout = new GroupLayout(mainFrame.getMainFrame().getContentPane());
        mainFrame.getMainFrame().getContentPane().setLayout(mainLayout);
        mainLayout.setAutoCreateGaps(true);
        mainLayout.setAutoCreateContainerGaps(true);
        mainLayout.setHorizontalGroup(mainLayout.createSequentialGroup()
                .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(projectsPanel)
                        .addComponent(reportPanel))
        );
        mainLayout.setVerticalGroup(mainLayout.createSequentialGroup()
                .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(projectsPanel))
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
        RequiredResourcesReport rrr = new RequiredResourcesReport(userFacade);
        rrr.setVisible(true);
    }
}
