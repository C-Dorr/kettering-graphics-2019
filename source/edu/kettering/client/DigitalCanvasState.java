/*
*   Class name:     DigitalCanvasState
*   Contributor(s): Jeremy Maxey-Vesperman, Christopher Dorr, Zachary Goldasich
*   Modified:       May 16th, 2019
*   Package:        edu.kettering.client
*   Purpose:        Container for relevant state information of the canvas.
*                   Allows interfacing with canvas data from within plugin tools.
* */

package edu.kettering.client;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DigitalCanvasState {
    /* PRIVATE CLASS CONSTANTS */
    // Modify these to change default canvas color
    private static final int DEFAULT_CANVAS_R = 255;
    private static final int DEFAULT_CANVAS_G = 255;
    private static final int DEFAULT_CANVAS_B = 255;

    // Modify these to change default drawing color
    private static final int DEFAULT_DRAW_R = 0;
    private static final int DEFAULT_DRAW_G = 0;
    private static final int DEFAULT_DRAW_B = 0;

    private static final Color DEFAULT_CANVAS_COLOR = new Color(
            DEFAULT_CANVAS_R,
            DEFAULT_CANVAS_G,
            DEFAULT_CANVAS_B
    );
    public static final Color DEFAULT_DRAW_COLOR = new Color(
            DEFAULT_DRAW_R,
            DEFAULT_DRAW_G,
            DEFAULT_DRAW_B
    );

    // Indicates that no tools are selected
    private static final int NO_TOOL_SELECTED = -1;

    /* INSTANCE VARIABLES */
    private BufferedImage canvasImg;
    private Color drawColor;
    private Color canvasColor;
    private int selectedTool;
    private Point lastMouseCoords;
    private int lastKeyCode;

    /* CONSTRUCTORS */
    // Instantiate canvas state with default initial canvas color
    DigitalCanvasState(Dimension canvasSize, int bufImgColorSpace) {
        this.initCanvasImg(canvasSize, bufImgColorSpace, DEFAULT_CANVAS_COLOR);
        this.setDrawColor(DEFAULT_DRAW_COLOR);
        this.setCanvasColor(DEFAULT_CANVAS_COLOR);
        this.setSelectedTool(NO_TOOL_SELECTED);
    }

    // Instantiate canvas state with specified drawing color
    public DigitalCanvasState(Dimension canvasSize, int bufImgColorSpace, Color drawColor) {
        this.initCanvasImg(canvasSize, bufImgColorSpace, DEFAULT_CANVAS_COLOR);
        this.setDrawColor(drawColor);
        this.setCanvasColor(DEFAULT_CANVAS_COLOR);
        this.setSelectedTool(NO_TOOL_SELECTED);
    }

    // Instantiate canvas state with specified canvas and drawing colors
    public DigitalCanvasState(
            Dimension canvasSize,
            int bufImgColorSpace,
            Color canvasColor,
            Color drawColor
    ) {
        this.initCanvasImg(canvasSize, bufImgColorSpace, canvasColor);
        this.setCanvasColor(canvasColor);
        this.setDrawColor(drawColor);
        this.setSelectedTool(NO_TOOL_SELECTED);
    }

    /* PUBLICLY-ACCESSIBLE OPERATIONS */
    // Wipes the canvas to a specified color
    public void wipeCanvasColor(Color wipeColor) {
        // Wipe to specified color
        for(int x = 0; x < this.canvasImg.getWidth(); x++) {
            for(int y = 0; y < this.canvasImg.getHeight(); y++) {
                this.canvasImg.setRGB(x, y, wipeColor.getRGB());
            }
        }
        this.setCanvasColor(wipeColor);
    }

    /* PRIVATE HELPER FUNCTIONS */
    // Creates the initial canvas image for this instance
    private void initCanvasImg(Dimension canvasSize, int bufImgColorSpace, Color canvasColor) {
        // Create canvas of specified size with set colorspace
        BufferedImage newCanvasImg = new BufferedImage(
                (int)canvasSize.getWidth(),
                (int)canvasSize.getHeight(),
                bufImgColorSpace
        );
        this.setCanvasImg(newCanvasImg);
        this.wipeCanvasColor(canvasColor);
    }

    /* PUBLICLY-ACCESSIBLE GETTERS */
    // Get through copies of objects to enforce usage of setters and protect instance variables
    public BufferedImage getCanvasImg() {
        BufferedImage returnImg = new BufferedImage(
                this.canvasImg.getWidth(),
                this.canvasImg.getHeight(),
                this.canvasImg.getType()
        );
        returnImg.setData(
                this.canvasImg.getRaster()
        );
        return returnImg;
    }
    public Color getDrawColor() { return new Color(this.drawColor.getRGB()); }
    public Color getCanvasColor() { return new Color(this.canvasColor.getRGB());}
    public Point getLastMouseCoords() { return new Point(this.lastMouseCoords); }
    public int getLastKeyCode() { return this.lastKeyCode; }

    /* PUBLICLY-ACCESSIBLE SETTERS */
    // Set through copies of objects to enforce usage of setters and protect instance variables
    public void updateCanvasImg(BufferedImage newCanvasImg) {
        this.canvasImg = new BufferedImage(
                newCanvasImg.getWidth(),
                newCanvasImg.getHeight(),
                newCanvasImg.getType()
        );
        this.canvasImg.setData(newCanvasImg.getRaster());
    }
    public void updateDrawColor(Color newDrawColor) {
        this.drawColor = new Color(newDrawColor.getRGB());
    }

    /* PACKAGE-LEVEL GETTERS */
    int getSelectedTool() { return this.selectedTool; }

    /* PACKAGE-LEVEL SETTERS */
    // Restrict control of selected tool and mouse coordinates to the Client package
    void setSelectedTool(int currTool) { this.selectedTool = currTool; }
    void updateMouseCoords(Point currMouseCoord) {
        this.lastMouseCoords = new Point(currMouseCoord);
    }
    void updateKeyCode(int currKeyCode) { this.lastKeyCode = currKeyCode; }

    /* PRIVATE SETTERS */
    // Trusting this class to handle objects in a safe manner
    private void setCanvasImg(BufferedImage newCanvasImg) { this.canvasImg = newCanvasImg; }
    private void setDrawColor(Color newDrawColor) { this.drawColor = newDrawColor; }
    private void setCanvasColor(Color newCanvasColor) {this.canvasColor = newCanvasColor;}
}
