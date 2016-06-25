package ui;

import access.PermissionType;
import business_logic_facade.UserFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Created by ppash on 6/24/2016.
 */
public class MainFrame implements Visiblity {

    private JFrame mainFrame;
    private UserFacade currentUser;
    private JMenuBar menuBar;

    public MainFrame(UserFacade currentUser) {
        this.currentUser = currentUser;
        prepareGUI();
    }

    protected JFrame getMainFrame() {
        return mainFrame;
    }

    private void prepareGUI() {
        prepareMenuBar();
        prepareFrame();
    }

    private void prepareFrame() {

        JPanel welcomePanel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;

        JLabel welcomeLabel = new JLabel("به سیستم مدیریت منابع انسانی خوش آمدید.");
        mainFrame.add(welcomeLabel);
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        welcomePanel.add(welcomeLabel, cs);

        mainFrame.getContentPane().add(welcomePanel, BorderLayout.NORTH);

        mainFrame.setSize(400, 400);

        mainFrame.setLocationRelativeTo(null);
    }

    private void prepareMenuBar() {
        Map<PermissionType, Boolean> permissionTypes = currentUser.getCurrentUserPermissions();
        menuBar = new JMenuBar();

        prepareOrganizationMenu(permissionTypes);
        prepareProjectsMenu(permissionTypes);
        prepareResourcesMenu();
        prepareReportsMenu(permissionTypes);
        prepareEstimationsMenu(permissionTypes);
        prepareUserMenu();

        mainFrame = new JFrame("سیستم مدیریت منابع سازمان");
        mainFrame.setJMenuBar(menuBar);
    }

    private void prepareReportsMenu(Map<PermissionType, Boolean> permissionTypes) {
        JMenu reports = new JMenu("گزارش");
        JMenuItem availableResourcesReport = new JMenuItem("گزارش منابع موجود");
        JMenuItem resourceFlowReport = new JMenuItem("گزارش جریان چرخشی منابع");
        JMenuItem neededResourcesReport = new JMenuItem("گزارش منابع موردنیاز");
        if (permissionTypes.get(PermissionType.canGetReport)) {
            reports.add(availableResourcesReport);
            reports.add(neededResourcesReport);
            reports.add(resourceFlowReport);
        }
        if (reports.getItemCount() > 0)
            menuBar.add(reports);
    }

    private void prepareResourcesMenu() {
        JMenu resources = new JMenu("منابع");
        JMenuItem humanResources = new JMenuItem("منابع انسانی");
        humanResources.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                ViewHumanResources viewResources = new ViewHumanResources(currentUser);
                viewResources.setVisible(true);
            }
        });

        JMenuItem physicalResources = new JMenuItem("منابع فیزیکی");
        physicalResources.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                ViewPhysicalResources viewResources = new ViewPhysicalResources(currentUser);
                viewResources.setVisible(true);
            }
        });

        JMenuItem monetaryResources = new JMenuItem("منابع مالی");
        monetaryResources.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                ViewMonetaryResources viewResources = new ViewMonetaryResources(currentUser);
                viewResources.setVisible(true);
            }
        });

        JMenuItem informationResources = new JMenuItem("منابع اطلاعاتی");
        informationResources.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                ViewInformationResources viewResources = new ViewInformationResources(currentUser);
                viewResources.setVisible(true);
            }
        });

        resources.add(humanResources);
        resources.add(physicalResources);
        resources.add(monetaryResources);
        resources.add(informationResources);
        if (resources.getItemCount() > 0)
            menuBar.add(resources);
    }

    private void prepareProjectsMenu(Map<PermissionType, Boolean> permissionTypes) {
        JMenu projects = new JMenu("پروژه‌ها");
        JMenuItem addNewProject = new JMenuItem("افزودن پروژه جدید");
        JMenuItem viewProjects = new JMenuItem("مشاهده پروژه‌ها");

        projects.add(viewProjects);
        if (permissionTypes.get(PermissionType.canAddProject))
            projects.add(addNewProject);
        if (projects.getItemCount() > 0)
            menuBar.add(projects);
    }

    private void prepareOrganizationMenu(Map<PermissionType, Boolean> permissionTypes) {
        JMenu organization = new JMenu("سازمان");
        //JMenuItem addNewUnit = new JMenuItem("افزودن واحد جدید");
        JMenuItem viewUnits = new JMenuItem("مشاهده واحدها");
        viewUnits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                ViewUnits viewUnits = new ViewUnits(currentUser);
                viewUnits.setVisible(true);
            }
        });


        JMenuItem userAccessLevels = new JMenuItem("سطح دسترسی کاربران");
        JMenuItem submitNewReq = new JMenuItem("ثبت نیازمندی جدید");
        organization.add(viewUnits);
//        if (permissionTypes.get(PermissionType.canAddUnit))
//            organization.add(addNewUnit);
        if (permissionTypes.get(PermissionType.canChangePermission))
            organization.add(userAccessLevels);
        if (permissionTypes.get(PermissionType.canAddRemReq))
            organization.add(submitNewReq);
        if (organization.getItemCount() > 0)
            menuBar.add(organization);
    }

    private void prepareEstimationsMenu(Map<PermissionType, Boolean> permissionTypes) {
        JMenu estimations = new JMenu("پیش‌بینی");
        JMenuItem resourceEstimation = new JMenuItem("تخمین منابع");
        JMenuItem findEssentialReqs = new JMenuItem("یافتن نیازمندی‌های ضروری");
        if (permissionTypes.get(PermissionType.canSearch)) {
            estimations.add(resourceEstimation);
            estimations.add(findEssentialReqs);
        }
        if (estimations.getItemCount() > 0)
            menuBar.add(estimations);
    }

    private void prepareUserMenu() {
        JMenu user = new JMenu("کاربری");
        JMenuItem help = new JMenuItem("راهنما");
        JMenuItem logout = new JMenuItem("خروج");
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentUser.logout(currentUser.getID());
                mainFrame.setVisible(false);
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            }
        });
        user.add(help);
        user.add(logout);
        menuBar.add(user);
    }

    public UserFacade getCurrentUser() {
        return currentUser;
    }

    @Override
    public void setVisible(boolean visible) {
        mainFrame.setVisible(true);
    }
}
