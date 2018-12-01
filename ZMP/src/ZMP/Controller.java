package ZMP;

import javax.swing.*;
import java.awt.Point;
import java.util.ArrayList;

public class Controller {
	static int done = 0;
	static Point startCell, endCell;
	public static int dim;
	public static void main(String[] args) {

		Model model;
		View view;
		AI ai;
		Object[] options = {"Sztuczną inteligencję",
				"Gra dwuosobowa"};
		int n = JOptionPane.showOptionDialog(new JFrame(),
				"Jakiego przeciwnika wybierasz?",
				"Wybór przeciwnika",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[1]);
		Object[] options2 = {"64",
				"100"};

		int n2 = JOptionPane.showOptionDialog(new JFrame(),
				"Ilość pól?",
				"Wielkość planszy",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options2,
				options2[1]);
		if (n2 == 0) {
			dim = 8;
			model = new Model(8);
		}
		else {
			model = new Model(10);
			dim = 10;
		}
		Object[] options3 = {"Tekstowa",
				"Graficzna"};
		int n3 = JOptionPane.showOptionDialog(new JFrame(),
				"Jaka forme gry wybierasz?",
				"Forma gry",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options3,
				options3[1]);

		try {
			if (n3 == 1) {
				view = Factory.createView("ZMP.GuiView");
			} else
				view = Factory.createView("ZMP.ConsoleView");
		} catch (IllegalArgumentException e) {
			System.out.println("Bad view parameter!!!\n Refering back to the console view!");
			view = new ConsoleView();
		}
		view.setBoard(model.createBoard());

		try {
			if (n == 0)
				ai = Factory.createAI("ZMP.ScoringAI");
			else
				ai = Factory.createAI("ZMP.RandomAI");
		} catch (IllegalArgumentException e) {
			System.out.println("Bad view parameter!!!\n Refering back to the randomAI!");
			ai = new RandomAI();
		}
		view.setBoard(model.createBoard());

		if (n3 == 0) {
			view.displayBoard();
		}
	int s=0;
		while (true) {

			ArrayList<Move> valid = model.checkValidMoves(view.getBoard(), model.currentPlayer);

			if (valid.size() == 0) {
				if (model.currentPlayer == Model.player2) {
					if (n3 == 0) {
						System.out.println("Komputer przegrał rozgrywkę");
					} else
						GuiView.jl.setText("Komputer przegrał rozgrywkę");
					break;
				}
				if (n3 == 0) {
					System.out.println("Użytkownik przegrał rozgrywkę");
				} else
					GuiView.jl.setText("Użytkownik przegrał rozgrywkę");
				break;
			}
			Move move = null;
			if (n3 == 0&&s==0) {
				for (Move m : valid) System.out.println("Współrzędne startowe "+m.start.x +", "+ m.start.y +". Współrzędne końcowe " + m.target.x+", "+m.target.y );
				move = new Move(view.moveStartCell(), view.moveEndCell(), null);
				move = model.verifyMove(move, valid);
				if (move == null) {
					System.out.println("Zły ruch!!");
					done = 0;
					s = 0;
				}else
				s=1;
			}


			if (model.currentPlayer == Model.player1 && done != 0) {
				move = new Move(startCell, endCell, null);
				move = model.verifyMove(move, valid);
				if (move == null) {
						GuiView.jl.setText("Zły ruch!!");
						done = 0;


				}

				} else if (model.currentPlayer == Model.player2) {
					move = ai.makeAMove(view.getBoard(), valid, model.currentPlayer, model.currentPlayer == Model.player1 ? Model.player2 : Model.player1, model);
					s=0;
			}
				if (move != null) {
					view.setBoard(model.performMove(move, view.getBoard()));
					view.displayBoard();
					model.changePlayer();
					GuiView.startpoint = null;
					GuiView.endpoint = null;
				}

			}
		}
	}



