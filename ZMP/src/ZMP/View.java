package ZMP;

import java.awt.Point;

public interface View {
	void displayBoard();
	char[][] getBoard();
	Point moveStartCell();
	Point moveEndCell();
	void setBoard(char[][] newBoard);
}
