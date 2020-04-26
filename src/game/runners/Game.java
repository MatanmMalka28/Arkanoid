package game.runners;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import game.GameGenerator;
import game.objects.attributes.Status;
import game.listeners.BallRemover;
import game.listeners.BlockRemover;
import game.listeners.HitListener;
import game.listeners.MovementListener;
import game.listeners.ScoreTrackingListener;
import game.managers.Counter;
import game.objects.sprites.Background;
import game.objects.Ball;
import game.objects.blocks.Block;
import game.objects.blocks.BorderBlock;
import game.objects.Collidable;
import game.objects.blocks.KillingBlock;
import game.objects.Paddle;
import game.objects.indicators.ScoreIndicator;
import game.objects.sprites.Sprite;
import game.objects.indicators.TurnsLeftIndicator;
import game.objects.attributes.Velocity;
import game.objects.collections.GameEnvironment;
import game.objects.collections.SpriteCollection;
import geometry.Line;
import geometry.Point;
import utilities.Axis;
import utilities.Direction;
import utilities.Utilities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final int BALL_SPEED = 10;
    private static final int BALL_ANGLE = 0;
    private static final Point DEFAULT_TOP_LEFT = new Point(0, 0);
    private static final Point DEFAULT_BOTTOM_RIGHT = new Point(800, 600);
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 20;
    private static final int BLOCK_WIDTH = 30;
    private static final int BLOCK_HEIGHT = 20;
    private static final int BORDER_WIDTH = 20;
    private static final int DEFAULT_LIVES = 3;
    private static final int SCORE_HEIGHT = 30;
    private static final int SCORE_WIDTH = ((int) DEFAULT_BOTTOM_RIGHT.getX() / 2);
    private static final int NUM_OF_BALLS = 1;
    private static final int NUM_OF_BALL_LIVES = 1;
    private static final Color[] COLORS = {Color.ORANGE, Color.RED, Color.CYAN, Color.BLUE, Color.GREEN, Color.PINK, Color.MAGENTA};

    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Point topLeft, bottomRight;
    private Background background;
    private GUI gui;
    private boolean stopped;
    private Status gameStatus = Status.PLAYING;
    private Counter scoreCounter;
    private Counter turnsCounter;
    private Counter ballsCounter;


    public Game() {
        this(DEFAULT_TOP_LEFT, DEFAULT_BOTTOM_RIGHT);
    }

    public Game(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.background = new Background(topLeft, bottomRight, Color.BLUE.darker().darker());
        this.gui = new GUI("Game", this.background.width(), this.background.height());
        this.scoreCounter = new Counter();
        this.turnsCounter = new Counter(DEFAULT_LIVES);
        this.ballsCounter = new Counter(NUM_OF_BALLS * NUM_OF_BALL_LIVES);

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

    private Paddle getPaddle() {
        for (Collidable c : this.environment.getCollidables()) {
            if (c instanceof Paddle) {
                return ((Paddle) c);
            }
        }
        return null;
    }

    private void setGameStatus(Status status) {
        this.gameStatus = status;
        switch (this.gameStatus) {
            case PLAYING:
                this.stopped = false;
                break;
            case WIN:
            case LOSE:
            case LOST_ROUND:
                this.stopped = true;
                break;
        }
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
        Block.setDrawHits(false);
        this.sprites.addSprite(this.background);
        this.addBlocks(this.makeBorders(), null);
        List<Block> blockList = this.makeBlocks(8, 0.75, 6, this.background.height() * 0.4, 1);
        List<HitListener> hitListenerList = new ArrayList<>();
        hitListenerList.add(new BlockRemover(this, blockList.size()));
        hitListenerList.add(new ScoreTrackingListener(this.scoreCounter));
        this.addBlocks(blockList, hitListenerList);
        this.generatePaddle().addToGame(this);
        this.generateBalls(7);
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.topLeft, SCORE_WIDTH, SCORE_HEIGHT, this.scoreCounter);
        scoreIndicator.addToGame(this);
        Line top = Utilities.translatePointsToBorders(this.topLeft, this.bottomRight).get(Direction.TOP);
        TurnsLeftIndicator turnsLeftIndicator = new TurnsLeftIndicator(top.middle(), SCORE_WIDTH, SCORE_HEIGHT, this.turnsCounter);
        turnsLeftIndicator.addToGame(this);
    }

    public void generateBalls(int radius) {
        for (int i = 0; i < NUM_OF_BALLS; i++) {
            this.addNewBall(radius);
        }
    }

    public void addNewBall(int radius) {
        this.ballsCounter.decrease();
        if (this.hasBallsInGame()) {
            Ball ball = this.createBall(radius);
            if (ball != null) {
                ball.addToGame(this);
            }
        }
    }

    // Run the game -- start the animation loop.
    public void run() {
        boolean running = true;
        while (running) {
            switch (this.gameStatus) {
                case LOSE:
                    running = false;
                    System.out.println("You Lose!");
                    break;
                case WIN:
                    running = false;
                    System.out.println("You Win!");
                    break;
                case LOST_ROUND:
                    this.softInitialize();
                    this.playOneTurn();
                    break;
                case PLAYING:
                    this.initialize();
                    this.playOneTurn();
                    break;
            }
        }
        this.gui.close();
    }

    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    public void assignGameEnvironment(Ball ball) {
        if (ball != null && this.sprites.getSpriteList().contains(ball)) {
            ball.setGameEnvironment(this.environment);
        }
    }

    public void assignMovementListener(MovementListener ml) {
        Paddle paddle = this.getPaddle();
        if (paddle != null) {
            paddle.addMovementListener(ml);
        }
    }

    public void removeMovementListener(MovementListener ml) {
        Paddle paddle = this.getPaddle();
        if (paddle != null) {
            paddle.removeMovementListener(ml);
        }
    }

    private Paddle generatePaddle() {
        return new Paddle(new Point(this.background.width() / 2 - PADDLE_WIDTH,
                this.background.height() - 2 * PADDLE_HEIGHT), PADDLE_WIDTH, PADDLE_HEIGHT,
                Color.green, this.gui.getKeyboardSensor());
    }

    private void softInitialize() {
        this.setGameStatus(Status.PLAYING);
        this.generatePaddle().addToGame(this);
        this.ballsCounter = new Counter(NUM_OF_BALLS * NUM_OF_BALL_LIVES);
        this.generateBalls(7);

    }

    private void playOneTurn() {
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
        Paddle paddle = this.getPaddle();
        if (paddle != null) {
            paddle.removeFromGame(this);
        }
        this.turnsCounter.decrease();
    }

    private void addBlocks(List<Block> blocks, List<HitListener> hitListeners) {
        for (Block block : blocks) {
            block.addToGame(this);
            if (hitListeners != null) {
                for (HitListener hitListener : hitListeners) {
                    block.addHitListener(hitListener);
                }
            }
        }
    }

    private List<Block> makeBorders() {
        Point topLeft = this.topLeft.shiftPoint(30, Axis.Y);
        Point topRight = new Point(bottomRight.getX(), topLeft.getY());
        Point bottomLeft = new Point(topLeft.getX(), bottomRight.getY());
        List<Block> blockList = new ArrayList<>();
        blockList.add(new BorderBlock(this.topLeft, bottomLeft.shiftPoint(BORDER_WIDTH, Axis.X))); //left
        blockList.add(new BorderBlock(this.topLeft, topRight.shiftPoint(BORDER_WIDTH, Axis.Y))); //up
        blockList.add(new BorderBlock(topRight.shiftPoint(-BORDER_WIDTH, Axis.X), this.bottomRight)); //right
        blockList.add(new KillingBlock(bottomLeft.shiftPoint(-BORDER_WIDTH, Axis.Y), this.bottomRight, new BallRemover(this))); //down
        return blockList;
    }

    private Ball createBall(int radius) {
        Paddle paddle = this.getPaddle();
        if (paddle != null) {
            return this.createBall(paddle, radius);
        } else {
            return null;
        }
    }

    private void updateStatus() {
        if (!this.hasLives()) {
            this.setGameStatus(Status.LOSE);
        }
        if (!this.hasBlocks()) {
            this.setGameStatus(Status.WIN);
        }
        if (!this.hasBallsInGame()) {
            this.setGameStatus(Status.LOST_ROUND);
        }
    }

    private boolean hasBlocks() {
        return this.environment.getCollidables().size() > 5; //5 = 4 borders + 1 paddle
    }

    private boolean hasLives() {
        return this.turnsCounter.getValue() > 0;
    }

    private boolean hasBallsInGame() {
        return !(this.ballsCounter.getValue() < 0);
    }

    private Ball createBall(Paddle paddle, int radius) {
        Point ballCenter = paddle.getCollisionRectangle().getEdge(Direction.TOP).middle();
        ballCenter = ballCenter.shiftPoint(-radius, Axis.Y);
        Ball ball = new Ball(ballCenter, radius, Color.WHITE);
        ball.setVelocity(Velocity.fromAngleAndSpeed(BALL_ANGLE, BALL_SPEED));
        return ball;
    }

    private List<Block> makeBlocks(int blocksOnFirstLine, double widthFill, int numOfLines, double startY, int blockDiff) {
        Block.setHitCount(3);
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

    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}