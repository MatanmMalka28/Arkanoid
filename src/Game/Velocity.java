package Game;

import Geometry.Point;
import Utilities.Utilities;
import Utilities.Axis;

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

    public static boolean signsEqual(Velocity first, Velocity second, Axis axis) {
        boolean signEqual;
        switch (axis) {
            case Y:
                signEqual = Math.signum(first.dy) == Math.signum(second.dy);
                break;
            case X:
                signEqual = Math.signum(first.dx) == Math.signum(second.dx);
                break;
            default:
            case XY:
                signEqual = Math.signum(first.dx) == Math.signum(second.dx) &&
                        Math.signum(first.dy) == Math.signum(second.dy);
                break;
        }
        return signEqual;
    }

    public static Axis signsDifferrance(Velocity first, Velocity second) {
        if (signsEqual(first, second, Axis.XY)) {
            return null;
        } else if (signsEqual(first, second, Axis.X)) {
            return Axis.Y;
        } else if (signsEqual(first, second, Axis.Y)) {
            return Axis.X;
        } else {
            return Axis.XY;
        }
    }
}
