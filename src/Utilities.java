import java.util.Map;
import java.util.TreeMap;

public class Utilities {
    private static final double TOLERANCE = Math.pow(10,-4);

    public static boolean compareDoubles(double num1,double num2){
        return Math.abs(num1-num2)<=TOLERANCE;
    }

    public static Map<Direction,Line> translatePointsToBorders(Point topLeft,Point bottomRight){
        Point topRight = new Point(bottomRight.getX(),topLeft.getY());
        Point bottomLeft = new Point(topLeft.getX(),bottomRight.getY());
        Map<Direction,Line> gameBorders = new TreeMap<>();
        gameBorders.put(Direction.LEFT,new Line(topLeft,bottomLeft));//left border
        gameBorders.put(Direction.TOP,new Line(topLeft,topRight));//top border
        gameBorders.put(Direction.RIGHT,new Line(topRight,bottomRight));//right border
        gameBorders.put(Direction.BOTTOM,new Line(bottomLeft,bottomRight));//bottom border
        return gameBorders;
    }
}
