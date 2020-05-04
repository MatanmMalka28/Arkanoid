package game.objects.blocks;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

/**
 * PaddleBlock is a game object which extends {@link Block}.
 * PaddleBlock has different functionality when being hit.
 */
public class PaddleBlock extends Block {

    /**
     * Instantiates a new Paddle block.
     *
     * @param rectangle the rectangle
     */
    public PaddleBlock(Rectangle rectangle) {
        super(rectangle);
    }

    /**
     * Instantiates a new Paddle block.
     *
     * @param rectangle the rectangle
     * @param color     the color
     */
    public PaddleBlock(Rectangle rectangle, Color color) {
        super(rectangle, color);
    }

    /**
     * Instantiates a new Paddle block.
     *
     * @param topLeft     the top left
     * @param bottomRight the bottom right
     */
    public PaddleBlock(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }

    /**
     * Instantiates a new Paddle block.
     *
     * @param topLeft     the top left
     * @param bottomRight the bottom right
     * @param color       the color
     */
    public PaddleBlock(Point topLeft, Point bottomRight, Color color) {
        super(topLeft, bottomRight, color);
    }

    /**
     * Instantiates a new Paddle block.
     *
     * @param topLeft the top left
     * @param width   the width
     * @param height  the height
     * @param color   the color
     */
    public PaddleBlock(Point topLeft, int width, int height, Color color) {
        super(topLeft, width, height, color);
    }

    /**
     * Instantiates a new Paddle block.
     *
     * @param topLeft the top left
     * @param width   the width
     * @param height  the height
     */
    public PaddleBlock(Point topLeft, int width, int height) {
        super(topLeft, width, height);
    }

    /**
     * Instantiates a new Paddle block.
     *
     * @param topLeft  the top left
     * @param width    the width
     * @param height   the height
     * @param color    the color
     * @param hitCount the hit count
     */
    public PaddleBlock(Point topLeft, int width, int height, Color color, int hitCount) {
        super(topLeft, width, height, color, hitCount);
    }

    @Override
    protected void decreaseHit() {
        //do nothing
    }

    @Override
    protected void drawText(DrawSurface d) {
        //do nothing
    }
}
