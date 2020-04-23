package DataHolder;

import java.util.ArrayList;
import java.util.Stack;

import Squares.Square;
import Squares.SolvingSquare;

public class DataHolder {
	
	public static int panelSize = 700;
	
	public static int gridSize = 70;
	public static double squareSize = panelSize / gridSize;
	
	public static Square[][] squares = new Square[gridSize][gridSize];
	public static ArrayList<Square> neighbourSquares = new ArrayList<>();
	public static Stack<Square> squareStack = new Stack<>();
	
	public static SolvingSquare[][] solvingSquares = new SolvingSquare[gridSize][gridSize];
	public static Stack<SolvingSquare> solvingSquareStack = new Stack<>();
	public static ArrayList<SolvingSquare> neighbourSolvingSquares = new ArrayList<>();
	
	// TODO
	public static boolean genVis = false; // not yet implemented
	public static boolean solVis = false; // not yet implemented

}
