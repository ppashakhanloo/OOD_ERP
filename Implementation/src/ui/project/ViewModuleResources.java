package ui.project;

import business_logic_facade.ProjectFacade;
import business_logic_facade.UserFacade;
import resource.Resource;
import ui.MainFrame;
import ui.Visibility;

import javax.swing.*;
import java.awt.*;


public class ViewModuleResources implements Visibility {
    private MainFrame mainFrame;


    public ViewModuleResources(UserFacade userFacade, String mid) {
        mainFrame = new MainFrame(userFacade);
        prepareGUI(mid);
    }

    private void prepareGUI(String mid) {
        mainFrame.getMainFrame().setTitle("مشاهده منابع ماژول");
        DefaultListModel<Resource> listModel = new DefaultListModel<>();
        for (Resource r : ProjectFacade.getInstance().getModuleResources(mid)) {
            listModel.addElement(r);
        }
        mainFrame.getMainFrame().add(new JScrollPane(new JList<>(listModel)), BorderLayout.CENTER);
        mainFrame.getMainFrame().pack();
        mainFrame.getMainFrame().setLocationRelativeTo(null);
    }

    @Override
    public void setVisible(boolean visible) {
        mainFrame.getMainFrame().setVisible(visible);
    }
}
