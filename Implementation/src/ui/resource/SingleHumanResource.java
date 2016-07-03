package ui.resource;

import access.AccessLevelType;
import business_logic_facade.OperationFacade;
import business_logic_facade.UserFacade;
import database.HumanResourceDAO;
import project.Project;
import resource.ConfirmStatus;
import resource.HumanResource;
import ui.MainDialog;
import ui.Visibility;
import ui.utilities.FormUtility;
import unit.Unit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SingleHumanResource extends MainDialog implements Visibility {

    private JTextField id;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField expertise;
    private JComboBox<Unit> unitsCombo;
    private JList<Project> projects;
    private JComboBox<AccessLevelType> accessLevelCombo;
    private JComboBox<ConfirmStatus> confirmStatus;

    public SingleHumanResource(UserFacade userFacade, String rID) {
        prepareGUI(userFacade, rID);

    }

    private void prepareGUI(UserFacade userFacade, String rID) {
        super.getMainDialog().setTitle("منبع انسانی");
        JPanel form = new JPanel(new GridBagLayout());

        super.getMainDialog().getContentPane().setLayout(new BorderLayout());
        super.getMainDialog().getContentPane().add(form, BorderLayout.NORTH);

        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        id = new JTextField(HumanResourceDAO.getInstance().get(rID).getID());
        formUtility.addLabel("کد پرسنلی ", form);
        formUtility.addLastField(id, form);
        id.setEditable(false);

        ArrayList<ConfirmStatus> stats = userFacade.getAllConfirmStatusTypes();
        confirmStatus = new JComboBox<>();
        for (ConfirmStatus type : stats)
            confirmStatus.addItem(type);
        confirmStatus.setSelectedItem(((HumanResource) HumanResourceDAO.getInstance().get(rID)).getConfirmStatus());
        System.out.println("::" + ((HumanResource) HumanResourceDAO.getInstance().get(rID)).getConfirmStatus().toString());
        formUtility.addLabel("وضعیت ", form);
        formUtility.addLastField(confirmStatus, form);


        firstName = new JTextField(((HumanResource) HumanResourceDAO.getInstance().get(rID)).getFirstName());
        formUtility.addLabel("نام ", form);
        formUtility.addLastField(firstName, form);

        lastName = new JTextField(((HumanResource) HumanResourceDAO.getInstance().get(rID)).getLastName());
        formUtility.addLabel("نام خانوادگی ", form);
        formUtility.addLastField(lastName, form);

        expertise = new JTextField(((HumanResource) HumanResourceDAO.getInstance().get(rID)).getExpertise());
        formUtility.addLabel("تخصص‌ها ", form);
        formUtility.addLastField(expertise, form);

        // JTextField ID

        ArrayList<AccessLevelType> accessLevels = userFacade.getAllAccessLevelTypes();
        accessLevelCombo = new JComboBox<>();
        for (AccessLevelType type : accessLevels)
            accessLevelCombo.addItem(type);
        accessLevelCombo.setSelectedItem(((HumanResource) HumanResourceDAO.getInstance().get(rID)).getAccessLevel().getAccessLevelType());
        formUtility.addLabel("سطح دسترسی ", form);
        formUtility.addLastField(accessLevelCombo, form);


        // TODO
        ArrayList<Unit> units = OperationFacade.getInstance().getUnits();
        unitsCombo = new JComboBox<>();
        for (Unit unit : units)
            unitsCombo.addItem(unit);
// TODO        unitsCombo.setSelectedItem(OperationFacade.getInstance().getResourceUnit(rID));
        formUtility.addLabel("واحد ", form);
        formUtility.addLastField(unitsCombo, form);


        JButton submit = new JButton("ذخیره تغییرات");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OperationFacade.getInstance().updateHumanResource(
                        id.getText(), firstName.getText(),
                        lastName.getText(), expertise.getText(),
                        (AccessLevelType) accessLevelCombo.getSelectedItem(), (ConfirmStatus) confirmStatus.getSelectedItem(), (Unit) unitsCombo.getSelectedItem());
                setVisible(false);
            }
        });
        JButton cancel = new JButton("انصراف و بازگشت");
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
        UserFacade userFacade = new UserFacade();
        userFacade.login("100824", "888");
        SingleHumanResource singleHumanResource = new SingleHumanResource(userFacade, "100824");
        singleHumanResource.setVisible(true);
    }

}