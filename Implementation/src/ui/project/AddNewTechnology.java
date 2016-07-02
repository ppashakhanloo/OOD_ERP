package ui.project;

import business_logic_facade.ProjectFacade;
import project.Project;
import ui.MainDialog;
import ui.utilities.FormUtility;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class AddNewTechnology extends MainDialog {

    private ArrayList<ProjectObserver> observers;

    AddNewTechnology(Project project) {
        observers = new ArrayList<>();
        prepareGUI(project);
    }

    public void attach(ProjectObserver observer) {
        observers.add(observer);
    }

    private void notifyAllObservers() {
        observers.forEach(ProjectObserver::update);
    }

    private void prepareGUI(Project project) {
        super.getMainDialog().setTitle("افزودن تکنولوژی جدید");
        JPanel form = new JPanel(new GridBagLayout());

        super.getMainDialog().getContentPane().setLayout(new BorderLayout());
        super.getMainDialog().getContentPane().add(form, BorderLayout.NORTH);

        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        JTextField name = new JTextField(20);
        formUtility.addLabel("نام ", form);
        formUtility.addLastField(name, form);

        JTextArea reason = new JTextArea();
        formUtility.addLabel("هدف به‌کارگیری ", form);
        formUtility.addLastField(new JScrollPane(reason), form);


        JButton submit = new JButton("افزودن");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProjectFacade.getInstance().addNewTechnology(name.getText(), reason.getText(), project.getID());
                setVisible(false);
                notifyAllObservers();
            }
        });
        JButton cancel = new JButton("صرف‌نظر");
        formUtility.addLastField(submit, form);
        formUtility.addLastField(cancel, form);

        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        super.getMainDialog().pack();
        super.getMainDialog().setLocationRelativeTo(null);
    }

    @Override
    public void setVisible(boolean visible) {
        getMainDialog().setVisible(visible);
    }

    public static void main(String[] args) {
//        UserFacade userFacade = new UserFacade();
//        userFacade.login("478837", "888");
//        AddNewTechnology addNewTechnology = new AddNewTechnology();
//        addNewTechnology.setVisible(true);
    }
}
