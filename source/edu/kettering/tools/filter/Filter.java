// ToDo - Header

package edu.kettering.tools.filter;

import edu.kettering.client.DigitalCanvasState;
import edu.kettering.tools.Tool;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

// ToDo - Comments
public class Filter extends Tool {
	/* Class Constants */
	private static final float[][] kernels  = {
			{0.f, -1.f, 0.f, -1.f, 5.0f, -1.f, 0.f, -1.f, 0.f},
			{ -1, -1, -1, -1, 8, -1, -1, -1, -1 },
			{ 0.272f, 0.534f, 0.131f, 0.349f, 0.686f, 0.168f, 0.393f, 0.769f, 0.189f },
			{-1, -1,  0, -1,  0,  1,  0,  1,  1}
	};

	/* Instance Variables */
	FilterSelectorWindow selector;
	private Kernel kernel;
	private float[] activeKernel;
	private DigitalCanvasState dcs;

	/* Constructors */
	public Filter() {
		 toolButton = new FilterButton();
	     toolButton.setBackground(COLOR_BUTTON_INACTIVE);
	     activeKernel = kernels[0];
	     kernel = new Kernel(3,3,activeKernel);
	}
	
	/* Instance-Level Functions/Methods */
	private void selectFilter() {
		if(selector == null) {
			selector = new FilterSelectorWindow(this);
		}
	}
	
	void setFilter(String name) {
		if (name.equals("Sharpen")) {
			 activeKernel = kernels[0];
		} else if(name.equals("Edge")) {
			 activeKernel = kernels[1];
		} else if(name.equals("Sepia")) {
			 activeKernel = kernels[2];
		} else {
			 activeKernel = kernels[3];
		}
		kernel = new Kernel(3,3,activeKernel);
	}

	void applyFilter() {
		BufferedImage image = dcs.getCanvasImg();
		BufferedImage rtrn = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		cop.filter(image, rtrn);
		dcs.updateCanvasImg(rtrn);
		repaintCanvas(); // Cause canvas redraw
	}

	@Override
	public void buttonActionHandler(DigitalCanvasState dcs) {
		toolButton.setBackground(COLOR_BUTTON_ACTIVE);
		this.dcs = dcs;
		selectFilter();
	}

	@Override
	public void deselectTool(DigitalCanvasState dcs) {
		toolButton.setBackground(COLOR_BUTTON_INACTIVE);
		if (selector != null) { selector.dispose(); }
	}
}
