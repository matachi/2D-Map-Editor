package matachi.mapeditor.editor;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class Tile {

	/**
	 * The character that will be used in the map file when saved.
	 */
	private char character;

	/**
	 * The icon that will be used in the editor.
	 */
	private BufferedImage icon;
	
	/**
	 * Construct a tile.
	 * @param icon The icon of the tile.
	 * @param character The character that will represent the tile when saved.
	 */
	public Tile(final BufferedImage icon, final char character) {
		this.icon = deepCopy(icon);
		this.character = character;
	}

	/**
	 * Get the icon.
	 * @return BufferedImage The tile icon.
	 */
	public BufferedImage getIcon() {
		return deepCopy(icon);
	}
	
	/**
	 * Get the character.
	 * @return char The tile character.
	 */
	public char getCharacter() {
		return character;
	}
	
	private static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
}
