package ui.resource;

import business_logic_facade.OperationFacade;
import business_logic_facade.UserFacade;
import database.PhysicalResourceDAO;
import resource.PhysicalResource;
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

public class SinglePhysicalResource extends MainDialog implements Visibility {

    private JTextField id;
    private JTextField name;
    private JTextField model;
    private JTextField location;
    private JComboBox<Unit> unitsCombo;

    public SinglePhysicalResource(UserFacade userFacade, String rID) {
        prepareGUI(userFacade, rID);
    }

    private void prepareGUI(UserFacade userFacade, String rID) {
        super.getMainDialog().setTitle("منبع فیزیکی");
        JPanel form = new JPanel(new GridBagLayout());

        super.getMainDialog().getContentPane().setLayout(new BorderLayout());
        super.getMainDialog().getContentPane().add(form, BorderLayout.NORTH);

        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        id = new JTextField(PhysicalResourceDAO.getInstance().get(rID).getID());
        formUtility.addLabel("شناسه ", form);
        formUtility.addLastField(id, form);
        id.setEditable(false);

        name = new JTextField(((PhysicalResource) PhysicalResourceDAO.getInstance().get(rID)).getName());
        formUtility.addLabel("نام ", form);
        formUtility.addLastField(name, form);

        model = new JTextField(((PhysicalResource) PhysicalResourceDAO.getInstance().get(rID)).getModel());
        formUtility.addLabel("مدل ", form);
        formUtility.addLastField(model, form);

        location = new JTextField(((PhysicalResource) PhysicalResourceDAO.getInstance().get(rID)).getLocation());
        formUtility.addLabel("محل ", form);
        formUtility.addLastField(location, form);


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
                OperationFacade.getInstance().updatePhysicalResource(
                        id.getText(), name.getText(),
                        model.getText(), location.getText(), (Unit) unitsCombo.getSelectedItem());
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
        SinglePhysicalResource singlePhysicalResource = new SinglePhysicalResource(userFacade, "764488");
        singlePhysicalResource.setVisible(true);
    }
}
