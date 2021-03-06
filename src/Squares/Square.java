package Squares;

import java.awt.*;

import DataHolder.DataHolder;

public class Square {

	/**
	 * Class representing the individual squares in the maze
	 * Squares.Square as byte 01ab cdef: 
	 * 0 - start (will be changed since always starts at top left) TODO
	 * 1 - finish (will be changed since always finishes at bottom right) TODO
	 * a - part of solve path 
	 * b - has been visited (can be removed too) TODO
	 * c - top wall (0 wall, 1 no wall) 
	 * d - right wall (0 wall, 1 no wall) 
	 * e - bottom wall (0 wall, 1 no wall) 
	 * f - left wall (0 wall, 1 no wall)
	 */
	
	// TODO:
	/* MAJOR CHANGE IN GENERATION WITH CUSTOM START AND FINISH COULD HAPPEN */

	public boolean[] walls = { true, true, true, true }; // true = wall, false = no wall
	public int x, y; // Pixel location
	public int absoluteX, absoluteY; // Array index
	public boolean visited = false;
	public boolean solve = false;
	public boolean isStart = false; // Will be removed soon
	public boolean isFinish = false; // Could be removed too

	/**
	 * Removes the top wall of the square
	 *
	 * @param g Graphics object
	 */
	public void removeTop(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawLine(this.x + 1, this.y, this.x + (int) DataHolder.squareSize - 1, this.y);
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
		g.setColor(Color.WHITE);
		g.drawLine(this.x + (int) DataHolder.squareSize, this.y + 1, this.x + (int) DataHolder.squareSize, this.y + (int) DataHolder.squareSize - 1);
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
		g.setColor(Color.WHITE);
		g.drawLine(this.x + 1, this.y + (int) DataHolder.squareSize, this.x + (int) DataHolder.squareSize - 1, this.y + (int) DataHolder.squareSize);
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
		g.setColor(Color.WHITE);
		g.drawLine(this.x, this.y + 1, this.x, this.y + (int) DataHolder.squareSize - 1);
	}

	/**
	 * Removes the left wall flag
	 */
	public void removeLeftBool() {
		this.walls[3] = false;
	}

}