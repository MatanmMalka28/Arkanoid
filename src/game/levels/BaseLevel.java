package game.levels;

import game.objects.attributes.Velocity;
import game.objects.blocks.Block;
import game.objects.sprites.Background;
import game.objects.sprites.Sprite;

import java.util.List;

public abstract class BaseLevel implements LevelInformation {
    private final String levelName;
    private final int windowHeight;
    private final int windowWidth;
    private final int paddleSpeed;
    private final int paddleWidth;
    private List<Velocity> initialBallVelocitiesList;
    private List<Block> blocksList;
    private Background background;


    public BaseLevel(String levelName, int windowHeight, int windowWidth, int paddleSpeed, int paddleWidth) {
        this.levelName = levelName;
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
    }

    /**
     * Number of balls int.
     *
     * @return the int
     */
    @Override
    public int numberOfBalls() {
        return this.initialBallVelocities() != null ? this.initialBallVelocities().size() : 0;
    }

    /**
     * The initial velocity of each ball.
     * Note that initialBallVelocities().size() == numberOfBalls()
     *
     * @return the list
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        if (this.initialBallVelocitiesList == null) {
            this.initialBallVelocitiesList = this.createInitialBallsVelocities();
        }
        return this.initialBallVelocitiesList;
    }

    /**
     * Paddle speed int.
     *
     * @return the int
     */
    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * Paddle width int.
     *
     * @return the int
     */
    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * the level name will be displayed at the top of the screen.
     *
     * @return the string
     */
    @Override
    public String levelName() {
        return this.levelName;
    }

    /**
     * The Blocks that make up this level, each block contains its size, color and location.
     *
     * @return the list
     */
    @Override
    public List<Block> blocks() {
        if (this.blocksList == null) {
            this.blocksList = this.createBlocks();
        }
        return this.blocksList;
    }


    /**
     * Number of blocks that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     *
     * @return the int
     */
    @Override
    public int numberOfBlocksToRemove() {
        return this.blocks() != null ? this.blocks().size() : 0;
    }

    /**
     * Returns a sprite with the background of the level
     *
     * @return the background
     */
    @Override
    public Sprite getBackground() {
        if (this.background == null) {
            this.background = this.createBackground();
        }
        return this.background;
    }

    /**
     * Get the window height.
     *
     * @return the int
     */
    @Override
    public int windowHeight() {
        return this.windowHeight;
    }

    /**
     * Get the window width.
     *
     * @return the int
     */
    @Override
    public int windowWidth() {
        return this.windowWidth;
    }

    protected abstract Background createBackground();
    protected abstract List<Velocity> createInitialBallsVelocities();
    protected abstract List<Block> createBlocks();

}
