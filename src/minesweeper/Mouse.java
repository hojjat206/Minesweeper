package minesweeper;

import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Mouse {
	private static final int MILLISECONDS_CLICK_DELAY = 25;
	private static HashMap<Integer, Pixel> centerOfSquares =
			HashMapsOnDisk.getHashMap("centerOfSquares.ser");
	
	public static void clickFirstSquare(){
		int startSquare = 0;
		int milliseconds = 200;
		Main.robot.delay(milliseconds);
		leftClickSquare(startSquare);
	}
	
	private static void leftClickSquare(int square){
		moveMouse(square);
		leftClickMouse();
	}

	private static void moveMouse(int square){
		Pixel pixel = centerOfSquares.get(square);
		Main.robot.mouseMove(pixel.x, pixel.y);
	}
	
	private static void leftClickMouse(){
		Main.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		Main.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		Main.robot.delay(MILLISECONDS_CLICK_DELAY);
		Main.robot.mouseMove(0, 0);
	}
	
	public static boolean clickRandomSurroundingNonClicked(){
		int square = randomSquare(Main.squaresWithNumbers);
		
		if (square == -1){
			return false;
		}

		Square squareData = Main.squaresMap.get(square);
		square = randomSquare(squareData.surroundingNonClickedSquares);
		
		if (square == -1){
			return false;
		}
		else{
			System.out.println("guessed");
			Main.guessed++;
			leftClickSquare(square);
			Square.updateTheSurroundingSquares(square);
			return true;
		}
	}
	
	public static void clickRandomNonClicked(){
		int square = randomSquare(Main.nonClickedSquares);
		
		if (square != -1){
			System.out.println("guessed empty");
			Main.guessed++;
			leftClickSquare(square);
			Square.updateTheSurroundingSquares(square);
		}
	}

	private static int randomSquare(ArrayList<Integer> squaresList){
		Random random = new Random();
		int randomNumber = 0;
		
		if (squaresList.size() == 0){
			return -1;
		}
		else if (squaresList.size() > 1){
			randomNumber = random.nextInt(squaresList.size()-1);
		}
		
		int randomSquare = squaresList.get(randomNumber);
		return randomSquare;
	}
	
	public static boolean clickSurroundingNonClicked(Square square){
		boolean clickedSquare = false;
		
		while(square.hasNextNonClicked()){
			int next = square.nextNonClicked();
			
			leftClickSquare(next);
			Square.updateTheSurroundingSquares(next);
			
			clickedSquare = true;
		}
		return clickedSquare;
	}
	
	public static void flagSurroudingNonClicked(Square square){
		while(square.hasNextNonClicked()){
			int next = square.nextNonClicked();

			flagSquare(next);
			Square.updateTheSurroundingSquares(next);
		}
	}
	
	public static void flagSquare(int square){
		moveMouse(square);
		rightClickMouse();
		Board.placeNumberOnBoard(square, 9);
		Main.removeFromNonClicked(square);
	}
	
	private static void rightClickMouse(){
		Main.robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		Main.robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
		Main.robot.delay(MILLISECONDS_CLICK_DELAY);
	}

	public static boolean clickAllExceptEdgeAndNextToEdge(AdvancedData.OneAndOne advanced){
		ArrayList<Integer> squaresToClick = new ArrayList<Integer>();
		Square otherNumberedSquareData = Main.squaresMap.get(advanced.adjecentSquareWithNumber);
		
		addSquaresToClick(squaresToClick, otherNumberedSquareData);
		removeEdgeAndNextToEdge(squaresToClick, advanced);

		boolean clickedSquares = false;
		if (squaresToClick.size() > 0){
			clickSquares(squaresToClick);
			clickedSquares = true;
		}

		return clickedSquares;
	}
	
	private static void addSquaresToClick(ArrayList<Integer> squaresToClick,
			Square square){
		
		for (int nonClicked : square.surroundingNonClickedSquares){
			squaresToClick.add(nonClicked);
		}
	}

	private static void removeEdgeAndNextToEdge(ArrayList<Integer> squaresToClick,
				AdvancedData.OneAndOne advanced){
			
		int indexEdge = squaresToClick.indexOf(advanced.nonClickedEdge);
		squaresToClick.remove(indexEdge);
		
		int indexNonEdge = squaresToClick.indexOf(advanced.nonClickedAdjecentToEdge);
		squaresToClick.remove(indexNonEdge);
	}

	public static void clickSquares(ArrayList<Integer> squaresToClick){
		for (int square : squaresToClick){
			leftClickSquare(square);
			Square.updateTheSurroundingSquares(square);
		}
	}
}
