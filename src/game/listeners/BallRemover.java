package game.listeners;

import game.objects.Ball;
import game.objects.blocks.Block;
import game.runners.GameLevel;

/**
 * The type Ball remover.
 */
public class BallRemover implements HitListener {
    /**
     * The Game.
     */
    private GameLevel gameLevel;

    /**
     * Instantiates a new Ball remover.
     *
     * @param gameLevel the game
     */
    public BallRemover(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.gameLevel);
        this.gameLevel.addNewBall(hitter.getRadius());
    }
}
