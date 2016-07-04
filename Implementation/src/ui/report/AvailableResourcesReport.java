package ui.report;

import business_logic_facade.OperationFacade;
import business_logic_facade.UserFacade;
import resource.Resource;
import ui.MainFrame;
import ui.Visibility;
import unit.Unit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Mozhdeh on 7/3/2016.
 */
public class AvailableResourcesReport implements Visibility {

    private MainFrame mainFrame;
    private DefaultTableModel reportTableModel;

    public AvailableResourcesReport(UserFacade currentUser) {
        mainFrame = new MainFrame(currentUser);

        Object[] columnNames = {"واحد", "منبع"};
        reportTableModel = new DefaultTableModel(columnNames, 0);
        Map<Unit, ArrayList<Resource>> availableResources = OperationFacade.getInstance().getAvailableResources();

        Iterator<Map.Entry<Unit, ArrayList<Resource>>> entries = availableResources.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Unit, ArrayList<Resource>> entry = entries.next();
            Unit key = entry.getKey();
            ArrayList<Resource> value = entry.getValue();

            for (Resource resource : value) {
                Object[] data = {key.getName(), resource};
                reportTableModel.addRow(data);
            }
        }
        
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame.getMainFrame().setTitle("گزارش منابع موجود");

        JPanel reportPanel = new JPanel();
        reportPanel.setBorder(BorderFactory.createTitledBorder("گزارش منابع موجود"));

        JTable reportTable = new JTable(reportTableModel);
        JScrollPane reportScrollPane = new JScrollPane(reportTable);

        GroupLayout reportPanelLayout = new GroupLayout(reportPanel);
        reportPanel.setLayout(reportPanelLayout);
        reportPanelLayout.setAutoCreateGaps(true);
        reportPanelLayout.setAutoCreateContainerGaps(true);
        reportPanelLayout.setHorizontalGroup(reportPanelLayout.createSequentialGroup()
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(reportScrollPane))
        );
        reportPanelLayout.setVerticalGroup(reportPanelLayout.createSequentialGroup()
                .addGroup(reportPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(reportScrollPane))
        );

        GroupLayout mainLayout = new GroupLayout(mainFrame.getMainFrame().getContentPane());
        mainFrame.getMainFrame().getContentPane().setLayout(mainLayout);
        mainLayout.setAutoCreateGaps(true);
        mainLayout.setAutoCreateContainerGaps(true);
        mainLayout.setHorizontalGroup(mainLayout.createSequentialGroup()
                .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(reportPanel))
        );
        mainLayout.setVerticalGroup(mainLayout.createSequentialGroup()
                .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(reportPanel))
        );

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
        AvailableResourcesReport arr = new AvailableResourcesReport(userFacade);
        arr.setVisible(true);
    }
}
