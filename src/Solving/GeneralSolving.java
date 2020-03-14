package Solving;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

import Squares.SquareSolving;
import ProgramLogic.Logic;

public class GeneralSolving {

	/**
	 * Class for gathering general functions to prevent duplicate code
	 */

	static SquareSolving[][] squareSolvingArray = new SquareSolving[70][70];
	static Stack<SquareSolving> squareSolvingStack = new Stack<>();

	static ArrayList<SquareSolving> squareNeighbours = new ArrayList<>();

	/**
	 * Initiates the solving algorithms
	 *
	 * @param s Squares.SquareSolving two dimensional array
	 */
	public static void init(SquareSolving[][] s) {
		squareSolvingArray = s;
	} // create SquareSolve array

	/**
	 * Checks the options of unvisited neighbours
	 *
	 * @param x x-coordinate of square
	 * @param y y-coordinate of square
	 * @return unvisited neighbour array list
	 * @deprecated
	 */
	static int checkOptions(int x, int y) {
		// TODO: don't know if it works
		squareNeighbours.clear();
		if (x - 1 >= 0) // check if already went to the left
			if (!squareSolvingArray[x - 1][y].visitedSolving)
				squareNeighbours.add(squareSolvingArray[x - 1][y]);
		if (y - 1 >= 0) // check if already went to the top
			if (!squareSolvingArray[x][y - 1].visitedSolving)
				squareNeighbours.add(squareSolvingArray[x][y - 1]);
		if (x + 1 < 70) // check if already went to the right
			if (!squareSolvingArray[x + 1][y].visitedSolving)
				squareNeighbours.add(squareSolvingArray[x + 1][y]);
		if (y + 1 < 70) // check if already went to the bottom
			if (!squareSolvingArray[x][y + 1].visitedSolving)
				squareNeighbours.add(squareSolvingArray[x][y + 1]);
		return squareNeighbours.size();
	}

	/**
	 * Starts the specific solving algorithm
	 *
	 * @param method   Name of method
	 * @param graphics Graphics object
	 */
	public static void selectSolve(String method, Graphics graphics) { // selects the specific solving method
		init(Logic.generateSquareSolve());
		switch (method) {
		case "alwaysLeft":
			AlwaysLeft.solve();
			break;
		case "randomDir":
			RandomDir.solve();
			break;
		case "humanLike":
			HumanLike.solve();
			break;
		}
	}

}