package game.runners;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import game.GameGenerator;
import game.Status;
import game.listeners.BallRemover;
import game.listeners.BlockRemover;
import game.listeners.HitListener;
import game.objects.Background;
import game.objects.Ball;
import game.objects.Block;
import game.objects.BorderBlock;
import game.objects.Collidable;
import game.objects.KillingBlock;
import game.objects.Paddle;
import game.objects.Sprite;
import game.objects.attributes.Velocity;
import game.objects.collections.GameEnvironment;
import game.objects.collections.SpriteCollection;
import geometry.Point;
import utilities.Axis;
import utilities.Direction;

import java.awt.Color;
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
    private static final int DEFAULT_LIVES = 3;
    private static final Color[] COLORS = {Color.ORANGE, Color.RED, Color.CYAN, Color.BLUE, Color.GREEN, Color.PINK, Color.MAGENTA};
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Point topLeft, bottomRight;
    private Background background;
    private GUI gui;
    private boolean stopped;
    private Status gameStatus = Status.PLAYING;
    private int lives = DEFAULT_LIVES;


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

    public boolean isStopped() {
        return this.stopped;
    }

    public void addCollidable(List<Collidable> collidableList) {
        environment.addCollidable(collidableList);
    }

    public void addSprite(List<Sprite> sprites) {
        this.sprites.addSprite(sprites);
    }

    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
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
        this.addNewBall(8);

    }

    private void addBlocks(List<Block> blocks) {
        for (Block block : blocks) {
            block.addToGame(this);
        }
    }

    private List<Block> makeBorders() {
        Point topRight = new Point(bottomRight.getX(), topLeft.getY());
        Point bottomLeft = new Point(topLeft.getX(), bottomRight.getY());
        List<Block> blockList = new ArrayList<>();
        blockList.add(new BorderBlock(this.topLeft, bottomLeft.shiftPoint(BORDER_WIDTH, Axis.X))); //left
        blockList.add(new BorderBlock(this.topLeft, topRight.shiftPoint(BORDER_WIDTH, Axis.Y))); //up
        blockList.add(new BorderBlock(topRight.shiftPoint(-BORDER_WIDTH, Axis.X), this.bottomRight)); //right
        blockList.add(new KillingBlock(bottomLeft.shiftPoint(-BORDER_WIDTH, Axis.Y), this.bottomRight, new BallRemover(this))); //down
        return blockList;
    }

    public void addNewBall(int radius) {
        this.lives--;
        if (this.lives >= 0) {
            Ball ball = this.createBall(radius);
            if (ball != null) {
                ball.addToGame(this);
            } else {
                //todo: add ball manually
            }
        } else {
            this.updateStatus();
        }
    }

    private Ball createBall(int radius) {
        Paddle paddle = null;
        for (Collidable c : this.environment.getCollidables()) {
            if (c instanceof Paddle) {
                return this.createBall(((Paddle) c), radius);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }

    private void updateStatus() {
        if (this.lives < 0) {
            this.setGameStatus(Status.LOSE);
        }
        if (this.environment.getCollidables().size() == 5) { //5 = 4 borders + 1 paddle
            this.setGameStatus(Status.WIN);
        }
    }

    private Ball createBall(Paddle paddle, int radius) {
        Point ballCenter = paddle.getCollisionRectangle().getEdge(Direction.TOP).middle();
        ballCenter = ballCenter.shiftPoint(-radius, Axis.Y);
        Ball ball = new Ball(ballCenter, radius, Color.WHITE);
        ball.setVelocity(Velocity.fromAngleAndSpeed(0, 12));
        return ball;
    }

    private void setGameStatus(Status status) {
        this.gameStatus = status;
        switch (this.gameStatus) {
            case PLAYING:
                this.stopped = false;
                break;
            case WIN:
            case LOSE:
                this.stopped = true;
        }
    }

    // Run the game -- start the animation loop.
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        Sleeper sleeper = new Sleeper();
        while (!this.stopped) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = this.gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();
            this.updateStatus();
            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
        this.gui.close();
    }

    private List<Block> makeBlocks(int blocksOnFirstLine, double widthFill, int numOfLines, double startY, int blockDiff) {
        Block.setHitCount(1);
        int blockWidth = ((int) (((this.background.width() - 2 * BORDER_WIDTH) * widthFill) / blocksOnFirstLine));
        double startX = this.background.width() - BORDER_WIDTH;
        List<Block> blockList = new ArrayList<>();
        for (int i = 0; i < numOfLines; i++) {
            blockList.addAll(GameGenerator.makeBlockLine(blocksOnFirstLine, new Point(startX, startY), blockWidth,
                    BLOCK_HEIGHT, COLORS[i % COLORS.length], Direction.LEFT));
            startY += BLOCK_HEIGHT;
            blocksOnFirstLine -= blockDiff;
        }
        HitListener hitListener = new BlockRemover(this, blockList.size());
        for (Block block : blockList) {
            block.addHitListener(hitListener);
        }
        return blockList;
    }

    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    public void assignGameEnvironment(Ball ball) {
        if (ball != null && this.sprites.getSpriteList().contains(ball)) {
            ball.setGameEnvironment(this.environment);
        }
    }
}