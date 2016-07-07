package ui.search;

import business_logic_facade.OperationFacade;
import business_logic_facade.UserFacade;
import project.Project;
import project.Technology;
import ui.MainFrame;
import ui.Visibility;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EstimateResources implements Visibility {

    private MainFrame mainFrame;

    public EstimateResources(UserFacade currentUser) {
        mainFrame = new MainFrame(currentUser);
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame.getMainFrame().setTitle("جست و جو برای تخمین منابع");

        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(BorderFactory.createTitledBorder("پارامترهای جست و جو"));

        JPanel searchResultsPanel = new JPanel();
        searchResultsPanel.setBorder(BorderFactory.createTitledBorder("نتایج جست و جو"));

        JLabel technologiesLbl = new JLabel("تکنولوژی های مورد نظر");

        DefaultListModel<Technology> listModelTech = new DefaultListModel<>();
        JList<Technology> techList = new JList(listModelTech);
        JScrollPane techListScroll = new JScrollPane(techList);
        ArrayList<Technology> techs = OperationFacade.getInstance().getTechnologies();
        techs.forEach(listModelTech::addElement);

        JLabel developersCountLbl = new JLabel("تعداد توسعه دهندگان");
        JSpinner developersCount = new JSpinner();

        JLabel usersCountLbl = new JLabel("تعداد کاربران");
        JSpinner usersCount = new JSpinner();

        JLabel modulesCountLbl = new JLabel("تعداد ماژول ها");
        JSpinner modulesCount = new JSpinner();

        JButton search = new JButton("جست و جو");

        GroupLayout searchPanelLayout = new GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setAutoCreateGaps(true);
        searchPanelLayout.setAutoCreateContainerGaps(true);
        searchPanelLayout.setHorizontalGroup(searchPanelLayout.createSequentialGroup()
                .addGroup(searchPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(techListScroll)
                        .addComponent(developersCount)
                        .addComponent(usersCount)
                        .addComponent(modulesCount)
                        .addComponent(search))
                .addGroup(searchPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(technologiesLbl)
                        .addComponent(developersCountLbl)
                        .addComponent(usersCountLbl)
                        .addComponent(modulesCountLbl))
        );
        searchPanelLayout.setVerticalGroup(searchPanelLayout.createSequentialGroup()
                .addGroup(searchPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(technologiesLbl)
                        .addComponent(techListScroll))
                .addGroup(searchPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(developersCountLbl)
                        .addComponent(developersCount))
                .addGroup(searchPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(usersCountLbl)
                        .addComponent(usersCount))
                .addGroup(searchPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(modulesCountLbl)
                        .addComponent(modulesCount))
                .addGroup(searchPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(search))
        );

        JLabel resultsLbl = new JLabel("پروژه های مشابه");

        DefaultListModel<Project> listModelProj = new DefaultListModel<>();
        JList<Technology> projList = new JList(listModelProj);
        JScrollPane projListScroll = new JScrollPane(projList);
        searchResultsPanel.add(projListScroll);

        GroupLayout searchResultsPanelLayout = new GroupLayout(searchResultsPanel);
        searchResultsPanel.setLayout(searchResultsPanelLayout);
        searchResultsPanelLayout.setAutoCreateGaps(true);
        searchResultsPanelLayout.setAutoCreateContainerGaps(true);
        searchResultsPanelLayout.setHorizontalGroup(searchResultsPanelLayout.createSequentialGroup()
                .addGroup(searchResultsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(projListScroll))
                .addGroup(searchResultsPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(resultsLbl))
        );
        searchResultsPanelLayout.setVerticalGroup(searchResultsPanelLayout.createSequentialGroup()
                .addGroup(searchResultsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(resultsLbl)
                        .addComponent(projListScroll))
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
                //java.util.List<Technology> selectedTechs = techList.getSelectedValuesList();
                Technology tech = techList.getSelectedValue();
                int usercount = (Integer) usersCount.getValue();
                int devCount = (Integer) developersCount.getValue();
                int modCount = (Integer) modulesCount.getValue();
                OperationFacade.getInstance().search(tech, devCount, usercount, modCount).forEach(project -> listModelProj.addElement(project));
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
        userFacade.login("310243", "admin");
        EstimateResources er = new EstimateResources(userFacade);
        er.setVisible(true);
    }
}
