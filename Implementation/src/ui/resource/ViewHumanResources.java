package ui.resource;

import access.PermissionType;
import business_logic_facade.OperationFacade;
import business_logic_facade.UserFacade;
import resource.HumanResource;
import resource.Resource;
import ui.MainDialog;
import ui.Visibility;
import unit.Unit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ViewHumanResources extends HumanResourceObserver implements Visibility {

    private MainDialog mainDialog;

    private AddNewHumanResource addNewHumanResource;

    private DefaultListModel<HumanResource> listModel;
    private JList<HumanResource> resourceList;
    private JPanel panel2;

    private UserFacade userFacade;

    public ViewHumanResources(UserFacade userFacade) {
        mainDialog = new MainDialog();
        addNewHumanResource = new AddNewHumanResource();
        addNewHumanResource.attach(this);
        this.userFacade = userFacade;
        prepareGUI();
    }

    public static void main(String[] args) {
        UserFacade userFacade = new UserFacade();
        userFacade.login("100824", "888");
        ViewHumanResources viewHumanResources = new ViewHumanResources(userFacade);
        viewHumanResources.setVisible(true);
    }

    private void prepareGUI() {
        mainDialog.getMainDialog().setTitle("مشاهده منابع انسانی");
        mainDialog.getMainDialog().setLayout(new FlowLayout());

        ////////////////////////////////////////////////////////
        JPanel panel1 = new JPanel(new GridLayout(2, 1));
        JButton addNew = new JButton("افزودن منبع جدید");
        if (userFacade.hasPermission(PermissionType.canAddRemResource))
            addNew.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addNewHumanResource.setVisible(true);
                }
            });
        else
            addNew.setEnabled(false);
        panel1.add(addNew);
        /////////////////////////
        JPanel innerPanel = new JPanel(new FlowLayout());
        ArrayList<Unit> units = OperationFacade.getInstance().getUnits();
        JComboBox<Unit> unitsCombo = new JComboBox<>();
        units.forEach(unitsCombo::addItem);
        unitsCombo.insertItemAt(null, 0);
        unitsCombo.setSelectedIndex(0);
        innerPanel.add(new JLabel("واحد"));
        innerPanel.add(unitsCombo);
        panel1.add(innerPanel);
        mainDialog.getMainDialog().add(panel1);
        ////////////////////
        panel2 = new JPanel(new GridLayout(1, 1));
        listModel = new DefaultListModel<>();
        for (Resource resource : OperationFacade.getInstance().getHumanResources())
            listModel.addElement((HumanResource) resource);
        resourceList = new JList<>(listModel);
        if (userFacade.hasPermission(PermissionType.canGetResourceAttributes))
            resourceList.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    JList list = (JList) evt.getSource();
                    if (evt.getClickCount() == 2) {
                        // Double-click detected
                        new SingleHumanResource(userFacade, ((HumanResource) list.getSelectedValue()).getID()).setVisible(true);
                    }
                }
            });
        panel2.add(new JScrollPane(resourceList));
        mainDialog.getMainDialog().add(panel2);

        unitsCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel2.removeAll();
                listModel = new DefaultListModel<>();
                if (unitsCombo.getSelectedItem() == null) {
                    for (Resource resource : OperationFacade.getInstance().getHumanResources()) {
                        listModel.addElement((HumanResource) resource);
                    }
                } else {
                    for (Resource resource : ((Unit) unitsCombo.getSelectedItem()).getResources())
                        if (resource instanceof HumanResource) {
                            listModel.addElement((HumanResource) resource);
                        }
                }
                resourceList = new JList<>(listModel);
                if (userFacade.hasPermission(PermissionType.canGetResourceAttributes))
                    resourceList.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent evt) {
                            JList list = (JList) evt.getSource();
                            if (evt.getClickCount() == 2) {
                                // Double-click detected
                                update();
//                                new SingleHumanResource(userFacade, ((HumanResource) list.getSelectedValue()).getID()).setVisible(true);
                            }
                        }
                    });
                panel2.add(new JScrollPane(resourceList));
                mainDialog.getMainDialog().repaint();
                mainDialog.getMainDialog().revalidate();
                mainDialog.getMainDialog().pack();
            }
        });

        mainDialog.getMainDialog().pack();
        mainDialog.getMainDialog().setLocationRelativeTo(null);
    }

    @Override
    public void setVisible(boolean visible) {
        mainDialog.getMainDialog().setVisible(visible);
    }

    @Override
    public void update() {
        panel2.removeAll();
        listModel = new DefaultListModel<>();
        for (Resource resource : OperationFacade.getInstance().getHumanResources())
            listModel.addElement((HumanResource) resource);
        resourceList = new JList<>(listModel);
        if (userFacade.hasPermission(PermissionType.canGetResourceAttributes))
            resourceList.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    JList list = (JList) evt.getSource();
                    if (evt.getClickCount() == 2) {
                        // Double-click detected
                        new SingleHumanResource(userFacade, ((HumanResource) list.getSelectedValue()).getID()).setVisible(true);
                    }
                }
            });
        JScrollPane jScrollPane = new JScrollPane(resourceList);
        mainDialog.getMainDialog().add(jScrollPane, BorderLayout.CENTER);
        mainDialog.getMainDialog().repaint();
        mainDialog.getMainDialog().revalidate();
        mainDialog.getMainDialog().pack();
    }
}