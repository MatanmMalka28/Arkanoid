package game.listeners;

import game.managers.Counter;
import game.objects.Ball;
import game.objects.blocks.Block;
import game.runners.GameLevel;


/**
 * a BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {

    /**
     * The Game this BlockRemover is linked to.
     */
    private GameLevel gameLevel;

    /**
     * keeps track on the amount of blocks left in the game.
     */
    private Counter remainingBlocks;


    /**
     * Instantiates a new Block remover.
     *
     * @param gameLevel   the game
     * @param number the number
     */
    public BlockRemover(GameLevel gameLevel, int number) {
        this(gameLevel, new Counter(number));
    }

    /**
     * Instantiates a new Block remover.
     *
     * @param gameLevel            the game
     * @param remainingBlocks the remaining blocks
     */
    public BlockRemover(GameLevel gameLevel, Counter remainingBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = remainingBlocks;
    }


    public void hitEvent(Block beingHit, Ball hitter) {
        /*
        Blocks that are hit and reach 0 hit-points should be removed
        from the game.
         */
        if (beingHit.getHitCount() == 0) {
            beingHit.removeFromGame(this.gameLevel);
            // remove this listener from the block that is being removed from the game.
            beingHit.removeHitListener(this);
            this.remainingBlocks.decrease(1);
            hitter.setGameEnvironment(this.gameLevel.getEnvironment());
        }

    }
}