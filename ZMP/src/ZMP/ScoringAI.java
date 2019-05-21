package ZMP;

import java.util.ArrayList;

public class ScoringAI implements AI {
	private final int queenValue = 4;
	private final int thinkingSteps = 5;

	@Override
	public Move makeAMove(char[][] board, ArrayList<Move> moves, char currentPlayer, char opponent, Model model) {
		return pickBest(board, currentPlayer, opponent, model, moves, this.thinkingSteps);
	}
	
	
	private char[][] simulateBoard(char[][] board, Move move, Model model){
		char[][] copy = Model.copyBoard(board);
		return model.performMove(move, copy);
		}
	
	private Move pickBest(char[][] board, char currentPlayer, char opponent, Model model, ArrayList<Move> moves, int steps) {
		Move best = null;
		int bestScore = -2000000;
		if(moves.size() == 1){
			return moves.get(0);
		}
		for(Move m : moves) {
			int score = scoreTheMove(m, board, currentPlayer, opponent, model, steps);
			if(score>bestScore) {
				best = m;
				bestScore = score;
			}
		}
		return best;
	}

	private int scoreTheBoard(char[][] board, char currentPlayer,char opponent) {
		int score = 0;		
		for(int i=1;i<board.length-1;i++) {
			for(int j=1;j<board[i].length-1;j++) {
				if(board[i][j]==currentPlayer)	score++;
				else if(board[i][j]==opponent) score--;
				else if(board[i][j]==Character.toUpperCase(currentPlayer)) score+=queenValue;
				else if(board[i][j]==Character.toUpperCase(opponent)) score-=queenValue;
			}
		}
		return score;
	}
	
	private int scoreTheMove(Move move, char[][] board, char currentPlayer, char opponent, Model model, int steps) {
		char[][] copy = Model.copyBoard(board);
		copy = model.performMove(move, copy);
		int score;
		if(steps == 0){
			score = scoreTheBoard(copy, currentPlayer, opponent);
		}
		else{
			ArrayList<Move> candidates = model.checkValidMoves(copy, opponent);
			if(candidates.size()==0){
				return 0;
			}
			Move response = this.pickBest(copy, opponent, currentPlayer, model, candidates, steps-1);
			score = this.scoreTheMove(response, copy, opponent, currentPlayer, model, steps -1) * (-1);
		}
		return score;
	}

}
