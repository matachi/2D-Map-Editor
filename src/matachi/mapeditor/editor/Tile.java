package matachi.mapeditor.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Tile {

	/**
	 * The character that will be used in the map file when saved.
	 */
	private char character;

	/**
	 * The icon that will be used in the editor.
	 */
	private Icon icon;
	
	/**
	 * Construct a tile.
	 * @param icon The icon of the tile.
	 * @param character The character that will represent the tile when saved.
	 */
	public Tile(BufferedImage icon, char character) {
		this.icon = new Icon(icon);
		this.setCharacter(character);
	}

	public JPanel getIcon() {
//		return (JPanel) icon.clone();
		return icon;
	}
	
	/**
	 * Change the icon.
	 * @param icon The new icon.
	 */
	public void setIcon(BufferedImage icon) {
		this.icon.icon = icon;
		this.icon.repaint();
	}

	public void flipGrid() {
		this.icon.showingGrid = !this.icon.showingGrid;
		this.icon.repaint();
	}
	
	public char getCharacter() {
		return character;
	}

	public void setCharacter(char character) {
		this.character = character;
	}

	/**
	 * The tile that will be used in the editor.
	 */
	private class Icon extends JPanel implements Cloneable {

		/**
		 * Serial something...
		 */
		private static final long serialVersionUID = 8886201740414079843L;

		/**
		 * The icon that will be used in the editor.
		 */
		private BufferedImage icon;

		private boolean showingGrid = true;
		
		/**
		 * Construct the icon.
		 * @param icon
		 */
		public Icon(BufferedImage icon) {
			this.icon = icon;
		}
		
		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(icon, 0, 0, null);
			g.setColor(Color.DARK_GRAY);
			if (showingGrid) {
				g.drawRect(0, 0, 30, 30);
			}
		}
		
		public Object clone() {
			try {
				return super.clone();
			} catch (CloneNotSupportedException e) {
				return null;
			}
		}
	}
}
