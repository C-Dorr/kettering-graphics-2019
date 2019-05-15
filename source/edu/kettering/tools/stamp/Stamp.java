package edu.kettering.tools.stamp;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Stamp {
    private BufferedImage STAMP = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_BINARY);

    public Stamp() {}

    protected void selectStamp() {
        //Create a file chooser
        final JFileChooser fc = new JFileChooser();

        //In response to a button click:
        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                BufferedImage stampRGB = ImageIO.read(file);
                this.setStamp(this.rgbToBW(stampRGB));
            } catch (IOException e) {
                System.out.println("Error opening image! Exiting.");
                System.exit(1);
            }
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

    public BufferedImage getStamp() { return STAMP; }

    private void setStamp(BufferedImage newStamp) { STAMP = newStamp; }
}
