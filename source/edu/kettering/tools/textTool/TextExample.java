/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textexample;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;
import javax.swing.JFrame;
/**
 *
 * @author Isthmas
 */
public class TextExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         //Shape c = new Ellipse2D.Float(50, 25, 150, 150);
         JFrame myFrame = new JFrame("TextDemo");
         Container myCont = myFrame.getContentPane();
         MyCanvas canvas = new MyCanvas();
         myCont.add(canvas);
         myFrame.setSize(300,200);
         myFrame.setVisible(true);
         Graphics g = null;
         MouseListener ye = new MouseListener() {
             @Override
             public void mouseClicked(MouseEvent e) {
                  int x = e.getX();
                  int y = e.getY();
             }

             @Override
             public void mousePressed(MouseEvent e) {
                 //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
             }

             @Override
             public void mouseReleased(MouseEvent e) {
                 //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
             }

             @Override
             public void mouseEntered(MouseEvent e) {
                 //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
             }

             @Override
             public void mouseExited(MouseEvent e) {
                 //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
             }
         };
         myFrame.addMouseListener(ye);
    }
}
   
    


 class MyCanvas extends JComponent {
 Graphics2D g2;
  @Override
  public void paintComponent(Graphics g) {
      if(g instanceof Graphics2D)
      {
        g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawString("My String Here",70,20); 
       }
   }
 }

