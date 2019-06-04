// ToDo - Header

package edu.kettering.tools.copypaste;

import edu.kettering.client.DigitalCanvasState;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import javax.swing.JButton;

// ToDo - Comments
public class CopyPaste extends edu.kettering.tools.Tool{
	private Point [] coords;
	private boolean haveImage;
	private boolean imagePlaced;
	private Raster copied;

	public CopyPaste() {
		toolButton = new JButton();
		toolButton.setText("Copy");
		toolButton.setBackground(COLOR_BUTTON_INACTIVE);
		coords = new Point[2];
	}
    // Called by canvas when a key is pressed and the tool is active
    public void keyPressedHandler(DigitalCanvasState dcs) {
    	BufferedImage canvasImg = dcs.getCanvasImg();
    	haveImage = false;
    	if (copied != null) { canvasImg.setData(copied); }
    	dcs.updateCanvasImg(canvasImg);
    }

    // Called by canvas when specific mouse button is pressed and tool is active
    public void mouseButton1PressedHandler(DigitalCanvasState dcs) {
    	Point mousePos = dcs.getLastMouseCoords(); 
    	if(haveImage) {
        	BufferedImage canvasImg = dcs.getCanvasImg();
         	Raster copied2 = copied.createTranslatedChild(mousePos.x, mousePos.y);
         	canvasImg.setData(copied2);
         	canvasImg.setData(copied);
         	dcs.updateCanvasImg(canvasImg);

         	haveImage = false;
         	imagePlaced = true;
         } else {
	         coords[0] = mousePos;
         }
    }

    // Called by canvas when specific mouse button is released and tool is active
    public void mouseButton1ReleasedHandler(DigitalCanvasState dcs) {
    	if (imagePlaced) {
    		imagePlaced = false;
		} else if (!haveImage) {
    		BufferedImage canvasImg = dcs.getCanvasImg();
    		coords[1] = dcs.getLastMouseCoords();

    		if(coords[0].x > coords[1].x) {
    			int temp = coords[0].x;
    			coords[0].x = coords[1].x;
    			coords[1].x = temp;
    		}

    		if(coords[0].y > coords[1].y) {
    			int temp = coords[0].y;
    			coords[0].y = coords[1].y;
    			coords[1].y = temp;
    		}

    		int width = coords[1].x - coords[0].x;
    		int height = coords[1].y - coords[0].y;

    		if (height == 0 || width == 0) { return; }

    		Rectangle rect = new Rectangle(coords[0].x, coords[0].y, width, height);

    		copied = canvasImg.getData(rect);

    		Graphics g = canvasImg.createGraphics();

    		int normal = Color.black.getRGB();
    		int opposite = Color.white.getRGB();

    		//draw rectangle in black if in black, white if in white
    		for(int i = coords[0].x; i < coords[1].x - 1 && i < canvasImg.getWidth(); i++) {
    			if(canvasImg.getRGB(i, coords[0].y) == normal) {
    				canvasImg.setRGB(i, coords[0].y, opposite);
    			} else {canvasImg.setRGB(i, coords[0].y, normal);}

    			if(canvasImg.getRGB(i, coords[1].y - 1) == normal){
    				canvasImg.setRGB(i, coords[1].y - 1, opposite);
    			} else { canvasImg.setRGB(i, coords[1].y-1,normal); }
    		}

    		for(int i=coords[0].y+1; i<coords[1].y-2&&i<canvasImg.getHeight(); i++ ) {
    			if(canvasImg.getRGB(coords[0].x, i)==normal) {
    				canvasImg.setRGB(coords[0].x, i,opposite);
    			} else {canvasImg.setRGB(coords[0].x, i,normal);}

    			if(canvasImg.getRGB(coords[1].x-1, i)==normal){
    				canvasImg.setRGB(coords[1].x-1, i,opposite);
    			} else {canvasImg.setRGB(coords[1].x-1, i,normal);}
    		}

    		dcs.updateCanvasImg(canvasImg);

    		g.dispose();

    		haveImage=true;
    	}
    }

    // Called by canvas when the tool's button is pressed
    public void buttonActionHandler(DigitalCanvasState dcs) { this.getToolButton().setBackground(COLOR_BUTTON_ACTIVE); }

    // Called by canvas when the tool is deselected
    public void deselectTool(DigitalCanvasState dcs) {
	    this.getToolButton().setBackground(COLOR_BUTTON_INACTIVE);
	    keyPressedHandler(dcs);
	}

    // Returns tool button reference to caller
    public JButton getToolButton() { return this.toolButton; }
    // Sets the button's action command property so that the canvas is aware of which tool button was pressed
    public void setButtonAction(String cmd) { this.getToolButton().setActionCommand(cmd); }
}
