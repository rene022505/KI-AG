package Squares;

import java.awt.*;

public class Square {

	/**
	 * Class representing the individual squares in the mae
	 * <p>
	 * Squares.Square as bit 00ab cdef: 0 - start (will be changed since always
	 * starts at top left) 0 - finish a - part of solve path b - has been visited c
	 * - top wall (0 wall, 1 no wall) d - right wall (0 wall, 1 no wall) e - bottom
	 * wall (0 wall, 1 no wall) f - left wall (0 wall, 1 no wall)
	 */

	public boolean[] walls = { true, true, true, true }; // true = wall, false = no wall
	public int x, y;
	public boolean visited = false;
	public boolean solve = false;
	public boolean isStart = false;
	public boolean isFinish = false;

	/**
	 * Removes the top wall of the square
	 *
	 * @param g Graphics object
	 */
	public void removeTop(Graphics g) {
		g.drawLine(this.x + 1, this.y, this.x + 9, this.y);
	}

	/**
	 * Removes the top wall flag
	 */
	public void removeTopBool() {
		this.walls[0] = false;
	}

	/**
	 * Removes the right wall of the square
	 *
	 * @param g Graphics object
	 */
	public void removeRight(Graphics g) {
		g.drawLine(this.x + 10, this.y + 1, this.x + 10, this.y + 9);
	}

	/**
	 * Removes the right wall flag
	 */
	public void removeRightBool() {
		this.walls[1] = false;
	}

	/**
	 * Removes the bottom wall of the square
	 *
	 * @param g Graphics object
	 */
	public void removeBottom(Graphics g) {
		g.drawLine(this.x + 1, this.y + 10, this.x + 9, this.y + 10);
	}

	/**
	 * Removes the bottom wall flag
	 */
	public void removeBottomBool() {
		this.walls[2] = false;
	}

	/**
	 * Removes the left wall of the square
	 *
	 * @param g Graphics object
	 */
	public void removeLeft(Graphics g) {
		g.drawLine(this.x, this.y + 1, this.x, this.y + 9);
	}

	/**
	 * Removes the left wall flag
	 */
	public void removeLeftBool() {
		this.walls[3] = false;
	}

}