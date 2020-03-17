package DataHolder;

import java.util.ArrayList;
import java.util.Stack;

import Squares.Square;
import Squares.SolvingSquare;

public class DataHolder {
	
	public static Square[][] squares = new Square[70][70];
	public static ArrayList<Square> neighbourSquares = new ArrayList<>();
	public static Stack<Square> squareStack = new Stack<>();
	
	public static SolvingSquare[][] solvingSquareArray = new SolvingSquare[70][70];
	public static Stack<SolvingSquare> solvingSquareStack = new Stack<>();
	public static ArrayList<SolvingSquare> squareNeighbours = new ArrayList<>();

}
