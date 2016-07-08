package ui.project;

import business_logic_facade.ProjectFacade;
import business_logic_facade.UserFacade;
import ui.MainDialog;
import ui.unit.UnitObserver;
import ui.utilities.FormUtility;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class AddNewSystem extends MainDialog {


    private ArrayList<UnitObserver> observers;

    public AddNewSystem(UserFacade currentUser, String pid) {
        observers = new ArrayList<>();
        prepareGUI(currentUser, pid);
    }

    public static void main(String[] args) {
//        UserFacade userFacade = new UserFacade();
//        userFacade.login("478837", "888");
//        AddNewSystem addNewUnit = new AddNewSystem();
//        addNewUnit.setVisible(true);
    }

    private void prepareGUI(UserFacade currentUser, String pid) {
        super.getMainDialog().setTitle("افزودن سیستم جدید");
        JPanel form = new JPanel(new GridBagLayout());

        super.getMainDialog().getContentPane().setLayout(new BorderLayout());
        super.getMainDialog().getContentPane().add(form, BorderLayout.NORTH);

        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        JTextField name = new JTextField(20);
        formUtility.addLabel("نام سیستم ", form);
        formUtility.addLastField(name, form);

        JButton submit = new JButton("افزودن");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProjectFacade.getInstance().addNewSystem(name.getText(), pid);
//                notifyAllObservers();
                setVisible(false);
                new ViewSingleProject(currentUser, pid).setVisible(true);
            }
        });
        JButton cancel = new JButton("انصراف");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        formUtility.addLastField(submit, form);
        formUtility.addLastField(cancel, form);

        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        super.getMainDialog().pack();
        super.getMainDialog().setLocationRelativeTo(null);
    }
}
