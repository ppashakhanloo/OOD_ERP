package ui.resource;

import business_logic_facade.OperationFacade;
import business_logic_facade.UserFacade;
import database.InformationResourceDAO;
import resource.InformationResource;
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

public class SingleInformationResource extends MainDialog implements Visibility {

    private JTextField id;
    private JTextField name;
    private JTextArea description;
    private JComboBox<Unit> unitsCombo;

    public SingleInformationResource(UserFacade userFacade, String rID) {
        prepareGUI(userFacade, rID);

    }

    public static void main(String[] args) {
        UserFacade userFacade = new UserFacade();
        userFacade.login("100824", "888");
        SingleInformationResource singleInformationResource = new SingleInformationResource(userFacade, "476220");
        singleInformationResource.setVisible(true);
    }

    private void prepareGUI(UserFacade userFacade, String rID) {
        super.getMainDialog().setTitle("منبع اطلاعاتی");
        JPanel form = new JPanel(new GridBagLayout());

        super.getMainDialog().getContentPane().setLayout(new BorderLayout());
        super.getMainDialog().getContentPane().add(form, BorderLayout.NORTH);

        form.setLayout(new GridBagLayout());
        FormUtility formUtility = new FormUtility();

        id = new JTextField(InformationResourceDAO.getInstance().get(rID).getID());
        formUtility.addLabel("شناسه ", form);
        formUtility.addLastField(id, form);
        id.setEditable(false);

        name = new JTextField(((InformationResource) InformationResourceDAO.getInstance().get(rID)).getName());
        formUtility.addLabel("نام ", form);
        formUtility.addLastField(name, form);

        description = new JTextArea(((InformationResource) InformationResourceDAO.getInstance().get(rID)).getDescription());
        formUtility.addLabel("شرح ", form);
        formUtility.addLastField(description, form);

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
                if (OperationFacade.getInstance().updateInformationResource(
                        id.getText(), name.getText(),
                        description.getText()))
                    setVisible(false);
                else
                    JOptionPane.showMessageDialog(null,
                            "مقادیر ورودی را بررسی کنید.",
                            "خطا",
                            JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton cancel = new JButton("انصراف و بازگشت");
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

    @Override
    public void setVisible(boolean visible) {
        getMainDialog().setVisible(visible);
    }

}
