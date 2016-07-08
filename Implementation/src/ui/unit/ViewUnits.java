package ui.unit;

import access.PermissionType;
import business_logic_facade.OperationFacade;
import business_logic_facade.UserFacade;
import ui.MainDialog;
import ui.Visibility;
import unit.Unit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ViewUnits extends UnitObserver implements Visibility {

    private MainDialog mainDialog;

    private AddNewUnit addNewUnit;

    private DefaultListModel<Unit> listModel;
    private JList<Unit> unitList;
    private JScrollPane jScrollPane;

    private UserFacade userFacade;

    public ViewUnits(UserFacade userFacade) {
        mainDialog = new MainDialog();
        addNewUnit = new AddNewUnit();
        addNewUnit.attach(this);
        this.userFacade = userFacade;
        prepareGUI();
    }

    public static void main(String[] args) {
        UserFacade userFacade = new UserFacade();
        userFacade.login("310243", "admin");
        ViewUnits viewUnits = new ViewUnits(userFacade);
        viewUnits.setVisible(true);
    }

    private void prepareGUI() {
        mainDialog.getMainDialog().setTitle("مشاهده واحدهای سازمان");
        JPanel addUnitsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;

        JButton addNew = new JButton("افزودن واحد جدید");
        if (userFacade.hasPermission(PermissionType.canAddUnit))
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
        mainDialog.getMainDialog().getContentPane().add(addUnitsPanel, BorderLayout.NORTH);

        ////////////////////
        listModel = new DefaultListModel<>();
        for (Unit unit : OperationFacade.getInstance().getUnits())
            listModel.addElement(unit);
        unitList = new JList<>(listModel);
        ////////////////////
        jScrollPane = new JScrollPane(unitList);
        mainDialog.getMainDialog().add(jScrollPane, BorderLayout.CENTER);
        mainDialog.getMainDialog().setResizable(true);
        mainDialog.getMainDialog().pack();
        mainDialog.getMainDialog().setLocationRelativeTo(null);

        unitList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {
                    // Double-click detected
                    ViewUnitRequirements viewUnitRequirements = new ViewUnitRequirements(userFacade, ((Unit) list.getSelectedValue()));
                    viewUnitRequirements.setVisible(true);
                }
            }
        });
    }

    @Override
    public void setVisible(boolean visible) {
        mainDialog.getMainDialog().setVisible(visible);
    }

    @Override
    public void update() {
        mainDialog.getMainDialog().remove(jScrollPane);
        listModel = new DefaultListModel<>();
        for (Unit unit : OperationFacade.getInstance().getUnits())
            listModel.addElement(unit);
        unitList = new JList<>(listModel);
        jScrollPane = new JScrollPane(unitList);
        mainDialog.getMainDialog().add(jScrollPane, BorderLayout.CENTER);
        mainDialog.getMainDialog().repaint();
        mainDialog.getMainDialog().revalidate();
    }
}