/*
 *   Class name:        Tool (Abstract)
 *   Contributor(s):    Jeremy Maxey-Vesperman, Christopher Dorr, Zachary Goldasich
 *   Modified:          May 16th, 2019
 *   Package:           edu.kettering.tools
 *   Purpose:           Abstract class to inherit from when implementing a tool.
 *                      Includes default implementation of some of the interface.
 * */

package edu.kettering.tools;

import edu.kettering.client.DigitalCanvasState;

import javax.swing.*;
import java.awt.*;

public abstract class Tool implements ToolInterface {
    // Default colors for button states
    protected static final Color COLOR_BUTTON_INACTIVE = new Color(175,175,175);
    protected static final Color COLOR_BUTTON_ACTIVE = new Color(0, 255, 0);

    // Every tool must implement a button to be added to one of the button panels
    protected JButton toolButton;

    // Called by canvas when a key is pressed and the tool is active
    public void keyPressedHandler(DigitalCanvasState dcs) {}

    // Called by canvas when specific mouse button is pressed and tool is active
    public void mouseButton1PressedHandler(DigitalCanvasState dcs) {}
    public void mouseButton2PressedHandler(DigitalCanvasState dcs) {}
    public void mouseButton3PressedHandler(DigitalCanvasState dcs) {}

    // Called by canvas when specific mouse button is released and tool is active
    public void mouseButton1ReleasedHandler(DigitalCanvasState dcs) {}
    public void mouseButton2ReleasedHandler(DigitalCanvasState dcs) {}
    public void mouseButton3ReleasedHandler(DigitalCanvasState dcs) {}

    // Called by canvas when specific mouse button is pressed, mouse moved, and tool is active
    public void mouseButton1DraggedHandler(DigitalCanvasState dcs) {}
    public void mouseButton2DraggedHandler(DigitalCanvasState dcs) {}
    public void mouseButton3DraggedHandler(DigitalCanvasState dcs) {}

    // Called by canvas when the tool's button is pressed
    public void buttonActionHandler(DigitalCanvasState dcs) { this.getToolButton().setBackground(COLOR_BUTTON_ACTIVE); }

    // Called by canvas when the tool is deselected
    public void deselectTool() { this.getToolButton().setBackground(COLOR_BUTTON_INACTIVE); }

    // Returns tool button reference to caller
    public JButton getToolButton() { return this.toolButton; }
    // Sets the button's action command property so that the canvas is aware of which tool button was pressed
    public void setButtonAction(String cmd) { this.getToolButton().setActionCommand(cmd); }
}
