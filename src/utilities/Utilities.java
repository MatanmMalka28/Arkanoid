package utilities;


import game.objects.blocks.Block;
import game.objects.Collidable;
import geometry.Line;
import geometry.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * The type Utilities.
 */
public class Utilities {
    /**
     * The constant TOLERANCE.
     */
    private static final double TOLERANCE = Math.pow(10, -8);
    private static Random rand = new Random();

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
    public static List<Collidable> translatePointsToBlocks(Point topLeft, Point bottomRight, int size) {
        Color color = Color.red;
        Point topRight = new Point(bottomRight.getX(), topLeft.getY());
        Point bottomLeft = new Point(topLeft.getX(), bottomRight.getY());
        List<Collidable> blockList = new ArrayList<>();
        blockList.add(new Block(new Point(topLeft.getX() - size, topLeft.getY()), bottomLeft, color)); //left
        blockList.add(new Block(topRight, new Point(bottomRight.getX() + size, bottomRight.getY()), color)); //right
        blockList.add(new Block(new Point(topLeft.getX(), topLeft.getY() - size), topRight, color)); //up
        blockList.add(new Block(bottomLeft, new Point(bottomRight.getX(), bottomRight.getY() + size), color)); //down
        return blockList;
    }

    /**
     * Calculate an angle based on x's and y's sign value.
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

    public static int getSign() {
        return rand.nextBoolean() ? 1 : -1;
    }
}
