package MSIWPG;

import java.awt.Point;

interface View {
	void displayBoard();
	char[][] getBoard();
	Point moveStartCell();
	Point moveEndCell();
	void setBoard(char[][] newBoard);
}
