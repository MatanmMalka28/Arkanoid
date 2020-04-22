package executables;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import game.Frame;
import game.Velocity;
import game.objects.Ball;
import game.objects.Block;
import game.objects.Collidable;
import geometry.Point;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class CollisionCheck {

    public static void main(String[] args) {
        Frame frame = new Frame(400, 400, Color.GRAY);
        GUI gui = new GUI("Collision Check", 400, 400);
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball(10, 100, 6, Color.WHITE);
        ball.setGameBorders(new Point(0, 0), new Point(400, 400), 20);
        ball.setVelocity(Velocity.fromAngleAndSpeed(27, 10));
        frame.addBall(ball);
        List<Collidable> collidableList = new ArrayList<>();
        collidableList.add(new Block(new Point(300, 100), 87, 23, Color.cyan));
        collidableList.add(new Block(new Point(100, 300), 50, 20, Color.cyan));
        collidableList.add(new Block(new Point(20, 20), 100, 10, Color.cyan));
        collidableList.add(new Block(new Point(90, 200), 30, 40, Color.cyan));
        collidableList.add(new Block(new Point(150, 150), 100, 45, Color.cyan));
        frame.addBlocks(collidableList);
        Ball.setDebugMode(true);
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            frame.moveBalls();
            frame.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(30);
        }
    }
}
