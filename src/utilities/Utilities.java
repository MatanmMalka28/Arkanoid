package utilities;


import game.objects.Block;
import game.objects.Collidable;
import geometry.Line;
import geometry.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Utilities.
 */
public class Utilities {
    /**
     * The constant TOLERANCE.
     */
    private static final double TOLERANCE = Math.pow(10, -4);

    /**
     * Compare doubles boolean.
     *
     * @param num1 the num 1
     * @param num2 the num 2
     * @return the boolean
     */
    public static boolean compareDoubles(double num1, double num2) {
        return Math.abs(num1 - num2) <= TOLERANCE;
    }

    /**
     * Translate points to borders map.
     *
     * @param topLeft     the top left
     * @param bottomRight the bottom right
     * @return the map
     */
    public static Map<Direction, Line> translatePointsToBorders(Point topLeft, Point bottomRight) {
        Point topRight = new Point(bottomRight.getX(), topLeft.getY());
        Point bottomLeft = new Point(topLeft.getX(), bottomRight.getY());
        Map<Direction, Line> gameBorders = new TreeMap<>();
        gameBorders.put(Direction.LEFT, new Line(topLeft, bottomLeft)); //left border
        gameBorders.put(Direction.TOP, new Line(topLeft, topRight)); //top border
        gameBorders.put(Direction.RIGHT, new Line(topRight, bottomRight)); //right border
        gameBorders.put(Direction.BOTTOM, new Line(bottomLeft, bottomRight)); //bottom border
        return gameBorders;
    }

    /**
     * Translate points to blocks list.
     *
     * @param topLeft     the top left
     * @param bottomRight the bottom right
     * @return the list
     */
    public static List<Collidable> translatePointsToBlocks(Point topLeft, Point bottomRight) {
        int size = 20;
        Point topRight = new Point(bottomRight.getX(), topLeft.getY());
        Point bottomLeft = new Point(topLeft.getX(), bottomRight.getY());
        List<Collidable> blockList = new ArrayList<>();
        blockList.add(new Block(topLeft, -size, ((int) bottomRight.getY()))); //left
        blockList.add(new Block(topRight, size, ((int) bottomRight.getY()))); //right
        blockList.add(new Block(topLeft, ((int) bottomRight.getX()), -size)); //up
        blockList.add(new Block(bottomLeft, ((int) bottomRight.getX()), size)); //down
        return blockList;
    }

    /**
     * Calculate angle double.
     *
     * @param y the y
     * @param x the x
     * @return the double
     */
    public static double calculateAngle(double y, double x) {
        double angle;
        if (x == 0) {
            if (y == 0) {
                angle = 0;
            } else if (y > 0) {
                angle = Math.PI / 2;
            } else {
                angle = -Math.PI / 2;
            }
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
