package Solving;

import java.awt.Graphics;

import DataHolder.SolveMode;

public class AlwaysLeft {

	/**
	 * Class for the solving method of always going to the left
	 */

	/**
	 * Will be the main solving function
	 * 
	 * @param g Graphics object
	 */
	public static void solve(Graphics g) {
		System.out.println("Solving.AlwaysLeft.solve()");

		GeneralSolving.multiPurpose(SolveMode.AlwaysLeft, g);
		
		System.out.println("Done Solving.AlwaysLeft.solve()");
	}

}