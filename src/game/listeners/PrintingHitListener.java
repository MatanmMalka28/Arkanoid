package game.listeners;

import game.objects.Ball;
import game.objects.blocks.Block;

/**
 * This class is used for debug purposes to print the hitCount of the block being hit.
 */
public class PrintingHitListener implements HitListener {

    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A Block with " + beingHit.getHitCount() + " points was hit.");
    }
}
