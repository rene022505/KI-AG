package ActualEditor;

import Editor.Editor;
import ProgramLogic.Logic;
import multiThreading.InvokeGenerate_Thread;
import multiThreading.InvokeSolving_Thread;
import multiThreading.Resize_Thread;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

import DataHolder.DataHolder;

@SuppressWarnings("serial")
public class ActualEditor extends JFrame {

	/**
	 * Class for displaying the actual maze editor
	 */

	public boolean hasMaze = false;

	private boolean changed = false;
	private String filename;

	public JPanel panel = new ImageFrame();
	
	public JScrollPane scrollPane = new JScrollPane();
	
	// Thread objects
	Thread invokeGeneration = new Thread();
	Thread invokeSolving = new Thread();
	Thread resizeMaze = new Thread();
	
	JPopupMenu solveMenu;
	JPopupMenu optionMenu;

	/**
	 * Constructor which creates the whole editors GUI and starts the basic logic
	 * 
	 * @param pFilePath String with path to file
	 */
	public ActualEditor(String pFilePath) {
		super(); // Base GUI stuff
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(735, 785);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getSize().width) / 2;
		int y = (d.height - getSize().height) / 2;
		setLocation(x, y);

		// Parse filename for some eye candy
		this.filename = pFilePath;
		for (int i = this.filename.length() - 5; i > 0; i--) {
			if (this.filename.charAt(i) == '\\') {
				this.filename = this.filename.substring(i + 1);
				break;
			}
		}
		setTitle("Editor.Editor: " + this.filename);
		setResizable(false);
		Container cp = getContentPane();
		cp.setLayout(null);

		// Add buttons and panels and all the good stuff
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JButton saveButton = new JButton();
		saveButton.setBounds(0, 0, 70, 20);
		saveButton.setText("Save");
		saveButton.setMargin(new Insets(2, 2, 2, 2));
		saveButton.addActionListener(this::saveButtonActionPerformed);
		menuBar.add(saveButton);

		solveMenu = new JPopupMenu();
		solveMenu.setBounds(0, 0, 90, 20);

		JMenuItem alwaysLeft = new JMenuItem();
		alwaysLeft.setText("Always left");
		alwaysLeft.setName("alwaysLeft");
		alwaysLeft.addActionListener(e -> solveButtonActionPerformed(alwaysLeft));
		solveMenu.add(alwaysLeft);

		JMenuItem randomDir = new JMenuItem();
		randomDir.setText("Random Direction");
		randomDir.setName("randomDir");
		randomDir.addActionListener(e -> solveButtonActionPerformed(randomDir));
		solveMenu.add(randomDir);

		JMenuItem trueAlwaysLeft = new JMenuItem();
		trueAlwaysLeft.setText("True Always Left");
		trueAlwaysLeft.setName("trueAlwaysLeft");
		trueAlwaysLeft.addActionListener(e -> solveButtonActionPerformed(trueAlwaysLeft));
		solveMenu.add(trueAlwaysLeft);
		
		JMenuItem quantumLike = new JMenuItem();
		quantumLike.setText("Quantum computer like");
		quantumLike.setName("quantumLike");
		quantumLike.addActionListener(e -> solveButtonActionPerformed(quantumLike));
		solveMenu.add(quantumLike);
		
		JMenuItem aStar = new JMenuItem();
		aStar.setText("A*");
		aStar.setName("aStar");
		aStar.addActionListener(e -> solveButtonActionPerformed(aStar));
		solveMenu.add(aStar);

		JButton solveButton = new JButton();
		solveButton.setBounds(0, 0, 70, 20);
		solveButton.setText("Solve");
		solveButton.setMargin(new Insets(2, 2, 2, 2));
		solveButton.addActionListener(this::solveButtonActionPerformed);
		menuBar.add(solveButton);

		JButton generateButton = new JButton();
		generateButton.setBounds(0, 0, 70, 20);
		generateButton.setText("Generate new maze");
		generateButton.setMargin(new Insets(2, 2, 2, 2));
		generateButton.addActionListener(this::generateButtonActionPerformed);
		menuBar.add(generateButton);

		JButton openNew = new JButton();
		openNew.setBounds(0, 0, 200, 20);
		openNew.setText("Open new File");
		openNew.setMargin(new Insets(2, 2, 2, 2));
		openNew.addActionListener(this::openNewButtonActionPerformed);
		menuBar.add(openNew);

		optionMenu = new JPopupMenu();

		JCheckBoxMenuItem visualizeGeneration = new JCheckBoxMenuItem();
		visualizeGeneration.setText("Visualize generation");
		optionMenu.add(visualizeGeneration);
		visualizeGeneration.addActionListener(this::visualizeGenerationCheckBoxActionPerformed);

		JCheckBoxMenuItem visualizeSolving = new JCheckBoxMenuItem();
		visualizeSolving.setText("Visualize solving");
		optionMenu.add(visualizeSolving);
		visualizeSolving.addActionListener(this::visualizeSolvingCheckBoxActionPerformed);
		
		JCheckBoxMenuItem multiThreading = new JCheckBoxMenuItem();
		multiThreading.setText("Multi threading");
		optionMenu.add(multiThreading);
		multiThreading.addActionListener(this::multiThreadingCheckBoxActionPerformed);

		JButton optionsButton = new JButton();
		optionsButton.setBounds(0, 0, 70, 20);
		optionsButton.setText("Options");
		optionsButton.setMargin(new Insets(2, 2, 2, 2));
		optionsButton.addActionListener(this::optionsButtonActionPerformed);
		menuBar.add(optionsButton);
		
		JTextField gridSize = new JTextField();
		gridSize.setBounds(0, 0, 150, 20);
		gridSize.addKeyListener(new KeyListener() { // key listener as submit action for resizing the maze size
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						int newSize = Integer.parseInt(gridSize.getText());
						if (newSize < 2)
							JOptionPane.showMessageDialog(null, "Please enter a number bigger or equal to 2!", "Input Error",
									JOptionPane.ERROR_MESSAGE);
						else {
							if (!resizeMaze.isAlive() && !invokeGeneration.isAlive() && !invokeSolving.isAlive()) { // if thread is not already running create new thread
								resizeMaze = new Thread(new Resize_Thread(filename, newSize, panel, scrollPane));
								resizeMaze.start();
							} else { // if thread is already running interrupt it and start a new one
								if (resizeMaze.isAlive())
									resizeMaze.interrupt();
								if (invokeGeneration.isAlive())
									invokeGeneration.interrupt();
								if (invokeSolving.isAlive())
									invokeSolving.interrupt();
								resizeMaze = new Thread(new Resize_Thread(filename, newSize, panel, scrollPane));
								resizeMaze.start();
							}
						}
						hasMaze = false;
						changed = true;
						setTitle("Editor.Editor: " + filename + " - unsaved work");
					} catch (NumberFormatException ex) { 
						JOptionPane.showMessageDialog(null, "Please enter a number bigger or equal to 2!", "NumberFormatException",
								JOptionPane.ERROR_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Something happened and I don't know what... Check console", "?!?!?",
								JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
				}
			}
		});
		menuBar.add(gridSize);
		
		panel.setPreferredSize(new Dimension(DataHolder.panelSize + 1, DataHolder.panelSize + 1)); // setPreferedSize because ScrollPane wants so
		panel.setOpaque(true);
		
		// For the bigger picture
		scrollPane.setBounds(0, 0, DataHolder.panelSize + 19, DataHolder.panelSize + 19);
		scrollPane.setOpaque(true);
		scrollPane.setViewportView(panel);
		cp.add(scrollPane);

		setVisible(true);

		// Updates defaultCloseOperation when closing
		// I'm so proud of this part because i managed to solve it without the Internet
		// but just from reading through
		// JFrame.java and some common sense
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				setDefaultCloseOperation(handleClose());
			}
		});
		
		// Parse and display file and all 
		Logic.init(pFilePath, (int) Math.sqrt(new File(filename).length()));
		Drawing.drawImage(panel.getGraphics()); 
	}
	
	/**
	 * Displays a little drop down with all the different solving algorithms
	 * 
	 * @param ae ActionEvent
	 */
	private void solveButtonActionPerformed(ActionEvent ae) {
		// http://java-demos.blogspot.com/2013/10/show-popup-menu-on-jbutton-click.html
		Component b = (Component) ae.getSource();
		Point p = b.getLocationOnScreen();
		solveMenu.show(this, 0, 0);
		solveMenu.setLocation(p.x, p.y + b.getHeight());
	}

	/**
	 * Displays a little drop down with all the different options
	 * 
	 * @param ae ActionEvent
	 */
	private void optionsButtonActionPerformed(ActionEvent ae) {
		// http://java-demos.blogspot.com/2013/10/show-popup-menu-on-jbutton-click.html
		Component b = (Component) ae.getSource();
		Point p = b.getLocationOnScreen();
		optionMenu.show(this, 0, 0);
		optionMenu.setLocation(p.x, p.y + b.getHeight());
	}

	/**
	 * ActionListener for the visualize generation option
	 * 
	 * @param ae Action event
	 */
	public void visualizeGenerationCheckBoxActionPerformed(ActionEvent ae) {
		AbstractButton abstractButton = (AbstractButton) ae.getSource();
		DataHolder.genVis = abstractButton.getModel().isSelected();
	}

	/**
	 * ActionListener for the visualize solving option
	 * 
	 * @param ae Action event
	 */
	public void visualizeSolvingCheckBoxActionPerformed(ActionEvent ae) {
		AbstractButton abstractButton = (AbstractButton) ae.getSource();
		DataHolder.solVis = abstractButton.getModel().isSelected();
		if (DataHolder.solVis)
			JOptionPane.showMessageDialog(null, "Not yet implemented properly", "LazyDevException",
					JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * ActionListener for activating multi threading
	 * 
	 * @param ae Action event
	 */
	public void multiThreadingCheckBoxActionPerformed(ActionEvent ae) {
		AbstractButton abstractButton = (AbstractButton) ae.getSource();
		DataHolder.multiThreading = abstractButton.getModel().isSelected();
		if (DataHolder.multiThreading)
			JOptionPane.showMessageDialog(null, "Not yet implemented properly", "LazyDevException",
					JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Saves the edited maze from RAM to primary storage
	 * 
	 * @param evt Action event
	 */
	public void saveButtonActionPerformed(ActionEvent evt) {
		if (changed) {
			Logic.save();
			changed = false;
			setTitle("Editor.Editor: " + this.filename);
		}
	}

	/**
	 * Activates the specific solving algorithm for selected option
	 * 
	 * @param ji JMenuItem to distinguish different options
	 */
	public void solveButtonActionPerformed(JMenuItem ji) {
		if (hasMaze) {
			if (!resizeMaze.isAlive() && !invokeGeneration.isAlive() && !invokeSolving.isAlive()) { // if thread is not already running create new thread
				invokeSolving = new Thread(new InvokeSolving_Thread(ji.getName(), panel.getGraphics()));
				invokeSolving.start();
			} else { // if thread is already running interrupt it and start a new one
				if (!invokeSolving.isAlive()) {
					if (invokeGeneration.isAlive())
						JOptionPane.showMessageDialog(this, "Wait while generation is in progress (you can interrupt generation by changing the size of the maze)", "Generation in progress", JOptionPane.ERROR_MESSAGE);
					if (resizeMaze.isAlive())
						JOptionPane.showMessageDialog(this, "Wait while the maze is being resized", "Resize in progress", JOptionPane.ERROR_MESSAGE);
				} else {
					invokeSolving.interrupt();
					invokeSolving = new Thread(new InvokeSolving_Thread(ji.getName(), panel.getGraphics()));
					invokeSolving.start();
				}
			}
			setTitle("Editor.Editor: " + this.filename + " - unsaved work");
			changed = true;
		}
		else
			JOptionPane.showMessageDialog(this, "Nothing to solve", "NothingToSolve Exception", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Starts generation of new maze
	 * 
	 * @param evt Action event
	 */
	public void generateButtonActionPerformed(ActionEvent evt) {
		if (!resizeMaze.isAlive() && !invokeGeneration.isAlive() && !invokeSolving.isAlive()) { // if thread is not already running create new thread
			invokeGeneration = new Thread(new InvokeGenerate_Thread(panel.getGraphics(), DataHolder.genVis));
			invokeGeneration.start();
		} else { // if thread is already running interrupt it and start a new one
			if (!invokeGeneration.isAlive()) {
				if (invokeSolving.isAlive())
					JOptionPane.showMessageDialog(this, "Wait while solving is in progress (you can interrupt solving by changing the size of the maze)", "Solving in progress", JOptionPane.ERROR_MESSAGE);
				if (resizeMaze.isAlive())
					JOptionPane.showMessageDialog(this, "Wait while the maze is being resized", "Resize in progress", JOptionPane.ERROR_MESSAGE);
			} else {
				invokeGeneration.interrupt();
				invokeGeneration = new Thread(new InvokeGenerate_Thread(panel.getGraphics(), DataHolder.genVis));
				invokeGeneration.start();
			}
		}
		changed = true;
		hasMaze = true;
		setTitle("Editor.Editor: " + this.filename + " - unsaved work");
	}

	/**
	 * Opens new instance of Editor.Editor in order to create or open a new file
	 * 
	 * @param evt Action event
	 */
	public void openNewButtonActionPerformed(ActionEvent evt) {
		if (handleClose() == 2)
			new Editor(this);
	}

	/**
	 * Activates when window is being closed, checks for unsaved work and asks the
	 * user if they are sure to continue. Also activates when trying to open a new
	 * file
	 * 
	 * @return Closing operation
	 */
	public int handleClose() {
		if (!changed)
			return WindowConstants.DISPOSE_ON_CLOSE;
		else // Ok 0, cancel 2
			switch (JOptionPane.showConfirmDialog(this, "Do you want to continue without saving?", "Warning",
					JOptionPane.OK_CANCEL_OPTION)) {
			case 0:
				return WindowConstants.DISPOSE_ON_CLOSE;
			case 2:
				return WindowConstants.DO_NOTHING_ON_CLOSE;
			default:
				JOptionPane.showMessageDialog(this, "Something happened and I don't know what...", "?!?!?",
						JOptionPane.ERROR_MESSAGE);
				return WindowConstants.DO_NOTHING_ON_CLOSE;
			}
	}
}