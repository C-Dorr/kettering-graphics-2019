package edu.kettering.tools.colorPicker;

import edu.kettering.client.DigitalCanvasState;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
    Aaron Howell
    Computer Graphics
    Kettering Paint
    Spring 2019

 */
public class ColorSelector extends edu.kettering.tools.Tool{

    private Color selectedColor;
    private Color[] colorHistory;


    public ColorSelector() {
        toolButton = new ColorSelectorButton();
        toolButton.setText("Color: ");
       //toolButton.setBackground(COLOR_BUTTON_INACTIVE);
        setSelectedColor(Color.black);
    }

    @Override
    public void buttonActionHandler(DigitalCanvasState dcs) {
        Color newColor = JColorChooser.showDialog(null, "Choose a color", selectedColor);
        setSelectedColor(newColor);
        dcs.updateDrawColor(newColor);
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color newColor) {
        selectedColor = newColor;
        toolButton.setBackground(newColor);
    }

    @Override
    public void deselectTool(DigitalCanvasState dcs) { return; }


}
