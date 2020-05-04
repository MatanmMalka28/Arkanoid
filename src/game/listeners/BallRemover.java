package game.listeners;

import game.objects.Ball;
import game.objects.blocks.Block;
import game.runners.Game;

/**
 * The type Ball remover.
 */
public class BallRemover implements HitListener {
    /**
     * The Game.
     */
    private Game game;

    /**
     * Instantiates a new Ball remover.
     *
     * @param game the game
     */
    public BallRemover(Game game) {
        this.game = game;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.game.addNewBall(hitter.getRadius());
    }
}
