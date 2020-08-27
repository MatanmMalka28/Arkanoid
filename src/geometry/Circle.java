package geometry;

import utilities.Axis;

public class Circle {
    private final Point center;
    private final double radius;

    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    private Circle (Circle circle){
        this(circle.center, circle.radius);
    }

    public Point getCenter() {
        return this.center;
    }

    public double getRadius() {
        return this.radius;
    }

    public Circle copy(){
        return new Circle(this);
    }

    public Circle shiftCircle(double shiftSize, Axis axis){
        return new Circle(this.center.shiftPoint(shiftSize, axis),this.radius);
    }
}
