package matachi.mapeditor.editor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;

/**
 * A label that shows information about the mouse cursor.
 * @author Daniel "MaTachi" Jonsson
 * @version 1
 * @since v0.0.6
 *
 */
public class GridMouseInformationLabel extends JLabel implements MouseMotionListener {

	private static final long serialVersionUID = 1577271777008860072L;

	/**
	 * A label that shows information about the mouse cursor.
	 * @param mouseBroadcaster The class that this label will listen to for
	 * mouse information.
	 */
	public GridMouseInformationLabel(Component mouseBroadcaster) {
		this.setText("Mouse: (0, 0), Hovering tile: (0, 0)");
		this.setPreferredSize(new Dimension(320, 15));
		mouseBroadcaster.addMouseMotionListener(this);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		this.mouseMoved(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.updateMousePosition(e.getX(), e.getY());
	}

	/**
	 * Update the text of the label.
	 * @param x The mouse's x coordinate.
	 * @param y The mouse's y coordiante.
	 */
	private void updateMousePosition(int x, int y) {
		this.setText("Mouse: (" + x + ", " + y + "), Hovering tile: (" + (x/Constants.TILE_WIDTH+1) + ", " + (y/Constants.TILE_HEIGHT+1) + ")");
	}

	
}
