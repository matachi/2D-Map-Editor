package matachi.mapeditor.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import matachi.mapeditor.grid.Camera;
import matachi.mapeditor.grid.Grid;
import matachi.mapeditor.grid.GridView;

public class View implements PropertyChangeListener {
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	/**
	 * A reference to the model. Needed to query data.
	 */
	private Camera camera;
	
	/**
	 * The JFrame.
	 */
	private JFrame frame;
	
	/**
	 * References to all tiles.
	 */
	private Tile[][] map;
	
	/**
	 * The image files to the tiles.
	 */
	private BufferedImage groundImage = null;
	private BufferedImage skyImage = null;
	
	/**
	 * Settings to the right.
	 */
	private JButton showGridButton;
	private JButton drawModeButton;
	
	private boolean showingGrid;
	private boolean drawingMode;

	private JLabel viewInformation;
	private JLabel mousePosition;
	
	/**
	 * Constructs the View.
	 * @param controller The controller.
	 * @param model The model.
	 */
	public View(Controller controller, Grid gridModel) {
		
		drawingMode = true;
		showingGrid = true;
		this.camera = camera;
		
		JPanel grid = new GridView(gridModel, 32, 20); // Every tile is 30x30 pixels
		grid.setPreferredSize(new Dimension(960, 600));
		grid.addMouseListener(controller);
		grid.addMouseMotionListener(controller);
		
		/** Create the bottom panel. */
		JButton airButton = new JButton();
		airButton.setPreferredSize(new Dimension(30, 30));
		airButton.setIcon(new ImageIcon("data/skyIcon.png"));
		airButton.addActionListener(controller);
		airButton.setActionCommand("sky");
		
		JButton groundButton = new JButton();
		groundButton.setPreferredSize(new Dimension(30, 30));
		groundButton.setIcon(new ImageIcon("data/groundIcon.png"));
		groundButton.addActionListener(controller);
		groundButton.setActionCommand("ground");
		
		JPanel palette = new JPanel(new FlowLayout());
		palette.add(airButton);
		palette.add(groundButton);
		
		/** Create the right panel. */
		showGridButton = new JButton("Hide grid");
		showGridButton.addActionListener(controller);
		showGridButton.setActionCommand("flipGrid");

		drawModeButton = new JButton("Draw mode OFF");
		drawModeButton.setPreferredSize(new Dimension(200, 20));
		drawModeButton.addActionListener(controller);
		drawModeButton.setActionCommand("flipDraw");
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(controller);
		saveButton.setActionCommand("save");
		
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		Border border = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		right.setBorder(border);
		right.add(showGridButton);
		right.add(drawModeButton);
		right.add(saveButton);
		
		/** The top panel, that shows coordinates and stuff. */
		viewInformation = new JLabel();
//		viewInformation.setText("Showing: 0 - 32/" + camera.getWidth()
//				+ ", 0 - 20/" + camera.getHeight());
		viewInformation.setPreferredSize(new Dimension(220, 15));
		viewInformation.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		
		mousePosition = new JLabel();
		
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.LEFT));
		top.setBorder(border);
		top.add(viewInformation);
		top.add(mousePosition);
		
		JPanel layout = new JPanel(new BorderLayout());
		JPanel test = new JPanel(new BorderLayout());
		test.add(top, BorderLayout.NORTH);
		test.add(grid, BorderLayout.WEST);
		test.add(right, BorderLayout.CENTER);
		layout.add(test, BorderLayout.NORTH);
		layout.add(palette, BorderLayout.CENTER);

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Map Editor");
//		frame.setExtendedState(frame.NORMAL);
		frame.add(layout);
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Flip the grid on or off.
	 */
	public void flipGrid() {
		this.showingGrid = !this.showingGrid;
		if (!showingGrid) {
			showGridButton.setText("Show grid");
			for (int y = 0; y < 20; y++) {
				for (int x = 0; x < 32; x++) {
					map[y][x].flipGrid();
				}
			}
		} else {
			showGridButton.setText("Hide grid");
			for (int y = 0; y < 20; y++) {
				for (int x = 0; x < 32; x++) {
					map[y][x].flipGrid();
				}
			}
		}
		frame.repaint();
	}
	
	/**
	 * Change the button that indicates if the drawing mode is on or off.
	 */
	public void flipDraw() {
		this.drawingMode = !this.drawingMode;
		if (!drawingMode) {
			drawModeButton.setText("Draw mode ON");
		} else {
			drawModeButton.setText("Draw mode OFF");
		}
	}
	
	public void updateMousePosition(int x, int y) {
		mousePosition.setText("Mouse: (" + x + ", " + y + "), Hovering tile: (" + (x/30+1) + ", " + (y/30+1) + ")");
	}
	
	public void updateCameraPosition() {
		viewInformation.setText("Showing: " + camera.getX() + " - "
				+ (camera.getX() + camera.getWidth()) + "/"
				+ camera.getModelWidth() + ", " + camera.getY() + " - "
				+ (camera.getY() + camera.getHeight()) + "/"
				+ camera.getModelHeight());
	}
	
	/**
	 * Update the whole interface.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		//TODO
	}
}
