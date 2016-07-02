package ui.project;

import business_logic_facade.OperationFacade;
import business_logic_facade.ProjectFacade;
import ui.MainDialog;
import ui.utilities.FormUtility;
import unit.Unit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

class AddNewProject extends MainDialog {

    private ArrayList<ProjectObserver> observers;

    AddNewProject() {
        observers = new ArrayList<>();
        prepareGUI();
    }

    public void attach(ProjectObserver observer) {
        observers.add(observer);
    }

    private void notifyAllObservers() {
        observers.forEach(ProjectObserver::update);
    }

    private void prepareGUI() {
        super.getMainDialog().setTitle("افزودن پروژه جدید");
        JPanel form = new JPanel(new GridBagLayout());

        super.getMainDialog().getContentPane().setLayout(new BorderLayout());
        super.getMainDialog().getContentPane().add(form, BorderLayout.NORTH);

        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        JTextField name = new JTextField(20);
        formUtility.addLabel("نام پروژه ", form);
        formUtility.addLastField(name, form);

        //////////////////////////////////////////////////////
        DefaultListModel<Unit> listModel = new DefaultListModel<>();
        OperationFacade.getInstance().getUnits().forEach(listModel::addElement);
        JList<Unit> unitList = new JList<>(listModel);
        unitList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        unitList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        getMainDialog().add(new JScrollPane(unitList), BorderLayout.CENTER);
        formUtility.addLabel("واحدهای درگیر ", form);
        formUtility.addLastField(unitList, form);
        //////////////////////////////////////////////////////

        JButton submit = new JButton("افزودن");
        submit.addActionListener(e -> {
            ArrayList<String> involvedUnits = new ArrayList<>();
            for (Unit unit : unitList.getSelectedValuesList()) involvedUnits.add(unit.getID());
            ProjectFacade.getInstance().addNewProject(name.getText(), involvedUnits);
            notifyAllObservers();
            setVisible(false);
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
        AddNewProject addNewProject = new AddNewProject();
        addNewProject.setVisible(true);
    }
}
