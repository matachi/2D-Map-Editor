package matachi.mapeditor.editor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class supports the Tile list with methods.
 * @author Daniel "MaTachi" Jonsson
 * @version 1
 * @since v0.0.5
 *
 */
public class TileManager {

	/**
	 * Returns a list with Tiles, constructed with images from the given folderPath.
	 * @param folderPath Path to image folder.
	 * @return List<Tile> List of tiles.
	 */
	public static List<Tile> getTilesFromFolder(final String folderPath) {
		
		List<Tile> tiles = new ArrayList<Tile>();

		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		
		int character = 48;
		
		Map<String, File> map = new TreeMap<String, File>();

		for (File f : listOfFiles) {
			map.put(f.getName(), f);
		}
		
		for (File f : map.values()) {
			tiles.add(new Tile(f.getPath(), (char)character++));
		}
		
		return tiles;
	}
}
