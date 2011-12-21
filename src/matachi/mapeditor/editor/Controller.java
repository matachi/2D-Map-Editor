package matachi.mapeditor.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class Controller implements ActionListener, GUIInformation {

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tile getSelectedTile() {
		return selectedTile;
	}
}
