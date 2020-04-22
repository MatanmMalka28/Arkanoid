package geometry;

import utilities.Direction;
import utilities.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * The type Rectangle.
 */
public class Rectangle {

    /**
     * The Top left.
     */
    private Point topLeft, /**
     * The Bottom right.
     */
    bottomRight;
    /**
     * The Width.
     */
    private double width, /**
     * The Height.
     */
    height;
    /**
     * The Edges map.
     */
    private Map<Direction, Line> edgesMap;

    /**
     * Instantiates a new Rectangle.
     *
     * @param topLeft the top left
     * @param width   the width
     * @param height  the height
     */
// Create a new rectangle with location and width/height.
    public Rectangle(Point topLeft, double width, double height) {
        this.topLeft = topLeft;
        this.bottomRight = new Point(topLeft.getX() + Math.abs(width), topLeft.getY() + Math.abs(height));
        this.edgesMap = Utilities.translatePointsToBorders(topLeft, this.bottomRight);
        this.width = width;
        this.height = height;
    }

    /**
     * Instantiates a new Rectangle.
     *
     * @param rectangle the rectangle
     */
    @SuppressWarnings("CopyConstructorMissesField")
    private Rectangle(Rectangle rectangle) {
        this(rectangle.topLeft, rectangle.bottomRight);
    }

    /**
     * Instantiates a new Rectangle.
     *
     * @param topLeft     the top left
     * @param bottomRight the bottom right
     */
    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.edgesMap = Utilities.translatePointsToBorders(this.topLeft, this.bottomRight);
        this.width = this.edgesMap.get(Direction.TOP).length();
        this.height = this.edgesMap.get(Direction.LEFT).length();
    }

    /**
     * Gets top left.
     *
     * @return the top left
     */
    public Point getTopLeft() {
        return this.topLeft.copy();
    }

    /**
     * Gets bottom right.
     *
     * @return the bottom right
     */
    public Point getBottomRight() {
        return this.bottomRight.copy();
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets edges map.
     *
     * @return the edges map
     */
    public Map<Direction, Line> getEdgesMap() {
        return new TreeMap<>(this.edgesMap);
    }

    /**
     * Gets edge.
     *
     * @param edge the edge
     * @return the edge
     */
    public Line getEdge(Direction edge) {
        return this.edgesMap.get(edge).copy();
    }

    /**
     * Intersection points list.
     *
     * @param line the line
     * @return the list
     */
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

    /**
     * Point on edge direction.
     *
     * @param p the p
     * @return the direction
     */
    public Direction pointOnEdge(Point p) {
        Direction direction = Direction.NONE;
        for (Direction key : this.edgesMap.keySet()) {
            if (this.edgesMap.get(key).pointOnLine(p)) {
                if (direction != Direction.NONE) {
                    direction = Direction.BOTH;
                    break;
                }
                direction = key;
            }
        }
        return direction;
    }

    /**
     * Equals boolean.
     *
     * @param other the other
     * @return the boolean
     */
    public boolean equals(Rectangle other) {
        return (this.topLeft.equals(other.topLeft) && this.bottomRight.equals(other.bottomRight))
                || (this.topLeft.equals(other.bottomRight) && this.bottomRight.equals(other.topLeft));
    }

    /**
     * Copy rectangle.
     *
     * @return the rectangle
     */
    public Rectangle copy() {
        return new Rectangle(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topLeft, bottomRight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rectangle rectangle = (Rectangle) o;
        return topLeft.equals(rectangle.topLeft)
                && bottomRight.equals(rectangle.bottomRight);
    }
}
