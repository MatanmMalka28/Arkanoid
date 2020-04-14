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
    private static final Color FRAME_COLOR = Color.GRAY;
    private static int width = 40;
    private static int height = 20;
    private Point topLeft, bottomRight;
    private List<Ball> ballList = new ArrayList<>();
    private GameEnvironment gameEnvironment = new GameEnvironment();
    private Color backgroundColor;
    private GameGenerator generator;

    public Frame(Point topLeft, Point bottomRight, List<Ball> ballList, Color backgroundColor) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.ballList = ballList;
        this.backgroundColor = backgroundColor;
        this.generator = new GameGenerator(topLeft, bottomRight);
        this.gameEnvironment.addCollidable(Utilities.translatePointsToBlocks(topLeft,bottomRight));
    }

    public Frame(Point topLeft, Point bottomRight, Color backgroundColor) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.backgroundColor = backgroundColor;
        this.generator = new GameGenerator(topLeft, bottomRight);
        this.gameEnvironment.addCollidable(Utilities.translatePointsToBlocks(topLeft,bottomRight));
    }

    public Frame(Point topLeft, Point bottomRight) {
        this(topLeft, bottomRight, FRAME_COLOR);
    }

    public static void setWidth(int width) {
        Frame.width = width;
    }

    public static void setHeight(int height) {
        Frame.height = height;
    }

    public void addBall() {
        this.addBall(this.generator.generateBall());
    }

    public void addBall(int radius) {
        this.addBall(this.generator.generateBall(radius));
    }

    public void addBall(int radiusBound, int speedBound) {
        int tempRadius = this.generator.getRadiusBound();
        int tempSpeed = this.generator.getSpeed();
        this.generator.setRadiusBound(radiusBound);
        this.generator.setSpeed(speedBound);
        this.addBall(this.generator.generateBall());
        this.generator.setRadiusBound(tempRadius);
        this.generator.setSpeed(tempSpeed);
    }

    public void addBall(Ball ball) {
        this.ballList.add(ball);
    }

    public void addBalls(int numOfBalls) {
        for (int i = 0; i < numOfBalls; i++) {
            this.addBall();
        }
    }

    public void addBlocks(int numOfBlocks) {
        for (int i = 0; i < numOfBlocks; i++) {
            this.gameEnvironment.addCollidable(this.generator.generateRandomBlock(width, height));
        }
        for (Ball ball : this.ballList) {
            ball.setGameEnvironment(this.gameEnvironment);
        }
    }

    public void runFrame(DrawSurface d) {
        this.drawBackground(d);
        for (Ball ball : this.ballList) {
            ball.moveOneStep();
            ball.drawOn(d);
            for (Collidable c : this.gameEnvironment.getCollidables()) {
                if (c instanceof Block){
                    ((Block) c).drawOn(d);
                }
            }
        }

    }

    private void drawBackground(DrawSurface d) {
        d.setColor(this.backgroundColor);
        int width = Math.abs((int) (this.topLeft.getX() - this.bottomRight.getX()));
        int height = Math.abs((int) (this.topLeft.getY() - this.bottomRight.getY()));
        d.fillRectangle(((int) this.topLeft.getX()), ((int) this.topLeft.getY()), width, height);
    }


}
