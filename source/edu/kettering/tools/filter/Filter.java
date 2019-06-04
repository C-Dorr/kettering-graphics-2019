package edu.kettering.tools.filter;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import edu.kettering.client.DigitalCanvasState;
import edu.kettering.tools.Tool;
public class Filter extends Tool {
	FilterSelectorWindow selector; 
	Kernel kernel;
	float[] activeKernel;
	BufferedImage image;
	DigitalCanvasState dcs;
	public Filter() {
		 toolButton = new FilterButton();
	     toolButton.setBackground(COLOR_BUTTON_INACTIVE);
	     activeKernel = kernels[0];
	     kernel = new Kernel(3,3,activeKernel);
	}
	
	
	private void selectFilter() {
		if(selector == null)
			selector = new FilterSelectorWindow(this);
		
	}
	
	public void setFilter(String name) {
		if(name.equals("sharpen")) {
			 activeKernel = kernels[0];
		} else if(name.equals("edge")) {
			 activeKernel = kernels[1];
		} else if(name.equals("sepia")) {
			 activeKernel = kernels[2];
		} else {
			 activeKernel = kernels[3];
		}
		kernel = new Kernel(3,3,activeKernel);
	}
	 public void buttonActionHandler(DigitalCanvasState dcs) { 
		 image = dcs.getCanvasImg();
		 this.dcs = dcs;
		 selectFilter();
	}
	 
	 public void applyFilter() {
		 image = dcs.getCanvasImg();
		BufferedImage rtrn = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		cop.filter(image, rtrn);
		dcs.updateCanvasImg(rtrn);
		image = rtrn;
	 }
	 
	 private float[][] kernels  = {
			 {0.f, -1.f, 0.f, -1.f, 5.0f, -1.f, 0.f, -1.f, 0.f}, 
			 { -1, -1, -1, -1, 8, -1, -1, -1, -1 },  
			 { 0.272f, 0.534f, 0.131f, 0.349f, 0.686f, 0.168f, 0.393f, 0.769f, 0.189f },  
			 {-1, -1,  0, -1,  0,  1,  0,  1,  1}
	};
}
