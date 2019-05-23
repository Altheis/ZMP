package MSIWPG;

import javax.swing.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

class Controller {
    static int done = 0;
    static Point startCell, endCell;
    static int dim;
    static int opponentPick;
    public static void main(String[] args) {

        Model model;
        View view;
        AI ai, aiOpp;
        Object[] options = {"Artificial intelligence (Scoring)", "Artificial intelligence (Random)",
                "2 players", "2 artificial intelligence"};
        opponentPick = JOptionPane.showOptionDialog(new JFrame(),
                "Pick your opponent",
                "Pick opponent",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        Object[] options2 = {"64",
                "100"};

        int sizeOfTheBoard = JOptionPane.showOptionDialog(new JFrame(),
                "Pick size of the board",
                "Board size",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options2,
                options2[1]);
        if (sizeOfTheBoard == 0) {
            dim = 8;
            model = new Model(8);
        } else {
            model = new Model(10);
            dim = 10;
        }
        Object[] options3 = {"Console",
                "Gui"};
        int typeOfPlay = JOptionPane.showOptionDialog(new JFrame(),
                "Pick form of game",
                "Game form",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options3,
                options3[1]);

        try {
            if (typeOfPlay == 1) {
                view = Factory.createView("MSIWPG.GuiView");
            } else
                view = Factory.createView("MSIWPG.ConsoleView");
        } catch (IllegalArgumentException e) {
            System.out.println("Bad view parameter!!!\n Referring back to the console view!");
            view = new ConsoleView();
        }
        view.setBoard(model.createBoard());
        aiOpp= Factory.createAI("MSIWPG.RandomAI");
        try {
            if (opponentPick == 0 || opponentPick == 3)
                ai = Factory.createAI("MSIWPG.ScoringAI");
            else
                ai = Factory.createAI("MSIWPG.RandomAI");
        } catch (IllegalArgumentException e) {
            System.out.println("Bad view parameter!!!\n Referring back to the randomAI!");
            ai = new RandomAI();
        }
        view.setBoard(model.createBoard());

        if (typeOfPlay == 0) {
            view.displayBoard();
        }
        int s = 0;
        Object[] options4 = {"Tak",
                "Nie"};
        while (true) {

            ArrayList<Move> valid = model.checkValidMoves(view.getBoard(), model.currentPlayer);

            out:
            if (valid.size() == 0) {
                if (model.currentPlayer == Model.player2) {
                    if (typeOfPlay == 0) {
                        System.out.println("Computer lost");
                        System.out.println("Do you wanna play again? (y/n)");
                        Scanner in = new Scanner(System.in);
                        if (in.next().equals("y")) {
                            view.setBoard(model.createBoard());
                            valid = model.checkValidMoves(view.getBoard(), model.currentPlayer);
                            break out;
                        } else break;
                    } else {
                        GuiView.jl.setText("Computer los");

                        int n4 = JOptionPane.showOptionDialog(new JFrame(),
                                "Do you wanna play again?",
                                "Game is over",
                                JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options4,
                                options4[1]);
                        if (n4 == 0) {
                            view.setBoard(model.createBoard());
                            valid = model.checkValidMoves(view.getBoard(), model.currentPlayer);
                            break out;
                        } else break;
                    }
                }
                if (typeOfPlay == 0) {
                    System.out.println("User lost");
                    System.out.println("Do you wanna play again? (y/n)");
                    Scanner in = new Scanner(System.in);
                    if (in.next().equals("y")) {
                        view.setBoard(model.createBoard());
                        valid = model.checkValidMoves(view.getBoard(), model.currentPlayer);
                    } else break;
                } else {
                    GuiView.jl.setText("User lost");
                    int n4 = JOptionPane.showOptionDialog(new JFrame(),
                            "Do you wanna play again?",
                            "Game is over",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options4,
                            options4[1]);
                    if (n4 == 0) {
                        view.setBoard(model.createBoard());
                        valid = model.checkValidMoves(view.getBoard(), model.currentPlayer);
                    } else break;
                }
            }
            Move move = null;
            if ((typeOfPlay == 0 && s == 0 && opponentPick != 3) || (opponentPick == 2 && typeOfPlay == 0)) {
                for (Move m : valid)
                    System.out.println("Starting point " + m.start.x + ", " + m.start.y + ". Ending point " + m.target.x + ", " + m.target.y);
                move = new Move(view.moveStartCell(), view.moveEndCell(), null);
                move = model.verifyMove(move, valid);
                if (move == null) {
                    System.out.println("Bad move!!");
                    done = 0;
                    s = 0;
                } else
                    s = 1;
            }


            if ((model.currentPlayer == Model.player1 && done != 0 && opponentPick != 3) || (opponentPick == 2 && done != 0)) {
                move = new Move(startCell, endCell, null);
                move = model.verifyMove(move, valid);
                if (move == null) {
                    GuiView.jl.setText("Bad move!!");
                    done = 0;


                }

            }
            else if ((model.currentPlayer == Model.player1 && opponentPick == 3)) {
                move = aiOpp.makeAMove(view.getBoard(), valid, model.currentPlayer, model.currentPlayer == Model.player1 ? Model.player2 : Model.player1, model);
                if(opponentPick==3){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        System.out.println("Do not interrupt");
                    }
                }
                s=0;
                done=0;
            }
            else if ((model.currentPlayer == Model.player2) && opponentPick != 2) {
                move = ai.makeAMove(view.getBoard(), valid, model.currentPlayer, model.currentPlayer == Model.player1 ? Model.player2 : Model.player1, model);
                if(opponentPick==3){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        System.out.println("Do not interrupt");
                    }
                }
                s = 0;
                done=0;
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



