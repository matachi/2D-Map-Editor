package matachi.mapeditor.editor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
		
		for (File f : listOfFiles) {
			tiles.add(new Tile(f.getPath(), (char)character++));
		}
		
		return tiles;
	}
}
