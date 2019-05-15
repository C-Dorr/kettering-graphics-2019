package edu.kettering.client;

import edu.kettering.tools.stamp.StampButton;

import javax.swing.*;

public class ToolBar extends JPanel {
    public String name;
    public int orientation;
    private JToolBar jToolBar;
    
    public ToolBar(String name, int orientation) {
        this.name = name;
        this.orientation = orientation;
        this.setLayout(new BoxLayout(this, orientation));
        jToolBar = new JToolBar(name + "_tb", orientation);
        jToolBar.setFloatable(false);
    }
    
    public void addMenuButtons(DigitalCanvas bar) {
        JButton button = makeNullButton("Flip Colors", "fakeAction", "Press this to use nothing.");
        button.addActionListener(bar);
        this.add(button);
        jToolBar.add(this);
    }
    
    //TODO: Abstract addition of buttons to module bar for easy addition of new modules
    public void addModuleButtons(DigitalCanvas bar) {
        JButton b1, b2, b3;
        b1 = makeNullButton("Red", "makeRed", "Tool Tip");
        b2 = makeNullButton("Green", "makeGreen", "Tool Tip");
        b3 = makeNullButton("Blue", "makeBlue", "Tool Tip");

        JButton b4 = new StampButton();

        b1.addActionListener(bar);
        b2.addActionListener(bar);
        b3.addActionListener(bar);

        this.add(b1);
        this.add(b2);
        this.add(b3);

        this.add(b4);

        jToolBar.add(this);
    }
    
    private JButton makeNullButton(String name, String actionCommand, String toolTip) {
        JButton button = new JButton();
        button.setText(name);
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTip);
        return button;
    }
}

