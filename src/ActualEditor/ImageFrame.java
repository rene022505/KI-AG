package ActualEditor;

import java.awt.*;

@SuppressWarnings("serial")
class ImageFrame extends javax.swing.JPanel {
	/**
	 * Class for painting the maze onto the screen
	 */

	/**
	 * Paints the Maze part
	 *
	 * @param g Graphics object
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 701, 701);
		Drawing.drawImage(g);
	}

}