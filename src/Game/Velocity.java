package Game;

import Geometry.Point;
import Utilities.Utilities;

public class Velocity {
    private double dx, dy;

    // constructor
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;

    }

    public double getDx() {
        return this.dx;
    }

    public double getDy() {
        return this.dy;
    }

    public double getAngle() {
        double angle = Math.toDegrees(Utilities.calculateAngle(this.dx, -this.dy));
        return angle;
    }

    public double getSpeed() {
        return Math.hypot(this.dx, this.dy);
    }

    // Take a point with position (x,y) and return a new point
    // with position (x+dx, y+dy)
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    public Velocity changeSign(double x, double y) {
        if (x == 0) {
            x = 1;
        }
        if (y == 0) {
            y = 1;
        }
        return new Velocity(this.dx * Math.signum(x), this.dy * Math.signum(y));
    }

    public Velocity copy() {
        return this.changeSign(1, 1);
    }

    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.sin(Math.toRadians(angle)) * speed;
        double dy = Math.cos(Math.toRadians(angle - 180)) * speed;
        return new Velocity(dx, dy);
    }
}
