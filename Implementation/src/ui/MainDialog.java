package ui;

import javax.swing.*;

/**
 * Created by ppash on 6/24/2016.
 */
public class MainDialog implements Visiblity {
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
