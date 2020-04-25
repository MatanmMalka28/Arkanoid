package game.listeners;

import game.managers.Counter;
import game.objects.Ball;
import game.objects.Block;
import game.runners.Game;

// a BlockRemover is in charge of removing blocks from the game, as well as keeping count
// of the number of blocks that remain.
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    public BlockRemover(Game game, int number) {
        this(game, new Counter(number));
    }

    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    // Blocks that are hit and reach 0 hit-points should be removed
    // from the game. Remember to remove this listener from the block
    // that is being removed from the game.
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeFromGame(this.game);
            beingHit.removeHitListener(this);
            this.remainingBlocks.decrease(1);
            hitter.setGameEnvironment(this.game.getEnvironment());
        }

    }
}