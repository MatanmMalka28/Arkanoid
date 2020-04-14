package Game;

import Game.Objects.Ball;
import Game.Objects.Block;
import Geometry.GeometryGenerator;
import Geometry.Point;

import java.awt.*;
import java.util.Random;

public class GameGenerator {
    private static int minRadius = 5;
    private static int minSpeed = 2;
    private static final int RADIUS = 15;
    private static final int DEGREES = 360;
    private static final int SPEED = 25;
    private Point topLeft, bottomRight;
    private int radiusBound, speed;
    private GeometryGenerator geometryGenerator;
    private Random random = new Random();

    public GameGenerator(Geometry.Point topLeft, Geometry.Point bottomRight, int radiusBound, int speed) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.radiusBound = radiusBound;
        this.speed = speed;
        this.geometryGenerator = new GeometryGenerator(topLeft, bottomRight);
    }

    public GameGenerator(Geometry.Point topLeft, Geometry.Point bottomRight) {
        this(topLeft, bottomRight, RADIUS, SPEED);
    }

    public int getRadiusBound() {
        return radiusBound;
    }

    public void setRadiusBound(int radiusBound) {
        this.radiusBound = radiusBound;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Color generateColor() {
        return new Color(this.random.nextFloat(), this.random.nextFloat(), this.random.nextFloat());
    }

    public static void setMinRadius(int minRadius) {
        GameGenerator.minRadius = minRadius;
    }

    public static void setMinSpeed(int minSpeed) {
        GameGenerator.minSpeed = minSpeed;
    }

    public Ball generateBall() {
        Ball ball = new Ball(this.geometryGenerator.generatePoint(),
                this.random.nextInt(this.radiusBound) + 1 + minRadius, this.generateColor());
        ball.setVelocity(this.generateVelocity());
        return ball;
    }

    public Ball generateBall(int size) {
        Ball ball = new Ball(this.geometryGenerator.generatePoint(), size, this.generateColor());
        ball.setVelocity(this.generateVelocity());
        return ball;
    }

    public Velocity generateVelocity(int size) {
        double speed = this.random.nextInt(this.speed) + this.random.nextDouble();
        double angle = this.random.nextInt(DEGREES) + this.random.nextDouble();
        return Velocity.fromAngleAndSpeed(angle, speed / size);
    }

    public Velocity generateVelocity() {
        double speed = this.random.nextInt(this.speed) + this.random.nextDouble() + minSpeed;
        double angle = this.random.nextInt(DEGREES) + this.random.nextDouble();
        return Velocity.fromAngleAndSpeed(angle, speed);
    }

    public Ball generateBalancedSpeedBall() {
        Ball ball = this.generateBall();
        ball.setVelocity(this.generateVelocity(ball.getSize()));
        return ball;
    }

    public Block generateRandomBlock(int width, int height){
        return new Block(this.geometryGenerator.generateRectangle(width,height), this.generateColor());
    }
}
