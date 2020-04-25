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
			multiPurpose(SolveMode.AlwaysLeft, g);
			break;
		case "randomDir":
			multiPurpose(SolveMode.RandomDir, g);
			break;
		case "trueAlwaysLeft":
			multiPurpose(SolveMode.TrueAlwaysLeft, g);
			break;
		case "quantumLike":
			QuantumLike.solve(g);
			break;
		case "aStar":
			A_Star.solve(g);
			break;
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
			x = current.absoluteX;
			y = current.absoluteY;
			
			neighbourCount = GeneralSolving.checkOptions(x, y);
			while (neighbourCount == 0) { // if no neighbors do the backtracking
				// if current cell is finish TODO rewrite if custom/random finish and start
				if ((x == DataHolder.gridSize - 1) && (y == DataHolder.gridSize - 1)) { 
					br = true;
					break;
				} else
					DataHolder.squares[x][y].solve = false;
				
				if (!DataHolder.solvingSquareStack.empty()) {
					current = DataHolder.solvingSquareStack.pop();
					x = current.absoluteX;
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
			else if (e == SolveMode.TrueAlwaysLeft) { // It solves the maze but not sure if it uses the method i was hoping for
				if (current.absoluteX > DataHolder.solvingSquareStack.get(DataHolder.solvingSquareStack.size() - 2).absoluteX) 
					current = DataHolder.neighbourSolvingSquares.get(DataHolder.neighbourSolvingSquares.size() == 1 ? 0 : 1);
				else if (current.absoluteX < DataHolder.solvingSquareStack.get(DataHolder.solvingSquareStack.size() - 2).absoluteX) 
					current = DataHolder.neighbourSolvingSquares.get(DataHolder.neighbourSolvingSquares.size() - 1);
				else if (current.absoluteY > DataHolder.solvingSquareStack.get(DataHolder.solvingSquareStack.size() - 2).absoluteY) 
					current = DataHolder.neighbourSolvingSquares.get(DataHolder.neighbourSolvingSquares.size() == 3 ? 2 : DataHolder.neighbourSolvingSquares.size() - 1);
				else
					current = DataHolder.neighbourSolvingSquares.get(0);
			}
			
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