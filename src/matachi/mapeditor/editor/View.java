package matachi.mapeditor.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import matachi.mapeditor.grid.Camera;


/**
 * The view of the application.
 * 
 * @author Daniel "MaTachi" Jonsson
 * @version 1
 * @since v0.0.5
 * 
 */
public class View {

	/**
	 * The JFrame.
	 */
	private JFrame frame;

	/**
	 * Settings to the right.
	 */
	// private JButton showGridButton;

	// private boolean showingGrid;

	private JTextField txtWidth;
	private JTextField txtHeight;

	/**
	 * Constructs the View.
	 * 
	 * @param controller
	 *            The controller.
	 * @param model
	 *            The model.
	 */
	public View(Controller controller, Camera camera, JPanel grid,
			List<? extends Tile> tiles) {

		// showingGrid = true;

		grid.setPreferredSize(new Dimension(Constants.GRID_WIDTH
				* Constants.TILE_WIDTH, Constants.GRID_HEIGHT
				* Constants.TILE_HEIGHT));

		/** Create the bottom panel. */
		JPanel palette = new JPanel(new FlowLayout());
		for (Tile t : tiles) {
			JButton button = new JButton();
			button.setPreferredSize(new Dimension(Constants.TILE_WIDTH,
					Constants.TILE_HEIGHT));
			button.setIcon(t.getIcon());
			button.addActionListener(controller);
			button.setActionCommand(Character.toString(t.getCharacter()));
			palette.add(button);
		}

		/** Create the right panel. */
		// showGridButton = new JButton("Hide grid");
		// showGridButton.addActionListener(controller);
		// showGridButton.setActionCommand("flipGrid");

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(controller);
		saveButton.setActionCommand("save");

		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(controller);
		loadButton.setActionCommand("load");

		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		Border border = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		right.setBorder(border);
		// right.add(showGridButton);
		right.add(saveButton);
		right.add(loadButton);

		/** The top panel, that shows coordinates and stuff. */
		CameraInformationLabel cameraInformationLabel = new CameraInformationLabel(
				camera);
		GridMouseInformationLabel mouseInformationLabel = new GridMouseInformationLabel(
				grid);

		JLabel lblWidth = new JLabel("Width(min:32):");
		JLabel lblHeight = new JLabel("Height(min:20):");

		txtWidth = new JTextField(Constants.MAP_WIDTH + "", 3);
		txtWidth.getDocument().addDocumentListener(controller.updateSizeFields);

		txtHeight = new JTextField(Constants.MAP_HEIGHT + "", 3);
		txtHeight.getDocument()
				.addDocumentListener(controller.updateSizeFields);

		JButton updateSize = new JButton("Update/Reset");
		updateSize.addActionListener(controller);
		updateSize.setActionCommand("update");

		JPanel top = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.LEFT));
		top.setBorder(border);
		top.add(cameraInformationLabel);
		top.add(mouseInformationLabel);
		top.add(lblWidth);
		top.add(txtWidth);
		top.add(lblHeight);
		top.add(txtHeight);
		top.add(updateSize);

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
		frame.add(layout);
		frame.pack();
		frame.setVisible(true);
	}

	public int getWidth() {
		String value = txtWidth.getText();
		if (value.equals("")) {
			value = Constants.MAP_WIDTH + "";
		}
		return Integer.parseInt(value);
	}

	public int getHeight() {
		String value = txtHeight.getText();
		if (value.equals("")) {
			value = Constants.MAP_WIDTH + "";
		}
		return Integer.parseInt(value);
	}
	
	public void setSize(int width, int height){
		txtWidth.setText(width+"");
		txtHeight.setText(height+"");
	}

	public void close() {
		frame.setVisible(false);
	}
	// /**
	// * Flip the grid on or off.
	// */
	// public void flipGrid() {
	// this.showingGrid = !this.showingGrid;
	// if (!showingGrid) {
	// showGridButton.setText("Show grid");
	// for (int y = 0; y < 20; y++) {
	// for (int x = 0; x < 32; x++) {
	// map[y][x].flipGrid();
	// }
	// }
	// } else {
	// showGridButton.setText("Hide grid");
	// for (int y = 0; y < 20; y++) {
	// for (int x = 0; x < 32; x++) {
	// map[y][x].flipGrid();
	// }
	// }
	// }
	// frame.repaint();
	// }
}
