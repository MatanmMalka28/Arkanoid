package Geometry;


import Utilities.Utilities;

import java.util.List;
import java.util.Objects;

public class Line {
    private Point start, end;
    private Double a, b;//y=ax+b
    boolean isVertical;


    // constructors
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.initializeLine();
    }

    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    private Line(Line l) {
        this(l.start, l.end);
    }

    public Double getSlope() {
        return this.a;
    }

    public Double getB() {
        return this.b;
    }

    public boolean isVertical() {
        return this.isVertical;
    }

    // Return the length of the line
    public double length() {
        return this.start.distance(this.end);
    }

    // Returns the middle point of the line
    public Point middle() {
        return this.start.middle(this.end);
    }

    // Returns the start point of the line
    public Point start() {
        return this.start.copy();
    }

    // Returns the end point of the line
    public Point end() {
        return this.end.copy();
    }

    // Returns true if the lines intersect, false otherwise
    public boolean isIntersecting(Line other) {
        return this.intersectionWith(other) != null;
    }

    // Returns the intersection point if the lines intersect,
    // and null otherwise.
    public Point intersectionWith(Line other) {
        Point intersection = null;
        double x, y;
        if (this.isVertical && other.isVertical) {
            /*if (Utilities.Utilities.compareDoubles(this.start.getX(), other.start.getX())) {
                if (this.pointOnLine(other.start) || this.pointOnLine(other.end)) {
                    return this.pointOnLine(other.start) ? other.start.copy() : other.end.copy();
                }
            }*/
            return null;
        } else if (this.isVertical) {
            x = this.start.getX();
            y = other.getSlope() * x + other.getB();
            intersection = new Point(x, y);
        } else if (other.isVertical) {
            x = other.start.getX();
            y = this.getSlope() * x + this.getB();
            intersection = new Point(x, y);
        } else {
            x = (this.getB() - other.getB()) / (other.getSlope() - this.getSlope());
            y = this.getSlope() * x + this.getB();
            intersection = new Point(x, y);
        }
        return this.pointOnLine(intersection) && other.pointOnLine(intersection) ? intersection : null;
    }

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

    // equals -- return true is the lines are equal, false otherwise
    public boolean equals(Line other) {
        return (this.start.equals(other.start) && this.end.equals(other.end)) ||
                (this.start.equals(other.end) && this.end.equals(other.start));
    }

    public boolean pointOnLine(Point p) {
        double length = this.start.distance(p) + p.distance(this.end);
        return Utilities.compareDoubles(length, this.length());
    }

    public Line copy() {
        return new Line(this);
    }

    private void initializeLine() {
        this.calcSlope();
        this.calcB();
    }

    private void calcSlope() {
        if (!Utilities.compareDoubles(this.start.getX(), this.end.getX())) {
            this.a = (this.start.getY() - this.end.getY()) / (this.start.getX() - this.end.getX());
        } else {
            this.a = null;
            this.isVertical = true;
        }
    }

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
        if (this == o) return true;
        if (!(o instanceof Line)) return false;
        Line line = (Line) o;
        return this.equals(line);
    }
}
