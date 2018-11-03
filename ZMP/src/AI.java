import java.util.ArrayList;

public interface AI {
	public Move makeAMove(char[][] board, ArrayList<Move> moves, char currentPlayer, char opponent, Model model);
}
