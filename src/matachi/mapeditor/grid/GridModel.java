package matachi.mapeditor.grid;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * An implementation of the interface Grid. It stores characters two
 * dimensionally.
 * @author Daniel "MaTachi" Jonsson
 * @version 1
 * @since v0.0.5
 *
 */
public class GridModel implements Grid {

	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	/**
	 * The 2D array that describes the editor's 2D map.
	 */
	private char[][] map;
	
	/**
	 * Announce changes.
	 */
	private PropertyChangeSupport changeSupport;
	
	/**
	 * Constructs the model with number of rows and columns.
	 * @param cols The number of columns.
	 * @param rows The number of rows.
	 */
	public GridModel(int columns, int rows) {
		changeSupport = new PropertyChangeSupport(this);
		map = createEmptyMap(columns, rows);
	}

	/**
	 * {@inheritDoc}
	 */
	public int getWidth() {
		return map[0].length;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getHeight() {
		return map.length;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setTile(int x, int y, char c) {
		map[y][x] = c;
		firePropertyChange();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public char getTile(int x, int y) {
		return map[y][x];
	}
	
	/**
	 * {@inheritDoc}
	 */
	public char[][] getMap() {
		char[][] tmpMap = new char[map.length][map[0].length];
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				tmpMap[y][x] = map[y][x];
			}
		}
		return tmpMap;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void expandMap(int n, int direction) {
		char[][] tmpMap;
		int northOffset = 0;
		int eastOffset = 0;
		int southOffset = 0;
		int westOffset = 0;
		
		if (direction == GridModel.NORTH) {
			northOffset = n;
		} else if (direction == GridModel.WEST) {
			westOffset = n;
		} else if (direction == GridModel.SOUTH) {
			southOffset = n;
		} else if (direction == GridModel.EAST) {
			eastOffset = n;
		} else {
			throw new IllegalArgumentException("Bad direction.");
		}

		int heightTmpMap = getWidth() + westOffset + eastOffset;
		int widthTmpMap = getHeight() + northOffset + southOffset;
		tmpMap = createEmptyMap(heightTmpMap, widthTmpMap);

		// Copy the old map's data to the new one.
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				tmpMap[y + northOffset][x + westOffset] = map[y][x];
			}
		}
		
		map = tmpMap;

		firePropertyChange();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void fillMap(char[][] map, char character) {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				map[y][x] = character;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getMapAsString() {
		String s = "";
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				s += map[y][x];
			}
			s += "\n";
		}
		return s;
	}
	
	/**
	 * Returns a new map filled with zeros.
	 * @param columns The number of columns.
	 * @param rows The number of rows.
	 * @return char[][] A 2D array of characters (zeros).
	 */
	private char[][] createEmptyMap(int columns, int rows) {
		char[][] tmpMap = new char[rows][columns];
		fillMap(tmpMap, '0');
		return tmpMap;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}
	
	private void firePropertyChange() {
		changeSupport.firePropertyChange("model", false, true);
	}
}
