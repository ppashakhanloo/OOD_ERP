package ui.unit;


import access.PermissionType;
import business_logic_facade.OperationFacade;
import business_logic_facade.UserFacade;
import resource.*;
import ui.MainFrame;
import ui.Visibility;
import unit.Requirement;
import unit.Unit;
import unit.UnitCatalogue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewUnitRequirements extends UnitObserver implements Visibility {

    private MainFrame mainFrame;
    private Unit unit;

    public ViewUnitRequirements(UserFacade currentUser, Unit unit) {
        mainFrame = new MainFrame(currentUser);
        this.unit = unit;
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame.getMainFrame().setTitle("مشاهده نیازمندی‌های واحد");

        JPanel requirementsPanel = new JPanel();
        requirementsPanel.setBorder(BorderFactory.createTitledBorder("نیازمندیهای واحد"));

        DefaultListModel<Requirement> monetaryListModel = new DefaultListModel<>();
        JList monetaryList = new JList(monetaryListModel);
        JScrollPane monetaryScrollPane = new JScrollPane(monetaryList);
        monetaryScrollPane.setBorder(BorderFactory.createTitledBorder("نیازمندی های مالی"));

        DefaultListModel<Requirement> humanListModel = new DefaultListModel<>();
        JList humanList = new JList(humanListModel);
        JScrollPane humanScrollPane = new JScrollPane(humanList);
        humanScrollPane.setBorder(BorderFactory.createTitledBorder("نیازمندی های انسانی"));

        DefaultListModel<Requirement> informationListModel = new DefaultListModel<>();
        JList informationList = new JList(informationListModel);
        JScrollPane informationScrollPane = new JScrollPane(informationList);
        informationScrollPane.setBorder(BorderFactory.createTitledBorder("نیازمندی های اطلاعاتی"));

        DefaultListModel<Requirement> physicalListModel = new DefaultListModel<>();
        JList physicalList = new JList(physicalListModel);
        JScrollPane physicalScrollPane = new JScrollPane(physicalList);
        physicalScrollPane.setBorder(BorderFactory.createTitledBorder("نیازمندی های فیزیکی"));

        JButton addNew = new JButton("افزودن نیازمندی جدید");

        GroupLayout requirementsPanelLayout = new GroupLayout(requirementsPanel);
        requirementsPanel.setLayout(requirementsPanelLayout);
        requirementsPanelLayout.setAutoCreateGaps(true);
        requirementsPanelLayout.setAutoCreateContainerGaps(true);
        requirementsPanelLayout.setHorizontalGroup(requirementsPanelLayout.createSequentialGroup()
                .addGroup(requirementsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(monetaryScrollPane)
                        .addComponent(humanScrollPane)
                        .addComponent(informationScrollPane)
                        .addComponent(physicalScrollPane))
        );
        requirementsPanelLayout.setVerticalGroup(requirementsPanelLayout.createSequentialGroup()
                .addGroup(requirementsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(monetaryScrollPane))
                .addGroup(requirementsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(humanScrollPane))
                .addGroup(requirementsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(informationScrollPane))
                .addGroup(requirementsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(physicalScrollPane))
        );

        GroupLayout mainLayout = new GroupLayout(mainFrame.getMainFrame().getContentPane());
        mainFrame.getMainFrame().getContentPane().setLayout(mainLayout);
        mainLayout.setAutoCreateGaps(true);
        mainLayout.setAutoCreateContainerGaps(true);
        mainLayout.setHorizontalGroup(mainLayout.createSequentialGroup()
                .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(requirementsPanel)
                        .addComponent(addNew))
        );
        mainLayout.setVerticalGroup(mainLayout.createSequentialGroup()
                .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(requirementsPanel))
                .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(addNew))
        );

        ArrayList<Requirement> unitRequirements = OperationFacade.getInstance().getUnitRequirements(unit.getID());
        Resource resource;

        for (Requirement unitRequirement : unitRequirements) {
            resource = ResourceCatalogue.getInstance().get(unitRequirement.getResource().getID());
            if (resource instanceof MonetaryResource)
                monetaryListModel.addElement(unitRequirement);
            else if (resource instanceof HumanResource)
                humanListModel.addElement(unitRequirement);
            else if (resource instanceof InformationResource)
                informationListModel.addElement(unitRequirement);
            else
                physicalListModel.addElement(unitRequirement);
        }

        if (mainFrame.getCurrentUser().hasPermission(PermissionType.canAddRemReq))
            addNew.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AddNewUnitRequirement addNewUnitRequirement = new AddNewUnitRequirement(unit.getID(), ViewUnitRequirements.this);
                    addNewUnitRequirement.setVisible(true);
                }
            });
        else
            addNew.setEnabled(false);

        mainFrame.getMainFrame().setResizable(true);

        mainFrame.getMainFrame().pack();
        mainFrame.getMainFrame().setLocationRelativeTo(null);
    }

    @Override
    public void setVisible(boolean visible) {
        mainFrame.getMainFrame().setVisible(visible);
    }

    @Override
    public void update() {
        this.setVisible(false);
        this.mainFrame.getMainFrame().getContentPane().removeAll();
        this.prepareGUI();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        UserFacade userFacade = new UserFacade();
        userFacade.login("100824", "888");
        Unit unit = UnitCatalogue.getInstance().get("836936");
        ViewUnitRequirements vur = new ViewUnitRequirements(userFacade, unit);
        vur.setVisible(true);
    }
}
