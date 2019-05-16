/*
 *   Class name:     StampButton
 *   Contributor(s): Jeremy Maxey-Vesperman
 *   Modified:       May 15th, 2019
 *   Package:        edu.kettering.tools.stamp
 *   Purpose:        JButton wrapper class for Stamp tool
 * */

package edu.kettering.tools.stamp;

import javax.swing.*;

class StampButton extends JButton {

    StampButton() {
        this.setText("Stamp");
        this.setActionCommand("Stamp");
        this.setToolTipText("Draw image as stamp");
    }
}
