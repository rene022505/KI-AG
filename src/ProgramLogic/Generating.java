package ProgramLogic;

import ActualEditor.Drawing;
import DataHolder.DataHolder;

import java.awt.*;
import java.util.Random;

import Squares.Square;

public class Generating {

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
			}
		}

		// Set first and last square to start and finish
		DataHolder.squares[0][0].isStart = true;
		DataHolder.squares[DataHolder.gridSize - 1][DataHolder.gridSize - 1].isFinish = true;

		// Read wikipedia maze generation recursive backtracking to understand the
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

			// Failsafe because it errors for some reason
			if (br)
				break;

			Square previous = current; // Step 2.2.2
			current = DataHolder.neighbourSquares.get(neighbour);

			if (previous.absoluteX - current.absoluteX > 0) { // Step 2.2.3
				previous.removeLeftBool();
				current.removeRightBool();
			} else if (previous.absoluteX - current.absoluteX < 0) {
				current.removeLeftBool();
				previous.removeRightBool();
			} else if (previous.absoluteY - current.absoluteY < 0) {
				current.removeTopBool();
				previous.removeBottomBool();
			} else {
				previous.removeTopBool();
				current.removeBottomBool();
			}

			current.visited = true; // Step 2.2.4
			DataHolder.squareStack.push(current);
		}
		g.setColor(Color.black);
		DataHolder.squareStack.clear();
		Drawing.drawImage(g, genVis);
	}

	/**
	 * Checks if the current cell has any unvisited neighbours
	 * 
	 * @param x x-coordinate of cell
	 * @param y y-coordinate of cell
	 * @return array with neighbours
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