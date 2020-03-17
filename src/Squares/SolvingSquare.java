package Squares;

public class SolvingSquare {

	/**
	 * Class for solving the maze. Square class and adds booleans
	 * for memorizing which path already was used
	 */
	public boolean visitedSolving = false;

	public int xIndex, yIndex;

	/**
	 * Constructor which copies the representative square
	 *
	 * @param s Squares.Square object
	 */
	public SolvingSquare(int pX, int pY) {
		this.xIndex = pX;
		this.yIndex = pY;
	}

}