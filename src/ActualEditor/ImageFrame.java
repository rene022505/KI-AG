package ActualEditor;

import java.awt.*;

import javax.swing.JPanel;

import DataHolder.DataHolder;

@SuppressWarnings("serial")
class ImageFrame extends JPanel {
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
		g.fillRect(0, 0, DataHolder.panelSize + 1, DataHolder.panelSize + 1);
		Drawing.drawImage(g);
	}

}