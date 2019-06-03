/*
 *   Class name:     KaleidoscopeButton
 *   Contributor(s): Mitchell Athayde
 *   Modified:       May 29th, 2019
 *   Package:        edu.kettering.tools.kaleidoscope
 *   Purpose:        JButton wrapper class for kaleidoscope tool
 * */

package edu.kettering.tools.kaleidoscope;

import javax.swing.*;

class KaleidoscopeButton extends JButton {

    KaleidoscopeButton() {
        this.setText("Kaleidoscope");
        this.setActionCommand("Kaleidoscope");
        this.setToolTipText("Draw as a kaleidoscope image");
    }
}
