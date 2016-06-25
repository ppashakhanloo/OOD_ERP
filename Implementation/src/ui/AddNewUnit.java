package ui;

import business_logic_facade.ResourceFacade;
import ui.utilities.FormUtility;
import ui.utilities.UnitObserver;
import unit.Unit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by ppash on 6/24/2016.
 */
public class AddNewUnit extends MainDialog {

    private ResourceFacade resourceFacade;

    ArrayList<UnitObserver> observers;

    public AddNewUnit() {
        resourceFacade = new ResourceFacade();
        observers = new ArrayList<>();
        prepareGUI();
    }

    public void attach(UnitObserver observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        observers.forEach(UnitObserver::update);
    }

    private void prepareGUI() {
        super.getMainDialog().setTitle("افزودن واحد جدید");
        JPanel form = new JPanel(new GridBagLayout());

        super.getMainDialog().getContentPane().setLayout(new BorderLayout());
        super.getMainDialog().getContentPane().add(form, BorderLayout.NORTH);

        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        JTextField name = new JTextField(20);
        formUtility.addLabel("نام واحد ", form);
        formUtility.addLastField(name, form);

        JButton submit = new JButton("افزودن");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resourceFacade.addNewUnit(name.getText());
                notifyAllObservers();
                setVisible(false);
            }
        });
        JButton cancel = new JButton("صرف‌نظر");
        formUtility.addLastField(submit, form);
        formUtility.addLastField(cancel, form);

        form.setBorder(new EmptyBorder(10, 10, 10, 10));
        super.getMainDialog().pack();
    }

    public static void main(String[] args) {
//        UserFacade userFacade = new UserFacade();
//        userFacade.login("478837", "888");
        AddNewUnit addNewUnit = new AddNewUnit();
        addNewUnit.setVisible(true);
    }
}
