package edu.kettering.tools.histogram;

import javax.swing.*;

public class HistogramButton extends JButton {
	public HistogramButton() {
		this.setText("Histogram");
		this.setActionCommand("Histogram");
		this.setToolTipText("Generate histogram of image data");
	}
}
