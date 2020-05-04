package game.objects.blocks;

import biuoop.DrawSurface;
import game.listeners.BallRemover;
import geometry.Point;
import geometry.Rectangle;


/**
 * KillingBlock is a game object which extends {@link BorderBlock}.
 * KillingBlock has different functionality when being hit.
 */
public class KillingBlock extends BorderBlock {

    /**
     * Instantiates a new Killing Block.
     * The block's {@link Rectangle} is defined by two {@link Point}s.
     *
     * @param topLeft     the top left corner of the block
     * @param bottomRight the bottom right corner of the block
     * @param ballRemover the ball remover
     */
    public KillingBlock(Point topLeft, Point bottomRight, BallRemover ballRemover) {
        this(new Rectangle(topLeft, bottomRight), ballRemover);
    }

    /**
     * Instantiates a new Killing Block.
     *
     * @param rectangle   the rectangle
     * @param ballRemover the ball remover
     */
    public KillingBlock(Rectangle rectangle, BallRemover ballRemover) {
        super(rectangle);
        super.addHitListener(ballRemover);
    }

    /**
     * Instantiates a new KillingBlock.
     * The block's {@link Rectangle} is defined by a single {@link Point}, height and width.
     *
     * @param topLeft     the top left corner of the block
     * @param width       the width of the block
     * @param height      the height of the block
     * @param ballRemover the ball remover
     */
    public KillingBlock(Point topLeft, int width, int height, BallRemover ballRemover) {
        this(new Rectangle(topLeft, width, height), ballRemover);
    }

    /**
     * Killing Block doesn't draw itself on the draw surface.
     *
     * @param d the draw surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        //don't draw
    }
}
