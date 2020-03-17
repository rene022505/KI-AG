package ProgramLogic;

import javax.swing.*;

import DataHolder.DataHolder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import Squares.Square;
import Squares.SolvingSquare;

public class Logic {

	/**
	 * Class doing the general program logic
	 */

	private static String filePath;

	private static byte[][] file = new byte[70][70];

	/**
	 * Heart of all the logic, starts parsing the file from primary storage into RAM
	 *
	 * @param fP File path
	 */
	public static void init(String fP) {
		filePath = fP;

		try {
			FileInputStream iStream = new FileInputStream(fP);
			for (int y = 0; y < 70; y++) {
				for (int x = 0; x < 70; x++) {
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
		for (int y = 0; y < 70; y++) {
			for (int x = 0; x < 70; x++) {
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
		for (int y = 0; y < 70; y++) {
			for (int x = 0; x < 70; x++) {
				s = new Square();
				s.solve = (file[x][y] & 0x20) == 0x20;
				s.visited = (file[x][y] & 0x10) == 0x10;
				s.walls[0] = (file[x][y] & 0x08) != 0x08;
				s.walls[1] = (file[x][y] & 0x04) != 0x04;
				s.walls[2] = (file[x][y] & 0x02) != 0x02;
				s.walls[3] = (file[x][y] & 0x01) != 0x01;

				s.x = x * 10;
				s.y = y * 10;

				DataHolder.squares[x][y] = s;
			}
		}
	}

	// --- END PARSING ---

	/**
	 * Generates a two dimensional Squares.SquareSolving array for solving
	 *
	 * @return Squares.SquareSolving array
	 */
	public static SolvingSquare[][] generateSquareSolve() {
		SolvingSquare[][] sv = new SolvingSquare[70][70];
		for (int y = 0; y < 70; y++)
			for (int x = 0; x < 70; x++)
				sv[x][y] = new SolvingSquare(x, y);
		return sv;
	}

	/**
	 * Is invoked when clicking the save button, calls parseFileFromSquares() and
	 * writes the result into the file
	 */
	public static void save() {
		parseFileFromSquares();

		try {
			FileOutputStream oStream = new FileOutputStream(filePath);
			for (int y = 0; y < 70; y++) {
				for (int x = 0; x < 70; x++) {
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