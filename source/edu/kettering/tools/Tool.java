/*
 *   Interface name:    DigitalCanvasState
 *   Contributor(s):    Jeremy Maxey-Vesperman, Christopher Dorr, Zachary Goldasich
 *   Modified:          May 15th, 2019
 *   Package:           edu.kettering.tool
 *   Purpose:           Interface that must be implemented for any plugin tool that wants to
 *                      interact with the paint client.
 * */

package edu.kettering.tools;

import edu.kettering.client.DigitalCanvasState;

interface Tool {
    /*
     *  Handler function for performing operations when the tool's respective button is pressed. Must return
     */
    int buttonActionHandler(DigitalCanvasState dcs);
    /*
     *   Handler function for when the tool is selected and the mouse button is released over the
     *   canvas. Designed so that tool can modify the canvas state by interfacing with the state object.
     */
    void mouseReleaseHandler(DigitalCanvasState dcs);
}
