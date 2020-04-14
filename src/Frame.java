import biuoop.DrawSurface;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Frame {
    private Point topLeft, bottomRight;
    private List<Ball> ballList = new ArrayList<>();
    private Color backgroundColor;
    private GameGenerator generator;

    public Frame(Point topLeft, Point bottomRight, List<Ball> ballList, Color backgroundColor) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.addBalls(ballList);
        this.backgroundColor = backgroundColor;
        this.generator = new GameGenerator(topLeft, bottomRight);
    }

    public Frame(Point topLeft, Point bottomRight, Color backgroundColor) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.backgroundColor = backgroundColor;
        this.generator = new GameGenerator(topLeft, bottomRight);
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
        ball.setGameBorders(this.topLeft,this.bottomRight);
        this.ballList.add(ball);
    }

    public void addBalls(int numOfBalls) {
        for (int i = 0; i < numOfBalls; i++) {
            this.addBall();
        }
    }

    public void addBalls(List<Ball> ballList) {
        for (Ball ball: ballList){
            ball.setGameBorders(this.topLeft,this.bottomRight);
        }
        this.ballList.addAll(ballList);
    }

    public void runFrame(DrawSurface d){
        this.drawBackground(d);
        for (Ball ball:this.ballList){
            ball.moveOneStep();
            ball.drawOn(d);
        }
    }

    private void drawBackground(DrawSurface d){
        d.setColor(this.backgroundColor);
        int width = Math.abs((int) (this.topLeft.getX() - this.bottomRight.getX()));
        int height = Math.abs((int) (this.topLeft.getY() - this.bottomRight.getY()));
        d.fillRectangle(((int) this.topLeft.getX()), ((int) this.topLeft.getY()),width,height);
    }


}
