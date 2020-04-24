package geometry;

import biuoop.DrawSurface;
import org.omg.CORBA.MARSHAL;
import utilities.Diagonal;
import utilities.Direction;
import utilities.Utilities;

import java.awt.*;
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

    public Line getDiagonal(Diagonal diagonal) {
        switch (diagonal) {
            default:
            case TOP_LEFT_TO_BOTTOM_RIGHT:
                return new Line(this.topLeft, this.bottomRight);
            case BOTTOM_LEFT_TO_TOP_RIGHT:
                Point topRight = new Point(this.bottomRight.getX(), this.topLeft.getY());
                Point bottomLeft = new Point(this.topLeft.getX(), this.bottomRight.getY());
                return new Line(bottomLeft, topRight);
        }
    }

    public static void drawEdges(Rectangle rect, DrawSurface d, Color color) {
        d.setColor(color);
        d.drawRectangle(((int) rect.getTopLeft().getX()), ((int) rect.getTopLeft().getY()),
                ((int) rect.width()), ((int) rect.height()));
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

    /**
     * Gets width.
     *
     * @return the width
     */
    public double width() {
        return Math.abs(topLeft.getX() - bottomRight.getX());
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public double height() {
        return Math.abs(topLeft.getY() - bottomRight.getY());
    }

    public static void fillRect(Rectangle rect, DrawSurface d, Color color) {
        d.setColor(color);
        d.fillRectangle(((int) rect.getTopLeft().getX()), ((int) rect.getTopLeft().getY()),
                ((int) rect.width()), ((int) rect.height()));
    }
}
