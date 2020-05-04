package game.listeners;

import game.managers.Counter;
import game.objects.Ball;
import game.objects.blocks.Block;

/**
 * This instance is used to keep track of game scores.
 */
public class ScoreTrackingListener implements HitListener {
    private static final int BLOCK_HIT_SCORE = 5;
    private static final int BLOCK_REMOVED_SCORE = 2 * BLOCK_HIT_SCORE;

    /**
     * The Current score counter.
     */
    private Counter currentScore;

    /**
     * Instantiates a new Score tracking listener.
     *
     * @param scoreCounter the initial score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Instantiates a new Score tracking listener.
     *
     * @param score the initial score
     */
    public ScoreTrackingListener(int score) {
        this(new Counter(score));
    }

    /**
     * Instantiates a new Score tracking listener.
     */
    public ScoreTrackingListener() {
        this(new Counter());
    }

    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitCount() > 0) {
            this.currentScore.increase(BLOCK_HIT_SCORE);
        } else if (beingHit.getHitCount() == 0) {
            this.currentScore.increase(BLOCK_REMOVED_SCORE);
        }
    }
}
