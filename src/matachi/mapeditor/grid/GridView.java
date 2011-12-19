package matachi.mapeditor.grid;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import matachi.mapeditor.editor.Tile;

public class GridView extends JPanel implements PropertyChangeListener {

	private static final long serialVersionUID = -345930170664066299L;

	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	/**
	 * A reference to the model. Needed to query data.
	 */
	private Camera camera;
	
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
	 * 
	 * @param controller
	 * @param camera
	 */
	public GridView(Grid grid, final int x, final int y) {
		super(new GridLayout(y, x));
		
		this.camera = new GridCamera(grid, x, y);
		GridController controller = new GridController(this, grid, camera);
		this.addMouseListener(controller);
		this.addMouseMotionListener(controller);
		
		/** Initialize the image icons. */
		try {
			groundImage = ImageIO.read(new File("data/groundIcon.png"));
			skyImage = ImageIO.read(new File("data/skyIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/** Add all tiles to the grid. */
		map = new Tile[y][x];
		for (int y_ = 0; y_ < y; y_++) {
			for (int x_ = 0; x_ < x; x_++) {
				map[y_][x_] = new Tile(skyImage, '0');
				map[y_][x_].getIcon().addKeyListener(controller);
				map[y_][x_].getIcon().setFocusable(true);
				this.add(map[y_][x_].getIcon());
			}
		}
	}
	
	/**
	 * Update the grid.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("movedCamera")) {
			redrawGrid();
		} else if (evt.getPropertyName().equals("changedTile")) {
			redrawTile((Point) evt.getNewValue());
		}
	}
	
	private void redrawGrid() {
		for (int y = 0; y < 20; y++) {
			for (int x = 0; x < 32; x++) {
				if (camera.getTile(x, y) == '0') {
					map[y][x].setIcon(skyImage);
				} else if (camera.getTile(x, y) == '1') {
					map[y][x].setIcon(groundImage);
				}
				map[y][x].getIcon().grabFocus();
			}
		}
		this.repaint();
//		frame.pack();
	}
	
	private void redrawTile(Point p) {
		if (camera.getTile(p.x, p.y) == '0') {
			map[p.y][p.x].setIcon(skyImage);
		} else if (camera.getTile(p.x, p.y) == '1') {
			map[p.y][p.x].setIcon(groundImage);
		}
	}
}
