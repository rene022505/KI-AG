package ProgramLogic;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import Squares.Square;
import Squares.SquareSolving;

public class Logic {

	/**
	 * Class doing the general program logic
	 */

	private static String filePath;

	private static byte[][] file = new byte[70][70];

	public static Square[][] squares = new Square[70][70];
	public static ArrayList<Square> neighbourSquares = new ArrayList<>();
	public static Stack<Square> squareStack = new Stack<>();

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
				if (squares[x][y].isStart)
					b |= 0x80;
				if (squares[x][y].isFinish)
					b |= 0x40;
				if (squares[x][y].solve)
					b |= 0x20;
				if (squares[x][y].visited)
					b |= 0x10;
				if (!squares[x][y].walls[0])
					b |= 0x08;
				if (!squares[x][y].walls[1])
					b |= 0x04;
				if (!squares[x][y].walls[2])
					b |= 0x02;
				if (!squares[x][y].walls[3])
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
				if ((file[x][y] & 0x80) == 0x80)
					s.isStart = true;
				if ((file[x][y] & 0x40) == 0x40)
					s.isFinish = true;
				if ((file[x][y] & 0x20) == 0x20) // solve bit
					s.solve = true;
				if ((file[x][y] & 0x10) == 0x10) // visited bit
					s.visited = true;
				if ((file[x][y] & 0x08) == 0x08) // top wall
					s.walls[0] = false;
				if ((file[x][y] & 0x04) == 0x04) // right wall
					s.walls[1] = false;
				if ((file[x][y] & 0x02) == 0x02) // bottom wall
					s.walls[2] = false;
				if ((file[x][y] & 0x01) == 0x01) // left wall
					s.walls[3] = false;

				s.x = x * 10;
				s.y = y * 10;

				squares[x][y] = s;
			}
		}
	}

	// --- END PARSING ---

	/**
	 * Generates a two dimensional Squares.SquareSolving array for solving
	 *
	 * @return Squares.SquareSolving array
	 */
	public static SquareSolving[][] generateSquareSolve() {
		SquareSolving[][] sv = new SquareSolving[70][70];
		for (int y = 0; y < 70; y++)
			for (int x = 0; x < 70; x++)
				sv[x][y] = new SquareSolving(Logic.squares[x][y]);
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