// ToDo - Header
package edu.kettering.tools.texttool;

import edu.kettering.client.DigitalCanvasState;
import edu.kettering.tools.Tool;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class TextTool extends Tool {
    /* Constructors */
    public TextTool() {
        toolButton = new JButton("Text");
        toolButton.setBackground(COLOR_BUTTON_INACTIVE);
    }

    /* Event Handlers */
    // Draws input string where user left-clicked
    @Override
    public void mouseButton1PressedHandler(DigitalCanvasState dcs) {
        Point clickPoint = dcs.getLastMouseCoords();
        BufferedImage canvas = dcs.getCanvasImg();

        Graphics2D g = (Graphics2D) canvas.getGraphics();

        // Prompt user to input a string to draw on the canvas
        String drawText = JOptionPane.showInputDialog("Text to insert: ");

        // Check that user didn't cancel before proceeding
        if (drawText == null) { return; }

        // Draw string at click point with current draw color
        g.setColor(dcs.getDrawColor());
        g.drawString(drawText, clickPoint.x, clickPoint.y);

        // Update the canvas image
        dcs.updateCanvasImg(canvas);
    }
}