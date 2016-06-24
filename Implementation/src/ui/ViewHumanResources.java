package ui;

import business_logic_facade.ResourceFacade;
import business_logic_facade.UserFacade;
import resource.HumanResource;
import resource.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ppash on 6/24/2016.
 */
public class ViewHumanResources  extends HumanResourceObserver implements Visiblity {

    private MainFrame mainFrame;
    private ResourceFacade resourceFacade;

    private AddNewHumanResource addNewHumanResource;

    private DefaultListModel<HumanResource> listModel;
    private JList<HumanResource> resourceList;
    private JScrollPane jScrollPane;


    public ViewHumanResources(UserFacade currentUser) {
        mainFrame = new MainFrame(currentUser);
        resourceFacade = new ResourceFacade();
        addNewHumanResource = new AddNewHumanResource();
        addNewHumanResource.attach(this);
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame.getMainFrame().setTitle("مشاهده منابع انسانی");
        JPanel addResourcesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        JButton addNew = new JButton("افزودن منبع جدید");
        addNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewHumanResource.setVisible(true);
            }
        });
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        addResourcesPanel.add(addNew, cs);
        mainFrame.getMainFrame().getContentPane().add(addResourcesPanel, BorderLayout.NORTH);

        ////////////////////
        listModel = new DefaultListModel<>();
        for (Resource resource : resourceFacade.getHumanResources())
            listModel.addElement((HumanResource) resource);
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
    public void update(){
        mainFrame.getMainFrame().remove(jScrollPane);
        listModel = new DefaultListModel<>();
        for (Resource resource : resourceFacade.getHumanResources())
            listModel.addElement((HumanResource) resource);
        resourceList = new JList<>(listModel);
        jScrollPane = new JScrollPane(resourceList);
        mainFrame.getMainFrame().add(jScrollPane, BorderLayout.CENTER);
        mainFrame.getMainFrame().repaint();
        mainFrame.getMainFrame().revalidate();
    }

    public static void main(String[] args) {
        UserFacade userFacade = new UserFacade();
        userFacade.login("871539", "888");
        ViewHumanResources viewHumanResources = new ViewHumanResources(userFacade);
        viewHumanResources.setVisible(true);
    }
}