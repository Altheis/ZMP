import java.awt.Point;
import java.util.ArrayList;

public class Controller {

	public static void main(String[] args) {
		Model model = new Model(8);
		View view;
		AI ai;
		try {
			view = Factory.createView("ConsoleView");
		}
		catch(IllegalArgumentException e) {
			System.out.println("Bad view parameter!!!\n Refering back to the console view!");
			view = new ConsoleView();
		}
		view.setBoard(model.createBoard());
		
		try {
			ai = Factory.createAI("ScoringAI");
		}
		catch(IllegalArgumentException e) {
			System.out.println("Bad view parameter!!!\n Refering back to the randomAI!");
			ai = new RandomAI();
		}
		view.setBoard(model.createBoard());
		
		Point startCell, endCell;
		while(true) {
			view.displayBoard();
			ArrayList<Move> valid =model.checkValidMoves(view.getBoard(), model.currentPlayer);
			if(valid.size()==0) {
				view.endGame(model.currentPlayer+" lost the game!");
				System.exit(0);
			}
			for(Move m : valid) System.out.println(m.start + " " + m.target + " " + m.takes );
			Move move;
			if(model.currentPlayer==model.player1) {
			startCell=view.moveStartCell();
			endCell=view.moveEndCell();
			move = new Move(startCell,endCell,null);
			move = model.verifyMove(move, valid);
			}
			else move = ai.makeAMove(view.getBoard(), valid, model.currentPlayer, model.currentPlayer==model.player1? model.player2 : model.player1, model);
			if(move != null) {
				view.setBoard(model.performMove(move, view.getBoard()));
				model.changePlayer();
			}
			else {
				System.out.println("Invalid move, try again.");
			}
		}
	}

}
