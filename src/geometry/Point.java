package geometry;

import utilities.Axis;
import utilities.Utilities;

import java.util.Objects;

/**
 * The type Point.
 */
public class Point {

    /**
     * The X.
     */
    private double x,
    /**
     * The Y.
     */
    y;

    // constructor


    /**
     * Instantiates a new Point.
     *
     * @param p the p
     */
    private Point(Point p) {
        this(p.x, p.y);
    }

    /**
     * Instantiates a new Point.
     *
     * @param x the x
     * @param y the y
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public double getX() {
        return this.x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public double getY() {
        return this.y;
    }

    /**
     * Distance double.
     *
     * @param other the other
     * @return the double
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    /**
     * Middle point.
     *
     * @param other the other
     * @return the point
     */
    public Point middle(Point other) {
        return new Point((this.x + other.x) / 2, (this.y + other.y) / 2);
    }

    /**
     * Copy point.
     *
     * @return the point
     */
    public Point copy() {
        return new Point(this);
    }

    /**
     * Shifts point by a given factor.
     *
     * @param shiftSize the shift size
     * @param axis      the axis
     * @return the point after the shift
     */
    public Point shiftPoint(double shiftSize, Axis axis) {
        Point shiftedPoint;
        switch (axis) {
            case X:
                shiftedPoint = new Point(this.x + shiftSize, this.y);
                break;
            case Y:
                shiftedPoint = new Point(this.x, this.y + shiftSize);
                break;
            default:
            case XY:
            case YX:
                shiftedPoint = new Point(this.x + shiftSize, this.y + shiftSize);
                break;
        }
        return shiftedPoint;
    }

    /**
     * Equals boolean.
     *
     * @param other the other
     * @return the boolean
     */
    public boolean equals(Point other) {
        return Utilities.compareDoubles(this.x, other.x) && Utilities.compareDoubles(this.y, other.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Point)) {
            return false;
        }
        Point point = (Point) o;
        return this.equals(point);
    }

    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + '}';
    }
}
