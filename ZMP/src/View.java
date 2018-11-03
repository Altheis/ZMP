import java.awt.Point;

public interface View {
	public void displayBoard();
	public char[][] getBoard();
	public Point moveStartCell();
	public Point moveEndCell();
	public void setBoard(char[][] newBoard);
	public void endGame(String result);
}
