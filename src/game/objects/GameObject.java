package game.objects;

import game.objects.sprites.Sprite;
import game.runners.Game;

/**
 * The interface Game object.
 */
public interface GameObject extends Sprite {

    /**
     * Remove from game.
     *
     * @param game the game
     */
    void removeFromGame(Game game);

    /**
     * Add to game.
     *
     * @param game the game
     */
    void addToGame(Game game);
}
