package matachi.mapeditor.grid;

import java.beans.PropertyChangeListener;

public interface Grid {
	
	/**
	 * Returns the width of the map.
	 * @return int The width of the total map.
	 */
	public int getWidth();

	/**
	 * Returns the height of the total map.
	 * @return int The height of the map.
	 */
	public int getHeight();

	/**
	 * Set the value of a tile.
	 * @param x The X-coordinate.
	 * @param y The Y-coordinate.
	 * @param c The character that should be added to the position. 
	 */
	public void setTile(int x, int y, char c);
	
	/**
	 * The the value of a tile.
	 * @param x The X-coordinate.
	 * @param y The Y-coordinate.
	 * @return char The character on the tile.
	 */
	public char getTile(int x, int y);
	
	/**
	 * Returns a copy of the whole map.
	 * @return char[][] A copy of the map.
	 */
	public char[][] getMap();
	
	/**
	 * Expand the map n number of rows or columns in a given direction.
	 * @param n Number of rows/columns that should be added to the model.
	 * @param direction The direction that the map should be expanded in. Use
	 * the public class constants NORTH, EAST, SOUTH and WEST for this.
	 */
	public void expandMap(int n, int direction);
	
	/**
	 * Fill the whole map with a specified character.
	 * @param map The char[][] map.
	 * @param character The char.
	 */
	public void fillMap(char[][] map, char character);
	
	/**
	 * Returns the map as a string.
	 * @return String The map as a string.
	 */
	public String getMapAsString();
	
	/**
	 * Add a listener to the model.
	 * @param listener The listener.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener);

	/**
	 * Remove a listener from the model.
	 * @param listener The listener.
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener);
}
