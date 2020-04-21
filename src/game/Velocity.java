package game;

import geometry.Point;
import utilities.Utilities;
import utilities.Axis;

/**
 * The type Velocity.
 */
public class Velocity {
    /**
     * The Dx.
     */
    private double dx;
    /**
     * The Dy.
     */
    private double dy;

    /**
     * Instantiates a new Velocity.
     *
     * @param dx the dx
     * @param dy the dy
     */
// constructor
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;

    }

    /**
     * Gets dx.
     *
     * @return the dx
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Gets dy.
     *
     * @return the dy
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Gets angle.
     *
     * @return the angle
     */
    public double getAngle() {
        return Math.toDegrees(Utilities.calculateAngle(this.dx, -this.dy));
    }

    /**
     * Gets speed.
     *
     * @return the speed
     */
    public double getSpeed() {
        return Math.hypot(this.dx, this.dy);
    }


    /**
     * Apply to point point.
     *
     * @param p the p
     * @return the point
     */
// Take a point with position (x,y) and return a new point
    // with position (x+dx, y+dy)
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * Copy velocity.
     *
     * @return the velocity
     */
    public Velocity copy() {
        return this.changeSign(1, 1);
    }

    /**
     * Change sign velocity.
     *
     * @param x the x
     * @param y the y
     * @return the velocity
     */
    public Velocity changeSign(double x, double y) {
        if (x == 0) {
            x = 1;
        }
        if (y == 0) {
            y = 1;
        }
        return new Velocity(this.dx * Math.signum(x), this.dy * Math.signum(y));
    }

    /**
     * From angle and speed velocity.
     *
     * @param angle the angle
     * @param speed the speed
     * @return the velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.sin(Math.toRadians(angle)) * speed;
        double dy = Math.cos(Math.toRadians(angle - 180)) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * Signs differrance axis.
     *
     * @param first  the first
     * @param second the second
     * @return the axis
     */
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

    /**
     * Signs equal boolean.
     *
     * @param first  the first
     * @param second the second
     * @param axis   the axis
     * @return the boolean
     */
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
                signEqual = Math.signum(first.dx) == Math.signum(second.dx)
                        && Math.signum(first.dy) == Math.signum(second.dy);
                break;
        }
        return signEqual;
    }
}
