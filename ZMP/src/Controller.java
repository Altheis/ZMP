

import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Controller {
	static int done=0;
	static Point startCell, endCell;
	public static void main(String[] args) {

		Model model = new Model(8);
		View view;
		AI ai;

		try {
			view = Factory.createView("GuiView");
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



		while(true) {

			ArrayList<Move> valid =model.checkValidMoves(view.getBoard(), model.currentPlayer);
			if(valid.size()==0) {
				view.endGame(model.currentPlayer+" lost the game!");
				System.exit(0);
			}

			Move move=null;

			if(model.currentPlayer== Model.player1&&done!=0) {
			move = new Move(startCell,endCell,null);
			move = model.verifyMove(move, valid);
			done=0;
			}
			else if (model.currentPlayer==Model.player2) {
				move = ai.makeAMove(view.getBoard(), valid, model.currentPlayer, model.currentPlayer==Model.player1? Model.player2 : Model.player1, model);
			}
			if(move != null) {
				view.setBoard(model.performMove(move, view.getBoard()));
				view.displayBoard();
				model.changePlayer();
				GuiView.startpoint=null;
				GuiView.endpoint=null;
			}

		}
	}


}
