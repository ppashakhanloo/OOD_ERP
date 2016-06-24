package ui;

import business_logic_facade.ResourceFacade;
import business_logic_facade.UserFacade;
import unit.Unit;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by ppash on 6/24/2016.
 */
public class AddNewHumanResource extends MainFrame {
    ResourceFacade resourceFacade;

    public AddNewHumanResource(UserFacade currentUser) {
        super(currentUser);
        resourceFacade = new ResourceFacade();
        prepareGUI();
    }

    private void prepareGUI() {
        super.getMainFrame().setTitle("مشاهده منابع انسانی");
        JPanel addResourcesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;

        // JTextField ID
        JTextField firstName = new JTextField(30);
        JTextField lastName = new JTextField(30);
        JTextField expertise = new JTextField(30);
        JTextField password = new JTextField(30);

        // TODO
        ArrayList<Unit> tempUnits = new ArrayList<>();
        tempUnits.add(new Unit());
        tempUnits.add(new Unit());
        JComboBox<Unit> units = new JComboBox<>();
        units.addItem(tempUnits.get(0));
        units.addItem(tempUnits.get(1));

    }
}
