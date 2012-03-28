package matachi.mapeditor.editor;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import matachi.mapeditor.grid.Camera;
import matachi.mapeditor.grid.Grid;
import matachi.mapeditor.grid.GridCamera;
import matachi.mapeditor.grid.GridModel;
import matachi.mapeditor.grid.GridView;

/**
 * Controller of the application.
 * 
 * @author Daniel "MaTachi" Jonsson
 * @version 1
 * @since v0.0.5
 * 
 */
public class Controller implements ActionListener, GUIInformation {

	/**
	 * The model of the map editor.
	 */
	private Grid model;

	private Tile selectedTile;

	private List<Tile> tiles;

	private GridView grid;

	/**
	 * Construct the controller.
	 */
	public Controller() {
		this.tiles = TileManager.getTilesFromFolder("data/");
		this.model = new GridModel(Constants.MAP_WIDTH, Constants.MAP_HEIGHT,
				tiles.get(0).getCharacter());
		System.out.println(tiles.get(0).getCharacter());

		Camera camera = new GridCamera(model, Constants.GRID_WIDTH,
				Constants.GRID_HEIGHT);

		grid = new GridView(this, camera, tiles); // Every tile is
													// 30x30 pixels

		new View(this, camera, grid, tiles);
	}

	/**
	 * Different commands that comes from the view.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		for (Tile t : tiles) {
			if (e.getActionCommand().equals(
					Character.toString(t.getCharacter()))) {
				selectedTile = t;
				break;
			}
		}
		if (e.getActionCommand().equals("flipGrid")) {
			// view.flipGrid();
		} else if (e.getActionCommand().equals("save")) {
			saveFile();
		} else if (e.getActionCommand().equals("load")) {
			loadFile();
		}
	}

	// OLD
	private void saveTEXTFile() {
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
				JOptionPane.showMessageDialog(null, "Invalid file!", "error",
						JOptionPane.ERROR_MESSAGE);
			}
		} while (true);
	}

	private void saveFile() {

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"xml files", "xml");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(null);
		try {
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				Element level = new Element("level");
				Document doc = new Document(level);
				doc.setRootElement(level);

				Element size = new Element("size");
				int height = model.getHeight();
				int width = model.getWidth();
				size.addContent(new Element("width").setText(width + ""));
				size.addContent(new Element("height").setText(height + ""));
				doc.getRootElement().addContent(size);

				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Element tile = new Element("tile");
						tile.addContent(new Element("x").setText(x + ""));
						tile.addContent(new Element("y").setText(y + ""));

						int tileNr = Character.getNumericValue(model.getTile(x,
								y));
						String type;
						if (tileNr == 0)
							type = "AirTile";
						else if (tileNr == 1)
							type = "GroundTile";
						else if (tileNr == 2)
							type = "SpawnTile";
						else if (tileNr == 3)
							type = "EndTile";
						else
							type = "AirTile";

						tile.addContent(new Element("type").setText(type + ""));
						doc.getRootElement().addContent(tile);
					}
				}

				XMLOutputter xmlOutput = new XMLOutputter();

				xmlOutput.setFormat(Format.getPrettyFormat());
				xmlOutput
						.output(doc, new FileWriter(chooser.getSelectedFile()));

				System.out.println("File Saved!");

			}
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "Invalid file!", "error",
					JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
		}
	}

	public void loadFile() {
		SAXBuilder builder = new SAXBuilder();
		try {
			JFileChooser chooser = new JFileChooser();
			File selectedFile;
			BufferedReader in;
			FileReader reader = null;

			int returnVal = chooser.showOpenDialog(null);
			Document document;
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				selectedFile = chooser.getSelectedFile();
				if (selectedFile.canRead() && selectedFile.exists()) {
					document = (Document) builder.build(selectedFile);

					Element rootNode = document.getRootElement();

					// Size: TODO!!!!!
					// List sizeList = rootNode.getChildren("size");
					// Element sizeElem = (Element) sizeList.get(0);
					// int height =
					// Integer.parseInt(sizeElem.getChildText("height"));
					// int width =
					// Integer.parseInt(sizeElem.getChildText("width"));
					// grid = new Tile[height][width];

					List list = rootNode.getChildren("tile");
					for (int i = 0; i < list.size(); i++) {
						Element tileElem = (Element) list.get(i);
						int x = Integer.parseInt(tileElem.getChildText("x"));
						int y = Integer.parseInt(tileElem.getChildText("y"));
						String tileType = tileElem.getChildText("type");
						char tileNr;
						if (tileType.equals("AirTile"))
							tileNr = '0';
						else if (tileType.equals("GroundTile"))
							tileNr = '1';
						else if (tileType.equals("SpawnTile"))
							tileNr = '2';
						else if (tileType.equals("EndTile"))
							tileNr = '3';
						else
							tileNr = '0';
						model.setTile(x, y, tileNr);
						grid.redrawGrid();
					}
				}
			}
		} catch (Exception e) {
		}
	}

	// OLD
	private void loadTEXTFile() {
		try {
			JFileChooser chooser = new JFileChooser();
			File selectedFile;
			BufferedReader in;
			FileReader reader = null;

			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				selectedFile = chooser.getSelectedFile();
				if (selectedFile.canRead() && selectedFile.exists()) {
					reader = new FileReader(selectedFile);
				}
			}

			in = new BufferedReader(reader);
			String line;
			int y = 0;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
				for (int x = 0; x < line.length(); x++) {
					char c = line.charAt(x);
					model.setTile(x, y, c);
				}
				y++;
			}
			in.close();
			grid.redrawGrid();
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		/**
		 * //catches input/output exceptions and all subclasses exceptions
		 * catch(IOException ex) { jTxtAMain.append("Error Processing File:\n" +
		 * ex.getMessage()+"\n"); } //catches nullpointer exception, file not
		 * found catch(NullPointerException ex) {
		 * jTxtAMain.append("Open File Cancelled:\n" + ex.getMessage()+"\n"); }
		 */
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Tile getSelectedTile() {
		return selectedTile;
	}
}
