package matachi.mapeditor.editor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class TileManager {

	ArrayList<Tile> tiles;

	/**
	 * The image files to the tiles.
	 */
	private BufferedImage groundImage = null;
	private BufferedImage skyImage = null;
	
	
	public TileManager() {
		tiles = new ArrayList<Tile>();

		/** Initialize the image icons. */
		try {
			groundImage = ImageIO.read(new File("data/groundIcon.png"));
			skyImage = ImageIO.read(new File("data/skyIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		tiles.add(new Tile(skyImage, '0'));
		tiles.add(new Tile(groundImage, '1'));
	}
}
