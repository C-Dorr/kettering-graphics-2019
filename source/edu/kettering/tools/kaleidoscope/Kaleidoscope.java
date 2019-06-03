/*
 *   Class name:     Kaleidoscope
 *   Contributor(s): Mitchell Athayde
 *   Modified:       June 2nd, 2019
 *   Package:        edu.kettering.tools.kaleidoscope
 *   Purpose:        Class for Kaleidoscope tool. Implements Tool interface.
 *                   Provides functionality for drawing a mirrored image over both axises.
 * */

package edu.kettering.tools.kaleidoscope;

import edu.kettering.client.DigitalCanvasState;
import java.awt.*;
import java.awt.image.BufferedImage;
//---import java.awt.image.BufferedImage;
import javax.swing.*;

public class Kaleidoscope extends edu.kettering.tools.Tool {

    private int prevX, prevY;             //previous location of the mouse.

    /* Called by DigitalCanvas to initiate the tool in the Tool array. */
    public Kaleidoscope() {
        toolButton = new KaleidoscopeButton();
        toolButton.setBackground(COLOR_BUTTON_INACTIVE);
    }

    /* Kaleidoscope tool selected from tools buttons */
    @Override
    public void buttonActionHandler(DigitalCanvasState dcs) { this.getToolButton().setBackground(COLOR_BUTTON_ACTIVE); }

    /*
    * Mouse1 pressed
    * get current mouse position
    * prevX and prevY are not set yet, so set them to current mouse position
    * set dragging to true
    * */
    @Override
    public void mouseButton1PressedHandler(DigitalCanvasState dcs) {
        Point mousePos = dcs.getLastMouseCoords();
        int x = (int)mousePos.getX();
        int y = (int)mousePos.getY();

        prevX = x;
        prevY = y;
    }

    /*
    * mouse being dragged, draw
    * image is mirrored over both axises
    * set prevX and prevY to current x and y respectively to prepare for next line
    * */
    @Override
    public void mouseButton1DraggedHandler(DigitalCanvasState dcs) {

        BufferedImage img = dcs.getCanvasImg();
        Graphics2D graphicsForDrawing = (Graphics2D) img.getGraphics();
        graphicsForDrawing.setColor(dcs.getDrawColor());

        int width = img.getWidth();    //Width of the panel.
        int height = img.getHeight();  //Height of the panel.

        Point mousePos = dcs.getLastMouseCoords();
        int x = (int)mousePos.getX();
        int y = (int)mousePos.getY();

        //this ensures you are only drawing within the bounds of the canvas
        if (x < 3) { x = 3; }
        if (x > img.getWidth()) { x = img.getWidth(); }
        if (y < 3) { y = 3; }
        if (y > img.getHeight()) { y = img.getHeight(); }

        /* first where your mouse is
           second is over y axis
           third is over x axis
           fourth is over both axises */
        graphicsForDrawing.drawLine(prevX, prevY, x, y);
        graphicsForDrawing.drawLine(prevX, -prevY+(height), x, -y+(height));
        graphicsForDrawing.drawLine(-prevX+(width), prevY, -x+(width), y);
        graphicsForDrawing.drawLine(-prevX+(width), -prevY+(height), -x+(width), -y+(height));

        graphicsForDrawing.drawLine(prevY, prevX, y, x);
        graphicsForDrawing.drawLine(prevY, -prevX+(height), y, -x+(height));
        graphicsForDrawing.drawLine(-prevY+(width), prevX, -y+(width), x);
        graphicsForDrawing.drawLine(-prevY+(width), -prevX+(height), -y+(width), -x+(height));

        prevX = x;
        prevY = y;

        dcs.updateCanvasImg(img);
    } //end mouseButton1DraggedHandler()

} //end Kaleidoscope
