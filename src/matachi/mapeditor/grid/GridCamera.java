package matachi.mapeditor.grid;

import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * An implementation of the interface Camera. It has a Grid and shows only a
 * rectangle of it.
 * @author Daniel "MaTachi" Jonsson
 * @version 1
 * @since v0.0.5
 *
 */
public class GridCamera implements Camera {

	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	private Grid model;
	
	private Rectangle camera;

	/**
	 * Announce changes.
	 */
	private PropertyChangeSupport changeSupport;

	/**
	 * Constructs the camera and model with number of rows and columns. It also sets the
	 * camera width and height.
	 * @param cols The number of columns.
	 * @param rows The number of rows.
	 * @param cameraWidth The width of the camera.
	 * @param cameraHeight The height of the camera.
	 */
	public GridCamera(int cols, int rows, int cameraWidth, int cameraHeight) {
		this(cols, rows, cameraWidth, cameraHeight, 0, 0);
	}

	/**
	 * Constructs the camera and model with number of rows and columns. It also sets the
	 * camera width and height.
	 * @param cols The number of columns.
	 * @param rows The number of rows.
	 * @param cameraWidth The width of the camera.
	 * @param cameraHeight The height of the camera.
	 * @param cameraX The X coordinate of the camera.
	 * @param cameraY The Y coordinate of the camera.
	 */
	public GridCamera(int cols, int rows, int cameraWidth, int cameraHeight, int cameraX, int cameraY) {
		checkValidCameraPosition(cols, rows, cameraWidth, cameraHeight, cameraX, cameraY);
		this.changeSupport = new PropertyChangeSupport(this);
		this.model = new GridModel(cols, rows);
		this.camera = new Rectangle(cameraX, cameraY, cameraWidth, cameraHeight);
	}


	/**
	 * Constructs the camera with a given model.
	 * @param model The model.
	 * @param cameraWidth The width of the camera.
	 * @param cameraHeight The height of the camera.
	 */
	public GridCamera(Grid model, int cameraWidth, int cameraHeight) {
		this(model, cameraWidth, cameraHeight, 0, 0);
	}

	/**
	 * Constructs the camera with a given model.
	 * @param model The model.
	 * @param cameraWidth The width of the camera.
	 * @param cameraHeight The height of the camera.
	 * @param cameraX The X coordinate of the camera.
	 * @param cameraY The Y coordinate of the camera.
	 */
	public GridCamera(Grid model, int cameraWidth, int cameraHeight, int cameraX, int cameraY) {
//		this(model.getWidth(), model.getHeight(), cameraWidth, cameraHeight, cameraX, cameraY);
		checkValidCameraPosition(model.getWidth(), model.getHeight(), cameraWidth, cameraHeight, cameraX, cameraY);
		this.changeSupport = new PropertyChangeSupport(this);
		this.model = model;
		this.camera = new Rectangle(cameraX, cameraY, cameraWidth, cameraHeight);
	}

	private void checkValidCameraPosition(int cols, int rows, int cameraWidth, int cameraHeight, int cameraX, int cameraY) {
		if (cameraWidth + cameraX > cols) {
			throw new IllegalArgumentException(
					"The camera width + camera x position > columns");
		} else if (cameraHeight + cameraY > rows) {
			throw new IllegalArgumentException(
					"The camera height + camera y position > rows");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getWidth() {
		return camera.width;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getHeight() {
		return camera.height;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getX() {
		return camera.x;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getY() {
		return camera.y;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getModelWidth() {
		return model.getWidth();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getModelHeight() {
		return model.getHeight();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTile(int x, int y, char c) {
		model.setTile(camera.x + x, camera.y + y, c);
		firePropertyChange("changedTile", new Point(x, y));
	}
	
	/**
	 * {@inheritDoc}
	 */
	public char getTile(int x, int y) {
		return model.getTile(camera.x + x, camera.y + y);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Rectangle getCamera() {
		return new Rectangle(camera);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void moveCamera(int direction) {
		if (direction == GridCamera.NORTH) {
			if (camera.y > 0) {
				camera.setLocation(camera.x, --camera.y);
			}
		} else if (direction == GridCamera.EAST) {
			if (camera.x + camera.width < model.getWidth()) {
				camera.setLocation(++camera.x, camera.y);
			}
		} else if (direction == GridCamera.SOUTH) {
			if (camera.y + camera.height < model.getHeight()) {
				camera.setLocation(camera.x, ++camera.y);
			}
		} else if (direction == GridCamera.WEST) {
			if (camera.x > 0) {
				camera.setLocation(--camera.x, camera.y);
			}
		}

		firePropertyChange("movedCamera");
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}
	
	private void firePropertyChange(String propertyName) {
		changeSupport.firePropertyChange(propertyName, false, true);
	}
	
	private void firePropertyChange(String propertyName, Object newValue) {
		changeSupport.firePropertyChange(propertyName, false, newValue);
	}
}
