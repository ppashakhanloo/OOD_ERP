package ui;

import javax.swing.*;

public class MainDialog implements Visibility {
    private JDialog mainDialog;

    public MainDialog() {
        mainDialog = new JDialog();
    }

    @Override
    public void setVisible(boolean visible) {
        mainDialog.setVisible(visible);
    }

    public JDialog getMainDialog() {
        return mainDialog;
    }
}
