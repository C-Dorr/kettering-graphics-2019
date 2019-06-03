/*
 *   Interface name:    ToolInterface
 *   Contributor(s):    Jeremy Maxey-Vesperman, Christopher Dorr, Zachary Goldasich
 *   Modified:          May 16th, 2019
 *   Package:           edu.kettering.tools
 *   Purpose:           Interface that must be implemented for any plugin tool that wants to
 *                      interact with the paint client.
 * */

package edu.kettering.tools;

import edu.kettering.client.DigitalCanvasState;

import javax.swing.*;

public interface ToolInterface {
    // Handler function for when tool's button is pressed
    void buttonActionHandler(DigitalCanvasState dcs);

    // Handler function for when key is pressed and tool is active
    void keyPressedHandler(DigitalCanvasState dcs);

    // Handler function for when particular mouse button is pressed and tool is active
    void mouseButton1PressedHandler(DigitalCanvasState dcs);
    void mouseButton2PressedHandler(DigitalCanvasState dcs);
    void mouseButton3PressedHandler(DigitalCanvasState dcs);

    // Handler function for when particular mouse button is released and tool is active
    void mouseButton1ReleasedHandler(DigitalCanvasState dcs);
    void mouseButton2ReleasedHandler(DigitalCanvasState dcs);
    void mouseButton3ReleasedHandler(DigitalCanvasState dcs);

    // Handler function for when particular mouse button is pressed, mouse is moved, and tool is active
    void mouseButton1DraggedHandler(DigitalCanvasState dcs);
    void mouseButton2DraggedHandler(DigitalCanvasState dcs);
    void mouseButton3DraggedHandler(DigitalCanvasState dcs);


    // Handler function for tool being deselected
    void deselectTool();

    // Returns reference to the tool's button
    JButton getToolButton();

    // Sets the action command of the tool's button as assigned by the caller
    void setButtonAction(String cmd);
}
