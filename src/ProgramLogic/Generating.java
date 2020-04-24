package ProgramLogic;

import ActualEditor.Drawing;
import DataHolder.DataHolder;

import java.awt.*;
import java.util.Random;

import javax.swing.JOptionPane;

import Squares.Square;

public class Generating {
	
	// TODO maze with multiple solutions
	// TODO different maze generation algo

	/**
	 * Class for generating the maze
	 */

	private static Random r = new Random();

	/**
	 * Generates a new random maze
	 * 
	 * @param g   Graphics object
	 * @param genVis visualize generation flag (not yet implemented)
	 */
	public static void generateMaze(Graphics g, boolean genVis) {
		// Reset entire maze to default
		for (int y = 0; y < DataHolder.gridSize; y++) {
			for (int x = 0; x < DataHolder.gridSize; x++) {
				DataHolder.squares[x][y].walls = new boolean[] { true, true, true, true };
				DataHolder.squares[x][y].visited = false;
				DataHolder.squares[x][y].solve = false;
				DataHolder.squares[x][y].isStart = false;
				DataHolder.squares[x][y].isFinish = false;
				if (genVis) {
					g.drawLine(x * (int) DataHolder.squareSize, 0, x * (int) DataHolder.squareSize, DataHolder.panelSize);
					g.drawLine(0, y * (int) DataHolder.squareSize, DataHolder.panelSize, y * (int) DataHolder.squareSize);
				}
			}
		}

		// Set first and last square to start and finish
		DataHolder.squares[0][0].isStart = true;
		DataHolder.squares[DataHolder.gridSize - 1][DataHolder.gridSize - 1].isFinish = true;

		// Read Wikipedia maze generation recursive backtracking to understand the
		// following
		Square current = DataHolder.squares[0][0]; // Step 1
		current.visited = true;
		DataHolder.squareStack.push(current);

		int neighbour;
		int help;
		boolean br = false;

		while (!DataHolder.squareStack.empty()) { // Step 2
			current = DataHolder.squareStack.pop(); // Step 2.1

			help = neighbours(current.absoluteX, current.absoluteY); // Step 2.2
			neighbour = r.nextInt(help != 0 ? help : 1);

			if (DataHolder.neighbourSquares.size() != 0)
				DataHolder.squareStack.push(current); // Step 2.2.1
			else
				while (DataHolder.neighbourSquares.size() == 0) {
					if (DataHolder.squareStack.empty()) {
						br = true;
						break;
					}
					current = DataHolder.squareStack.pop();
					help = neighbours(current.absoluteX, current.absoluteY);
					neighbour = r.nextInt(help != 0 ? help : 1);
				}

			// Break when done
			if (br)
				break;

			Square previous = current; // Step 2.2.2
			current = DataHolder.neighbourSquares.get(neighbour);

			if (previous.absoluteX - current.absoluteX > 0) { // Step 2.2.3
				previous.removeLeftBool();
				current.removeRightBool();
				if (genVis) {
					previous.removeLeft(g);
					current.removeRight(g);
				}
			} else if (previous.absoluteX - current.absoluteX < 0) {
				current.removeLeftBool();
				previous.removeRightBool();
				if (genVis) {
					current.removeLeft(g);
					previous.removeRight(g);
				}
			} else if (previous.absoluteY - current.absoluteY < 0) {
				current.removeTopBool();
				previous.removeBottomBool();
				if (genVis) {
					previous.removeBottom(g);
					current.removeTop(g);
				}
			} else {
				previous.removeTopBool();
				current.removeBottomBool();
				if (genVis) {
					previous.removeTop(g);
					current.removeBottom(g);
				}
			}
			
			if(genVis)
				try {
					Thread.sleep(0,  50);
				} catch (InterruptedException e) {
					JOptionPane.showMessageDialog(null, "Something happened and I don't know what... Check console", "?!?!?",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}

			current.visited = true; // Step 2.2.4
			DataHolder.squareStack.push(current);
		}
		DataHolder.squareStack.clear();
		if (!genVis)
			Drawing.drawImage(g);
		else {
			g.setColor(Color.ORANGE);
			g.fillRect(DataHolder.panelSize - (int) DataHolder.squareSize - 1, DataHolder.panelSize - (int) DataHolder.squareSize - 1, DataHolder.panelSize - 1, DataHolder.panelSize - 1);
			g.setColor(Color.RED);
			g.fillRect(1, 1, (int) DataHolder.squareSize - 1, (int) DataHolder.squareSize - 1);
		}
	}

	/**
	 * Checks if the current cell has any unvisited neighbors
	 * 
	 * @param x x-coordinate of cell
	 * @param y y-coordinate of cell
	 * @return array with neighbors
	 */
	static int neighbours(int x, int y) {
		DataHolder.neighbourSquares.clear();
		if (x - 1 >= 0) // check if neighbour left is available
			if (!DataHolder.squares[x - 1][y].visited)
				DataHolder.neighbourSquares.add(DataHolder.squares[x - 1][y]);
		if (y - 1 >= 0) // check if neighbour on top is available
			if (!DataHolder.squares[x][y - 1].visited)
				DataHolder.neighbourSquares.add(DataHolder.squares[x][y - 1]);
		if (x + 1 < DataHolder.gridSize) // check if neighbour right is available
			if (!DataHolder.squares[x + 1][y].visited)
				DataHolder.neighbourSquares.add(DataHolder.squares[x + 1][y]);
		if (y + 1 < DataHolder.gridSize) // check if neighbour bellow is available
			if (!DataHolder.squares[x][y + 1].visited)
				DataHolder.neighbourSquares.add(DataHolder.squares[x][y + 1]);
		return DataHolder.neighbourSquares.size();
	}

}