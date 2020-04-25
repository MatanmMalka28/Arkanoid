package game.listeners;

import game.managers.Counter;
import game.objects.Ball;
import game.objects.Block;
import game.runners.Game;

public class BallRemover implements HitListener {
    private Game game;

    public BallRemover(Game game) {
        this.game = game;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.game.addNewBall(hitter.getRadius());
    }
}
