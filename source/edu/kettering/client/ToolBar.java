// ToDo - Heading

package edu.kettering.client;

import javax.swing.*;
import java.util.LinkedList;
import java.util.ListIterator;

class ToolBar extends JPanel {
    /* Instance Variables */
    private JToolBar jToolBar;

    /* Constructors */
    ToolBar(String name, int orientation) {
        this.setLayout(new BoxLayout(this, orientation));
        this.jToolBar = new JToolBar(name + "_tb", orientation);
        this.jToolBar.setFloatable(false);
    }

    /* Package-level Functions/Methods */
    // Create a menu button bar
    void addMenuButtons(DigitalCanvas canvas, String [] actions) {
        // Iteratively generate all buttons with text/actions based on input strings
        for (String actionText : actions) {
            JButton button = new JButton(actionText);
            button.setActionCommand(actionText);
            button.addActionListener(canvas);
            this.add(button);
        }

        this.jToolBar.add(this);
    }

    // Create a tool button bar
    void addModuleButtons(DigitalCanvas canvas, LinkedList<JButton> buttons) {
        // Get iterator for linked list of buttons
        ListIterator<JButton> buttonIter = buttons.listIterator();

        // Iteratively add all the tool buttons to the toolbar
        for(JButton  button : buttons) {
            button = buttonIter.next(); // get next button
            button.addActionListener(canvas); // register canvas as listener for button
            this.add(button); // add button to toolbar
        }

        this.jToolBar.add(this);
    }
}

