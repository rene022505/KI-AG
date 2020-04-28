package multiThreading;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ActualEditor.Drawing;
import ProgramLogic.Logic;
import DataHolder.DataHolder;

public class Resize_Thread implements Runnable {
	
	/**
	 * Class for generating a new sized maze in a new thread to not freeze the GUI
	 */
	
	int size;
	String name;
	JPanel panel;
	JScrollPane scrollPane;
	
	public Resize_Thread(String name, int size, JPanel panel, JScrollPane scrollPane) {
		this.name = name;
		this.size = size;
		this.panel = panel;
		this.scrollPane = scrollPane;
	}

	@Override
	public void run() {		
		if (size > 70) { // change the panels size depending on the grid size
			panel.setPreferredSize(new Dimension(size * 10 + 1, size * 10 + 1)); 
		} else {
			panel.setPreferredSize(new Dimension(701, 701));
		}
		scrollPane.setViewportView(panel); // update the scroll pane 
		
		DataHolder.rwl.writeLock().lock(); // Declares a write lock so that other code that is
										   // read locked can't be run
		Logic.init(name, size); // create new file and squares in RAM
		DataHolder.rwl.writeLock().unlock();
		
		Drawing.drawImage(panel.getGraphics());
	}

}
