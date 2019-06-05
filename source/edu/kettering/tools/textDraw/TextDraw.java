package textDraw;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//Coding help taken from: https://stackoverflow.com/questions/8063040/java-drawing-a-circle-when-mouse-clicked

public class TextDraw extends JFrame implements ActionListener, MouseListener {

    public TextDraw() {
        setSize(250,150);
        addMouseListener(this);
    }

    public static void main(String[] args) {
        //TODO code application logic here
        java.awt.EventQueue.invokeLater(() -> {
            TextDraw frame = new TextDraw();
            frame.setVisible(true);
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {

    }
    
    public void drawText(int x, int y) {
        Graphics g = this.getGraphics();
        String draw = "Default";
        try
        {
            draw = JOptionPane.showInputDialog("String to add: ");
        }
        catch(Exception E)
        {
                    
        }
        g.drawString(draw, x, y);
    }

    int x, y;

    @Override
    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        repaint();
    }
    
    @Override
    public void mouseExited(MouseEvent e) {

    }
    
    @Override
    public void mousePressed(MouseEvent e) {

    }
    
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    
    @Override
    public void paint(Graphics g) {
    drawText(x,y);
}
}