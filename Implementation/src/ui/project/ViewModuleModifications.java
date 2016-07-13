package ui.project;

import business_logic_facade.ProjectFacade;
import business_logic_facade.UserFacade;
import project.Module;
import project.ModuleModification;
import ui.MainFrame;
import ui.Visibility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewModuleModifications implements Visibility {

    private MainFrame mainFrame;

    private DefaultListModel<ModuleModification> listModel;
    private JList<ModuleModification> moduleModificationList;

    private UserFacade userFacade;

    public ViewModuleModifications(UserFacade userFacade, String pid) {
        mainFrame = new MainFrame(userFacade);
        this.userFacade = userFacade;
        prepareGUI(pid);
    }

    public static void main(String[] args) {
        UserFacade userFacade = new UserFacade();
        userFacade.login("1", "admin");
        ViewModuleModifications viewModuleModifications = new ViewModuleModifications(userFacade, "1");
        viewModuleModifications.setVisible(true);
    }

    private void prepareGUI(String pid) {
        mainFrame.getMainFrame().setTitle("مشاهده تغییرات ماژول");
        mainFrame.getMainFrame().getContentPane().remove(0);
        mainFrame.getMainFrame().setLayout(new GridLayout(2, 1));

        JPanel modulePanel = new JPanel(new FlowLayout());
        ArrayList<Module> modules = new ArrayList<Module>();
        for (project.System system : ProjectFacade.getInstance().getProject(pid).getSystems())
            modules.addAll(system.getModules());
        JComboBox<Module> moduleJComboBox = new JComboBox<>();
        modules.forEach(moduleJComboBox::addItem);
        moduleJComboBox.setSelectedIndex(0);
        modulePanel.add(moduleJComboBox);
        mainFrame.getMainFrame().add(modulePanel);
        ////////////////////
        JPanel modificationsPanel = new JPanel(new GridLayout(1, 1));
        listModel = new DefaultListModel<>();
        for (ModuleModification moduleModification : ((Module) moduleJComboBox.getSelectedItem()).getModuleModifications())
            listModel.addElement((ModuleModification) moduleModification);
        moduleModificationList = new JList<>(listModel);

        modificationsPanel.add(new JScrollPane(moduleModificationList));
        mainFrame.getMainFrame().add(modificationsPanel);

        moduleJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificationsPanel.removeAll();
                listModel = new DefaultListModel<>();
                for (ModuleModification moduleModification : ((Module) moduleJComboBox.getSelectedItem()).getModuleModifications())
                    listModel.addElement(moduleModification);
                moduleModificationList = new JList<>(listModel);
                modificationsPanel.add(new JScrollPane(moduleModificationList));
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
}