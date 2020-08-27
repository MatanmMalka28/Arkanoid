package game.runners;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.GameGenerator;
import game.animations.Animatable;
import game.animations.AnimationRunner;
import game.animations.CountdownAnimation;
import game.animations.screens.PauseScreen;
import game.listeners.BallRemover;
import game.listeners.BlockRemover;
import game.listeners.HitListener;
import game.listeners.MovementListener;
import game.listeners.ScoreTrackingListener;
import game.managers.Counter;
import game.objects.Ball;
import game.objects.Collidable;
import game.objects.Paddle;
import game.objects.attributes.Status;
import game.objects.attributes.Velocity;
import game.objects.blocks.Block;
import game.objects.blocks.BorderBlock;
import game.objects.blocks.KillingBlock;
import game.objects.collections.GameEnvironment;
import game.objects.collections.SpriteCollection;
import game.objects.indicators.ScoreIndicator;
import game.objects.indicators.TurnsLeftIndicator;
import game.objects.sprites.Background;
import game.objects.sprites.Sprite;
import geometry.Line;
import geometry.Point;
import utilities.Axis;
import utilities.Direction;
import utilities.Utilities;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Game.
 */
public class Game implements Animatable {
    /**
     * The constant BALL_SPEED.
     */
    private static final int BALL_SPEED = 10;
    /**
     * The constant BALL_ANGLE.
     */
    private static final int BALL_ANGLE = 0;
    /**
     * The constant DEFAULT_TOP_LEFT.
     */
    private static final Point DEFAULT_TOP_LEFT = new Point(0, 0);
    /**
     * The constant DEFAULT_BOTTOM_RIGHT.
     */
    private static final Point DEFAULT_BOTTOM_RIGHT = new Point(800, 600);
    /**
     * The constant PADDLE_WIDTH.
     */
    private static final int PADDLE_WIDTH = 100;
    /**
     * The constant PADDLE_HEIGHT.
     */
    private static final int PADDLE_HEIGHT = 20;
    /**
     * The constant BLOCK_WIDTH.
     */
    private static final int BLOCK_WIDTH = 30;
    /**
     * The constant BLOCK_HEIGHT.
     */
    private static final int BLOCK_HEIGHT = 20;
    /**
     * The constant BORDER_WIDTH.
     */
    private static final int BORDER_WIDTH = 20;
    /**
     * The constant DEFAULT_LIVES.
     */
    private static final int DEFAULT_LIVES = 3;
    /**
     * The constant SCORE_HEIGHT.
     */
    private static final int SCORE_HEIGHT = 30;
    /**
     * The constant SCORE_WIDTH.
     */
    private static final int SCORE_WIDTH = ((int) DEFAULT_BOTTOM_RIGHT.getX() / 2);
    /**
     * The constant NUM_OF_BALLS.
     */
    private static final int NUM_OF_BALLS = 1;
    /**
     * The constant NUM_OF_BALL_LIVES.
     */
    private static final int NUM_OF_BALL_LIVES = 1;
    /**
     * The constant COLORS.
     */
    private static final Color[] COLORS = {Color.ORANGE, Color.RED, Color.CYAN, Color.BLUE, Color.GREEN,
            Color.PINK, Color.MAGENTA};
    /**
     * The constant FRAMES_PER_SECOND.
     */
    private static final int FRAMES_PER_SECOND = 60;

    /**
     * The Sprites.
     */
    private final SpriteCollection sprites;
    /**
     * The Environment.
     */
    private final GameEnvironment environment;
    /**
     * The Top left.
     */
    private final Point topLeft;
    /**
     * The Bottom right.
     */
    private final Point bottomRight;
    /**
     * The Background.
     */
    private final Background background;

    /**
     * The Animation runner.
     */
    private final AnimationRunner runner;
    /**
     * The Stopped.
     */
    private boolean stopped;
    /**
     * The Game status.
     */
    private Status gameStatus = Status.PLAYING;
    /**
     * The Score counter.
     */
    private final Counter scoreCounter;
    /**
     * The Turns counter.
     */
    private final Counter turnsCounter;
    /**
     * The Balls counter.
     */
    private Counter ballsCounter;


    /**
     * Instantiates a new Game.
     */
    public Game() {
        this(DEFAULT_TOP_LEFT, DEFAULT_BOTTOM_RIGHT);
    }

    /**
     * Instantiates a new Game.
     *
     * @param topLeft     the top left
     * @param bottomRight the bottom right
     */
    public Game(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.background = new Background(topLeft, bottomRight, Color.BLUE.darker().darker());
        this.runner = new AnimationRunner(new GUI("Game", this.background.width(),
                this.background.height()), FRAMES_PER_SECOND);
        this.scoreCounter = new Counter();
        this.turnsCounter = new Counter(DEFAULT_LIVES);
        this.ballsCounter = new Counter(NUM_OF_BALLS * NUM_OF_BALL_LIVES);

    }

    /**
     * Gets top left.
     *
     * @return the top left
     */
    public Point getTopLeft() {
        return topLeft.copy();
    }

    /**
     * Gets bottom right.
     *
     * @return the bottom right
     */
    public Point getBottomRight() {
        return bottomRight.copy();
    }

    /**
     * Gets environment.
     *
     * @return the environment
     */
    public GameEnvironment getEnvironment() {
        return new GameEnvironment(this.environment.getCollidables());
    }

    /**
     * Is stopped boolean.
     *
     * @return the boolean
     */
    public boolean isStopped() {
        return this.stopped;
    }

    /**
     * Gets paddle.
     *
     * @return the paddle
     */
    private Paddle getPaddle() {
        for (Collidable c : this.environment.getCollidables()) {
            if (c instanceof Paddle) {
                return ((Paddle) c);
            }
        }
        return null;
    }

    /**
     * Sets game status.
     *
     * @param status the status
     */
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
            default:
                break;
        }
    }

    /**
     * Add collidable.
     *
     * @param collidableList the collidable list
     */
    public void addCollidable(List<Collidable> collidableList) {
        environment.addCollidable(collidableList);
    }

    /**
     * Add sprite.
     *
     * @param spriteList the sprites
     */
    public void addSprite(List<Sprite> spriteList) {
        this.sprites.addSprite(spriteList);
    }

    /**
     * Add collidable.
     *
     * @param c the c
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * Remove sprite.
     *
     * @param s the s
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Remove collidable.
     *
     * @param c the c
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Initialize.
     */
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
        TurnsLeftIndicator turnsLeftIndicator = new TurnsLeftIndicator(top.middle(), SCORE_WIDTH,
                SCORE_HEIGHT, this.turnsCounter);
        turnsLeftIndicator.addToGame(this);
    }

    /**
     * Generate balls.
     *
     * @param radius the radius
     */
    public void generateBalls(int radius) {
        for (int i = 0; i < NUM_OF_BALLS; i++) {
            this.addNewBall(radius);
        }
    }

    /**
     * Add new ball.
     *
     * @param radius the radius
     */
    public void addNewBall(int radius) {
        this.ballsCounter.decrease();
        if (this.hasBallsInGame()) {
            Ball ball = this.createBall(radius);
            if (ball != null) {
                ball.addToGame(this);
            }
        }
    }

    /**
     * Run.
     */
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
                default:
                    break;
            }
        }
        this.runner.closeGuiWindow();
    }

    /**
     * Add sprite.
     *
     * @param s the s
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Assign game environment.
     *
     * @param ball the ball
     */
    public void assignGameEnvironment(Ball ball) {
        if (ball != null && this.sprites.getSpriteList().contains(ball)) {
            ball.setGameEnvironment(this.environment);
        }
    }

    /**
     * Assign movement listener.
     *
     * @param ml the ml
     */
    public void assignMovementListener(MovementListener ml) {
        Paddle paddle = this.getPaddle();
        if (paddle != null) {
            paddle.addMovementListener(ml);
        }
    }

    /**
     * Remove movement listener.
     *
     * @param ml the ml
     */
    public void removeMovementListener(MovementListener ml) {
        Paddle paddle = this.getPaddle();
        if (paddle != null) {
            paddle.removeMovementListener(ml);
        }
    }

    /**
     * Generate paddle paddle.
     *
     * @return the paddle
     */
    private Paddle generatePaddle() {
        return new Paddle(new Point(this.background.width() / 2 - PADDLE_WIDTH,
                this.background.height() - 2 * PADDLE_HEIGHT), PADDLE_WIDTH, PADDLE_HEIGHT,
                Color.green, this.runner.getKeyboardSensor());
    }

    /**
     * Soft initialize.
     */
    private void softInitialize() {
        this.setGameStatus(Status.PLAYING);
        this.generatePaddle().addToGame(this);
        this.ballsCounter = new Counter(NUM_OF_BALLS * NUM_OF_BALL_LIVES);
        this.generateBalls(7);

    }

    /**
     * Play one turn.
     */
    private void playOneTurn() {
        this.runner.run(new CountdownAnimation(3,3,this.getSprites(),this.topLeft.middle(this.bottomRight)));
        this.runner.run(this);
        Paddle paddle = this.getPaddle();
        if (paddle != null) {
            paddle.removeFromGame(this);
        }
        this.turnsCounter.decrease();
    }

    /**
     * Add blocks.
     *
     * @param blocks       the blocks
     * @param hitListeners the hit listeners
     */
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

    /**
     * Make borders list.
     *
     * @return the list
     */
    private List<Block> makeBorders() {
        Point bordersTopLeft = this.topLeft.shiftPoint(30, Axis.Y);
        Point topRight = new Point(bottomRight.getX(), bordersTopLeft.getY());
        Point bottomLeft = new Point(bordersTopLeft.getX(), bottomRight.getY());
        List<Block> blockList = new ArrayList<>();
        blockList.add(new BorderBlock(this.topLeft, bottomLeft.shiftPoint(BORDER_WIDTH, Axis.X))); //left
        blockList.add(new BorderBlock(this.topLeft, topRight.shiftPoint(BORDER_WIDTH, Axis.Y))); //up
        blockList.add(new BorderBlock(topRight.shiftPoint(-BORDER_WIDTH, Axis.X), this.bottomRight)); //right
        blockList.add(new KillingBlock(bottomLeft.shiftPoint(-BORDER_WIDTH, Axis.Y), this.bottomRight,
                new BallRemover(this))); //down
        return blockList;
    }

    /**
     * Create ball ball.
     *
     * @param radius the radius
     * @return the ball
     */
    private Ball createBall(int radius) {
        Paddle paddle = this.getPaddle();
        if (paddle != null) {
            return this.createBall(paddle, radius);
        } else {
            return null;
        }
    }

    /**
     * Update status.
     */
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

    /**
     * Has blocks boolean.
     *
     * @return the boolean
     */
    private boolean hasBlocks() {
        return this.environment.getCollidables().size() > 5; //5 = 4 borders + 1 paddle
    }

    /**
     * Has lives boolean.
     *
     * @return the boolean
     */
    private boolean hasLives() {
        return this.turnsCounter.getValue() > 0;
    }

    /**
     * Has balls in game boolean.
     *
     * @return the boolean
     */
    private boolean hasBallsInGame() {
        return !(this.ballsCounter.getValue() < 0);
    }

    /**
     * Create ball ball.
     *
     * @param paddle the paddle
     * @param radius the radius
     * @return the ball
     */
    private Ball createBall(Paddle paddle, int radius) {
        Point ballCenter = paddle.getCollisionRectangle().getEdge(Direction.TOP).middle();
        ballCenter = ballCenter.shiftPoint(-radius, Axis.Y);
        Ball ball = new Ball(ballCenter, radius, Color.WHITE);
        ball.setVelocity(Velocity.fromAngleAndSpeed(BALL_ANGLE, BALL_SPEED));
        return ball;
    }

    /**
     * Make blocks list.
     *
     * @param firstLineBlocks the blocks on first line
     * @param widthFill       the width fill
     * @param lines           the num of lines
     * @param startY          the start y
     * @param blockDiff       the block diff
     * @return the list
     */
    private List<Block> makeBlocks(int firstLineBlocks, double widthFill, int lines, double startY, int blockDiff) {
        Block.setInitialHitCount(3);
        int blockWidth = ((int) (((this.background.width() - 2 * BORDER_WIDTH) * widthFill) / firstLineBlocks));
        double startX = this.background.width() - BORDER_WIDTH;
        List<Block> blockList = new ArrayList<>();
        for (int i = 0; i < lines; i++) {
            blockList.addAll(GameGenerator.makeBlockLine(firstLineBlocks, new Point(startX, startY), blockWidth,
                    BLOCK_HEIGHT, COLORS[i % COLORS.length], Direction.LEFT));
            startY += BLOCK_HEIGHT;
            firstLineBlocks -= blockDiff;
        }
        return blockList;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
        KeyboardSensor keyboard = this.runner.getKeyboardSensor();
        if (keyboard.isPressed("p") || keyboard.isPressed("P") || keyboard.isPressed("פ")){
            this.runner.run(new PauseScreen(keyboard));
            this.runner.run(new CountdownAnimation(3,3,this.getSprites(),this.topLeft.middle(this.bottomRight)));
        }
        this.updateStatus();

    }

    private SpriteCollection getSprites() {
        return new SpriteCollection(this.sprites.getSpriteList());
    }

    public static Point getDefaultTopLeft() {
        return DEFAULT_TOP_LEFT;
    }

    public static Point getDefaultBottomRight() {
        return DEFAULT_BOTTOM_RIGHT;
    }

    @Override
    public boolean shouldStop() {
        return this.stopped;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        //todo: add args parser
        Game game = new Game();
        game.run();
    }
}