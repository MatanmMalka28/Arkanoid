package game.objects.blocks;

import biuoop.DrawSurface;
import game.listeners.BallRemover;
import geometry.Point;
import geometry.Rectangle;

public class KillingBlock extends BorderBlock {

    public KillingBlock(Point topLeft, Point bottomRight, BallRemover ballRemover) {
        this(new Rectangle(topLeft, bottomRight), ballRemover);
    }

    public KillingBlock(Rectangle rectangle, BallRemover ballRemover) {
        super(rectangle);
        super.addHitListener(ballRemover);
    }

    public KillingBlock(Point topLeft, int width, int height, BallRemover ballRemover) {
        this(new Rectangle(topLeft, width, height), ballRemover);
    }

    @Override
    public void drawOn(DrawSurface d) {
        //don't draw
    }
}
