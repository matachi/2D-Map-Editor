package matachi.mapeditor.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import matachi.mapeditor.grid.Grid;
import matachi.mapeditor.grid.GridView;

public class View implements PropertyChangeListener {
	
	/**
	 * The JFrame.
	 */
	private JFrame frame;
	
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
	public View(Controller controller, Grid gridModel, List<? extends Tile> tiles) {
		
		drawingMode = true;
		showingGrid = true;
		
		JPanel grid = new GridView(controller, gridModel, 32, 20, tiles); // Every tile is 30x30 pixels
		grid.setPreferredSize(new Dimension(960, 600));
		grid.addMouseListener(controller);
		grid.addMouseMotionListener(controller);
		
		/** Create the bottom panel. */
		JPanel palette = new JPanel(new FlowLayout());
		for (Tile t : tiles) {
			JButton button = new JButton();
			button.setPreferredSize(new Dimension(30, 30));
			button.setIcon(t.getIcon());
			button.addActionListener(controller);
			button.setActionCommand(Character.toString(t.getCharacter()));
			palette.add(button);
		}
		
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
	
//	/**
//	 * Flip the grid on or off.
//	 */
//	public void flipGrid() {
//		this.showingGrid = !this.showingGrid;
//		if (!showingGrid) {
//			showGridButton.setText("Show grid");
//			for (int y = 0; y < 20; y++) {
//				for (int x = 0; x < 32; x++) {
//					map[y][x].flipGrid();
//				}
//			}
//		} else {
//			showGridButton.setText("Hide grid");
//			for (int y = 0; y < 20; y++) {
//				for (int x = 0; x < 32; x++) {
//					map[y][x].flipGrid();
//				}
//			}
//		}
//		frame.repaint();
//	}
	
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
	
	/**
	 * Update the whole interface.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		//TODO
	}
}
