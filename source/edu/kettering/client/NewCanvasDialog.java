/*
 *   Class name:        NewCanvasDialog
 *   Contributor(s):    Jeremy Maxey-Vesperman
 *   Modified:          June 4th, 2019
 *   Package:           edu.kettering.client
 *   Purpose:           Class for new canvas dialog box.
 *                      Allows user to specify size and background color of canvas.
 * */
package edu.kettering.client;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class NewCanvasDialog extends JDialog implements ActionListener, ChangeListener {
    /* Class Constants */
    // Possible return values from calling showDialog
    static final int OK_OPTION = 1;
    static final int CANCEL_OPTION = -1;

    // Default spinner values
    private static final int DEFAULT_WIDTH_VALUE = 640;
    private static final int DEFAULT_HEIGHT_VALUE = 480;
    private static final int DEFAULT_R_VALUE = 255;
    private static final int DEFAULT_G_VALUE = 255;
    private static final int DEFAULT_B_VALUE = 255;
    // Default text color for color input label
    private static final Color DEFAULT_COLOR = new Color(DEFAULT_R_VALUE,
            DEFAULT_G_VALUE,
            DEFAULT_B_VALUE);

    /* Instance Variables */
    // Components
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner spinnerWidth;
    private JSpinner spinnerHeight;
    private JSpinner spinnerRed;
    private JSpinner spinnerGreen;
    private JSpinner spinnerBlue;
    private JLabel labelBackColor;
    private JLabel labelHeight;
    private JLabel labelWidth;
    private JLabel labelBlue;
    private JLabel labelGreen;
    private JLabel labelRed;

    private int returnVal;
    private Dimension size;
    private Color bgColor;

    /* Constructors */
    NewCanvasDialog(Component parent) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // Initialize spinners with default values
        spinnerWidth.setValue(DEFAULT_WIDTH_VALUE);
        spinnerHeight.setValue(DEFAULT_HEIGHT_VALUE);
        spinnerRed.setValue(DEFAULT_R_VALUE);
        spinnerGreen.setValue(DEFAULT_G_VALUE);
        spinnerBlue.setValue(DEFAULT_B_VALUE);

        // Initialize text color of label with default color
        labelBackColor.setForeground(DEFAULT_COLOR);

        // Add action listeners to components
        buttonOK.addActionListener(this);
        buttonCancel.addActionListener(this);
        spinnerWidth.addChangeListener(this);
        spinnerHeight.addChangeListener(this);
        spinnerRed.addChangeListener(this);
        spinnerGreen.addChangeListener(this);
        spinnerBlue.addChangeListener(this);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // Center pop-up in center of parent window
        Point parentLocation = parent.getLocationOnScreen();
        Dimension dialogSize = this.getPreferredSize();
        int xPos = (int) (parentLocation.getX() + (parent.getWidth() / 2) - (dialogSize.width / 2));
        int yPos = (int) (parentLocation.getY() + (parent.getHeight() / 2) - (dialogSize.height / 2));
        setLocation(xPos, yPos);
    }

    /* Instance-level Functions/Methods */
    // Performed on OK action
    private void onOK() {
        // Store the canvas dimension specified
        size = new Dimension((int) spinnerWidth.getValue(),
                (int) spinnerHeight.getValue());

        // Store the canvas background color specified
        bgColor = new Color((int) spinnerRed.getValue(),
                (int) spinnerGreen.getValue(),
                (int) spinnerBlue.getValue());

        // Set the return value to the OK option
        returnVal = OK_OPTION;
        // Destroy the modal
        dispose();
    }

    // Performed on CANCEL action
    private void onCancel() {
        // Set the return value to the CANCEL option
        returnVal = CANCEL_OPTION;
        // Destroy the modal
        dispose();
    }

    // Displays dialog box with indicated status
    int showDialog(String title) {
        this.setTitle(title);
        this.pack();
        this.setVisible(true);

        return returnVal;
    }

    /* Getters */
    Dimension getCanvasSize() { return new Dimension(size); }
    Color getBgColor() { return new Color(bgColor.getRGB()); }

    /* Event Handlers */
    // Handle spinners
    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JSpinner) {
            // Dimensions must be > 0
            int width = (int) spinnerWidth.getValue();
            width = (width < 1) ? 1 : width;
            spinnerWidth.setValue(width);

            int height = (int) spinnerHeight.getValue();
            height = (height < 1) ? 1 : height;
            spinnerHeight.setValue(height);

            // RGB values must be between 0-255 inclusive
            int red = (int) spinnerRed.getValue();
            red = (red > 255) ? 255 : (red < 0) ? 0 : red;
            spinnerRed.setValue(red);

            int green = (int) spinnerGreen.getValue();
            green = (green > 255) ? 255 : (green < 0) ? 0 : green;
            spinnerGreen.setValue(green);

            int blue = (int) spinnerBlue.getValue();
            blue = (blue > 255) ? 255 : (blue < 0) ? 0 : blue;
            spinnerBlue.setValue(blue);

            // Set the text color of the label to indicate currently selected color
            Color textColor = new Color(red, green, blue);
            labelBackColor.setForeground(textColor);
        }
    }

    // Handle button input
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals(buttonOK.getActionCommand())) {
            onOK();
        } else if (action.equals(buttonCancel.getActionCommand())) {
            onCancel();
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel2.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(8, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        spinnerWidth = new JSpinner();
        panel3.add(spinnerWidth, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerHeight = new JSpinner();
        panel3.add(spinnerHeight, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        spinnerRed = new JSpinner();
        panel3.add(spinnerRed, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerGreen = new JSpinner();
        panel3.add(spinnerGreen, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerBlue = new JSpinner();
        panel3.add(spinnerBlue, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelRed = new JLabel();
        labelRed.setText("Red");
        panel3.add(labelRed, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelGreen = new JLabel();
        labelGreen.setText("Green");
        panel3.add(labelGreen, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelBlue = new JLabel();
        labelBlue.setText("Blue");
        panel3.add(labelBlue, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel3.add(spacer3, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        labelWidth = new JLabel();
        labelWidth.setText("Width");
        panel3.add(labelWidth, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelHeight = new JLabel();
        labelHeight.setText("Height");
        panel3.add(labelHeight, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelBackColor = new JLabel();
        labelBackColor.setText("Background Color");
        panel3.add(labelBackColor, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
