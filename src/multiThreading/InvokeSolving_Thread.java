package multiThreading;

import java.awt.Graphics;

import DataHolder.DataHolder;
import Solving.GeneralSolving;

public class InvokeSolving_Thread implements Runnable {
	
	/**
	 * Class for invoking solving in a new thread to not freeze the GUI
	 */
	
	String name;
	Graphics g;
	
	public InvokeSolving_Thread(String name, Graphics g) {
		this.name = name;
		this.g = g;
	}

	@Override
	public void run() {
		GeneralSolving.selectSolve(name, g, DataHolder.solVis); // Executes the selected solving method
	}

}
