/*
 *   Class name:     DigitalCanvasState
 *   Contributor(s): Christopher Dorr
 *   Modified:       June 5th, 2019
 *   Package:        edu.kettering.client
 *   Purpose:        Main paint program class. Generates the main frame.
 * */

package edu.kettering.client;

import java.awt.*;
import javax.swing.JFrame;

public class Client extends JFrame {
    
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    private Client() {
        initUI();
    }
    
    private void initUI() {
        //Set properties
        setSize(screenSize.width / 2, screenSize.height / 2);
        setTitle("Kettering Paint");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Add main program
        DigitalCanvas canvas = new DigitalCanvas(this);
        add(canvas);
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.setVisible(true);
    }
}