package ui;

import business_logic_facade.UserFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ppash on 6/24/2016.
 */
public class LoginForm implements Visiblity {
    private JFrame mainFrame;
    private JMenuBar menuBar;
    private JMenu help;
    private JMenu register;
    private JTextField ID;
    private JTextField password;
    private JLabel IDLabel;
    private JLabel passwordLabel;
    private JButton login;
    private JLabel errorLabel;

    private UserFacade userFacade;

    public LoginForm() {
        userFacade = new UserFacade();
        prepareGUI();
    }

    @Override
    public void setVisible(boolean visible) {
        mainFrame.setVisible(true);
    }

    private void prepareGUI() {
        prepareMenuBar();
        prepareFrame();
        setVisible(true);
    }

    private void prepareFrame() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;

        ID = new JTextField(20);
        password = new JTextField(20);
        passwordLabel = new JLabel("رمز");
        IDLabel = new JLabel("شناسه");
        passwordLabel.setLabelFor(password);
        IDLabel.setLabelFor(ID);
        login = new JButton("ورود");


        login.addActionListener(new ActionListener() {
            MainFrame userFrame;

            public void actionPerformed(ActionEvent e) {
                boolean loginStatus;
                if (ID.getText() != null && password.getText() != null) {
                    loginStatus = userFacade.login(ID.getText(), password.getText());
                    if (loginStatus) {
                        userFrame = new MainFrame(userFacade);
                        userFrame.setVisible(true);
                        mainFrame.setVisible(false);
                    } else {
                        errorLabel.setText("خطایی در ورود رخ داده است.");
                    }
                }
            }
        });

        mainFrame.add(ID);
        mainFrame.add(password);
        mainFrame.add(IDLabel);
        mainFrame.add(passwordLabel);
        mainFrame.add(login);

        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        loginPanel.add(IDLabel, cs);

        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        loginPanel.add(ID, cs);

        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        loginPanel.add(passwordLabel, cs);

        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        loginPanel.add(password, cs);

        JPanel bp = new JPanel();
        bp.add(login);


        JPanel ep = new JPanel();
        errorLabel = new JLabel("به سیستم مدیریت منابع سازمان خوش آمدید.");
        ep.add(errorLabel);


        mainFrame.getContentPane().add(ep, BorderLayout.NORTH);
        mainFrame.getContentPane().add(loginPanel, BorderLayout.CENTER);
        mainFrame.getContentPane().add(bp, BorderLayout.SOUTH);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.pack();
        mainFrame.setResizable(false);
    }

    private void prepareMenuBar() {
        mainFrame = new JFrame("ورود");
        menuBar = new JMenuBar();
        help = new JMenu("راهنما");
        register = new JMenu("ثبت‌نام");
        menuBar.add(help);
        menuBar.add(register);
        mainFrame.setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm();
    }
}
