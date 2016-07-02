package ui.resource;

import access.PermissionType;
import business_logic_facade.OperationFacade;
import business_logic_facade.UserFacade;
import resource.InformationResource;
import resource.Resource;
import ui.MainFrame;
import ui.Visibility;
import unit.Unit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ViewInformationResources extends InformationResourceObserver implements Visibility {

    private MainFrame mainFrame;

    private AddNewInformationResource addNewInformationResource;

    private DefaultListModel<InformationResource> listModel;
    private JList<InformationResource> resourceList;
    private JPanel panel2;

    private UserFacade userFacade;

    public ViewInformationResources(UserFacade userFacade) {
        mainFrame = new MainFrame(userFacade);
        addNewInformationResource = new AddNewInformationResource();
        addNewInformationResource.attach(this);
        this.userFacade = userFacade;
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame.getMainFrame().setTitle("مشاهده منابع اطلاعاتی");
        mainFrame.getMainFrame().getContentPane().remove(0);
        mainFrame.getMainFrame().setLayout(new FlowLayout());

        ////////////////////////////////////////////////////////
        JPanel panel1 = new JPanel(new GridLayout(2, 1));
        JButton addNew = new JButton("افزودن منبع جدید");
        if (mainFrame.getCurrentUser().getCurrentUserPermissions().get(PermissionType.canAddRemResource))
            addNew.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addNewInformationResource.setVisible(true);
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
        mainFrame.getMainFrame().add(panel1);
        ////////////////////
        panel2 = new JPanel(new GridLayout(1, 1));
        listModel = new DefaultListModel<>();
        for (Resource resource : OperationFacade.getInstance().getInformationResources())
            listModel.addElement((InformationResource) resource);
        resourceList = new JList<>(listModel);
        resourceList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {
                    // Double-click detected
                    new SingleInformationResource(userFacade, ((InformationResource) list.getSelectedValue()).getID()).setVisible(true);
                }
            }
        });
        panel2.add(new JScrollPane(resourceList));
        mainFrame.getMainFrame().add(panel2);

        unitsCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel2.removeAll();
                listModel = new DefaultListModel<>();
                if (unitsCombo.getSelectedItem() == null) {
                    for (Resource resource : OperationFacade.getInstance().getInformationResources()) {
                        listModel.addElement((InformationResource) resource);
                    }
                } else {
                    for (Resource resource : ((Unit) unitsCombo.getSelectedItem()).getResources())
                        if (resource instanceof InformationResource) {
                            listModel.addElement((InformationResource) resource);
                        }
                }
                resourceList = new JList<>(listModel);
                resourceList.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        JList list = (JList) evt.getSource();
                        if (evt.getClickCount() == 2) {
                            // Double-click detected
                            new SingleInformationResource(userFacade, ((InformationResource) list.getSelectedValue()).getID()).setVisible(true);
                        }
                    }
                });
                panel2.add(new JScrollPane(resourceList));
                mainFrame.getMainFrame().repaint();
                mainFrame.getMainFrame().revalidate();
            }
        });

        mainFrame.getMainFrame().pack();
        mainFrame.getMainFrame().setLocationRelativeTo(null);
    }

    @Override
    public void setVisible(boolean visible) {
        mainFrame.getMainFrame().setVisible(visible);
    }

    @Override
    public void update() {
        panel2.removeAll();
        listModel = new DefaultListModel<>();
        for (Resource resource : OperationFacade.getInstance().getInformationResources())
            listModel.addElement((InformationResource) resource);
        resourceList = new JList<>(listModel);
        resourceList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {
                    // Double-click detected
                    new SingleInformationResource(userFacade, ((InformationResource) list.getSelectedValue()).getID()).setVisible(true);
                }
            }
        });
        JScrollPane jScrollPane = new JScrollPane(resourceList);
        mainFrame.getMainFrame().add(jScrollPane, BorderLayout.CENTER);
        mainFrame.getMainFrame().repaint();
        mainFrame.getMainFrame().revalidate();
    }

    public static void main(String[] args) {
        UserFacade userFacade = new UserFacade();
        userFacade.login("100824", "888");
        ViewInformationResources viewInformationResources = new ViewInformationResources(userFacade);
        viewInformationResources.setVisible(true);
    }
}