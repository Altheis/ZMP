package ZMP;

import java.awt.Point;
import java.util.ArrayList;

public class Move {
    public Point start;
    public Point target;
    public ArrayList<Point> takes;

    public Move(Point start, Point target, ArrayList<Point> takes) {
        this.start = start;
        this.target = target;
        this.takes = takes;
    }
}
