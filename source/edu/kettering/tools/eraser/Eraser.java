package edu.kettering.tools.eraser;

import java.awt.*;
import java.awt.image.BufferedImage;

import edu.kettering.client.DigitalCanvasState;

public class Eraser extends edu.kettering.tools.Tool {

    private int prevX, prevY;

    public Eraser() {
        toolButton = new EraserButton();
        toolButton.setBackground(COLOR_BUTTON_INACTIVE);
    }

    public void buttonActionHandler(DigitalCanvasState dcs) { this.getToolButton().setBackground(COLOR_BUTTON_ACTIVE); }

    public void mouseButtonPressedHandler(DigitalCanvasState dcs) {
        Point mousePos = dcs.getLastMouseCoords();
        int x = (int)mousePos.getX();
        int y = (int)mousePos.getY();
    }

    @Override
    public void mouseButton1PressedHandler(DigitalCanvasState dcs) {
        Point mousePos = dcs.getLastMouseCoords();
        int x = (int)mousePos.getX();
        int y = (int)mousePos.getY();

        prevX = x;
        prevY = y;
    }

    @Override
    public void mouseButton1DraggedHandler(DigitalCanvasState dcs) {

        BufferedImage img = dcs.getCanvasImg();
        Graphics2D graphicsForDrawing = (Graphics2D) img.getGraphics();
        graphicsForDrawing.setColor(dcs.getCanvasColor());
        graphicsForDrawing.setStroke(new BasicStroke(10));

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

        graphicsForDrawing.drawLine(prevX, prevY, x, y);

        prevX = x;
        prevY = y;

        dcs.updateCanvasImg(img);
    }
}
