package ui;

import business_logic_facade.ResourceFacade;
import business_logic_facade.UserFacade;
import resource.MonetaryResource;
import resource.Resource;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ppash on 6/24/2016.
 */
public class ViewMonetaryResources extends MainFrame {

    ResourceFacade resourceFacade;

    public ViewMonetaryResources(UserFacade currentUser) {
        super(currentUser);
        resourceFacade = new ResourceFacade();
        prepareGUI();
    }

    private void prepareGUI() {
        super.getMainFrame().setTitle("مشاهده منابع مالی");
        JPanel addResourcesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        JButton addNew = new JButton("افزودن منبع جدید");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        addResourcesPanel.add(addNew, cs);
        super.getMainFrame().getContentPane().removeAll();
        super.getMainFrame().getContentPane().add(addResourcesPanel, BorderLayout.NORTH);

        ////////////////////
        DefaultListModel<MonetaryResource> listModel = new DefaultListModel<>();
        for (Resource resource : resourceFacade.getMonetaryResources())
            listModel.addElement((MonetaryResource) resource);
        JList<MonetaryResource> resourceList = new JList<>(listModel);
        super.getMainFrame().add(new JScrollPane(resourceList), BorderLayout.CENTER);
        ////////////////////

        super.getMainFrame().setResizable(true);
        super.getMainFrame().pack();
    }

    public static void main(String[] args) {
        UserFacade userFacade = new UserFacade();
        userFacade.login("478837", "888");
        ViewMonetaryResources viewMonetaryResources = new ViewMonetaryResources(userFacade);
        viewMonetaryResources.setVisible(true);
    }
}