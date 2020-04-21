package Utilities;


import Game.Objects.Block;
import Game.Objects.Collidable;
import Geometry.Line;
import Geometry.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Utilities {
    private static final double TOLERANCE = Math.pow(10,-4);

    public static boolean compareDoubles(double num1,double num2){
        return Math.abs(num1-num2)<=TOLERANCE;
    }

    public static Map<Direction,Line> translatePointsToBorders(Point topLeft, Point bottomRight){
        Point topRight = new Point(bottomRight.getX(),topLeft.getY());
        Point bottomLeft = new Point(topLeft.getX(),bottomRight.getY());
        Map<Direction,Line> gameBorders = new TreeMap<>();
        gameBorders.put(Direction.LEFT,new Line(topLeft,bottomLeft));//left border
        gameBorders.put(Direction.TOP,new Line(topLeft,topRight));//top border
        gameBorders.put(Direction.RIGHT,new Line(topRight,bottomRight));//right border
        gameBorders.put(Direction.BOTTOM,new Line(bottomLeft,bottomRight));//bottom border
        return gameBorders;
    }

    public static List<Collidable> translatePointsToBlocks(Point topLeft, Point bottomRight){
        int size = 20;
        Point topRight = new Point(bottomRight.getX(),topLeft.getY());
        Point bottomLeft = new Point(topLeft.getX(),bottomRight.getY());
        List<Collidable> blockList = new ArrayList<>();
        blockList.add(new Block(topLeft,-size, ((int) bottomRight.getY())));//left
        blockList.add(new Block(topRight,size, ((int) bottomRight.getY())));//right
        blockList.add(new Block(topLeft, ((int) bottomRight.getX()), -size));//up
        blockList.add(new Block(bottomLeft,((int) bottomRight.getX()),size));//down
        return blockList;
    }

    public static double calculateAngle(double y, double x) {
        double angle = 0;
        if (x == 0) {
            if (y == 0)
                angle = 0;
            else if (y > 0)
                angle = Math.PI / 2;
            else
                angle = -Math.PI / 2;
        } else {
            angle = Math.atan(y / x);
            if (x < 0) {
                if (y > 0) {
                    angle += Math.PI;
                } else if (y < 0) {
                    angle -= Math.PI;
                } else {
                    angle = Math.PI;
                }
            }
        }
        return angle;
    }
}
