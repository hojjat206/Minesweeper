package minesweeper;

import java.util.ArrayList;

public class Lists {
	public static ArrayList<Integer> nonClickedSquares = new ArrayList<Integer>();
	public static ArrayList<Integer> squaresWithNumbers = new ArrayList<Integer>();

	public static void fillArrayLists(){
		for (int square = 0; square < 30*16; square++){
			int number = Board.getNumberOnSquare(square);
			
			if (number == 8){
				nonClickedSquares.add(square);
			}
			else if (number != 0 && number != 9){
				squaresWithNumbers.add(square);
			}
		}
	}
	
	public static void updateSquaresWithNumbers(){
		for (int square : squaresWithNumbers){
			Square squareWithNumber = Maps.squareMap.get(square);
			squareWithNumber.updateSurroundings();
			Maps.squareMap.put(square, squareWithNumber);
		}
	}
	
	public static void removeFromSquaresWithNumbers(int square){
		int index = squaresWithNumbers.indexOf(square);
		squaresWithNumbers.remove(index);
	}
	
	public static void removeFromNonClicked(int square){
		int index = nonClickedSquares.indexOf(square);
		nonClickedSquares.remove(index);
	}
	
	public static void printArrayList(ArrayList<Integer> liste){
		for (int element : liste){
			System.out.print(element + " ");
		}
		System.out.println();
	}
	
	public static void updateNonClickedSquares(){
		//copy ArrayList to avoid concurrency issues
		ArrayList<Integer> nonClickedCopy = new ArrayList<Integer>(nonClickedSquares);
		
		for (int square : nonClickedCopy){
			int number = ComputerVision.getNumber(square);
			
			if (number != 8){
				Board.placeNumberOnBoard(square, number);
				Lists.removeFromNonClicked(square);
	
				if (number != 0 && number != 9){
					Square squareWithNumber = new Square(square);
					Maps.squareMap.put(square, squareWithNumber);
					squaresWithNumbers.add(square);
				}
			}
		}
	}
}