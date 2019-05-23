package MSIWPG;

import java.util.ArrayList;

interface AI {
	Move makeAMove(char[][] board, ArrayList<Move> moves, char currentPlayer, char opponent, Model model);
}
