package edu.kettering.client;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;
import java.util.Queue;

public class DigitalCanvas extends JPanel implements ActionListener {
    private ToolBar menuBar;
    private ToolBar moduleBar;
   
    public DigitalCanvas(Client parent) {
        setFocusable(true);
    
        initToolBars(parent);
        addKeyListener(new KeyboardActions(this));
        addMouseListener(new MouseActions(this));
    
        this.setBackground(Color.white);
    }
    
    private void initToolBars(Client parent) {
        menuBar = new ToolBar("Menu Bar", SwingConstants.HORIZONTAL);
        menuBar.addMenuButtons(this);
        parent.add(menuBar, BorderLayout.PAGE_START);

        moduleBar = new ToolBar("Module Bar", JToolBar.VERTICAL);
        moduleBar.addModuleButtons(this);
        parent.add(moduleBar, BorderLayout.EAST);
        
    }
    
    private void handleButtonAction(String action)  {
        switch(action) {
            case "makeGreen":
                this.setBackground(Color.green);
                break;
            case "makeRed":
                this.setBackground(Color.red);
                break;
            case "makeBlue":
                this.setBackground(Color.blue);
                break;
            case "fakeAction":
                this.setBackground(flipColors());
                break;
            case "PickStamp":

                break;
        }
    }
    
    @Override //repaint() -> paintComponents()
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        handleButtonAction(e.getActionCommand());
        repaint();
    }
    
    private Color flipColors() {
        Color current = this.getBackground();
        int red = current.getRed();
        int green = current.getGreen();
        int blue = current.getBlue();
        return new Color(255 - red, 255 - green, 255 - blue);
    }
    
    class KeyboardActions extends KeyAdapter {
        DigitalCanvas canvas;
        
        public KeyboardActions(DigitalCanvas canvas) {
            this.canvas = canvas;
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();
            
            switch (keycode) {
                case KeyEvent.VK_SPACE:
                    //Placeholder
                    break;
            }
        }
    }
    
    class MouseActions extends MouseAdapter {
        DigitalCanvas canvas;
        boolean flag = false;
    
        public MouseActions(DigitalCanvas canvas) {
            this.canvas = canvas;
        }
    
        @Override
        public void mouseReleased(MouseEvent me) {
        
            if (flag) {
                canvas.setBackground(Color.white);
            } else {
                canvas.setBackground(new Color(230, 230, 230));
            }
            flag = !flag;
            canvas.repaint();
        }
    }
}