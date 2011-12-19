package matachi.mapeditor.editor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

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
			BufferedImage icon = null;
			try {
				icon = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
			tiles.add(new Tile(icon, (char)character++));
		}
		
		return tiles;
	}
}
