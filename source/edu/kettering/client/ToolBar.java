package edu.kettering.client;

import javax.swing.*;
import java.util.LinkedList;
import java.util.ListIterator;

class ToolBar extends JPanel {
    private String name;
    private int orientation;
    private JToolBar jToolBar;
    
    ToolBar(String name, int orientation) {
        this.name = name;
        this.orientation = orientation;
        this.setLayout(new BoxLayout(this, orientation));
        this.jToolBar = new JToolBar(name + "_tb", orientation);
        this.jToolBar.setFloatable(false);
    }

    void addMenuButtons(DigitalCanvas bar) {
        JButton button = makeNullButton("Flip Colors", "fakeAction", "Press this to use nothing.");
        button.addActionListener(bar);
        this.add(button);
        this.jToolBar.add(this);
    }

    void addModuleButtons(DigitalCanvas canvas, LinkedList<JButton> buttons) {
        // Get iterator for linked list of buttons
        ListIterator<JButton> buttonIter = buttons.listIterator();

        // Iteratively add all the tool buttons to the toolbar
        for(JButton  button: buttons) {
            button = buttonIter.next(); // get next button
            button.addActionListener(canvas); // register canvas as listener for button
            this.add(button); // add button to toolbar
        }

        this.jToolBar.add(this);
    }

    private JButton makeNullButton(String name, String actionCommand, String toolTip) {
        JButton button = new JButton();
        button.setText(name);
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTip);
        return button;
    }
}

