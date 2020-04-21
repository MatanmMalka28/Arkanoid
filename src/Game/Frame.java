package Game;

import Game.Objects.Ball;
import Game.Objects.Block;
import Game.Objects.Collections.GameEnvironment;
import Game.Objects.Collidable;
import Geometry.Point;
import Utilities.Utilities;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Frame {
    private Point topLeft, bottomRight;
    private List<Ball> ballList;
    private Color backgroundColor;
    private GameGenerator generator;

    public Frame(Point topLeft, Point bottomRight, Color backgroundColor) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.backgroundColor = backgroundColor;
        this.generator = new GameGenerator(topLeft, bottomRight);
        this.ballList = new ArrayList<>();
    }

    public Frame(double width, double height, Color backgroundColor) {
        this(new Point(0, 0), new Point(width, height), backgroundColor);
    }

    public void addBall() {
        this.addBall(this.generator.getBall());
    }

    public void addBall(int size) {
        this.addBall(this.generator.getBall(size));
    }

    public void setSpeed(double lower, double upper) {
        this.generator.setSpeedLower(lower);
        this.generator.setSpeedUpper(upper);
    }

    public void setDeg(double lower, double upper) {
        this.generator.setDegLower(lower);
        this.generator.setDegUpper(upper);
    }

    public void setRadius(int lower, int upper) {
        this.generator.setRadiusLower(lower);
        this.generator.setRadiusUpper(upper);
    }

    public void addBall(Ball ball) {
        this.ballList.add(ball);
    }

    public void addBalls(List<Integer> ballsSize) {
        for (Integer size : ballsSize) {
            this.addBall(size);
        }
    }

    public void moveBalls() {
        for (Ball ball : this.ballList) {
            ball.moveOneStep();
        }
    }

    public void drawOn(DrawSurface d) {
        this.drawBackground(d);
        for (Ball ball : this.ballList) {
            ball.drawOn(d);
        }

    }

    private void drawBackground(DrawSurface d) {
        d.setColor(this.backgroundColor);
        int width = Math.abs((int) (this.topLeft.getX() - this.bottomRight.getX()));
        int height = Math.abs((int) (this.topLeft.getY() - this.bottomRight.getY()));
        d.fillRectangle(((int) this.topLeft.getX()), ((int) this.topLeft.getY()), width, height);
    }


}
