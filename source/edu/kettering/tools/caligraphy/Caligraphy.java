
package edu.kettering.tools.caligraphy;

import edu.kettering.client.DigitalCanvasState;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Caligraphy extends edu.kettering.tools.Tool {

    private int prevX, prevY;          

    /* Called by DigitalCanvas to initiate the tool in the Tool array. */
    public Caligraphy() {
        toolButton = new CaligraphyButton();
        toolButton.setBackground(COLOR_BUTTON_INACTIVE);
    }

    /* Caligraphy tool selected from tools buttons */
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

        for(int i = 0; i < 12; i++){
            graphicsForDrawing.drawLine(prevX, prevY - i, x, y - i);
        }

        prevX = x;
        prevY = y;

        dcs.updateCanvasImg(img);
    } //end mouseButton1DraggedHandler()

} //end Caligraphy
