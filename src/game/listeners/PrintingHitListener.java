package game.listeners;

import game.objects.Ball;
import game.objects.blocks.Block;

public class PrintingHitListener implements HitListener {
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A Block with " + beingHit.getHitCount() + " points was hit.");
    }
}
