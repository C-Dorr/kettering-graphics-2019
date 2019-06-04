/*
 *   Class name:        Histogram
 *   Contributor(s):    Zachary Goldasich
 *   Modified:          June 4th, 2019
 *   Package:           edu.kettering.tools.histogram
 *   Purpose:           Class for histogram viewer tool. Extends base tool.
 *                      Used for viewing color distribution as % of total image pixels.
 *                      Generates histogram data structure from canvas then passes to renderer.
 * */

package edu.kettering.tools.histogram;

import edu.kettering.client.DigitalCanvasState;

import java.util.ArrayList;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Histogram extends edu.kettering.tools.Tool {
	private HistogramWindow display;

	public Histogram() {
		toolButton = new HistogramButton();
		toolButton.setBackground(COLOR_BUTTON_INACTIVE);
	}

	private ArrayList<ArrayList<Integer[]>> generateHistogram(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		ArrayList<ArrayList<Integer[]>> outputArray = new ArrayList<ArrayList<Integer[]>>();

		for (int i = 0; i < 3; i++) {
			outputArray.add(new ArrayList<Integer[]>());
		}

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Color operatingPixel = new Color(image.getRGB(j,i));
				outputArray.set(0, sortPixelToBin(operatingPixel.getRed(),outputArray.get(0)));
				outputArray.set(1, sortPixelToBin(operatingPixel.getGreen(),outputArray.get(1)));
				outputArray.set(2, sortPixelToBin(operatingPixel.getBlue(),outputArray.get(2)));
			}
		}
		return outputArray;
	}

	private ArrayList<Integer[]> sortPixelToBin(int value, ArrayList<Integer[]> bin) {
		if (bin.size() == 0) {
			bin.add(new Integer[]{value, 1});
		} else {
			boolean match = false;
			for (int i = 0; i < bin.size(); i++) {
				if (bin.get(i)[0] == value) {
					Integer[] newArray = bin.get(i);
					newArray[1]++;
					bin.set(i, newArray);
					match = true;
					break;
				}
			}
			if (!match) {
				bin.add(new Integer[]{value, 1});
			}
		}
		return bin;
	}

	@Override
	public void buttonActionHandler(DigitalCanvasState dcs) {
		toolButton.setBackground(COLOR_BUTTON_ACTIVE);
		if (display != null) {
			display.dispose();
		}
		display = new HistogramWindow(generateHistogram(dcs.getCanvasImg()),dcs.getCanvasImg().getWidth()*dcs.getCanvasImg().getHeight());
	}

	@Override
	public void deselectTool(DigitalCanvasState dcs) {
		toolButton.setBackground(COLOR_BUTTON_INACTIVE);
		if (display != null) {
			display.dispose();
		}
	}
}
