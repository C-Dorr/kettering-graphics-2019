package edu.kettering.client;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Client extends JFrame {
    
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    public Client() {
        initUI();
    }
    
    private void initUI() {
        //Add main program
        DigitalCanvas canvas = new DigitalCanvas(this);
        add(canvas);
        
        //Set properties
        setSize(screenSize.width / 2, screenSize.height / 2);
        setTitle("Kettering Paint");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                Client client = new Client();
                client.setVisible(true);
            }
        });
    }
}