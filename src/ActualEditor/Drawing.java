package ActualEditor;

import java.awt.*;

import DataHolder.DataHolder;

public class Drawing {

	/**
	 * Class for drawing the maze
	 */

	/**
	 * Prints the maze onto the screen
	 * 
	 * @param g Graphics object
	 * @param genVis boolean visualize generation
	 */
	public static void drawImage(Graphics g, boolean genVis) {
		// draws the actual image
		for (int y = 0; y < 70; y++) {
			for (int x = 0; x < 70; x++) {
				// TODO: Add visualizing
				allWalls(g, x, y);
				if (DataHolder.squares[x][y].solve) {
					parseWallsSolve(g, x, y);
				} else if (DataHolder.squares[x][y].visited) {
					parseWalls(g, x, y);
				}
			}
		}
	}

	/**
	 * Paints a square with all its walls
	 * 
	 * @param g Graphics object
	 * @param x x-coordinate of square
	 * @param y y-coordinate of square
	 */
	static void allWalls(Graphics g, int x, int y) {
		g.setColor(Color.BLACK);
		g.drawRect(x * 10, y * 10, 10, 10);
		if (DataHolder.squares[x][y].isStart) {
			g.setColor(Color.RED);
			g.fillRect(x * 10 + 1, y * 10 + 1, 9, 9);
			g.setColor(Color.WHITE);
		} else if (DataHolder.squares[x][y].isFinish) {
			g.setColor(Color.ORANGE);
			g.fillRect(x * 10 + 1, y * 10 + 1, 9, 9);
			g.setColor(Color.WHITE);
		} else {
			g.setColor(Color.WHITE);
			g.fillRect(x * 10 + 1, y * 10 + 1, 9, 9);
		}
	}

	/**
	 * Paints the squares which are part of the solving path green, stupid method
	 * name
	 * 
	 * @param g Graphics object
	 * @param x x-coordinate of square
	 * @param y y-coordinate of square
	 */
	private static void parseWallsSolve(Graphics g, int x, int y) {
		g.setColor(Color.GREEN);
		g.fillRect(DataHolder.squares[x][y].x + 1, DataHolder.squares[x][y].y + 1, DataHolder.squares[x][y].x + 9,
				DataHolder.squares[x][y].y + 9);
		parseWalls(g, x, y);
	}

	/**
	 * Parses which walls every square has to repaint the square
	 * 
	 * @param g Graphics object
	 * @param x x-coordinate of object
	 * @param y y-coordinate of object
	 */
	private static void parseWalls(Graphics g, int x, int y) {
		if (!DataHolder.squares[x][y].walls[0])
			DataHolder.squares[x][y].removeTop(g);
		if (!DataHolder.squares[x][y].walls[1])
			DataHolder.squares[x][y].removeRight(g);
		if (!DataHolder.squares[x][y].walls[2])
			DataHolder.squares[x][y].removeBottom(g);
		if (!DataHolder.squares[x][y].walls[3])
			DataHolder.squares[x][y].removeLeft(g);
	}
}