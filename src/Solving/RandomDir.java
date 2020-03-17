package Solving;

import java.awt.Graphics;

import DataHolder.SolveMode;

public class RandomDir {

	/**
	 * Class for the solving method of choosing a random direction
	 */

	/**
	 * Will be the main solving function
	 * @param g Graphics object
	 */
	public static void solve(Graphics g) {
		// TODO
		System.out.println("Solving.RandomDir.solve()");
		
		GeneralSolving.multiPurpose(SolveMode.RandomDir, g);
		
		System.out.println("Done Solving.RandomDir.solve()");
	}
}