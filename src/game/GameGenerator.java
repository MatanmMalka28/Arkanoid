package game;

import game.objects.Ball;
import game.objects.blocks.Block;
import game.objects.attributes.Velocity;
import geometry.GeometryGenerator;
import geometry.Point;
import utilities.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The GameGenerator class is used to create randomized game objects.
 */
public class GameGenerator {
    /**
     * The constant DEFAULT_RADIUS_LOWER_BOUND.
     */
    private static final int DEFAULT_RADIUS_LOWER_BOUND = 5;
    /**
     * The constant DEFAULT_RADIUS_UPPER_BOUND.
     */
    private static final int DEFAULT_RADIUS_UPPER_BOUND = 15;
    /**
     * The constant DEFAULT_SPEED_LOWER_BOUND.
     */
    private static final double DEFAULT_SPEED_LOWER_BOUND = 5;
    /**
     * The constant DEFAULT_SPEED_UPPER_BOUND.
     */
    private static final double DEFAULT_SPEED_UPPER_BOUND = 15;
    /**
     * The constant DEFAULT_DEGREES_LOWER.
     */
    private static final double DEFAULT_DEGREES_LOWER = 0;
    /**
     * The constant DEFAULT_DEGREES_UPPER.
     */
    private static final double DEFAULT_DEGREES_UPPER = 360;
    /**
     * The Speed lower.
     */
    private double speedLower, /**
     * The Speed upper.
     */
    speedUpper, /**
     * The Deg lower.
     */
    degLower, /**
     * The Deg upper.
     */
    degUpper;
    /**
     * The Radius lower.
     */
    private int radiusLower, /**
     * The Radius upper.
     */
    radiusUpper;
    /**
     * The Top left.
     */
    private Point topLeft, /**
     * The Bottom right.
     */
    bottomRight;
    /**
     * The Generator.
     */
    private GeometryGenerator generator;


    /**
     * Instantiates a new game generator.
     *
     * @param topLeft     the top left border
     * @param bottomRight the bottom right border
     */
    public GameGenerator(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.speedLower = DEFAULT_SPEED_LOWER_BOUND;
        this.speedUpper = DEFAULT_SPEED_UPPER_BOUND;
        this.degLower = DEFAULT_DEGREES_LOWER;
        this.degUpper = DEFAULT_DEGREES_UPPER;
        this.radiusLower = DEFAULT_RADIUS_LOWER_BOUND;
        this.radiusUpper = DEFAULT_RADIUS_UPPER_BOUND;
        this.generator = new GeometryGenerator(this.topLeft, this.bottomRight);
    }

    /**
     * Gets a newly random generated block.
     *
     * @return the block
     */
    public Block getBlock() {
        return new Block(this.generator.getRectangle(), this.getColor());
    }

    /**
     * Gets a newly random generated color.
     *
     * @return the color
     */
    public Color getColor() {
        return new Color(this.generator.getFloat(), this.generator.getFloat(), this.generator.getFloat());
    }

    /**
     * Gets a newly random generated ball.
     *
     * @return the ball
     */
    public Ball getBall() {
        return this.getBall(this.getRadius(), this.getVelocity());
    }

    /**
     * Gets a newly random generated velocity.
     *
     * @return the velocity
     */
    public Velocity getVelocity() {
        return Velocity.fromAngleAndSpeed(this.getAngle(), this.getSpeed());
    }

    /**
     * Gets a number which is a valid angle.
     *
     * @return angle
     */
    private double getAngle() {
        return this.generator.getNumber(this.degLower, this.degUpper);
    }

    /**
     * Gets a number which is a valid speed.
     *
     * @return the speed
     */
    private double getSpeed() {
        return this.generator.getNumber(this.speedLower, this.speedUpper);
    }

    /**
     * Gets a number which is a valid radius size.
     *
     * @return the radius
     */
    private int getRadius() {
        return ((int) this.generator.getNumber(this.radiusLower, this.radiusUpper));
    }

    /**
     * Gets a newly random generated ball with a certain given size, and velocity.
     *
     * @param size the size
     * @param v    the v
     * @return the ball
     */
    public Ball getBall(int size, Velocity v) {
        Point center = this.generator.getPoint();
        while (!this.ballCenterInFrame(center, size)) {
            center = this.generator.getPoint();
        }
        Ball ball = new Ball(center, size, this.getColor());
        ball.setVelocity(v);
        ball.setGameBorders(this.topLeft, this.bottomRight, 20);
        return ball;
    }

    /**
     * checks if the entire area of the ball falls inside the borders.
     *
     * @param center the center
     * @param size   the size
     * @return the boolean
     */
    private boolean ballCenterInFrame(Point center, int size) {
        return center.getX() + size < this.bottomRight.getX() && center.getX() - size > topLeft.getX()
                && center.getY() + size < this.bottomRight.getY() && center.getY() - size > topLeft.getY();
    }

    /**
     * Sets speed's lower bound.
     *
     * @param newSpeedLower the speed
     */
    public void setSpeedLower(double newSpeedLower) {
        this.speedLower = Math.abs(newSpeedLower);
    }

    /**
     * Sets speed's upper bound.
     *
     * @param newSpeedUpper the speed
     */
    public void setSpeedUpper(double newSpeedUpper) {
        this.speedUpper = Math.abs(newSpeedUpper);
    }

    /**
     * Sets deg's lower bound.
     *
     * @param newDegLower the degree
     */
    public void setDegLower(double newDegLower) {
        if (newDegLower >= -360 && newDegLower <= 360) {
            this.degLower = newDegLower;
        }
    }

    /**
     * Sets deg's upper bound.
     *
     * @param newDegUpper the degree
     */
    public void setDegUpper(double newDegUpper) {
        if (newDegUpper >= 0 && newDegUpper <= 360) {
            this.degUpper = newDegUpper;
        }
    }

    /**
     * Sets radius's lower bound.
     *
     * @param radius the radius lower
     */
    public void setRadiusLower(int radius) {
        this.radiusLower = Math.abs(radius);
    }

    /**
     * Sets radius's upper bound.
     *
     * @param radius the radius
     */
    public void setRadiusUpper(int radius) {
        this.radiusUpper = Math.abs(radius);
    }

    /**
     * Gets a newly random generated ball with a certain given size, movement angle and movement speed.
     *
     * @param size  the size
     * @param angle the angle
     * @param speed the speed
     * @return the ball
     */
    public Ball getBall(int size, double angle, double speed) {
        return this.getBall(size, Velocity.fromAngleAndSpeed(angle, speed));
    }

    /**
     * Gets a newly random generated ball with a certain given size.
     *
     * @param size the size
     * @return the ball
     */
    public Ball getBall(int size) {
        return this.getBall(size, this.getVelocity(size));
    }

    /**
     * Gets a newly random generated velocity.
     * The velocity's speed is proportional to given size such that the larger the size smaller the speed.
     *
     * @param size the size
     * @return the velocity
     */
    public Velocity getVelocity(int size) {
        return Velocity.fromAngleAndSpeed(this.getAngle(), this.getSpeed() / Math.abs(size * 0.3));
    }

    /**
     * Gets a newly random generated block.
     *
     * @param width  the width
     * @param height the height
     * @return the block
     */
    public Block getBlock(int width, int height) {
        return new Block(this.generator.getRectangle(width, height), this.getColor());
    }

    /**
     * Sets the {@link GameGenerator}'s borders.
     *
     * @param newTopLeft     the top left
     * @param newBottomRight the bottom right
     */
    public void setPoints(Point newTopLeft, Point newBottomRight) {
        this.topLeft = newTopLeft;
        this.bottomRight = newBottomRight;
        this.generator = new GeometryGenerator(this.topLeft, this.bottomRight);
    }

    public static List<Block> makeBlockLine(int numOfBlocks, Point startPoint, int width, int height, Color color, Direction buildDirection) {
        int sign;
        switch (buildDirection) {
            case LEFT:
                sign = -1;
                break;
            default:
            case RIGHT:
                sign = 1;
        }
        List<Block> blocks = new ArrayList<>();
        double x = startPoint.getX() + width * sign;
        double y = startPoint.getY();
        for (int i = 0; i < numOfBlocks; i++, x += width * sign) {
            blocks.add(new Block(new Point(x, y), width, height, color));
        }
        return blocks;
    }
}
