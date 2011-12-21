package matachi.mapeditor.editor;

/**
 * Information that the GUI has, which its components also need access to. Like
 * the GridView needs to know what tile is selected and should be drawn when
 * you draw on the grid.
 * @author Daniel "MaTachi" Jonsson
 * @version 1
 * @since v0.0.5
 *
 */
public interface GUIInformation {
	
	/**
	 * Get the tile that is selected and should be drawn.
	 * @return Tile The selected tile.
	 */
	public Tile getSelectedTile();
}
