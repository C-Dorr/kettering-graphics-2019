package edu.kettering.tools.stamp;

import javax.swing.*;
import java.awt.event.*;

public class StampButton extends JButton {

    public StampButton() {
        this.setText("Select Stamp");
        this.setActionCommand("PickStamp");
        this.setToolTipText("Selects an image for stamp tool");
    }
}
