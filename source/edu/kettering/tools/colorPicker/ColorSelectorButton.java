package edu.kettering.tools.colorPicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
    Aaron Howell
    Computer Graphics
    Kettering Paint
    Spring 2019

 */


public class ColorSelectorButton extends JButton {

    private Color currentColor;

    ColorSelectorButton() {
        //this.setForeground(Color.BLACK);
        this.setBackground(Color.white);
        this.setBorderPainted(true);
        this.setText("Color");
        this.setActionCommand("Color Selector");
        this.setToolTipText("Select a Color");

    }
}
