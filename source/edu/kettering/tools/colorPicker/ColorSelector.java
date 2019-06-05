package edu.kettering.tools.colorPicker;

import edu.kettering.client.DigitalCanvasState;

import javax.swing.*;
import java.awt.*;
/*
    Aaron Howell
    Computer Graphics
    Kettering Paint
    Spring 2019

 */
public class ColorSelector extends edu.kettering.tools.Tool{

    private Color selectedColor;

    public ColorSelector() {
        toolButton = new ColorSelectorButton();
        toolButton.setText("Color");

        setSelectedColor(DigitalCanvasState.DEFAULT_DRAW_COLOR);
    }

    @Override
    public void buttonActionHandler(DigitalCanvasState dcs) {
        setSelectedColor(dcs.getDrawColor());

        Color newColor = JColorChooser.showDialog(null, "Choose a color", selectedColor);

        // Check that color was selected before proceeding
        if (newColor == null) { return; }

        setSelectedColor(newColor);

        dcs.updateDrawColor(newColor);
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    private void setSelectedColor(Color newColor) {
        selectedColor = newColor;
        setButtonTextColor(newColor);
        toolButton.setBackground(newColor);
    }

    // Set text color of button in contrast to background color
    private void setButtonTextColor(Color backColor) {
        int r = backColor.getRed();
        int g = backColor.getGreen();
        int b = backColor.getBlue();

        // Add RGB components and see if its greater than 50% White
        Color textColor = ((r + g + b) > 382) ? Color.BLACK : Color.WHITE;

        toolButton.setForeground(textColor);
    }

    @Override
    public void deselectTool(DigitalCanvasState dcs) { return; }


}
