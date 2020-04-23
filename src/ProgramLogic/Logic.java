package ProgramLogic;

import javax.swing.*;

import DataHolder.DataHolder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import Squares.SolvingSquare;
import Squares.Square;

public class Logic {

	/**
	 * Class doing the general program logic
	 */

	private static String filePath;

	private static byte[][] file = new byte[DataHolder.gridSize][DataHolder.gridSize]; // important

	/**
	 * Heart of all the logic, starts parsing the file from primary storage into RAM
	 *
	 * @param fP File path
	 * @param sizeChange If the maze size changed while the program was running
	 * @param size New maze size
	 */
	public static void init(String fP, boolean sizeChange, int size) {
		filePath = fP;

		try {
			if (!sizeChange) {
				DataHolder.gridSize = (int) Math.sqrt(new File(fP).length());
			} else {
				DataHolder.gridSize = size;
				
				// create new file with filename
				OutputStream oStream = new FileOutputStream(fP);

				byte[] temp = ByteBuffer.allocate(DataHolder.gridSize * DataHolder.gridSize).array();
				oStream.write(temp);

				oStream.close();
			}
			if (size > 70) {
				DataHolder.squareSize = 10;
				DataHolder.panelSize = size * 10;
			} else 
				DataHolder.squareSize = DataHolder.panelSize / DataHolder.gridSize;
			
			file = new byte[DataHolder.gridSize][DataHolder.gridSize];
			DataHolder.solvingSquares = new SolvingSquare[DataHolder.gridSize][DataHolder.gridSize];
			DataHolder.squares = new Square[DataHolder.gridSize][DataHolder.gridSize];

			FileInputStream iStream = new FileInputStream(fP);
			for (int y = 0; y < DataHolder.gridSize; y++) {
				for (int x = 0; x < DataHolder.gridSize; x++) {
					file[x][y] = iStream.readNBytes(1)[0];
				}
			}
			iStream.close();
		} catch (FileNotFoundException e) { // * In case the file can'T be read
			JOptionPane.showMessageDialog(null,
					"Something withing the last few java calls went wrong for the file to have vanished :(",
					"FileNotFoundException", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) { // * in case of something else
			JOptionPane.showMessageDialog(null, "Reading from file failed", "IOException", JOptionPane.ERROR_MESSAGE);
		}

		parseSquareFromFile();
	}

	// --- PARSING ---

	/**
	 * Parses what to write into the file from RAM
	 */
	static void parseFileFromSquares() {
		byte b;
		for (int y = 0; y < DataHolder.gridSize; y++) {
			for (int x = 0; x < DataHolder.gridSize; x++) {
				b = 0x00;
				if (DataHolder.squares[x][y].isStart)
					b |= 0x80;
				if (DataHolder.squares[x][y].isFinish)
					b |= 0x40;
				if (DataHolder.squares[x][y].solve)
					b |= 0x20;
				if (DataHolder.squares[x][y].visited)
					b |= 0x10;
				if (!DataHolder.squares[x][y].walls[0])
					b |= 0x08;
				if (!DataHolder.squares[x][y].walls[1])
					b |= 0x04;
				if (!DataHolder.squares[x][y].walls[2])
					b |= 0x02;
				if (!DataHolder.squares[x][y].walls[3])
					b |= 0x01;
				file[x][y] = b;
			}
		}
	}

	/**
	 * Parses the file into RAM
	 */
	static void parseSquareFromFile() {
		Square s;
		for (int y = 0; y < DataHolder.gridSize; y++) {
			for (int x = 0; x < DataHolder.gridSize; x++) {
				s = new Square();
				s.isStart = (file[x][y] & 0x80) == 0x80;
				s.isFinish = (file[x][y] & 0x40) == 0x40;
				s.solve = (file[x][y] & 0x20) == 0x20;
				s.visited = (file[x][y] & 0x10) == 0x10;
				s.walls[0] = (file[x][y] & 0x08) != 0x08;
				s.walls[1] = (file[x][y] & 0x04) != 0x04;
				s.walls[2] = (file[x][y] & 0x02) != 0x02;
				s.walls[3] = (file[x][y] & 0x01) != 0x01;

				s.x = (int) (x * DataHolder.squareSize);
				s.y = (int) (y * DataHolder.squareSize);
				s.absoluteX = x;
				s.absoluteY = y;

				DataHolder.squares[x][y] = s;
			}
		}
	}

	// --- END PARSING ---

	/**
	 * Is invoked when clicking the save button, calls parseFileFromSquares() and
	 * writes the result into the file
	 */
	public static void save() {
		parseFileFromSquares();

		try {
			FileOutputStream oStream = new FileOutputStream(filePath);
			for (int y = 0; y < DataHolder.gridSize; y++) {
				for (int x = 0; x < DataHolder.gridSize; x++) {
					oStream.write(file[x][y]);
				}
			}
			oStream.close();
		} catch (FileNotFoundException e) { // In case the file can't be read
			JOptionPane.showMessageDialog(null,
					"Something withing the last few java calls went wrong for the file to have vanished :(",
					"FileNotFoundException", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) { // in case of something else
			JOptionPane.showMessageDialog(null, "Reading from file failed", "IOException", JOptionPane.ERROR_MESSAGE);
		}
	}
}