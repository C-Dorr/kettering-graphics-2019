package edu.kettering.tools.filter;

import javax.swing.JButton;

public class FilterButton extends JButton {
	
	 FilterButton() {
		setText("Filter");
		this.setActionCommand("Filter");
        this.setToolTipText("Apply Filter");
	}

}
