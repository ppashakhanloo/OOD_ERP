package ui.search;

import business_logic_facade.UserFacade;
import ui.MainFrame;
import ui.Visibility;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EstimateRequirements implements Visibility {

    private MainFrame mainFrame;

    public EstimateRequirements(UserFacade currentUser) {
        mainFrame = new MainFrame(currentUser);
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame.getMainFrame().setTitle("جست و جو برای تخمین ضرورت نیازمندی ها");

        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(BorderFactory.createTitledBorder("منبع"));

        JPanel searchResultsPanel = new JPanel();
        searchResultsPanel.setBorder(BorderFactory.createTitledBorder("نتایج جست و جو"));

        JLabel resourceLbl = new JLabel("نام منبع");
        JTextField resourceName = new JTextField();

        JButton search = new JButton("جست و جو");

        Object[] columnNames = {"زمان تأمین شدن", "ضرورت", "پروژه"};

        DefaultTableModel resultsTableModel = new DefaultTableModel(columnNames, 5);
        JTable resultsTable = new JTable(resultsTableModel);
        JScrollPane resultsScrollPane = new JScrollPane(resultsTable);

        GroupLayout searchPanelLayout = new GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setAutoCreateGaps(true);
        searchPanelLayout.setAutoCreateContainerGaps(true);
        searchPanelLayout.setHorizontalGroup(searchPanelLayout.createSequentialGroup()
                .addGroup(searchPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(search))
                .addGroup(searchPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(resourceName))
                .addGroup(searchPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(resourceLbl))
        );
        searchPanelLayout.setVerticalGroup(searchPanelLayout.createSequentialGroup()
                .addGroup(searchPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(resourceLbl)
                        .addComponent(resourceName)
                        .addComponent(search))
        );

        GroupLayout searchResultsPanelLayout = new GroupLayout(searchResultsPanel);
        searchResultsPanel.setLayout(searchResultsPanelLayout);
        searchResultsPanelLayout.setAutoCreateGaps(true);
        searchResultsPanelLayout.setAutoCreateContainerGaps(true);
        searchResultsPanelLayout.setHorizontalGroup(searchResultsPanelLayout.createSequentialGroup()
                .addGroup(searchResultsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(resultsScrollPane))
        );
        searchResultsPanelLayout.setVerticalGroup(searchResultsPanelLayout.createSequentialGroup()
                .addGroup(searchResultsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(resultsScrollPane))
        );

        GroupLayout mainLayout = new GroupLayout(mainFrame.getMainFrame().getContentPane());
        mainFrame.getMainFrame().getContentPane().setLayout(mainLayout);
        mainLayout.setAutoCreateGaps(true);
        mainLayout.setAutoCreateContainerGaps(true);
        mainLayout.setHorizontalGroup(mainLayout.createSequentialGroup()
                .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(searchPanel)
                        .addComponent(searchResultsPanel))
        );
        mainLayout.setVerticalGroup(mainLayout.createSequentialGroup()
                .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(searchPanel))
                .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(searchResultsPanel))
        );

        search.addActionListener(new ActionListener() {
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
        EstimateRequirements er = new EstimateRequirements(userFacade);
        er.setVisible(true);
    }
}
