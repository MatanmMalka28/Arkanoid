package game.objects.attributes;

import geometry.Point;
import utilities.Utilities;
import utilities.Axis;

/**
 * The type Velocity.
 */
public class Velocity {
    /**
     * The change in x.
     */
    private double dx;
    /**
     * The change in y.
     */
    private double dy;

    /**
     * Instantiates a new Velocity.
     *
     * @param dx the change in x.
     * @param dy the change in y.
     */
// constructor
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;

    }


    /**
     * Gets dx.
     *
     * @return the change in x.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Gets dy.
     *
     * @return the change in y.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Calculates the current progress vector angle and returns it.
     *
     * @return the angle
     */
    public double getAngle() {

        return Math.toDegrees(Utilities.calculateAngle(this.dx, -this.dy));
    }

    /**
     * Calculates the current progress vector length which is the speed of this velocity.
     * Gets speed.
     *
     * @return the speed of this velocity.
     */
    public double getSpeed() {
        /*
        dx = sin(angle) * speed
        dy = cos(angle-180) * speed = -cos(angle)
        sqrt((dx)^2 + (-dy)^2 = sqrt(speed^2 *(cos(angle)^2+sin(angle)^2) = sqrt(speed^2*1) = speed
         */
        return Math.hypot(this.dx, this.dy);
    }


    /**
     * Apply current Velocity to given point.
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p the point to apply the velocity upon.
     * @return the point with the new position.
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * Copy velocity.
     *
     * @return an exact copy of this velocity.
     */
    public Velocity copy() {
        return this.changeSign(1, 1);
    }

    /**
     * Changes the direction of the progress vector defined by dx and dy.
     * This function expects x and y to be 1 or -1.
     * If x=-1 then the sign of dx is flipped otherwise the sign is unchanged.
     * If y=-1 then the sign of dy is flipped otherwise the sign is unchanged.
     *
     * @param x marks whether to change dx's sign or not.
     * @param y marks whether to change dy's sign or not.
     * @return a velocity with the same speed as this instance with the new signs.
     */
    public Velocity changeSign(double x, double y) {
        //Math.signum(double a) returns 0 in case a is 0 so we avoid it.
        if (x == 0) {
            x = 1;
        }
        if (y == 0) {
            y = 1;
        }
        return new Velocity(this.dx * Math.signum(x), this.dy * Math.signum(y));
    }

    /**
     * A more convenient method to generate a Velocity instance.
     * Mind that angle 0 is defined as moving up and angle 90 is defined as moving right.
     *
     * @param angle the angle
     * @param speed the speed
     * @return the velocity defined by the given angle and speed.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.sin(Math.toRadians(angle)) * speed;
        //angle-180 because the y axis is inverted on a GUI
        double dy = Math.cos(Math.toRadians(angle - 180)) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * Finds the signs differences between two Velocities.
     *
     * @param first  the first velocity
     * @param second the second velocity
     * @return the axis in which dx's and dy's signs are different.
     */
    public static Axis signsDifference(Velocity first, Velocity second) {
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
     * Checks if the signs of two Velocities are equal on the given axis.
     *
     * @param first  the first velocity
     * @param second the second velocity
     * @param axis   the axis to compare
     * @return true if signs match, false otherwise.
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

    //todo add velocity change with direction
}
