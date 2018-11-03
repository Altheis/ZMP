import java.util.ArrayList;
import java.util.Random;

public class RandomAI implements AI {

	@Override
	public Move makeAMove(char[][] board, ArrayList<Move> moves, char currentPlayer, char opponent, Model model) {
		Random rand = new Random();
	    return moves.get(rand.nextInt(moves.size()));
	}

}
