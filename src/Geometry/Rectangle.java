package Geometry;

import Utilities.Direction;
import Utilities.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Rectangle {

    private Point topLeft, bottomRight;
    private double width, height;
    private Map<Direction, Line> edgesMap;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.edgesMap = Utilities.translatePointsToBorders(this.topLeft, this.bottomRight);
        this.width = this.edgesMap.get(Direction.TOP).length();
        this.height = this.edgesMap.get(Direction.LEFT).length();
    }

    // Create a new rectangle with location and width/height.
    public Rectangle(Point topLeft, double width, double height) {
        this.topLeft = topLeft;
        this.bottomRight = new Point(topLeft.getX() + width, topLeft.getY() + height);
        this.edgesMap = Utilities.translatePointsToBorders(topLeft, this.bottomRight);
        this.width = width;
        this.height = height;
    }

    private Rectangle (Rectangle rectangle){
        this(rectangle.topLeft,rectangle.bottomRight);
    }

    public Point getTopLeft() {
        return this.topLeft.copy();
    }

    public Point getBottomRight() {
        return this.bottomRight.copy();
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Map<Direction, Line> getEdgesMap() {
        return new TreeMap<Direction, Line>(this.edgesMap);
    }

    public Line getEdge(Direction edge) {
        return this.edgesMap.get(edge).copy();
    }

    // Return a (possibly empty) List of intersection points
    // with the specified line.
    public List<Point> intersectionPoints(Line line) {
        List<Point> hitPoints = new ArrayList<>();
        for (Line edge : this.edgesMap.values()) {
            if (edge.isIntersecting(line)) {
                hitPoints.add(edge.intersectionWith(line));
            }
        }
        return hitPoints;
    }

    public Direction pointOnEdge(Point p){
        //todo: check if point is on two lines
        Direction edgeHit = Direction.NONE;
        for(Direction edge:this.edgesMap.keySet()){
            if (this.edgesMap.get(edge).pointOnLine(p)){
                if (edgeHit!=Direction.NONE){
                    return Direction.BOTH;
                }
                edgeHit = edge;
            }
        }
        return edgeHit;
    }

    public boolean equals(Rectangle other) {
        return (this.topLeft.equals(other.topLeft) && this.bottomRight.equals(other.bottomRight)) ||
                (this.topLeft.equals(other.bottomRight) && this.bottomRight.equals(other.topLeft));
    }

    @Override
    public int hashCode() {
        return Objects.hash(topLeft, bottomRight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return topLeft.equals(rectangle.topLeft) &&
                bottomRight.equals(rectangle.bottomRight);
    }

    public Rectangle copy(){
        return new Rectangle(this);
    }
}
