package ui;

import access.PermissionType;
import business_logic_facade.UserFacade;
import ui.project.ViewProjects;
import ui.report.AvailableResourcesReport;
import ui.report.RequiredResourcesReport;
import ui.report.UsageFlowReport;
import ui.resource.ViewHumanResources;
import ui.resource.ViewInformationResources;
import ui.resource.ViewMonetaryResources;
import ui.resource.ViewPhysicalResources;
import ui.search.EstimateRequirements;
import ui.search.EstimateResources;
import ui.unit.ViewUnits;
import ui.user.LoginForm;
import ui.user.UserAccessLevels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class MainFrame implements Visibility {

    private JFrame mainFrame;
    private UserFacade currentUser;
    private JMenuBar menuBar;

    public MainFrame(UserFacade currentUser) {
        this.currentUser = currentUser;
        prepareGUI();
    }

    public JFrame getMainFrame() {
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

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation(dim.width / 2 - mainFrame.getSize().width / 2, dim.height / 2 - mainFrame.getSize().height / 2);
    }

    private void prepareMenuBar() {
        Map<PermissionType, Boolean> permissionTypes = currentUser.getCurrentUserPermissions();
        menuBar = new JMenuBar();

        prepareOrganizationMenu();
        prepareProjectsMenu();
        prepareResourcesMenu();
        prepareReportsMenu(permissionTypes);
        prepareEstimationsMenu(permissionTypes);
        prepareUserMenu(permissionTypes);

        mainFrame = new JFrame("سیستم مدیریت منابع سازمان");
        mainFrame.setJMenuBar(menuBar);
    }

    private void prepareReportsMenu(Map<PermissionType, Boolean> permissionTypes) {
        JMenu reports = new JMenu("گزارش");
        JMenuItem availableResourcesReport = new JMenuItem("گزارش منابع موجود");
        availableResourcesReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AvailableResourcesReport(currentUser).setVisible(true);
            }
        });
        JMenuItem resourceFlowReport = new JMenuItem("گزارش جریان چرخشی منابع");
        resourceFlowReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UsageFlowReport(currentUser).setVisible(true);
            }
        });
        JMenuItem neededResourcesReport = new JMenuItem("گزارش منابع موردنیاز");
        neededResourcesReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RequiredResourcesReport(currentUser).setVisible(true);
            }
        });
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
                ViewHumanResources viewResources = new ViewHumanResources(currentUser);
                viewResources.setVisible(true);
            }
        });

        JMenuItem physicalResources = new JMenuItem("منابع فیزیکی");
        physicalResources.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewPhysicalResources viewResources = new ViewPhysicalResources(currentUser);
                viewResources.setVisible(true);
            }
        });

        JMenuItem monetaryResources = new JMenuItem("منابع مالی");
        monetaryResources.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewMonetaryResources viewResources = new ViewMonetaryResources(currentUser);
                viewResources.setVisible(true);
            }
        });

        JMenuItem informationResources = new JMenuItem("منابع اطلاعاتی");
        informationResources.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

    private void prepareProjectsMenu() {
        JMenu projects = new JMenu("پروژه‌ها");
        JMenuItem viewProjects = new JMenuItem("مشاهده پروژه‌ها");
        viewProjects.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewProjects viewProjects = new ViewProjects(currentUser);
                viewProjects.setVisible(true);
            }
        });

        projects.add(viewProjects);
        if (projects.getItemCount() > 0)
            menuBar.add(projects);
    }

    private void prepareOrganizationMenu() {
        JMenu organization = new JMenu("سازمان");
        JMenuItem viewUnits = new JMenuItem("مشاهده واحدها");
        viewUnits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewUnits viewUnits = new ViewUnits(currentUser);
                viewUnits.setVisible(true);
            }
        });

        organization.add(viewUnits);

        if (organization.getItemCount() > 0)
            menuBar.add(organization);
    }

    private void prepareEstimationsMenu(Map<PermissionType, Boolean> permissionTypes) {
        JMenu estimations = new JMenu("پیش‌بینی");
        JMenuItem resourceEstimation = new JMenuItem("تخمین منابع");
        resourceEstimation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EstimateResources(currentUser).setVisible(true);
            }
        });
        JMenuItem findEssentialReqs = new JMenuItem("یافتن نیازمندی‌های ضروری");
        findEssentialReqs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EstimateRequirements(currentUser).setVisible(true);
            }
        });
        if (permissionTypes.get(PermissionType.canSearch)) {
            estimations.add(resourceEstimation);
            estimations.add(findEssentialReqs);
        }
        if (estimations.getItemCount() > 0)
            menuBar.add(estimations);
    }

    private void prepareUserMenu(Map<PermissionType, Boolean> permissionTypes) {
        JMenu user = new JMenu("کاربری");
        JMenuItem userAccessLevels = new JMenuItem("سطح دسترسی کاربران");
        userAccessLevels.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserAccessLevels(currentUser).setVisible(true);
            }
        });
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
        if (permissionTypes.get(PermissionType.canChangePermission))
            user.add(userAccessLevels);
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
