package Squares;

public class SquareSolving extends Square {

	/**
	 * Class for solving the maze Extends the Squares.Square class and adds booleans
	 * for memorizing which path already was used
	 */
	public boolean visitedSolving = false;

	public Square self;

	/**
	 * Constructor which copies the representative square
	 *
	 * @param s Squares.Square object
	 */
	public SquareSolving(Square s) {
		this.self = s;
	}

}