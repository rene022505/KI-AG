package ProgramLogic;

import ActualEditor.Drawing;

import java.awt.*;
import java.util.Random;

import Squares.Square;

public class Generating {

	/**
	 * Class for generating the maze
	 */

	private static Random r = new Random();
	static boolean vG;

	/**
	 * Generates a new random maze
	 * 
	 * @param g   Graphics object
	 * @param pVG visualize generation flag (not yet implemented)
	 */
	public static void generateMaze(Graphics g, boolean pVG) {
		vG = pVG;
		// Reset entire maze to default
		for (int y = 0; y < 70; y++) {
			for (int x = 0; x < 70; x++) {
				// TODO: change this to constructor call
				Logic.squares[x][y].walls = new boolean[] { true, true, true, true };
				Logic.squares[x][y].visited = false;
				Logic.squares[x][y].solve = false;
				Logic.squares[x][y].isStart = false;
				Logic.squares[x][y].isFinish = false;
			}
		}

		// Set first and last square tp start and finish
		Logic.squares[0][0].isStart = true;
		Logic.squares[69][69].isFinish = true;

		// Read wikipedia maze generation recursive backtracking to understand the
		// following
		Square current = Logic.squares[0][0]; // Step 1
		current.visited = true;
		Logic.squareStack.push(current);

		int neighbour;
		int help;
		boolean br = false;

		while (!Logic.squareStack.empty()) { // Step 2
			current = Logic.squareStack.pop(); // Step 2.1

			help = neighbours(current.x / 10, current.y / 10); // Step 2.2
			neighbour = r.nextInt(help != 0 ? help : 1);

			if (Logic.neighbourSquares.size() != 0)
				Logic.squareStack.push(current); // Step 2.2.1
			else
				while (Logic.neighbourSquares.size() == 0) {
					if (Logic.squareStack.empty()) {
						br = true;
						break;
					}
					current = Logic.squareStack.pop();
					help = neighbours(current.x / 10, current.y / 10);
					neighbour = r.nextInt(help != 0 ? help : 1);
				}

			// Failsafe because it errors for some reason
			if (br)
				break;

			Square previous = current; // Step 2.2.2
			current = Logic.neighbourSquares.get(neighbour);

			if (previous.x - current.x > 0) { // Step 2.2.3
				previous.removeLeftBool();
				current.removeRightBool();
			} else if (previous.x - current.x < 0) {
				current.removeLeftBool();
				previous.removeRightBool();
			} else if (previous.y - current.y < 0) {
				current.removeTopBool();
				previous.removeBottomBool();
			} else {
				previous.removeTopBool();
				current.removeBottomBool();
			}

			current.visited = true; // Step 2.2.4
			Logic.squareStack.push(current);
		}
		g.setColor(Color.black);
		Logic.squareStack.clear();
		Drawing.drawImage(g);
	}

	/**
	 * Checks if the current cell has any unvisited neighbours
	 * 
	 * @param x x-coordinate of cell
	 * @param y y-coordinate of cell
	 * @return array with neighbours
	 */
	static int neighbours(int x, int y) {
		Logic.neighbourSquares.clear();
		if (x - 1 >= 0) // check if neighbour left is available
			if (!Logic.squares[x - 1][y].visited)
				Logic.neighbourSquares.add(Logic.squares[x - 1][y]);
		if (y - 1 >= 0) // check if neighbour on top is available
			if (!Logic.squares[x][y - 1].visited)
				Logic.neighbourSquares.add(Logic.squares[x][y - 1]);
		if (x + 1 < 70) // check if neighbour right is available
			if (!Logic.squares[x + 1][y].visited)
				Logic.neighbourSquares.add(Logic.squares[x + 1][y]);
		if (y + 1 < 70) // check if neighbour bellow is available
			if (!Logic.squares[x][y + 1].visited)
				Logic.neighbourSquares.add(Logic.squares[x][y + 1]);
		return Logic.neighbourSquares.size();
	}

}