package matachi.mapeditor.grid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JPanel;

import matachi.mapeditor.editor.Tile;

public class GridView extends JPanel implements PropertyChangeListener {

	private static final long serialVersionUID = -345930170664066299L;
	
	/**
	 * A reference to the model. Needed to query data.
	 */
	private Camera camera;
	
	/**
	 * References to all tiles.
	 */
	private GridTile[][] map;
	
	private List<? extends Tile> tiles;
	
	/**
	 * 
	 * @param controller
	 * @param camera
	 */
	public GridView(Grid grid, final int x, final int y, List<? extends Tile> tiles) {
		super(new GridLayout(y, x));
		
		this.tiles = tiles;
		
		this.camera = new GridCamera(grid, x, y);
		GridController controller = new GridController(this, grid, camera);
		this.addMouseListener(controller);
		this.addMouseMotionListener(controller);
		
		/** Add all tiles to the grid. */
		map = new GridTile[y][x];
		for (int y_ = 0; y_ < y; y_++) {
			for (int x_ = 0; x_ < x; x_++) {
				map[y_][x_] = new GridTile(tiles.get(0));
				map[y_][x_].addKeyListener(controller);
				map[y_][x_].setFocusable(true);
				this.add(map[y_][x_]);
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
				for (Tile t : tiles) {
					if (camera.getTile(x, y) == t.getCharacter()) {
						map[y][x].setTile(t);
						map[y][x].grabFocus();
					}
				}
			}
		}
		this.repaint();
//		frame.pack();
	}
	
	private void redrawTile(Point p) {
		for (Tile t : tiles) {
			if (camera.getTile(p.x, p.y) == t.getCharacter()) {
				map[p.y][p.x].setTile(t);
			}
		}
	}
	
	private class GridTile extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 8127828009105626334L;
		
		private Tile tile;
		
		/**
		 * Construct a tile.
		 * @param icon The icon of the tile.
		 * @param character The character that will represent the tile when saved.
		 */
		public GridTile(Tile tile) {
			this.tile = tile;
		}

		public void setTile(Tile tile) {
			this.tile = tile;
			this.repaint();
		}

//		public JPanel getIcon() {
////			return (JPanel) icon.clone();
//			return icon;
//		}
//		
//		/**
//		 * Change the icon.
//		 * @param icon The new icon.
//		 */
//		public void setIcon(BufferedImage icon) {
//			this.icon.icon = icon;
//			this.icon.repaint();
//		}
//
//		public void flipGrid() {
//			this.icon.showingGrid = !this.icon.showingGrid;
//			this.icon.repaint();
//		}
//		
//		public char getCharacter() {
//			return character;
//		}
//
//		public void setCharacter(char character) {
//			this.character = character;
//		}

		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(tile.getImage(), 0, 0, null);
			g.setColor(Color.DARK_GRAY);
			g.drawRect(0, 0, 30, 30);
//			if (showingGrid) {
//				g.drawRect(0, 0, 30, 30);
//			}
		}
	}
}
