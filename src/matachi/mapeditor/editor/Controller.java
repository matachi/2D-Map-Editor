package matachi.mapeditor.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import matachi.mapeditor.grid.Grid;
import matachi.mapeditor.grid.GridModel;

/**
 * Controller of the application.
 * @author Daniel "MaTachi" Jonsson
 * @version 1
 * @since v0.0.5
 *
 */
public class Controller implements MouseListener, MouseMotionListener, ActionListener, KeyListener, GUIInformation {

	/**
	 * The GUI of the map editor.
	 */
	private final View view;

	/**
	 * The model of the map editor.
	 */
	private Grid model;
	
	private Tile selectedTile;
	
	private List<Tile> tiles;
	
	/**
	 * Construct the controller.
	 */
	public Controller() {
		this.tiles = TileManager.getTilesFromFolder("data/");
		this.model = new GridModel(42, 30);
		this.view = new View(this, model, tiles);
		this.model.addPropertyChangeListener(view);
	}

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }
	
	/**
	 * If a mouse button is clicked.
	 */
	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	/**
	 * If the user keeps the mouse button pressed it will keep drawing if it is
	 * in drawing mode, which it is by default.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		this.mousePressed(e);
	}

	/**
	 * If the mouse cursor in moved.
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		view.updateMousePosition(e.getX(), e.getY());
	}

	/**
	 * Different commands that comes from the view.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		for (Tile t : tiles) {
			if (e.getActionCommand().equals(Character.toString(t.getCharacter()))) {
				selectedTile = t;
				break;
			}
		}
		if (e.getActionCommand().equals("flipGrid")) {
//			view.flipGrid();
		} else if (e.getActionCommand().equals("save")) {
			saveFile();
		}
	}
	
	private void saveFile() {
		do {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"Text/Java files", "txt", "java");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showSaveDialog(null);
			try {
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					PrintStream p;
					p = new PrintStream(chooser.getSelectedFile());
					p.print(model.getMapAsString());
					break;
				} else {
					break;
				}
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null, "Invalid file!",
						"error", JOptionPane.ERROR_MESSAGE);
			}
		} while (true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) { }
	
	@Override
	public void keyTyped(KeyEvent e) { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tile getSelectedTile() {
		return selectedTile;
	}
}
