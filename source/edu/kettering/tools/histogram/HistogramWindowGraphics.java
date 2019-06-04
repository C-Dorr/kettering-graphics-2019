/*
 *   Class name:        HistogramWindowGraphics
 *   Contributor(s):    Zachary Goldasich
 *   Modified:          June 4th, 2019
 *   Package:           edu.kettering.tools.histogram
 *   Purpose:           Class for histogram viewer tool. Handles rendering process for displaying histogram.
 * */

package edu.kettering.tools.histogram;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

class HistogramWindowGraphics extends JPanel {
	private ArrayList<ArrayList<Integer[]>> histogram;
	private int imageArea;
	private Shape currentShape;

	HistogramWindowGraphics(ArrayList<ArrayList<Integer[]>> inputHistogram, int area) {
		histogram = inputHistogram;
		imageArea = area;
		setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(768,200));
		updateWindow();
		this.setVisible(true);
		this.setFocusable(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		ArrayList<Integer[]> redArray = histogram.get(0);
		ArrayList<Integer[]> greenArray = histogram.get(1);
		ArrayList<Integer[]> blueArray = histogram.get(2);

		g2.setPaint(Color.BLACK);
		currentShape = new Rectangle2D.Double(0,0,this.getWidth(),this.getHeight());
		g2.fill(currentShape);
		g2.setColor(Color.RED);
		renderHistogram(0, redArray, g2);
		g2.setColor(Color.GREEN);
		renderHistogram(256, greenArray, g2);
		g2.setColor(Color.BLUE);
		renderHistogram(512, blueArray, g2);
	}

	private void renderHistogram(int x_offset, ArrayList<Integer[]> colorArray, Graphics2D g2) {
		for (int i = 0; i < 256; i++) {
			for (int j = 0; j < colorArray.size(); j++) {
				if (colorArray.get(j)[0] == i) {
					double lineLength = ((double) colorArray.get(j)[1]/ (double) imageArea)*this.getHeight();
					currentShape = new Line2D.Double(x_offset + i,this.getHeight(),x_offset + i,this.getHeight()-lineLength);
					g2.draw(currentShape);
				}
			}
		}
	}

	private void updateWindow() {
		revalidate();
		repaint();
	}
}
