package matachi.mapeditor.editor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

public class TileManager {

	ArrayList<Tile> tiles;
	
	/**
	 * Constructs a TileManager and load it with images from folderPath.
	 * @param folderPath Path to image folder.
	 */
	public TileManager(final String folderPath) {
		
		tiles = new ArrayList<Tile>();

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
			System.out.println((char)character);
		}
		
//		for (int i = 0; i < 1000; i++) {
//			System.out.println(i + ". " + (char)i);
//		}
	}

	/**
	 * Get Tile with index number x.
	 * @param index Index number.
	 * @return Tile The Tile.
	 */
	public Tile get(final int index) {
		return tiles.get(index);
	}

	/**
	 * The amount of Tiles in the TileManager.
	 * @return int Size of TileManager.
	 */
	public int size() {
		return tiles.size();
	}

	/**
	 * Iterator.
	 * @return Iterator<Tile> Iterator.
	 */
	public Iterator<Tile> iterator() {
		return tiles.iterator();
	}
}
