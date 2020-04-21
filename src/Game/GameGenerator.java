package Game;

import Game.Objects.Ball;
import Game.Objects.Block;
import Geometry.GeometryGenerator;
import Geometry.Point;

import java.awt.Color;

public class GameGenerator {
    private static final int DEFAULT_RADIUS_LOWER_BOUND = 5;
    private static final int DEFAULT_RADIUS_UPPER_BOUND = 15;
    private static final double DEFAULT_SPEED_LOWER_BOUND = 5;
    private static final double DEFAULT_SPEED_UPPER_BOUND = 35;
    private static final double DEFAULT_DEGREES_LOWER = 0;
    private static final double DEFAULT_DEGREES_UPPER = 360;
    private double speedLower, speedUpper, degLower, degUpper;
    private int radiusLower, radiusUpper;
    private Point topLeft, bottomRight;
    private GeometryGenerator generator;


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

    public Velocity getVelocity() {
        return Velocity.fromAngleAndSpeed(this.getAngle(), this.getSpeed());
    }

    public Block getBlock() {
        return new Block(this.generator.getRectangle(), this.getColor());
    }

    public Color getColor() {
        return new Color(this.generator.getFloat(), this.generator.getFloat(), this.generator.getFloat());
    }

    private int getRadius() {
        return ((int) this.generator.getNumber(this.radiusLower, this.radiusUpper));
    }

    private double getAngle() {
        return this.generator.getNumber(this.degLower, this.degUpper);
    }

    private double getSpeed() {
        return this.generator.getNumber(this.speedLower, this.speedUpper);
    }

    public Ball getBall() {
        return this.getBall(this.getRadius(), this.getVelocity());
    }

    public void setSpeedLower(double speedLower) {
        this.speedLower = Math.abs(speedLower);
    }

    public void setSpeedUpper(double speedUpper) {
        this.speedUpper = Math.abs(speedUpper);
    }

    public void setDegLower(double degLower) {
        if (degLower >= 0 && degLower <= 360) {
            this.degLower = degLower;
        }
    }

    public void setDegUpper(double degUpper) {
        if (degUpper >= 0 && degUpper <= 360) {
            this.degUpper = degUpper;
        }
    }

    public void setRadiusLower(int radiusLower) {
        this.radiusLower = Math.abs(radiusLower);
    }

    public void setRadiusUpper(int radiusUpper) {
        this.radiusUpper = Math.abs(radiusUpper);
    }

    public Ball getBall(int size, Velocity v) {
        Point center = this.generator.getPoint();
        while (!this.ballCenterInFrame(center, size)) {
            center = this.generator.getPoint();
        }
        Ball ball = new Ball(center, size, this.getColor());
        ball.setVelocity(v);
        ball.setGameBorders(this.topLeft, this.bottomRight);
        return ball;
    }

    public Ball getBall(int size, double angle, double speed) {
        return this.getBall(size, Velocity.fromAngleAndSpeed(angle, speed));
    }

    public Ball getBall(int size) {
        return this.getBall(size, this.getVelocity(size));
    }

    public Velocity getVelocity(int size) {
        return Velocity.fromAngleAndSpeed(this.getAngle(), this.getSpeed() / Math.abs(size * 0.3));
    }

    public Block getBlock(int width, int height) {
        return new Block(this.generator.getRectangle(width, height), this.getColor());
    }

    public void setPoints(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.generator = new GeometryGenerator(this.topLeft, this.bottomRight);
    }

    private boolean ballCenterInFrame(Point center, int size) {
        return center.getX() + size < this.bottomRight.getX() && center.getX() - size > topLeft.getX() &&
                center.getY() + size < this.bottomRight.getY() && center.getY() - size > topLeft.getY();
    }
}
