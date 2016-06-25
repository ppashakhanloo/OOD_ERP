package ui.resource;

import access.PermissionType;
import business_logic_facade.OperationFacade;
import business_logic_facade.UserFacade;
import resource.PhysicalResource;
import resource.Resource;
import ui.MainFrame;
import ui.Visibility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewPhysicalResources extends PhysicalResourceObserver implements Visibility {

    private MainFrame mainFrame;
    private OperationFacade operationFacade;

    private AddNewPhysicalResource addNewPhysicalResource;

    private DefaultListModel<PhysicalResource> listModel;
    private JList<PhysicalResource> resourceList;
    private JScrollPane jScrollPane;


    public ViewPhysicalResources(UserFacade currentUser) {
        mainFrame = new MainFrame(currentUser);
        operationFacade = new OperationFacade();
        addNewPhysicalResource = new AddNewPhysicalResource();
        addNewPhysicalResource.attach(this);
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame.getMainFrame().setTitle("مشاهده منابع فیزیکی");
        JPanel addResourcesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;

        JButton addNew = new JButton("افزودن منبع جدید");
        if (mainFrame.getCurrentUser().getCurrentUserPermissions().get(PermissionType.canAddRemResource))
            addNew.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addNewPhysicalResource.setVisible(true);
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
        for (Resource resource : operationFacade.getPhysicalResources())
            listModel.addElement((PhysicalResource) resource);
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
        for (Resource resource : operationFacade.getPhysicalResources())
            listModel.addElement((PhysicalResource) resource);
        resourceList = new JList<>(listModel);
        jScrollPane = new JScrollPane(resourceList);
        mainFrame.getMainFrame().add(jScrollPane, BorderLayout.CENTER);
        mainFrame.getMainFrame().repaint();
        mainFrame.getMainFrame().revalidate();
    }

    public static void main(String[] args) {
        UserFacade userFacade = new UserFacade();
        userFacade.login("871539", "888");
        ViewPhysicalResources viewPhysicalResources = new ViewPhysicalResources(userFacade);
        viewPhysicalResources.setVisible(true);
    }
}