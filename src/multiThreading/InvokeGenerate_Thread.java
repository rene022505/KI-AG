package multiThreading;

import java.awt.Graphics;

import DataHolder.DataHolder;
import ProgramLogic.Generating;

public class InvokeGenerate_Thread implements Runnable {
	
	/**
	 * Class for invoking generating in a new thread to not freeze the GUI
	 */
	
	Graphics g;
	boolean genVis;
	
	public InvokeGenerate_Thread(Graphics g, boolean genVis) {
		this.g = g;
		this.genVis = genVis;
	}

	@Override
	public void run() {
		Generating.generateMaze(this.g, this.genVis);
		DataHolder.ae.setTitle("Editor.Editor: " + DataHolder.ae.filename + " - unsaved work");
	}

}
