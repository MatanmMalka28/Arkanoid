package game.objects.blocks;

import biuoop.DrawSurface;
import game.runners.Game;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

/**
 * BorderBlock is a game object which extends {@link Block}.
 * BorderBlock has different functionality when being hit.
 */
public class BorderBlock extends Block {
    /**
     * The constant BORDER_COLOR.
     */
    private static final Color BORDER_COLOR = Color.GRAY;
    /**
     * The constant BORDER_HIT_COUNT.
     */
    private static final int BORDER_HIT_COUNT = -1;


    /**
     * Instantiates a new Border Block.
     * The block's {@link Rectangle} is defined by two {@link Point}s.
     * All BorderBlocks color is {@link Color#GRAY}.
     *
     * @param topLeft     the top left corner of the block
     * @param bottomRight the bottom right corner of the block
     */
    public BorderBlock(Point topLeft, Point bottomRight) {
        this(new Rectangle(topLeft, bottomRight));
    }

    /**
     * Instantiates a new Border Block.
     * All BorderBlocks color is {@link Color#GRAY}.
     *
     * @param rectangle the rectangle
     */
    public BorderBlock(Rectangle rectangle) {
        super(rectangle, BORDER_COLOR, BORDER_HIT_COUNT);
    }

    /**
     * Instantiates a new BorderBlock.
     * The block's {@link Rectangle} is defined by a single {@link Point}, height and width.
     * All BorderBlocks color is {@link Color#GRAY}.
     *
     * @param topLeft the top left corner of the block
     * @param width   the width of the block
     * @param height  the height of the block
     */
    public BorderBlock(Point topLeft, int width, int height) {
        this(new Rectangle(topLeft, width, height));
    }

    /**
     * BorderBlock doesn't take hits, that way it can be removed from the game.
     */
    @Override
    protected void decreaseHit() {
        //do nothing
    }

    /**
     * BorderBlock doesn't draw his hit count on a draw surface.
     *
     * @param d the draw surface.
     */
    @Override
    protected void drawText(DrawSurface d) {
        //do nothing
    }

    /**
     * BorderBlock can't be removed from game.
     */
    @Override
    public void removeFromGame(Game game) {
        //do nothing
    }
}
