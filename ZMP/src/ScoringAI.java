import java.util.ArrayList;

public class ScoringAI implements AI {
	private final int queenValue = 4;
	private final int thinkingSteps = 9;

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
		for(Move m : moves) {
			int score = scoreTheMove(m, board, currentPlayer, opponent, model);
			if(steps > 1)  {
				char[][] testBoard = simulateBoard(board, m, model);
				ArrayList<Move> candidates = model.checkValidMoves(testBoard, opponent);
				if(candidates.size()>0) {
					Move bestResponse = pickBest(testBoard, opponent, currentPlayer, model, candidates, steps-1);
			    	score -= scoreTheMove(bestResponse, testBoard, opponent, currentPlayer, model);
				}
				else score+=10000000;
			}
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
	
	private int scoreTheMove(Move move, char[][] board, char currentPlayer, char opponent, Model model) {
		char[][] copy = Model.copyBoard(board);
		return scoreTheBoard(model.performMove(move, copy), currentPlayer, opponent);
	}

}
