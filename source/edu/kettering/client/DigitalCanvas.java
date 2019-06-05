// ToDo - Heading

package edu.kettering.client;

import edu.kettering.tools.Tool;
import edu.kettering.tools.copypaste.CopyPaste;
import edu.kettering.tools.filter.Filter;
import edu.kettering.tools.kaleidoscope.Kaleidoscope;
import edu.kettering.tools.stamp.Stamp;
import edu.kettering.tools.histogram.Histogram;
import edu.kettering.tools.colorPicker.ColorSelector;
import edu.kettering.tools.eyeDropper.EyeDropper;
import edu.kettering.tools.eraser.Eraser;
import edu.kettering.tools.caligraphy.Caligraphy;
import edu.kettering.tools.texttool.TextTool;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DigitalCanvas extends JPanel implements ActionListener {
    /* Class Constants */
    private static final String [] IMG_CHOOSER_EXTENSION_DESCRIPTION = {"Images"};
    private static final String [][] IMG_CHOOSER_EXTENSIONS = {{"jpg", "png"}};

    private static final String TITLE_NEW_DIALOG = "New Canvas";
    private static final String TITLE_OPEN_DIALOG = "Select Image to Open";
    private static final String TITLE_SAVE_DIALOG = "Save Image";
    private static final String TITLE_DIALOG_OPEN_WARNING = "Open Image Warning";
    private static final String TITLE_DIALOG_SAVE_WARNING = "Save Image Warning";

    private static final String MSG_ERROR_IMAGE_IO = "Error reading/writing image!";
    private static final String MSG_DIALOG_IMAGE_IO = "Error occurred when opening/saving image.";

    /* Instance Variables */
    private ToolBar menuBar;
    private ToolBar moduleBar;
    private DigitalCanvasState DCS; // Canvas state object

    // Add your tool here. Buttons will appear in the order that the tools are listed.
    private Tool [] tools = {
		    new Eraser(),        // First tool button in list
            new Stamp(), 		 // Second tool button in list
            new Kaleidoscope(),
            new Filter(),
            new CopyPaste(),
            new Histogram(),
            new ColorSelector(),
            new EyeDropper(),
			new Caligraphy(),
            new TextTool(),
                                // ...
    };

    // Array of button action commands to use while generating buttons for menubar
    private String [] menuBarButtonTexts = {
            "New",
            "Open",
            "Save",
    };

    /* Constructors */
    DigitalCanvas(Client parent) {
        this.DCS = new DigitalCanvasState(parent.getSize(), BufferedImage.TYPE_INT_RGB);

        this.setFocusable(true);
    
        this.initToolBars(parent);

        this.addKeyListener(new KeyboardActions(this));
        this.addMouseListener(new MouseActions(this));
        this.addMouseMotionListener(new MouseMotionActions(this));
    
        this.setBackground(Color.WHITE);
    }

    /* Instance-level Functions/Methods */
    private void initToolBars(Client parent) {
        // Generate linked list of buttons for toolbar
        LinkedList<JButton> buttons = new LinkedList<>();

        // Loop through and generate all action commands / buttons for the toolbar
        int i = 0;
        for(Tool tool : this.tools) {
            tool.setButtonAction(Integer.toString(i++));
            buttons.add(tool.getToolButton());
        }

        // Create the menubar and add its buttons
        menuBar = new ToolBar("Menu Bar", SwingConstants.HORIZONTAL);
        menuBar.addMenuButtons(this, menuBarButtonTexts);
        parent.add(menuBar, BorderLayout.PAGE_START);

        // Create the toolbar and add its buttons
        moduleBar = new ToolBar("Module Bar", JToolBar.VERTICAL);
        moduleBar.addModuleButtons(this, buttons);
        parent.add(moduleBar, BorderLayout.EAST);
        
    }

    /* Event Handlers */
    // Handles button presses
    private void handleButtonAction(String action)  {
        try {
            if (!performMenuButtonAction(action)) { // If this is not a menubar button...
                int lastToolId = this.DCS.getSelectedTool();
                int toolID = Integer.parseInt(action);
                // Let last tool do whatever it needs to do when deselected
                if (lastToolId >= 0 && lastToolId < this.tools.length) {
                    this.tools[lastToolId].deselectTool(this.DCS);
                }
                // Set current tool based on button index
                this.DCS.setSelectedTool(toolID);
                // Trigger button action handler for the tool
                this.tools[toolID].buttonActionHandler(this.DCS);
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // Invalid action... shouldn't ever happen unless the menubar switch misses a case
            System.out.println("Invalid action type performed: " + action);
        }
    }

    // Performs the appropriate action based on what menu button was pressed
    private boolean performMenuButtonAction(String action) {
        switch (action) {
            case "New":
                newCanvas();
                break;
            case "Open":
                openImage();
                break;
            case "Save":
                saveImage();
                break;
            default:
                // This action is not a menubar button action
                return false;
        }
        // Action was a menubar button action
        return true;
    }

    // Handles the creation of new canvas of specified size and color
    private void newCanvas() {
        // Create dialog to get input values
        NewCanvasDialog newDialog = new NewCanvasDialog(this.getParent());
        int returnVal = newDialog.showDialog(TITLE_NEW_DIALOG);

        // If OK button was pressed...
        if (returnVal == NewCanvasDialog.OK_OPTION) {
            // Create new buffered image of user-specified size
            BufferedImage img = new BufferedImage(newDialog.getCanvasSize().width,
                    newDialog.getCanvasSize().height,
                    BufferedImage.TYPE_INT_RGB);
            // Update the state object with this new canvas
            this.DCS.updateCanvasImg(img);
            // Wipe this canvas to the user-specified color
            this.DCS.wipeCanvasColor(newDialog.getBgColor());

            // Redraw parent component in case canvas is smaller than previous
            this.getParent().repaint();
        }
    }

    // Handles opening an image for editing
    private void openImage() {
        // Create file chooser
        JFileChooser imageChooser = createFileChooser(IMG_CHOOSER_EXTENSION_DESCRIPTION,
                IMG_CHOOSER_EXTENSIONS,
                TITLE_OPEN_DIALOG);

        // Get user to specify a file to open
        int returnVal = imageChooser.showOpenDialog(this.getParent());

        // If user clicked the OPEN button...
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // Attempt to read the image in...
            File file = imageChooser.getSelectedFile();
            try {
                BufferedImage canvasImg = ImageIO.read(file);

                if (file.getName().endsWith(".png")) { // Remove alpha channel
                    BufferedImage tmp = new BufferedImage(canvasImg.getWidth(), canvasImg.getHeight(), BufferedImage.TYPE_INT_RGB);
                    tmp.createGraphics().drawImage(canvasImg, 0, 0, Color.WHITE, null);
                    canvasImg = tmp;
                }

                // Update canvas to new image
                DCS.updateCanvasImg(canvasImg);

                // Redraw parent component in case canvas is smaller than previous
                this.getParent().repaint();
            } catch (IOException e) {
                System.out.println(MSG_ERROR_IMAGE_IO);
                JOptionPane.showMessageDialog(null, MSG_DIALOG_IMAGE_IO, TITLE_DIALOG_OPEN_WARNING, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Handles saving current canvas to a file
    private void saveImage() {
        // Create file chooser
        JFileChooser saveChooser = createFileChooser(IMG_CHOOSER_EXTENSION_DESCRIPTION,
                IMG_CHOOSER_EXTENSIONS,
                TITLE_SAVE_DIALOG);

        // Get user to specify name to save file as
        int returnVal = saveChooser.showSaveDialog(this.getParent());

        // If they clicked SAVE...
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File saveFile = saveChooser.getSelectedFile();

            // Check that user actually set a name
            if (saveFile == null) { return; }

            // Append file extension if they didn't add it
            if (!saveFile.getName().toLowerCase().endsWith(".png")) {
                saveFile = new File(saveFile.getParent(), saveFile.getName() + ".png");
            }

            // Try to write canvas to png file
            try {
                ImageIO.write(this.DCS.getCanvasImg(), "png", saveFile);
            } catch (IOException e) {
                System.out.println(MSG_ERROR_IMAGE_IO);
                JOptionPane.showMessageDialog(null, MSG_DIALOG_IMAGE_IO, TITLE_DIALOG_SAVE_WARNING, JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    // Generates a file chooser with specified extensions and title
    private JFileChooser createFileChooser(String [] extensionsDescriptions, String [][] extensions, String title) {
        JFileChooser chooser = new JFileChooser();

        int extIdx = 0;
        for (String extensionsDescription : extensionsDescriptions) {
            if (extensions[extIdx] != null) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        extensionsDescription,
                        extensions[extIdx++]
                );
                chooser.setFileFilter(filter);
            }
        }

        chooser.setDialogTitle(title);

        return chooser;
    }

    @Override //repaint() -> paintComponents()
    protected void paintComponent(Graphics g) {
        g.drawImage(this.DCS.getCanvasImg(), 0, 0, null);
    }

    /* Event Handlers */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.handleButtonAction(e.getActionCommand());

        this.repaint();
    }
    
    class KeyboardActions extends KeyAdapter {
        DigitalCanvas canvas;
        
        KeyboardActions(DigitalCanvas canvas) {
            this.canvas = canvas;
        }
        
        @Override
        public void keyPressed(KeyEvent ke) {
            this.canvas.DCS.updateKeyCode(ke.getKeyCode()); // update key pressed
            int toolID = this.canvas.DCS.getSelectedTool(); // get current tool id

            try {
                Tool currTool = this.canvas.tools[toolID]; // attempt to access tool at this id
                currTool.keyPressedHandler(this.canvas.DCS); // call key pressed handler
            } catch (ArrayIndexOutOfBoundsException e) { // non-existent tool id
                System.out.println("Invalid tool currently selected. Ignoring key press input.");
            }

            this.canvas.repaint();
        }
    }
    
    class MouseActions extends MouseAdapter {
        DigitalCanvas canvas;

        MouseActions(DigitalCanvas canvas) {
            this.canvas = canvas;
        }

        @Override
        public void mousePressed(MouseEvent me) {
            this.canvas.DCS.updateMouseCoords(me.getPoint()); // update mouse coordinate
            int toolID = this.canvas.DCS.getSelectedTool(); // get current tool id

            try {
                Tool currTool = this.canvas.tools[toolID]; // attempt to access tool at this id

                switch (me.getButton()) {
                    case MouseEvent.BUTTON1: // left button pressed
                        currTool.mouseButton1PressedHandler(this.canvas.DCS);
                        break;
                    case MouseEvent.BUTTON2: // right button pressed
                        currTool.mouseButton2PressedHandler(this.canvas.DCS);
                        break;
                    case MouseEvent.BUTTON3: // middle button pressed
                        currTool.mouseButton3PressedHandler(this.canvas.DCS);
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException e) { // non-existent tool id
                System.out.println("Invalid tool currently selected. Ignoring mouse pressed input.");
            }

            this.canvas.repaint();

            this.canvas.requestFocusInWindow();
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            this.canvas.DCS.updateMouseCoords(me.getPoint()); // update mouse coordinate
            int toolID = this.canvas.DCS.getSelectedTool(); // get current tool id

            try {
                Tool currTool = this.canvas.tools[toolID]; // attempt to access tool at this id

                switch(me.getButton()) {
                    case MouseEvent.BUTTON1: // left button released
                        currTool.mouseButton1ReleasedHandler(this.canvas.DCS);
                        break;
                    case MouseEvent.BUTTON2: // right button released
                        currTool.mouseButton2ReleasedHandler(this.canvas.DCS);
                        break;
                    case MouseEvent.BUTTON3: // middle button released
                        currTool.mouseButton3ReleasedHandler(this.canvas.DCS);
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException e) { // non-existent tool id
                System.out.println("Invalid tool currently selected. Ignoring mouse released input.");
            }

            this.canvas.repaint();
        }
    }

    class MouseMotionActions implements MouseMotionListener {
        DigitalCanvas canvas;

        MouseMotionActions(DigitalCanvas canvas) {
            this.canvas = canvas;
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            this.canvas.DCS.updateMouseCoords(me.getPoint()); // update mouse coordinate
            int toolID = this.canvas.DCS.getSelectedTool(); // get current tool id

            try {
                Tool currTool = this.canvas.tools[toolID]; // attempt to access tool at this id

                if (SwingUtilities.isLeftMouseButton(me)) {  // dragging on left button
                    currTool.mouseButton1DraggedHandler(this.canvas.DCS);
                } else if (SwingUtilities.isRightMouseButton(me)) { // dragging on right button
                    currTool.mouseButton2DraggedHandler(this.canvas.DCS);
                } else if (SwingUtilities.isMiddleMouseButton(me)) { // dragging on middle button
                    currTool.mouseButton3DraggedHandler(this.canvas.DCS);
                }
            } catch (ArrayIndexOutOfBoundsException e) { // non-existent tool id
                System.out.println("Invalid tool currently selected. Ignoring mouse dragged input.");
            }

            this.canvas.repaint();

            this.canvas.requestFocusInWindow();
        }

        @Override
        public void mouseMoved(MouseEvent e) {}
    }
}