package game.listeners;

import game.objects.Ball;
import game.objects.blocks.Block;

public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     *
     * @param beingHit the object being hit.
     * @param hitter   the object that made the hit.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
