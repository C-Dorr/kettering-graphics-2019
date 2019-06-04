// ToDo - Header

package edu.kettering.tools.filter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

// ToDo - Comments
public class FilterSelectorWindow extends JFrame{
	private static final String[] filters = {"Sharpen", "Edge", "Sepia", "Emboss"};

	private JComboBox<String> box;
	private JButton applyButton;
	private Filter filter;

	FilterSelectorWindow(Filter filter) {
		super("Filter Select");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.filter = filter;
		setLayout(new FlowLayout());
		box = new JComboBox<>(filters);
		box.addItemListener(
				new ItemListener() {
					public void itemStateChanged(ItemEvent event) {
						if(event.getStateChange() == ItemEvent.SELECTED) {
							filter.setFilter((String) box.getSelectedItem());
						} 
					}
				});
		add(box);
		applyButton = new JButton("Apply");
		applyButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						filter.applyFilter();
					}
			
		});
		add(applyButton);
		setSize(200,200);
		setLocation(200,200);
		setVisible(true);
	}
	
	@Override
	public void dispose() {
		filter.selector = null;
		super.dispose();
	}
}
