import java.awt.Point;
import java.util.ArrayList;

public class Model {
    public static final char player1 = '1';
    public static final char player2 = '2';
    public static final char empty = '0';
    private int dim;
    public char currentPlayer;

    public Model(int dim) {
        this.dim = dim;
        this.currentPlayer = player1;
    }

    public static char[][] copyBoard(char[][] board) {
        char[][] copy = new char[board.length][board[0].length];
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                copy[i][j] = board[i][j];
        return copy;
    }

    public char[][] createBoard() {
        char[][] board = new char[this.dim + 2][this.dim + 2];

        for (int i = 1; i < board.length - 1; i++) {
            for (int j = 1; j < board[i].length - 1; j++) {
                if ((i < this.dim / 2) && (i % 2 * j % 2 == 1 || i % 2 + j % 2 == 0))
                    board[i][j] = player2;
                else if ((i > this.dim - this.dim / 2 + 1) && (i % 2 * j % 2 == 1 || i % 2 + j % 2 == 0))
                    board[i][j] = player1;
                else board[i][j] = empty;
            }
        }
        // default char value '\u0000'

        return board;
    }

    private ArrayList<Move> checkMove(char[][] board, int i, int j) {
        ArrayList<Move> moves = new ArrayList<Move>();
        int direction = (board[i][j] == player1) ? -1 : 1;
        //check basic moves
        if (board[i + direction][j - 1] == empty)
            moves.add(new Move(new Point(i, j), new Point(i + direction, j - 1), new ArrayList<Point>()));
        if (board[i + direction][j + 1] == empty)
            moves.add(new Move(new Point(i, j), new Point(i + direction, j + 1), new ArrayList<Point>()));

        //check takes
        moves.addAll(checkTake(board, i, j));


        return pickLongestTake(moves);
    }

    private ArrayList<Move> removeDuplicateTakes(ArrayList<Move> moves) {
        for (Move m : moves) {
            ArrayList<Point> cleaned = new ArrayList<Point>();
            for (Point p1 : m.takes) {
                boolean found = false;
                for (Point p2 : cleaned)
                    if (p1.equals(p2)) found = true;
                if (!found) cleaned.add(p1);
            }
            m.takes = cleaned;
        }
        return moves;
    }

    private ArrayList<Move> checkQueenMove(char[][] board, int i, int j) {
        ArrayList<Move> moves = new ArrayList<Move>();
        int distance = 1;
        //check basic moves
        while (board[i + distance][j + distance] == empty) {
            moves.add(new Move(new Point(i, j), new Point(i + distance, j + distance), new ArrayList<Point>()));
            distance++;
        }
        distance = 1;
        while (board[i + distance][j - distance] == empty) {
            moves.add(new Move(new Point(i, j), new Point(i + distance, j - distance), new ArrayList<Point>()));
            distance++;
        }
        distance = 1;
        while (board[i - distance][j + distance] == empty) {
            moves.add(new Move(new Point(i, j), new Point(i - distance, j + distance), new ArrayList<Point>()));
            distance++;
        }
        distance = 1;
        while (board[i - distance][j - distance] == empty) {
            moves.add(new Move(new Point(i, j), new Point(i - distance, j - distance), new ArrayList<Point>()));
            distance++;
        }

        //check takes
        moves.addAll(checkQueenTake(board, i, j));


        return pickLongestTake(moves);
    }

    private ArrayList<Move> pickLongestTake(ArrayList<Move> chain) {
        ArrayList<Move> filtered = new ArrayList<Move>();
        int best = 0;
        for (Move m : chain) if (m.takes.size() > best) best = m.takes.size();
        for (Move m : chain) if (m.takes.size() == best) filtered.add(m);
        return filtered;
    }

    private ArrayList<Move> colapseChainTake(Move first, ArrayList<Move> chain) {
        chain = pickLongestTake(chain);
        for (Move m : chain) {
            m.start = first.start;
            m.takes.addAll(first.takes);
        }
        return chain;
    }

    private ArrayList<Move> checkTake(char[][] board, int i, int j) {
        ArrayList<Move> moves = new ArrayList<Move>();
        ArrayList<Point> takes = new ArrayList<Point>();
        char opponent = currentPlayer == player1 ? player2 : player1;
        for (int n = -1; n <= 1; n += 2)
            for (int m = -1; m <= 1; m += 2)
                if (Character.toLowerCase(board[i + n][j + m]) == opponent)
                    if (board[i + 2 * n][j + 2 * m] == empty) {
                        char[][] copy = copyBoard(board);
                        takes.add(new Point(i + n, j + m));
                        Move take = new Move(new Point(i, j), new Point(i + 2 * n, j + 2 * m), takes);
                        copy = performMove(take, copy);
                        ArrayList<Move> chain = checkTake(copy, i + 2 * n, j + 2 * m);
                        if (chain.size() > 0) {
                            moves.addAll(colapseChainTake(take, chain));
                        } else moves.add(take);
                    }


        return moves;
    }

    private ArrayList<Move> checkQueenTake(char[][] board, int i, int j) {
        ArrayList<Move> moves = new ArrayList<Move>();
        ArrayList<Point> takes = new ArrayList<Point>();
        char opponent = currentPlayer == player1 ? player2 : player1;
        int distance = 1;
        for (int n = -1; n <= 1; n += 2)
            for (int m = -1; m <= 1; m += 2) {
                distance = 1;
                while (board[i + distance * n][j + distance * m] == empty) distance++;
                if (board[i + distance * n][j + distance * m] != '\u0000') {
                    if (Character.toLowerCase(board[i + distance * n][j + distance * m]) == opponent) {
                        int jumpover = Math.abs(distance) + 1;
                        while (board[i + jumpover * n][j + jumpover * m] == empty) {
                            char[][] copy = copyBoard(board);
                            takes.add(new Point(i + distance * n, j + distance * m));
                            Move take = new Move(new Point(i, j), new Point(i + jumpover * n, j + jumpover * m), takes);
                            copy = performMove(take, copy);
                            ArrayList<Move> chain = checkTake(copy, i + jumpover * n, j + jumpover * m);
                            if (chain.size() > 0) {
                                moves.addAll(colapseChainTake(take, chain));
                            } else moves.add(take);
                            jumpover++;
                        }
                    }
                }
            }


        return removeDuplicateTakes(moves);
    }

    public ArrayList<Move> checkValidMoves(char[][] board, char player) {
        ArrayList<Move> moves = new ArrayList<Move>();
        for (int i = 1; i < board.length - 1; i++) {
            for (int j = 1; j < board[i].length - 1; j++) {
                if (board[i][j] == player||board[i][j]==player+2) {
                    moves.addAll(checkMove(board, i, j));
                } else if (board[i][j] == Character.toUpperCase(player)||board[i][j]==Character.toUpperCase(player+2)) {
                    moves.addAll(checkQueenMove(board, i, j));
                }
            }
        }
        moves = pickLongestTake(moves);
        return moves;
    }

    public Move verifyMove(Move move, ArrayList<Move> list) {
        for (Move m : list) if (m.start.equals(move.start) && m.target.equals(move.target)) return m;
        return null;
    }

    public char[][] performMove(Move move, char[][] board) {
        board[move.target.x][move.target.y] = board[move.start.x][move.start.y];
        if ((this.currentPlayer == player1 && move.target.x == 1) || (this.currentPlayer == player2 && move.target.x == board.length - 2))
            board[move.target.x][move.target.y] = Character.toUpperCase(board[move.target.x][move.target.y]);
        board[move.start.x][move.start.y] = empty;
        if (move.takes.size() > 0) for (Point p : move.takes) board[p.x][p.y] = empty;
        return board;
    }

    public void changePlayer() {
        this.currentPlayer = currentPlayer == player1 ? player2 : player1;
    }

}
