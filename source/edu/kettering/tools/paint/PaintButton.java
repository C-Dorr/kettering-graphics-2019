/*
 *   Class name:     PaintButton
 *   Contributor(s): Eric Clark
 *   Modified:       June 4th, 2019
 *   Package:        edu.kettering.tools.paint
 *   Purpose:        JButton wrapper class for paint tool
 * */
package edu.kettering.tools.paint;

import javax.swing.*;

public class PaintButton extends JButton
{
    PaintButton()
    {
        this.setText("Paint");
        this.setActionCommand("Paint");
        this.setToolTipText("Draw by clicking and dragging the mouse");
    }
}
