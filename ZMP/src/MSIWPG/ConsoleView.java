package MSIWPG;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleView implements View {

	private char[][] board;
	
	@Override
	public void displayBoard() {
        for (char[] chars : this.board) {
			for (char aChar : chars) System.out.print(" " + aChar);
            System.out.print("\n");
        }

	}
	@Override
	public char[][] getBoard() {
		return this.board;
	}

	@Override
	public Point moveStartCell() {
		String input;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Pick staring point");
		try {
			input = br.readLine();
			if(input.length()!=2) return moveStartCell();
			return new Point(Character.getNumericValue(input.charAt(0)),Character.getNumericValue(input.charAt(1)));
		} catch (IOException e) {
			return moveStartCell();
		}
	}

	@Override
	public Point moveEndCell() {
		String input;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Pick ending point");
		try {
			input = br.readLine();
			if(input.length()!=2) return moveEndCell();
			return new Point(Character.getNumericValue(input.charAt(0)),Character.getNumericValue(input.charAt(1)));
		} catch (IOException e) {
			return moveEndCell();
		}
	}

	@Override
	public void setBoard(char[][] newBoard) {
		this.board = newBoard;
	}


}
