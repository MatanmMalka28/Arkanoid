package game.runners;

import game.GameGenerator;
import game.objects.Ball;
import game.objects.Block;
import game.objects.Collidable;
import game.objects.collections.GameEnvironment;
import geometry.Point;
import biuoop.DrawSurface;
import org.omg.CORBA.BAD_CONTEXT;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Frame.
 */
public class Frame {
    /**
     * The Top left.
     */
    private Point topLeft, /**
     * The Bottom right.
     */
    bottomRight;
    /**
     * The Ball list.
     */
    private List<Ball> ballList;
    /**
     * The Background color.
     */
    private Color backgroundColor;
    /**
     * The Generator.
     */
    private GameGenerator generator;

    /**
     * Instantiates a new Frame.
     *
     * @param width           the width
     * @param height          the height
     * @param backgroundColor the background color
     */
    public Frame(double width, double height, Color backgroundColor) {
        this(new Point(0, 0), new Point(width, height), backgroundColor);
    }

    /**
     * Instantiates a new Frame.
     *
     * @param topLeft         the top left
     * @param bottomRight     the bottom right
     * @param backgroundColor the background color
     */
    public Frame(Point topLeft, Point bottomRight, Color backgroundColor) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.backgroundColor = backgroundColor;
        this.generator = new GameGenerator(topLeft, bottomRight);
        this.ballList = new ArrayList<>();
    }

    /**
     * Add ball.
     */
    public void addBall() {
        this.addBall(this.generator.getBall());
    }

    /**
     * Add ball.
     *
     * @param ball the ball
     */
    public void addBall(Ball ball) {
        this.ballList.add(ball);
    }

    /**
     * Sets speed.
     *
     * @param lower the lower
     * @param upper the upper
     */
    public void setSpeed(double lower, double upper) {
        this.generator.setSpeedLower(lower);
        this.generator.setSpeedUpper(upper);
    }

    /**
     * Sets deg.
     *
     * @param lower the lower
     * @param upper the upper
     */
    public void setDeg(double lower, double upper) {
        this.generator.setDegLower(lower);
        this.generator.setDegUpper(upper);
    }

    /**
     * Sets radius.
     *
     * @param lower the lower
     * @param upper the upper
     */
    public void setRadius(int lower, int upper) {
        this.generator.setRadiusLower(lower);
        this.generator.setRadiusUpper(upper);
    }

    /**
     * Add balls.
     *
     * @param ballsSize the balls size
     */
    public void addBalls(List<Integer> ballsSize) {
        for (Integer size : ballsSize) {
            this.addBall(size);
        }
    }

    /**
     * Add ball.
     *
     * @param size the size
     */
    public void addBall(int size) {
        this.addBall(this.generator.getBall(size));
    }

    public void setGameEnviorment(GameEnvironment gameEnvironment) {
        for (Ball ball : this.ballList) {
            ball.setGameEnvironment(gameEnvironment);
        }
    }

    /**
     * Move balls.
     */
    public void moveBalls() {
        for (Ball ball : this.ballList) {
            ball.moveOneStep();
        }
    }

    /**
     * Draw background.
     *
     * @param d the d
     */
    private void drawBackground(DrawSurface d) {
        d.setColor(this.backgroundColor);
        int width = Math.abs((int) (this.topLeft.getX() - this.bottomRight.getX()));
        int height = Math.abs((int) (this.topLeft.getY() - this.bottomRight.getY()));
        d.fillRectangle(((int) this.topLeft.getX()), ((int) this.topLeft.getY()), width, height);
    }

    /**
     * Draw on.
     *
     * @param d the d
     */
    public void drawOn(DrawSurface d) {
        this.drawBackground(d);
        GameEnvironment gameEnvironment = this.ballList.get(0).getGameEnvironment();
        for (Collidable collidable : gameEnvironment.getCollidables()) {
            if (collidable instanceof Block) {
                ((Block) collidable).drawOn(d);
            }
        }
        for (Ball ball : this.ballList) {
            ball.drawOn(d);
        }


    }

    public void addBlocks(List<Collidable> collidables) {

        for (Ball ball : this.ballList) {
            ball.getGameEnvironment().addCollidable(collidables);
        }
    }

    public void addBlock(Collidable collidable) {
        for (Ball ball : this.ballList) {
            ball.getGameEnvironment().addCollidable(collidable);
        }
    }


}
