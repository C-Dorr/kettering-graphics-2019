/*
 *   Class name:     KaleidoscopeButton
 *   Contributor(s): Mitchell Athayde
 *   Modified:       May 29th, 2019
 *   Package:        edu.kettering.tools.kaleidoscope
 *   Purpose:        JButton wrapper class for kaleidoscope tool
 * */

package edu.kettering.tools.caligraphy;

import javax.swing.*;

class CaligraphyButton extends JButton {

    CaligraphyButton() {
        this.setText("Caligraphy Pen");
        this.setActionCommand("Caligraphy");
        this.setToolTipText("Draw as a caligraphy image");
    }
}
