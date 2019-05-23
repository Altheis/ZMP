package MSIWPG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GuiView implements View {

    private char[][] board;
    private final GUI gui;
    static public Point startpoint;
    static public Point endpoint;
    static JLabel jl;
    GuiView() {
        gui = new GUI();
    }

    @Override
    public void displayBoard() {
        gui.repaint();

    }

    @Override
    public char[][] getBoard() {
        return this.board;
    }

    @Override
    public Point moveStartCell() {

        return new Point((int) startpoint.getX(), (int) startpoint.getY());

    }

    @Override
    public Point moveEndCell() {
        return new Point((int) endpoint.getX(), (int) endpoint.getY());
    }

    @Override
    public void setBoard(char[][] newBoard) {
        this.board = newBoard;
    }


    class GUI extends JFrame implements MouseListener {

        int xLocation, yLocation;
        final int onePoint = 40;



        GUI() {
            super("Checkers");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            if(Controller.dim==8){
            setSize(400,420);}
            else setSize(480,480);
            Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
            int x=(int)((d.getWidth()-getWidth())/2);
            int y=(int)((d.getHeight()-getHeight())/2);
            setLocation(x,y);
            setLayout(new BorderLayout());

            if(Controller.opponentPick==3){
                jl = new JLabel("Enjoy the game", JLabel.CENTER);
            } else{
                jl = new JLabel("Start the game", JLabel.CENTER);
            }

            add(BorderLayout.SOUTH, jl);
            addMouseListener(this);

            setVisible(true);

        }

        void makeBoard(Graphics g) {
            for (int x = 1; x < board.length - 1; x++) {
                for (int y = 1; y < board.length - 1; y++) {
                    if ((x % 2 == 0 && y % 2 != 0) || (x % 2 != 0 && y % 2 == 0)) {
                        g.setColor(new Color(89, 170, 103));
                    } else {
                        g.setColor(new Color(92, 99, 87));
                    }
                    g.fillRect(xLocation, yLocation, onePoint, onePoint);
                    yLocation = yLocation + onePoint;
                }
                xLocation = xLocation + onePoint;
                yLocation = 50;
            }

            xLocation = 40;
            for (int x = 1; x < board.length - 1; x++) {
                for (int y = 1; y < board.length - 1; y++) {
                    if (board[y][x] == Model.player1) {
                        g.setColor(new Color(229, 38, 22));
                    } else if (board[y][x] == Model.player2) {
                        g.setColor(new Color(247, 250, 246));
                    } else if (board[y][x] == Character.toUpperCase(Model.player1)){
                        g.setColor(new Color(122, 8, 8));
                    } else if (board[y][x] == Character.toUpperCase(Model.player2)){
                        g.setColor(new Color(118, 122, 119));
                    }

                    if (board[y][x] != Model.empty) {
                        g.fillOval(xLocation, yLocation, onePoint, onePoint);
                    }
                    yLocation = yLocation + onePoint;
                }
                xLocation = xLocation + onePoint;
                yLocation = 50;
            }

        }


        public void paint(Graphics g) {
            super.paint(g);
            xLocation = 40;
            yLocation = 50;

            makeBoard(g);



            x_mouse = 0;
            y_mouse = 0;
        }

        public void update(Graphics g) {
            paint(g);
        }

        int x_mouse = 0, y_mouse = 0;

        int first = 0;

        @Override
        public void mouseClicked(MouseEvent ev) {
            x_mouse = ev.getX();
            y_mouse = ev.getY();

            int x = (x_mouse / 40);
            int y = (y_mouse / 40);

            if (x == board.length) x--;
            if (y == board.length) y--;


            if (first == 0) {
                startpoint = new Point(y, x);
                first = 1;
                jl.setText("Start point: " + y + "," + x);
                Controller.startCell = moveStartCell();

            } else {

                endpoint = new Point(y, x);
                Controller.endCell = moveEndCell();
                jl.setText("End point: " + y + "," + x);
                first = 0;
                Controller.done = 1;

            }


            repaint();

        }

        @Override
        public void mousePressed(MouseEvent me) {

        }

        @Override
        public void mouseReleased(MouseEvent me) {

        }

        @Override
        public void mouseEntered(MouseEvent me) {

        }

        @Override
        public void mouseExited(MouseEvent me) {

        }


    }
}