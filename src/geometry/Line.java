package geometry;


import biuoop.DrawSurface;
import utilities.Axis;
import utilities.Utilities;

import java.util.List;
import java.util.Objects;

/**
 * The type Line.
 */
public class Line {
    /**
     * The Is vertical.
     */
    private boolean isVertical;
    /**
     * The Start.
     */
    private Point start,
    /**
     * The End.
     */
    end;
    /**
     * The A.
     */
    private Double a,
    /**
     * The B.
     */
    b; //y=ax+b


    /**
     * Instantiates a new Line.
     *
     * @param x1 the x 1
     * @param y1 the y 1
     * @param x2 the x 2
     * @param y2 the y 2
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * Instantiates a new Line.
     *
     * @param start the start
     * @param end   the end
     */
// constructors
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.initializeLine();
    }

    /**
     * Instantiates a new Line.
     *
     * @param l the l
     */
    @SuppressWarnings("CopyConstructorMissesField")
    private Line(Line l) {
        this(l.start, l.end);
    }

    /**
     * Gets slope.
     *
     * @return the slope
     */
    public Double getSlope() {
        return this.a;
    }

    /**
     * Is vertical boolean.
     *
     * @return the boolean
     */
    public boolean isVertical() {
        return this.isVertical;
    }

    /**
     * Gets b.
     *
     * @return the b
     */
    public Double getB() {
        return this.b;
    }

    /**
     * Middle point.
     *
     * @return the point
     */
// Returns the middle point of the line
    public Point middle() {
        return this.start.middle(this.end);
    }

    /**
     * Start point.
     *
     * @return the point
     */
// Returns the start point of the line
    public Point start() {
        return this.start.copy();
    }

    /**
     * End point.
     *
     * @return the point
     */
// Returns the end point of the line
    public Point end() {
        return this.end.copy();
    }

    /**
     * Is intersecting boolean.
     *
     * @param other the other
     * @return the boolean
     */
// Returns true if the lines intersect, false otherwise
    public boolean isIntersecting(Line other) {
        return this.intersectionWith(other) != null;
    }

    /**
     * Intersection with point.
     *
     * @param other the other
     * @return the point
     */
// Returns the intersection point if the lines intersect,
    // and null otherwise.
    public Point intersectionWith(Line other) {
        Point intersection;
        double x, y;
        if (this.isVertical && other.isVertical) {
            return null;
        } else if (this.isVertical) {
            x = this.start.getX();
            y = other.a * x + other.b;
            intersection = new Point(x, y);
        } else if (other.isVertical) {
            x = other.start.getX();
            y = this.a * x + this.b;
            intersection = new Point(x, y);
        } else {
            x = (this.b - other.b) / (other.a - this.a);
            y = this.a * x + this.b;
            intersection = new Point(x, y);
        }
        return this.pointOnLine(intersection) && other.pointOnLine(intersection) ? intersection : null;
    }

    /**
     * Point on line boolean.
     *
     * @param p the p
     * @return the boolean
     */
    public boolean pointOnLine(Point p) {
        double length = this.start.distance(p) + p.distance(this.end);
        return Utilities.compareDoubles(length, this.length());
    }

    /**
     * Length double.
     *
     * @return the double
     */
// Return the length of the line
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Closest intersection to start of line point.
     *
     * @param rect the rect
     * @return the point
     */
// If this line does not intersect with the rectangle, return null.
    // Otherwise, return the closest intersection point to the
    // start of the line.
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> hitPoints = rect.intersectionPoints(this);
        if (hitPoints.isEmpty()) {
            return null;
        } else {
            Point closestPoint = hitPoints.get(0);
            hitPoints.remove(0);
            for (Point hit : hitPoints) {
                if (hit.distance(this.start) < closestPoint.distance(this.start)) {
                    closestPoint = hit;
                }
            }
            return closestPoint;
        }
    }

    /**
     * Copy line.
     *
     * @return the line
     */
    public Line copy() {
        return new Line(this);
    }

    /**
     * Equals boolean.
     *
     * @param other the other
     * @return the boolean
     */
// equals -- return true is the lines are equal, false otherwise
    public boolean equals(Line other) {
        return (this.start.equals(other.start) && this.end.equals(other.end))
                || (this.start.equals(other.end) && this.end.equals(other.start));
    }

    /**
     * Shift line line.
     *
     * @param shiftSize the shift size
     * @param axis      the axis
     * @return the line
     */
    public Line shiftLine(double shiftSize, Axis axis) {
        return new Line(this.start.shiftPoint(shiftSize, axis), this.end.shiftPoint(shiftSize, axis));
    }

    /**
     * Draw on.
     *
     * @param d the d
     */
    public void drawOn(DrawSurface d) {
        d.drawLine(((int) this.start.getX()), ((int) this.start.getY()),
                ((int) this.end.getX()), ((int) this.end.getY()));
    }

    /**
     * Initialize line.
     */
    private void initializeLine() {
        this.calcSlope();
        this.calcB();
    }

    /**
     * Calc slope.
     */
    private void calcSlope() {
        if (!Utilities.compareDoubles(this.start.getX(), this.end.getX())) {
            this.a = (this.start.getY() - this.end.getY()) / (this.start.getX() - this.end.getX());
        } else {
            this.a = null;
            this.isVertical = true;
        }
    }

    /**
     * Calc b.
     */
    private void calcB() {
        if (!this.isVertical) {
            this.b = this.start.getY() - (this.start.getX() * this.getSlope());
        } else {
            this.b = null;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Line)) {
            return false;
        }
        Line line = (Line) o;
        return this.equals(line);
    }

}
