package Solving;

import java.awt.*;
import java.util.Random;

import ActualEditor.Drawing;
import DataHolder.DataHolder;
import DataHolder.SolveMode;
import Squares.SolvingSquare;
import ProgramLogic.Logic;

public class GeneralSolving {

	/**
	 * Class for gathering general functions to prevent duplicate code
	 */

	/**
	 * Initiates the solving algorithms
	 *
	 * @param s Squares.SquareSolving two dimensional array
	 */
	public static void init(SolvingSquare[][] s) {
		DataHolder.solvingSquareArray = s;
	} // create SquareSolve array

	/**
	 * Checks the options of unvisited neighbours
	 *
	 * @param x x-coordinate of square
	 * @param y y-coordinate of square
	 * @return unvisited neighbour array list
	 */
	static int checkOptions(int x, int y) {
		DataHolder.squareNeighbours.clear();
		if (!DataHolder.squares[x][y].walls[3]) // check if left wall is gone
			if (x - 1 >= 0) // check if already went to the left
				if (!DataHolder.solvingSquareArray[x - 1][y].visitedSolving)
					DataHolder.squareNeighbours.add(DataHolder.solvingSquareArray[x - 1][y]);
		if (!DataHolder.squares[x][y].walls[0]) // check if top wall is gone
			if (y - 1 >= 0) // check if already went to the top
				if (!DataHolder.solvingSquareArray[x][y - 1].visitedSolving)
					DataHolder.squareNeighbours.add(DataHolder.solvingSquareArray[x][y - 1]);
		if (!DataHolder.squares[x][y].walls[1]) // check if right wall is gone
			if (x + 1 < 70) // check if already went to the right
				if (!DataHolder.solvingSquareArray[x + 1][y].visitedSolving)
					DataHolder.squareNeighbours.add(DataHolder.solvingSquareArray[x + 1][y]);
		if (!DataHolder.squares[x][y].walls[2]) // check if bottom wall is gone
			if (y + 1 < 70) // check if already went to the bottom
				if (!DataHolder.solvingSquareArray[x][y + 1].visitedSolving)
					DataHolder.squareNeighbours.add(DataHolder.solvingSquareArray[x][y + 1]);
		return DataHolder.squareNeighbours.size();
	}

	/**
	 * Starts the specific solving algorithm
	 *
	 * @param method   Name of method
	 * @param graphics Graphics object
	 */
	public static void selectSolve(String method, Graphics g, boolean vis) { 
		// TODO implement visualize
		init(Logic.generateSquareSolve());
		switch (method) {
		case "alwaysLeft":
			AlwaysLeft.solve(g);
			break;
		case "randomDir":
			RandomDir.solve(g);
			break;
		case "humanLike":
			HumanLike.solve(g);
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
		SolvingSquare current = DataHolder.solvingSquareArray[0][0];
		current.visitedSolving = true;
		DataHolder.solvingSquareStack.push(current);

		int x, y, neighbourCount;
		boolean br = false;

		while (!DataHolder.solvingSquareStack.empty()) {
			x = current.xIndex;
			y = current.yIndex;

			neighbourCount = GeneralSolving.checkOptions(x, y);
			while (neighbourCount == 0) {
				DataHolder.squares[x][y].solve = false;
				if (!DataHolder.solvingSquareStack.empty()) {
					current = DataHolder.solvingSquareStack.pop();
					x = current.xIndex;
					y = current.yIndex;
					neighbourCount = GeneralSolving.checkOptions(x, y);
				} else {
					br = true;
					break;
				}
			}

			if (br)
				break;

			DataHolder.solvingSquareStack.push(current);

			if (e == SolveMode.RandomDir)
				current = DataHolder.squareNeighbours.get(new Random().nextInt(DataHolder.squareNeighbours.size()));
			else if (e == SolveMode.AlwaysLeft)
				current = DataHolder.squareNeighbours.get(0);
			
			current.visitedSolving = true;
			if (DataHolder.squares[x][y].isFinish)
				break;
			else
				if (!(x == 0 && y == 0))
					DataHolder.squares[x][y].solve = true;
		}
		Drawing.drawImage(g, false);
	}

}