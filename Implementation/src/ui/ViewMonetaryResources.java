package ui;

import access.PermissionType;
import business_logic_facade.OperationFacade;
import business_logic_facade.UserFacade;
import resource.MonetaryResource;
import resource.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ppash on 6/24/2016.
 */
public class ViewMonetaryResources extends MonetaryResourceObserver implements Visiblity {

    private MainFrame mainFrame;
    private OperationFacade operationFacade;

    private AddNewMonetaryResource addNewMonetaryResource;

    private DefaultListModel<MonetaryResource> listModel;
    private JList<MonetaryResource> resourceList;
    private JScrollPane jScrollPane;


    public ViewMonetaryResources(UserFacade currentUser) {
        mainFrame = new MainFrame(currentUser);
        operationFacade = new OperationFacade();
        addNewMonetaryResource = new AddNewMonetaryResource();
        addNewMonetaryResource.attach(this);
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame.getMainFrame().setTitle("مشاهده منابع مالی");
        JPanel addResourcesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;

        JButton addNew = new JButton("افزودن منبع جدید");
        if (mainFrame.getCurrentUser().getCurrentUserPermissions().get(PermissionType.canAddRemResource))
            addNew.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addNewMonetaryResource.setVisible(true);
                }
            });
        else
            addNew.setEnabled(false);

        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        addResourcesPanel.add(addNew, cs);
        mainFrame.getMainFrame().getContentPane().add(addResourcesPanel, BorderLayout.NORTH);

        ////////////////////
        listModel = new DefaultListModel<>();
        for (Resource resource : operationFacade.getMonetaryResources())
            listModel.addElement((MonetaryResource) resource);
        resourceList = new JList<>(listModel);
        ////////////////////
        jScrollPane = new JScrollPane(resourceList);
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
        for (Resource resource : operationFacade.getMonetaryResources())
            listModel.addElement((MonetaryResource) resource);
        resourceList = new JList<>(listModel);
        jScrollPane = new JScrollPane(resourceList);
        mainFrame.getMainFrame().add(jScrollPane, BorderLayout.CENTER);
        mainFrame.getMainFrame().repaint();
        mainFrame.getMainFrame().revalidate();
    }

    public static void main(String[] args) {
        UserFacade userFacade = new UserFacade();
        userFacade.login("158481", "y");
        ViewMonetaryResources viewMonetaryResources = new ViewMonetaryResources(userFacade);
        viewMonetaryResources.setVisible(true);
    }
}