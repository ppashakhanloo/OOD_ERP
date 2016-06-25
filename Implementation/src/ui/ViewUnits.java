package ui;

import access.PermissionType;
import business_logic_facade.ResourceFacade;
import business_logic_facade.UserFacade;
import ui.utilities.UnitObserver;
import unit.Unit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ppash on 6/24/2016.
 */
public class ViewUnits extends UnitObserver implements Visiblity {

    private MainFrame mainFrame;
    private ResourceFacade resourceFacade;

    private AddNewUnit addNewUnit;

    private DefaultListModel<Unit> listModel;
    private JList<Unit> unitList;
    private JScrollPane jScrollPane;


    public ViewUnits(UserFacade currentUser) {
        mainFrame = new MainFrame(currentUser);
        resourceFacade = new ResourceFacade();
        addNewUnit = new AddNewUnit();
        addNewUnit.attach(this);
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame.getMainFrame().setTitle("مشاهده واحدهای سازمان");
        JPanel addUnitsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;

        JButton addNew = new JButton("افزودن واحد جدید");
        if (mainFrame.getCurrentUser().getCurrentUserPermissions().get(PermissionType.canAddUnit))
            addNew.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addNewUnit.setVisible(true);
                }
            });
        else
            addNew.setEnabled(false);

        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        addUnitsPanel.add(addNew, cs);
        mainFrame.getMainFrame().getContentPane().add(addUnitsPanel, BorderLayout.NORTH);

        ////////////////////
        listModel = new DefaultListModel<>();
        for (Unit unit : resourceFacade.getUnits())
            listModel.addElement(unit);
        unitList = new JList<>(listModel);
        ////////////////////
        jScrollPane = new JScrollPane(unitList);
        mainFrame.getMainFrame().add(jScrollPane, BorderLayout.CENTER);
        mainFrame.getMainFrame().setResizable(true);
        mainFrame.getMainFrame().pack();
    }

    @Override
    public void setVisible(boolean visible) {
        mainFrame.getMainFrame().setVisible(visible);
    }

    @Override
    public void update() {
        mainFrame.getMainFrame().remove(jScrollPane);
        listModel = new DefaultListModel<>();
        for (Unit unit : resourceFacade.getUnits())
            listModel.addElement(unit);
        unitList = new JList<>(listModel);
        jScrollPane = new JScrollPane(unitList);
        mainFrame.getMainFrame().add(jScrollPane, BorderLayout.CENTER);
        mainFrame.getMainFrame().repaint();
        mainFrame.getMainFrame().revalidate();
    }

    public static void main(String[] args) {
        UserFacade userFacade = new UserFacade();
        userFacade.login("871539", "888");
        ViewUnits viewUnits = new ViewUnits(userFacade);
        viewUnits.setVisible(true);
    }
}