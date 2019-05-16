/*
 *   Class name:        Stamp
 *   Contributor(s):    Jeremy Maxey-Vesperman
 *   Modified:          May 16th, 2019
 *   Package:           edu.kettering.tools.stamp
 *   Purpose:           Class for stamp tool. Implements Tool interface.
 *                      Provides functionality for importing, converting to B/W, and drawing an
 *                      image as a stamp in specified color at specified location on the canvas.
 * */

package edu.kettering.tools.stamp;

import edu.kettering.client.DigitalCanvasState;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

// ToDo - Comment this
// ToDo - Reorganize classes
public class Stamp
        extends edu.kettering.tools.Tool {
    private static final String TITLE_STAMP_CHOOSER = "Select Stamp Image";
    private static final String TITLE_DIALOG_STAMP_WARNING = "Stamp Tool Warning";

    private static final String STAMP_CHOOSER_EXTENSION_DESCRIPTION = "Images";
    private static final String [] STAMP_CHOOSER_EXTENSIONS = {"jpg", "png"};

    private static final String MSG_ERROR_IMAGE_IO = "Error reading image!";
    private static final String MSG_ERROR_NO_STAMP =
            "Error! No stamp has been selected. Can't draw anything...";

    private static final String MSG_DIALOG_IMAGE_IO = "Error occurred when opening the image.";
    private static final String MSG_DIALOG_NO_STAMP = "Please select a stamp first!";

    private static final Color COLOR_BUTTON_NO_STAMP = new Color(255, 0, 0 );

    private BufferedImage STAMP_IMG;

    public Stamp() {
        toolButton = new StampButton();
        toolButton.setBackground(COLOR_BUTTON_INACTIVE);
    }

    private void selectStamp() {
        //Create a file chooser
        final JFileChooser stampChooser = new JFileChooser();
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
                STAMP_CHOOSER_EXTENSION_DESCRIPTION,
                STAMP_CHOOSER_EXTENSIONS
        );
        stampChooser.setFileFilter(imageFilter);
        stampChooser.setDialogTitle(TITLE_STAMP_CHOOSER);

        //In response to a button click:
        int returnVal = stampChooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = stampChooser.getSelectedFile();
            try {
                BufferedImage stamp = ImageIO.read(file);
                stamp = this.rgbToBW(stamp);
                stamp = this.trimStamp(stamp);
                this.setStamp(stamp);
            } catch (Exception e) {
                System.out.println(MSG_ERROR_IMAGE_IO);
                JOptionPane.showMessageDialog(null, MSG_DIALOG_IMAGE_IO, TITLE_DIALOG_STAMP_WARNING, JOptionPane.ERROR_MESSAGE);
                this.setStamp(null);
            }
        }

        if (this.STAMP_IMG != null) {
            this.getToolButton().setBackground(COLOR_BUTTON_ACTIVE);
        } else {
            this.getToolButton().setBackground(COLOR_BUTTON_NO_STAMP);
        }
    }

    private BufferedImage rgbToBW(BufferedImage rgbImg) {
        int rgbWidth = rgbImg.getWidth();
        int rgbHeight = rgbImg.getHeight();

        BufferedImage tmpGrayImg = new BufferedImage(rgbWidth, rgbHeight, BufferedImage.TYPE_BYTE_BINARY);

        for(int x = 0; x < rgbWidth; x++) {
            for(int y = 0; y < rgbHeight; y++) {
                Color pixelVal = new Color(rgbImg.getRGB(x, y));
                int red = pixelVal.getRed();
                int green = pixelVal.getGreen();
                int blue = pixelVal.getBlue();
                Double grayScaleDbl = (0.30*red)+(0.59*green)+(0.11*blue);
                byte grayScale = grayScaleDbl.byteValue();

                tmpGrayImg.setRGB(x, y, grayScale);
            }
        }

        return tmpGrayImg;
    }

    private BufferedImage trimStamp(BufferedImage origStamp) {
        int origWidth = origStamp.getWidth();
        int origHeight = origStamp.getHeight();

        Point upperLeftMostPixel = new Point(origWidth, origHeight);
        Point lowerRightMostPixel = new Point(0, 0);
        for(int x = 0; x < origWidth; x++) {
            for(int y = 0; y < origHeight; y++) {
                Color stampRGB = new Color(origStamp.getRGB(x, y));
                boolean drawStampPixel = (stampRGB.getRed() == 0) && (stampRGB.getGreen() == 0) && (stampRGB.getBlue() == 0);

                if(drawStampPixel) {
                    if (x < upperLeftMostPixel.getX()) {
                        upperLeftMostPixel.x = x;
                    } else if (x > lowerRightMostPixel.getX()) {
                        lowerRightMostPixel.x = x;
                    }

                    if (y < upperLeftMostPixel.getY()) {
                        upperLeftMostPixel.y = y;
                    } else if (y > lowerRightMostPixel.getY()) {
                        lowerRightMostPixel.y = y;
                    }
                }
            }
        }

        int newWidth = (int)(lowerRightMostPixel.getX() - upperLeftMostPixel.getX());
        int newHeight = (int)(lowerRightMostPixel.getY() - upperLeftMostPixel.getY());

        return origStamp.getSubimage((int)upperLeftMostPixel.getX(), (int)upperLeftMostPixel.getY(), newWidth, newHeight);
    }

    private void drawStamp(BufferedImage canvasImg, Color drawColor, Point mousePos) throws NoStampSelectedException {
        if (this.STAMP_IMG == null) { throw new NoStampSelectedException(MSG_ERROR_NO_STAMP); }

        int mouseX = (int)mousePos.getX();
        int mouseY = (int)mousePos.getY();
        int stampColor = drawColor.getRGB();
        int canvasWidth = canvasImg.getWidth();
        int canvasHeight = canvasImg.getHeight();
        int stampWidth = this.STAMP_IMG.getWidth();
        int stampHeight = this.STAMP_IMG.getHeight();

        for (int x = mouseX; x >= 0 && x < canvasWidth && (x - mouseX) < stampWidth; x++) {
            for (int y = mouseY; y >= 0 && y < canvasHeight && (y - mouseY) < stampHeight; y++) {
                Color stampRGB = new Color(this.STAMP_IMG.getRGB((x - mouseX), (y - mouseY)));
                boolean drawStampPixel = (stampRGB.getRed() == 0) && (stampRGB.getGreen() == 0) && (stampRGB.getBlue() == 0);
                if (drawStampPixel) {
                    canvasImg.setRGB(x, y, stampColor);
                }
            }
        }
    }

    private void setStamp(BufferedImage newStamp) { this.STAMP_IMG = newStamp; }

    @Override
    public void buttonActionHandler(DigitalCanvasState dcs) { this.selectStamp(); }

    @Override
    public void mouseButton1ReleasedHandler(DigitalCanvasState dcs) {
        BufferedImage canvasImg = dcs.getCanvasImg();
        Color drawColor = dcs.getDrawColor();
        Point mousePos = dcs.getLastMouseCoords();

        try {
            this.drawStamp(canvasImg, drawColor, mousePos);
        } catch (NoStampSelectedException e) {
            System.out.println(MSG_ERROR_NO_STAMP);
            JOptionPane.showMessageDialog(null, MSG_DIALOG_NO_STAMP, TITLE_DIALOG_STAMP_WARNING, JOptionPane.WARNING_MESSAGE);
        }
        dcs.updateCanvasImg(canvasImg);

        /*Random rand = new Random();
        int r  = rand.nextInt(256);
        int g  = rand.nextInt(256);
        int b  = rand.nextInt(256);

        Color newColor = new Color(r, g, b);

        dcs.updateDrawColor(newColor);

        File outputFile = new File("imageOut.png");
        try {
            ImageIO.write(canvasImg, "png", outputFile);
        } catch (IOException e) {
            System.out.println("Unable to write image");
        }*/
    }
}
