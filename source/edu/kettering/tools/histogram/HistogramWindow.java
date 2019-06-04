/*
 *   Class name:        HistogramWindow
 *   Contributor(s):    Zachary Goldasich
 *   Modified:          June 4th, 2019
 *   Package:           edu.kettering.tools.histogram
 *   Purpose:           Class for histogram viewer tool. Container frame for histogram renderer.
 * */

package edu.kettering.tools.histogram;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

class HistogramWindow extends JFrame {
	HistogramWindow(ArrayList<ArrayList<Integer[]>> inputHistogram, int area) {
		this.setSize(768, 200);
		this.setPreferredSize(new Dimension(768,200));
		this.setTitle("Histogram Output");
		this.add(new HistogramWindowGraphics(inputHistogram, area), BorderLayout.CENTER);
		this.setVisible(true);
	}
}
