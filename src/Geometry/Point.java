package Geometry;

import Utilities.Utilities;

import java.util.Objects;

public class Point {

    private double x, y;

    // constructor


    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    private Point(Point p) {
        this(p.x, p.y);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public Point middle(Point other) {
        return new Point((this.x + other.x) / 2, (this.y + other.y) / 2);
    }

    public boolean equals(Point other) {
        return Utilities.compareDoubles(this.x, other.x) && Utilities.compareDoubles(this.y, other.y);
    }

    public Point copy() {
        return new Point(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return this.equals(point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
