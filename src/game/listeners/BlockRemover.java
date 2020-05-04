package game.listeners;

import game.managers.Counter;
import game.objects.Ball;
import game.objects.blocks.Block;
import game.runners.Game;


/**
 * a BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {

    /**
     * The Game this BlockRemover is linked to.
     */
    private Game game;

    /**
     * keeps track on the amount of blocks left in the game.
     */
    private Counter remainingBlocks;


    /**
     * Instantiates a new Block remover.
     *
     * @param game   the game
     * @param number the number
     */
    public BlockRemover(Game game, int number) {
        this(game, new Counter(number));
    }

    /**
     * Instantiates a new Block remover.
     *
     * @param game            the game
     * @param remainingBlocks the remaining blocks
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }


    public void hitEvent(Block beingHit, Ball hitter) {
        /*
        Blocks that are hit and reach 0 hit-points should be removed
        from the game.
         */
        if (beingHit.getHitCount() == 0) {
            beingHit.removeFromGame(this.game);
            // remove this listener from the block that is being removed from the game.
            beingHit.removeHitListener(this);
            this.remainingBlocks.decrease(1);
            hitter.setGameEnvironment(this.game.getEnvironment());
        }

    }
}