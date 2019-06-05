/*
 *   Class name:     Paint
 *   Contributor(s): Eric Clark
 *   Modified:       June 4th, 2019
 *   Package:        edu.kettering.tools.paint
 *   Purpose:        Class for paint tool. Implements Tool interface.
 *                   Provides functionality for drawing using the mouse.
 * */
package edu.kettering.tools.paint;

import edu.kettering.client.DigitalCanvasState;
import java.awt.*;
import java.awt.image.BufferedImage;


public class Paint extends edu.kettering.tools.Tool
{
    private int prevX;
    private int prevY;
    //called by DigitalCanvas, initiates tool
    public Paint()
    {
        toolButton = new PaintButton();
        toolButton.setBackground(COLOR_BUTTON_INACTIVE);
    }

    @Override
    //sets button color when it is selected from the toolbar
    public void buttonActionHandler(DigitalCanvasState dcs)
    {
        this.getToolButton().setBackground(COLOR_BUTTON_ACTIVE);
    }

    @Override
    //gets the mouse coordinates to prepare for drawing
    public void mouseButton1PressedHandler(DigitalCanvasState dcs)
    {
        Point mousePosition = dcs.getLastMouseCoords();//gets mouse position when it clicked

        prevX = (int)mousePosition.getX();//sets these coordinates as previous position
        prevY =  (int)mousePosition.getY();
    }

    @Override
    //drawing  via dragging mouse
    public void mouseButton1DraggedHandler(DigitalCanvasState dcs)
    {
        BufferedImage image = dcs.getCanvasImg();//gets the canvas
        Graphics2D g2 = (Graphics2D) image.getGraphics();//creates drawing tool for canvas
        g2.setColor(dcs.getDrawColor());//gets most recently selected color
        Point mousePos = dcs.getLastMouseCoords();//gets most recent mouse coordinates
        int x = (int) mousePos.getX();
        int y = (int) mousePos.getY();
        g2.drawLine(prevX, prevY, x, y);//draws line from prev to current
        prevX = x;//sets new coordinates as past
        prevY = y;
        dcs.updateCanvasImg(image);//redraws image
    }
}