package edu.kettering.client;

import edu.kettering.tools.Tool;
import edu.kettering.tools.kaleidoscope.Kaleidoscope;
import edu.kettering.tools.stamp.Stamp;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.*;

public class DigitalCanvas extends JPanel implements ActionListener {
    private ToolBar menuBar;
    private ToolBar moduleBar;
    private DigitalCanvasState DCS; // Canvas state object

    // Add your tool here. Buttons will appear in the order that the tools are listed.
    private Tool [] tools = {
            new Stamp(),        // First tool button in list
            new Kaleidoscope(), // Second tool button in list
                                // ...
    };

    DigitalCanvas(Client parent) {
        this.DCS = new DigitalCanvasState(parent.getSize(), BufferedImage.TYPE_INT_RGB);

        this.setFocusable(true);
    
        this.initToolBars(parent);

        this.addKeyListener(new KeyboardActions(this));
        this.addMouseListener(new MouseActions(this));
        this.addMouseMotionListener(new MouseMotionActions(this));
    
        this.setBackground(Color.white);
    }
    
    private void initToolBars(Client parent) {
        // Generate linked list of buttons for
        LinkedList<JButton> buttons = new LinkedList<>();

        for(int i = 0; i < this.tools.length; i++) {
            this.tools[i].setButtonAction(Integer.toString(i));
            buttons.add(this.tools[i].getToolButton());
        }

        menuBar = new ToolBar("Menu Bar", SwingConstants.HORIZONTAL);
        menuBar.addMenuButtons(this);
        parent.add(menuBar, BorderLayout.PAGE_START);

        moduleBar = new ToolBar("Module Bar", JToolBar.VERTICAL);
        moduleBar.addModuleButtons(this, buttons);
        parent.add(moduleBar, BorderLayout.EAST);
        
    }
    
    private void handleButtonAction(String action)  {
        try {
            int lastToolId = this.DCS.getSelectedTool();
            int toolID = Integer.parseInt(action);
            if (lastToolId >= 0 && lastToolId < this.tools.length) {
                this.tools[lastToolId].deselectTool();
            }
            this.DCS.setSelectedTool(toolID);
            this.tools[toolID].buttonActionHandler(this.DCS);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            switch(action) {
                case "fakeAction":
                    System.out.println("Fake action performed.");
                    break;
            }
        }
    }
    
    @Override //repaint() -> paintComponents()
    protected void paintComponent(Graphics g) {
        g.drawImage(this.DCS.getCanvasImg(), 0, 0, null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        handleButtonAction(e.getActionCommand());

        repaint();
    }
    
    class KeyboardActions extends KeyAdapter {
        DigitalCanvas canvas;
        
        KeyboardActions(DigitalCanvas canvas) {
            this.canvas = canvas;
        }
        
        @Override
        public void keyPressed(KeyEvent ke) {
            this.canvas.DCS.updateKeyCode(ke.getKeyCode()); // update key pressed
            int toolID = this.canvas.DCS.getSelectedTool(); // get current tool id

            try {
                Tool currTool = this.canvas.tools[toolID]; // attempt to access tool at this id
                currTool.keyPressedHandler(this.canvas.DCS); // call key pressed handler
            } catch (ArrayIndexOutOfBoundsException e) { // non-existent tool id
                System.out.println("Invalid tool currently selected. Ignoring key press input.");
            }

            this.canvas.repaint();
        }
    }
    
    class MouseActions extends MouseAdapter {
        DigitalCanvas canvas;

        MouseActions(DigitalCanvas canvas) {
            this.canvas = canvas;
        }

        @Override
        public void mousePressed(MouseEvent me) {
            this.canvas.DCS.updateMouseCoords(me.getPoint()); // update mouse coordinate
            int toolID = this.canvas.DCS.getSelectedTool(); // get current tool id

            try {
                Tool currTool = this.canvas.tools[toolID]; // attempt to access tool at this id

                switch (me.getButton()) {
                    case MouseEvent.BUTTON1: // left button pressed
                        currTool.mouseButton1PressedHandler(this.canvas.DCS);
                        break;
                    case MouseEvent.BUTTON2: // right button pressed
                        currTool.mouseButton2PressedHandler(this.canvas.DCS);
                        break;
                    case MouseEvent.BUTTON3: // middle button pressed
                        currTool.mouseButton3PressedHandler(this.canvas.DCS);
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException e) { // non-existent tool id
                System.out.println("Invalid tool currently selected. Ignoring mouse pressed input.");
            }

            this.canvas.repaint();

            this.canvas.requestFocusInWindow();
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            this.canvas.DCS.updateMouseCoords(me.getPoint()); // update mouse coordinate
            int toolID = this.canvas.DCS.getSelectedTool(); // get current tool id

            try {
                Tool currTool = this.canvas.tools[toolID]; // attempt to access tool at this id

                switch(me.getButton()) {
                    case MouseEvent.BUTTON1: // left button released
                        currTool.mouseButton1ReleasedHandler(this.canvas.DCS);
                        break;
                    case MouseEvent.BUTTON2: // right button released
                        currTool.mouseButton2ReleasedHandler(this.canvas.DCS);
                        break;
                    case MouseEvent.BUTTON3: // middle button released
                        currTool.mouseButton3ReleasedHandler(this.canvas.DCS);
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException e) { // non-existent tool id
                System.out.println("Invalid tool currently selected. Ignoring mouse released input.");
            }

            this.canvas.repaint();
        }
    }

    class MouseMotionActions implements MouseMotionListener {
        DigitalCanvas canvas;

        MouseMotionActions(DigitalCanvas canvas) {
            this.canvas = canvas;
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            this.canvas.DCS.updateMouseCoords(me.getPoint()); // update mouse coordinate
            int toolID = this.canvas.DCS.getSelectedTool(); // get current tool id

            try {
                Tool currTool = this.canvas.tools[toolID]; // attempt to access tool at this id

                if (SwingUtilities.isLeftMouseButton(me)) {  // dragging on left button
                    currTool.mouseButton1DraggedHandler(this.canvas.DCS);
                } else if (SwingUtilities.isRightMouseButton(me)) { // dragging on right button
                    currTool.mouseButton2DraggedHandler(this.canvas.DCS);
                } else if (SwingUtilities.isMiddleMouseButton(me)) { // dragging on middle button
                    currTool.mouseButton3DraggedHandler(this.canvas.DCS);
                }
            } catch (ArrayIndexOutOfBoundsException e) { // non-existent tool id
                System.out.println("Invalid tool currently selected. Ignoring mouse dragged input.");
            }

            this.canvas.repaint();

            this.canvas.requestFocusInWindow();
        }

        @Override
        public void mouseMoved(MouseEvent e) {}
    }
}