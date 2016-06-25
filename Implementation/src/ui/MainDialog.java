package ui;

import javax.swing.*;

class MainDialog implements Visibility {
    private JDialog mainDialog;

    MainDialog() {
        mainDialog = new JDialog();
    }

    @Override
    public void setVisible(boolean visible) {
        mainDialog.setVisible(visible);
    }

    JDialog getMainDialog() {
        return mainDialog;
    }
}
