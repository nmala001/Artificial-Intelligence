import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class CrosswordPuzzle {
	private static ArrayList<String> three =new ArrayList<String>();
	private static ArrayList<String> four =new ArrayList<String>();
	private static HashSet<String> fourSet = new HashSet<String>();
	private static ArrayList<String> five =new ArrayList<String>();
	private static int numCrossCalls = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader f = new BufferedReader(new FileReader("englishWords.txt"));
		while(true){
			String word = f.readLine();
			if(word==null){
				break;
			}
			if(word.length()==3){
				three.add(word);
			}
			if(word.length()==4){
				four.add(word);
			}
			if(word.length()==5){
				five.add(word);
			}
		}
	}
		
		//fourSet.addAll(four);
		/*
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 2; i++) {
			System.out.println(five.get(i));
			String theFive = five.get(i);

			char[][] board = new char[4][4];
			for (int j = 3; j < 7; j++) {
				System.out.println("# 4 letter words ending in " + theFive.charAt(j) + ": " + countEndingIn(four, theFive.charAt(j)));
				board[3][j-3] = theFive.charAt(j);
			}
			
			if (tryToSolve(board, 0)) {
				System.out.println("");
				printBoard(board, theFive);
			} else {
				System.out.println("... failure ...");
			}
			
			System.out.println();
			
		}
		long endTime = System.currentTimeMillis();
		
		System.out.println("Solved puzzles in " + (endTime - startTime) + " millis.");
	}
	*/
	
	private static void printBoard(char[][] board, String theFive) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print("" + board[i][j]);
			}
			System.out.println();
		}
		System.out.println();
		
	}

	private static boolean tryToSolve(char[][] board, int colNumber) {
		if (colNumber == 4) {
			return checkCrossWords(board);
		}
		
		for (int i = 0; i < four.size(); i++) {
			String thisFour = four.get(i);
			if (thisFour.charAt(3) == board[3][colNumber]) {
				// last character matches, let's try this one
				// Copy it onto board in the given column (colNumber)
				for (int j = 0; j < 4; j++) {
					board[j][colNumber] = thisFour.charAt(j);
				}
				
				if (tryToSolve(board, colNumber + 1)) {
					return true;
				}
				
				// If this word didn't work, take it off the board
				for (int j = 0; j < 3; j++) {
					board[j][colNumber] = 0;
				}
			}
		}
		
		return false;
	}
	
	private static boolean checkCrossWords(char[][] board) {
		numCrossCalls++;
		if (numCrossCalls % 100000 == 0) {
			System.out.println("Considered " + numCrossCalls + "possibilities.");
		}
		for (int i = 0; i < 3; i++) {
			String row = new String(board[i]);
			if (!fourSet.contains(row)) {
				return false;
			}
		}
		return true;
	}

	private static int countEndingIn(ArrayList<String> words, char endChar) {
		int count = 0;
		for (int i = 0; i < words.size(); i++) {
			String thisWord = words.get(i);
			if (thisWord.charAt(thisWord.length() - 1) == endChar) {
				count++;
			}
		}
		return count;
	}

}
