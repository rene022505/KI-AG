package Editor;

import ActualEditor.ActualEditor;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import javax.swing.*;

@SuppressWarnings("serial")
public class Editor extends JFrame {

	/**
	 * Class displaying the file selector/creator
	 */

	private JTextField path = new JTextField();
	private JTextField fileName = new JTextField();
	private JLabel fileNameLabel = new JLabel();
	private JLabel pathLabel = new JLabel();
	private JButton submit = new JButton();

	private boolean errorBool;

	public Editor() {
		super();
		init(null);
	}

	public Editor(JFrame jf) {
		super();
		init(jf);
	}

	/**
	 * Method which initializes the Editor.Editor instance with actual UI elements
	 *
	 * @param jf JFrame instance of the "ActualEditor.ActualEditor" or null
	 */
	public void init(JFrame jf) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		int frameWidth = 300;
		int frameHeight = 135;
		setSize(frameWidth, frameHeight);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getSize().width) / 2;
		int y = (d.height - getSize().height) / 2;
		setLocation(x, y);
		setTitle("Editor");
		setResizable(false);
		Container cp = getContentPane();
		cp.setLayout(null);

		fileNameLabel.setBounds(20, 10, 70, 20);
		fileNameLabel.setText("Name: ");
		cp.add(fileNameLabel);
		fileName.setBounds(100, 10, 150, 20);
		inputKeyListener(jf, cp, fileName);

		pathLabel.setBounds(20, 40, 70, 20);
		pathLabel.setText("Path: ");
		cp.add(pathLabel);
		path.setBounds(100, 40, 150, 20);
		inputKeyListener(jf, cp, path);

		submit.setBounds(100, 70, 100, 25);
		submit.setText("Ok");
		submit.setMargin(new Insets(2, 2, 2, 2));
		submit.addActionListener(evt -> submitActionPerformed(jf));
		submit.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (errorBool) {
						errorBool = false;
					} else {
						submitActionPerformed(jf);
					}
				}
			}
		});
		cp.add(submit);

		setVisible(true);
	}

	/**
	 * Adds key press shortcuts like enter to submit and if
	 * ActualEditor.ActualEditor instance is running esc to close the window
	 *
	 * @param jf   JFrame instance of ActualEditor.ActualEditor or null
	 * @param cp   Container which holds all the JComponents
	 * @param comp The JComponent of which to add the keyListener
	 */
	private void inputKeyListener(JFrame jf, Container cp, JTextField comp) {
		comp.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (jf != null)
						dispose();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (errorBool) {
						errorBool = false;
					} else {
						submit.doClick();
					}

				}
			}
		});
		cp.add(comp);
	}

	public static void main(String[] args) {
		new Editor();
	}

	/**
	 * Method which creates a new instance of ActualEditor.ActualEditor and handles
	 * opening and creating files
	 *
	 * @param jf Instance of ActualEditor.ActualEditor or null
	 */
	public void submitActionPerformed(JFrame jf) {
		if (path.getText().equals(""))
			if (!fileName.getText().equals(""))
				try {
					// create new file with filename
					OutputStream oStream = new FileOutputStream(fileName.getText() + ".rcif");

					byte[] temp = ByteBuffer.allocate(4900).array();
					oStream.write(temp);

					oStream.close();

					// close old editor if there was one
					if (jf != null)
						jf.dispose();

					new ActualEditor(fileName.getText() + ".rcif");
					dispose(); // closes this window
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(this,
							"Something withing the last few java calls went wrong for the file to have vanished :(",
							"FileNotFoundException", JOptionPane.ERROR_MESSAGE);
					errorBool = true;
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this, "Something happened to IO...", "IOException",
							JOptionPane.ERROR_MESSAGE);
					errorBool = true;
				} catch (Exception e) { // * Error if anything else goes wrong (don't know what would fulfil this)
					JOptionPane.showMessageDialog(this, "I don't know how we got here!", "Exception",
							JOptionPane.ERROR_MESSAGE);
					errorBool = true;
				}
			else {
				JOptionPane.showMessageDialog(this, "Please specify a name or path", "User error",
						JOptionPane.ERROR_MESSAGE);
				errorBool = true;
			}
		else { // if path is not empty prioritize it above creating a new file
				// check if file type is correct
			if (!path.getText().endsWith(".rcif")) {
				JOptionPane.showMessageDialog(this, "File must be type rcif", "Illegal file type",
						JOptionPane.ERROR_MESSAGE);
				errorBool = true;
			} else {
				File temp = new File(path.getText()); // (try to) open the file
				if (!temp.exists()) { // if file does not exist error
					JOptionPane.showMessageDialog(this, "File not found", "File not found", JOptionPane.ERROR_MESSAGE);
					errorBool = true;
				} else if (!temp.isFile()) { // if path does not lead to any file error
					JOptionPane.showMessageDialog(this, "Please select a valid .rcif file", "Not a file",
							JOptionPane.ERROR_MESSAGE);
					errorBool = true;
				} else { // start editor
					// close old editor if there was one
					if (jf != null)
						jf.dispose();

					new ActualEditor(path.getText()).hasMaze = true;
					dispose(); // closes this window
				}
			}
		}
	}
}
