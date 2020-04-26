package game.listeners;

import game.managers.Counter;
import game.objects.Ball;
import game.objects.blocks.Block;

public class ScoreTrackingListener implements HitListener {
    private static final int BLOCK_HIT_SCORE = 5;
    private static final int BLOCK_REMOVED_SCORE = 2 * BLOCK_HIT_SCORE;
    private Counter currentScore;

    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    public ScoreTrackingListener(int score) {
        this(new Counter(score));
    }

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
