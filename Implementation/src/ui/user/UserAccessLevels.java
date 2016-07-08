package ui.user;


import access.AccessLevelType;
import access.PermissionType;
import business_logic_facade.UserFacade;
import ui.MainDialog;
import ui.Visibility;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class UserAccessLevels implements Visibility {

    private MainDialog mainDialog;

    public UserAccessLevels(UserFacade currentUser) {
        mainDialog = new MainDialog();
        prepareGUI(currentUser);
    }

    public static void main(String[] args) {
        UserFacade userFacade = new UserFacade();
        userFacade.login("100824", "888");
        new UserAccessLevels(userFacade).setVisible(true);
    }

    private void prepareGUI(UserFacade currentUser) {
        mainDialog.getMainDialog().setTitle("مشاهده عملیات مجاز سطوح دسترسی");
        mainDialog.getMainDialog().getContentPane().removeAll();
        mainDialog.getMainDialog().setLayout(new GridLayout(4, 1));
        JPanel[] forms = new JPanel[currentUser.getAllAccessLevelTypes().size()];
        int i = 0;
        for (AccessLevelType accessLevelType : currentUser.getAllAccessLevelTypes()) {
            forms[i] = new JPanel();
            forms[i].setBorder(new TitledBorder(accessLevelType.toString()));
            forms[i].setLayout(new GridLayout(3, 1));
            Map<PermissionType, Boolean> permissions = currentUser.getPermissions(accessLevelType);
            for (PermissionType permissionType : currentUser.getPermissions(currentUser.getAllAccessLevelTypes().get(0)).keySet()) {
                JCheckBox box = new JCheckBox(permissionType.toString());
                if (!currentUser.getCurrentUserPermissions().get(PermissionType.canChangePermission))
                    box.setEnabled(false);
                box.setSelected(permissions.get(permissionType));
                forms[i].add(box);
            }
            mainDialog.getMainDialog().add(forms[i]);
            i++;
        }

        JPanel form = new JPanel(new FlowLayout());
        JButton save = new JButton("ذخیره");

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < currentUser.getAllAccessLevelTypes().size(); i++) {
                    HashMap<PermissionType, Boolean> permissions = new HashMap<>();
                    for (Component comp : forms[i].getComponents()) {
                        if (comp instanceof JCheckBox) {
                            permissions.put(PermissionType.valueOf(((JCheckBox) comp).getText()), ((JCheckBox) comp).isSelected());
                        }
                    }
                    currentUser.setPermissions(AccessLevelType.valueOf(((TitledBorder) forms[i].getBorder()).getTitle()), permissions);
                }
            }
        });


        JButton cancel = new JButton("انصراف");
        if (!currentUser.getCurrentUserPermissions().get(PermissionType.canChangePermission))
            cancel.setText("بازگشت");

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        if (currentUser.getCurrentUserPermissions().get(PermissionType.canChangePermission))
            form.add(save);
        form.add(cancel);
        mainDialog.getMainDialog().add(form);
        mainDialog.getMainDialog().pack();
        mainDialog.getMainDialog().setLocationRelativeTo(null);
    }

    @Override
    public void setVisible(boolean visible) {
        mainDialog.getMainDialog().setVisible(true);
    }
}
