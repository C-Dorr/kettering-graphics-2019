package edu.kettering.tools.eyeDropper;

/*
    Aaron Howell
    Computer Graphics
    Kettering Paint
    Spring 2019

 */

import edu.kettering.client.DigitalCanvasState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EyeDropper extends edu.kettering.tools.Tool{

    private int prevX, prevY;

    public EyeDropper() {
        toolButton = new EyeDropperButton();
        toolButton.setBackground(COLOR_BUTTON_INACTIVE);
    }

    @Override
    public void buttonActionHandler(DigitalCanvasState dcs) { this.getToolButton().setBackground(COLOR_BUTTON_ACTIVE); }

    @Override
    public void mouseButton1PressedHandler(DigitalCanvasState dcs) {
        Point mousePos = dcs.getLastMouseCoords();
        int x = (int)mousePos.getX();
        int y = (int)mousePos.getY();
        prevX = x;
        prevY = y;

        BufferedImage img = dcs.getCanvasImg();

        Color color = new Color(img.getRGB(x,y));

        dcs.updateDrawColor(color);
        toolButton.setBackground(color);

    }

    @Override
    public void mouseButton1DraggedHandler(DigitalCanvasState dcs) {

        BufferedImage img = dcs.getCanvasImg();
        Graphics2D graphicsForDrawing = (Graphics2D) img.getGraphics();
        graphicsForDrawing.setColor(dcs.getDrawColor());

        Point mousePos = dcs.getLastMouseCoords();
        int x = (int) mousePos.getX();
        int y = (int) mousePos.getY();

        /* first where your mouse is
           second is over y axis
           third is over x axis
           fourth is over both axises */
        graphicsForDrawing.drawLine(prevX, prevY, x, y);

        prevX = x;
        prevY = y;

        dcs.updateCanvasImg(img);
    }


}
