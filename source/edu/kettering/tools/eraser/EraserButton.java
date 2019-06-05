package edu.kettering.tools.eraser;

import javax.swing.*;

class EraserButton extends JButton {

    EraserButton() {
        this.setText("Eraser");
        this.setActionCommand("Eraser");
        this.setToolTipText("Clear away those happy accidents");
    }
}
