package matachi.mapeditor.grid;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GridController implements MouseListener, MouseMotionListener, ActionListener, KeyListener {
	
	/**
	 * The GUI of the map editor.
	 */
	private final GridView view;

	/**
	 * The model of the map editor.
	 */
	private Grid model;

	/**
	 * The camera of the model.
	 */
	private Camera camera;
	
	/**
	 * The current type of tile that the user is painting with.
	 */
	private int curTile;
	private int curTileX;
	private int curTileY;
	
	/**
	 * If the drawingMode is on or off.
	 */
	private boolean drawingMode;

	/**
	 * Construct the controller.
	 */
	public GridController(GridView view, Grid grid, Camera camera) {
		this.camera = camera;
		this.view = view;
		this.model = grid;
		this.camera.addPropertyChangeListener(view);
		this.model.addPropertyChangeListener(view);
		this.drawingMode = true;
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
	public void mousePressed(MouseEvent e) {
		if (ifLeftMouseButtonPressed(e)) {
			int xCor = e.getX() / 30;
			int yCor = e.getY() / 30;
			updateTile(xCor, yCor);
		} else if (ifRightMouseButtonPressed(e)) {
			int newTileX = e.getX() / 30;
			int newTileY = e.getY() / 30;
			updateCamera(newTileX, newTileY);
		}
	}

	private boolean ifLeftMouseButtonPressed(MouseEvent e) {
		return (e.getModifiers() & MouseEvent.BUTTON1_MASK) == MouseEvent.BUTTON1_MASK;
	}
	
	private boolean ifRightMouseButtonPressed(MouseEvent e) {
		return (e.getModifiers() & MouseEvent.BUTTON3_MASK) == MouseEvent.BUTTON3_MASK;
	}
	
	private void updateTile(int xCor, int yCor) {
		if (curTile == 0) {
			if (camera.getTile(xCor, yCor) != '0') {
				camera.setTile(xCor, yCor, '0');
			}
		} else if (curTile == 1) {
			if (camera.getTile(xCor, yCor) != '1') {
				camera.setTile(xCor, yCor, '1');
			}
		}
	}

	private void updateCamera(int newTileX, int newTileY) {
		if (newTileX != curTileX) {
			if (newTileX - curTileX > 0) {
				camera.moveCamera(GridCamera.WEST);
			} else {
				camera.moveCamera(GridCamera.EAST);
			}
		}
		if (newTileY != curTileY) {
			if (newTileY - curTileY > 0) {
				camera.moveCamera(GridCamera.NORTH);
			} else {
				camera.moveCamera(GridCamera.SOUTH);
			}
		}
		curTileX = newTileX;
		curTileY = newTileY;
	}

	@Override
	public void mouseReleased(MouseEvent e) { }

	/**
	 * If the user keeps the mouse button pressed it will keep drawing if it is
	 * in drawing mode, which it is by default.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		if (drawingMode) {
			this.mousePressed(e);
		}
	}

	/**
	 * If the mouse cursor in moved.
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
	}

	/**
	 * Different commands that comes from the view.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("sky")) {
			curTile = 0;
		} else if (e.getActionCommand().equals("ground")) {
			curTile = 1;
		} else if (e.getActionCommand().equals("flipGrid")) {
//			view.flipGrid();
		} else if (e.getActionCommand().equals("flipDraw")) {
			drawingMode = !drawingMode;
//			view.flipDraw();
		}
	}

	/**
	 * If a key is pressed down.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			view.moveCamera(GridView.NORTH);
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			view.moveCamera(GridView.EAST);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			view.moveCamera(GridView.SOUTH);
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			view.moveCamera(GridView.WEST);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { }
	
	@Override
	public void keyTyped(KeyEvent e) { }
}
