package Solving;

import java.awt.*;
import java.util.Random;

import ActualEditor.Drawing;
import DataHolder.DataHolder;
import DataHolder.SolveMode;
import Squares.SolvingSquare;

public class GeneralSolving {

	/**
	 * Class for gathering general functions to prevent duplicate code
	 */
	
	// TODO algos miteinander vergleichen
		// RAM, zyklen, steps
	// TODO multi threading?

	/**
	 * Initiates the solving algorithms
	 *
	 * @param s Squares.SquareSolving two dimensional array
	 */
	public static void init(SolvingSquare[][] s) {
		DataHolder.solvingSquares = s;
	} // create SquareSolve array

	/**
	 * Checks the options of unvisited neighbors
	 *
	 * @param x x-coordinate of square
	 * @param y y-coordinate of square
	 * @return unvisited neighbor array list
	 */
	static int checkOptions(int x, int y) {
		DataHolder.neighbourSolvingSquares.clear();
		if (!DataHolder.squares[x][y].walls[3]) // check if left wall is gone
			if (x - 1 >= 0) // check if already went to the left
				if (!DataHolder.solvingSquares[x - 1][y].visitedSolving)
					DataHolder.neighbourSolvingSquares.add(DataHolder.solvingSquares[x - 1][y]);
		if (!DataHolder.squares[x][y].walls[0]) // check if top wall is gone
			if (y - 1 >= 0) // check if already went to the top
				if (!DataHolder.solvingSquares[x][y - 1].visitedSolving)
					DataHolder.neighbourSolvingSquares.add(DataHolder.solvingSquares[x][y - 1]);
		if (!DataHolder.squares[x][y].walls[1]) // check if right wall is gone
			if (x + 1 < DataHolder.gridSize) // check if already went to the right
				if (!DataHolder.solvingSquares[x + 1][y].visitedSolving)
					DataHolder.neighbourSolvingSquares.add(DataHolder.solvingSquares[x + 1][y]);
		if (!DataHolder.squares[x][y].walls[2]) // check if bottom wall is gone
			if (y + 1 < DataHolder.gridSize) // check if already went to the bottom
				if (!DataHolder.solvingSquares[x][y + 1].visitedSolving)
					DataHolder.neighbourSolvingSquares.add(DataHolder.solvingSquares[x][y + 1]);
		return DataHolder.neighbourSolvingSquares.size();
	}

	/**
	 * Starts the specific solving algorithm
	 *
	 * @param method   Name of method
	 * @param graphics Graphics object
	 */
	public static void selectSolve(String method, Graphics g, boolean vis) { 
		// TODO implement visualize
		for (int y = 0; y < DataHolder.gridSize; y++)
			for (int x = 0; x < DataHolder.gridSize; x++)
				DataHolder.solvingSquares[x][y] = new SolvingSquare(x, y);
		switch (method) {
		case "alwaysLeft":
			System.out.println("Start solving");
			multiPurpose(SolveMode.AlwaysLeft, g);
			System.out.println("Done solving");
			break;
		case "randomDir":
			System.out.println("Start solving");
			multiPurpose(SolveMode.RandomDir, g);
			System.out.println("Done solving");
			break;
		case "trueAlwaysLeft":
			TrueAlwaysLeft.solve(g);
			break;
		case "quantumLike":
			QuantumLike.solve(g);
		}
	}
	
	/**
	 * Solving approach which is generally spoken just recursive backtracking with a few tweaks
	 * @param e Enum of solving type
	 * @param g Graphics object
	 */
	public static void multiPurpose(Enum<SolveMode> e, Graphics g) {
		SolvingSquare current = DataHolder.solvingSquares[0][0];
		current.visitedSolving = true;
		DataHolder.solvingSquareStack.push(current);

		int x, y, neighbourCount;
		boolean br = false;
		
		while (!DataHolder.solvingSquareStack.empty()) {
			x = current.absolueX;
			y = current.absoluteY;
			
			neighbourCount = GeneralSolving.checkOptions(x, y);
			while (neighbourCount == 0) {
				if ((x == DataHolder.gridSize - 1) && (y == DataHolder.gridSize - 1)) {
					br = true;
					break;
				} else
					DataHolder.squares[x][y].solve = false;
				
				if (!DataHolder.solvingSquareStack.empty()) {
					current = DataHolder.solvingSquareStack.pop();
					x = current.absolueX;
					y = current.absoluteY;
					neighbourCount = GeneralSolving.checkOptions(x, y);
				} else {
					br = true;
					break;
				}
			}

			if (br) //break when done
				break;

			DataHolder.solvingSquareStack.push(current);

			if (e == SolveMode.RandomDir)
				current = DataHolder.neighbourSolvingSquares.get(new Random().nextInt(DataHolder.neighbourSolvingSquares.size()));
			else if (e == SolveMode.AlwaysLeft)
				current = DataHolder.neighbourSolvingSquares.get(0);
			// else if (e == SolveMode.TrueAlwaysLeft)
				// TODO
			
			current.visitedSolving = true;
			if (x + y == (DataHolder.gridSize - 1) * 2)
				break;
			else
				if (!(x == 0 && y == 0))
					DataHolder.squares[x][y].solve = true;
		}
		Drawing.drawImage(g);
	}

}