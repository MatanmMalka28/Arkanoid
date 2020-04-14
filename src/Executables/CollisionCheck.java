package Executables;

import Game.Frame;
import Geometry.Point;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

public class CollisionCheck {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    public static void main(String[] args) {
        GUI gui = new GUI("Collision Check", WIDTH, HEIGHT);
        Sleeper sleeper = new Sleeper();
        Frame frame = new Frame(new Point(0,0),new Point(WIDTH,HEIGHT));
        frame.addBalls(4);
        frame.addBlocks(4);
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            frame.runFrame(d);
            gui.show(d);
            sleeper.sleepFor(50);
        }
    }
}
