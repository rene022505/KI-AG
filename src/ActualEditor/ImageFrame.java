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
		DataHolder.rwl.readLock().lock(); // Makes this code only runnable if no writeLock is declared
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, DataHolder.panelSize + 1, DataHolder.panelSize + 1);
		Drawing.drawImage(g);
		DataHolder.rwl.readLock().unlock();
	}

}