package game.runners;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import game.GameGenerator;
import game.objects.Background;
import game.objects.Ball;
import game.objects.Block;
import game.objects.Collidable;
import game.objects.Paddle;
import game.objects.Sprite;
import game.objects.attributes.Velocity;
import game.objects.collections.GameEnvironment;
import game.objects.collections.SpriteCollection;
import geometry.Point;
import utilities.Axis;
import utilities.Direction;
import utilities.Utilities;

import java.awt.*;
import java.security.Guard;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final Point DEFAULT_TOP_LEFT = new Point(0, 0);
    private static final Point DEFAULT_BOTTOM_LEFT = new Point(800, 600);
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 20;
    private static final int BLOCK_WIDTH = 30;
    private static final int BLOCK_HEIGHT = 20;
    private static final int BORDER_WIDTH = 20;
    private static final Color[] COLORS = {Color.ORANGE, Color.RED, Color.CYAN, Color.BLUE, Color.GREEN, Color.PINK, Color.MAGENTA};
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Point topLeft, bottomRight;
    private Background background;
    private GUI gui;


    public Game() {
        this(DEFAULT_TOP_LEFT, DEFAULT_BOTTOM_LEFT);
    }

    public Game(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.background = new Background(topLeft, bottomRight, Color.BLUE.darker().darker());
        this.gui = new GUI("Game", this.background.width(), this.background.height());
    }

    public Point getTopLeft() {
        return topLeft.copy();
    }

    public Point getBottomRight() {
        return bottomRight.copy();
    }

    public GameEnvironment getEnvironment() {
        return new GameEnvironment(this.environment.getCollidables());
    }

    public void addCollidable(List<Collidable> collidableList) {
        environment.addCollidable(collidableList);
    }

    public void addSprite(List<Sprite> sprites) {
        this.sprites.addSprite(sprites);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }

    // Initialize a new game: create the Blocks and Ball (and Paddle)
    // and add them to the game.
    public void initialize() {
        this.sprites.addSprite(this.background);
        this.addBlocks(this.makeBorders());
        this.addBlocks(this.makeBlocks(8, 0.75, 7, this.background.height() * 0.4, 1));
        Paddle paddle = new Paddle(new Point(this.background.width() / 2 - PADDLE_WIDTH,
                this.background.height() - 2 * PADDLE_HEIGHT), PADDLE_WIDTH, PADDLE_HEIGHT,
                Color.green, this.gui.getKeyboardSensor());
        paddle.addToGame(this);
        Ball ball = new Ball(this.background.width() / 2, this.background.height() * 0.6, 8, Color.WHITE);
        ball.setVelocity(Velocity.fromAngleAndSpeed(120, 12));
        ball.setGameEnvironment(this.environment);
        this.addSprite(ball);

    }

    // Run the game -- start the animation loop.
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        Sleeper sleeper = new Sleeper();
        while (true) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = this.gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    private void addBlocks(List<Block> blocks) {
        for (Block block : blocks) {
            this.addCollidable(block);
            this.addSprite(block);
        }
    }

    private List<Block> makeBorders() {
        Point topRight = new Point(bottomRight.getX(), topLeft.getY());
        Point bottomLeft = new Point(topLeft.getX(), bottomRight.getY());
        List<Block> blockList = new ArrayList<>();
        blockList.add(new Block(this.topLeft, bottomLeft.shiftPoint(BORDER_WIDTH, Axis.X), 0)); //left
        blockList.add(new Block(this.topLeft, topRight.shiftPoint(BORDER_WIDTH, Axis.Y), 0)); //up
        blockList.add(new Block(topRight.shiftPoint(-BORDER_WIDTH, Axis.X), this.bottomRight, 0)); //right
        blockList.add(new Block(bottomLeft.shiftPoint(-BORDER_WIDTH, Axis.Y), this.bottomRight, 0)); //down
        return blockList;
    }

    private List<Block> makeBlocks(int blocksOnFirstLine, double widthFill, int numOfLines, double startY, int blockDiff) {
        int blockWidth = ((int) (((this.background.width() - 2 * BORDER_WIDTH) * widthFill) / blocksOnFirstLine));
        double startX = this.background.width() - BORDER_WIDTH;
        List<Block> blockList = new ArrayList<>();
        for (int i = 0; i < numOfLines; i++) {
            blockList.addAll(GameGenerator.makeBlockLine(blocksOnFirstLine, new Point(startX, startY), blockWidth,
                    BLOCK_HEIGHT, COLORS[i % COLORS.length], Direction.LEFT));
            startY += BLOCK_HEIGHT;
            blocksOnFirstLine -= blockDiff;
        }
        return blockList;
    }

    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }
}