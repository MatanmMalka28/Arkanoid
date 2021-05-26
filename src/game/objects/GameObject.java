package game.objects;

import game.objects.sprites.Sprite;
import game.runners.GameLevel;

/**
 * The interface Game object.
 */
public interface GameObject extends Sprite {

    /**
     * Remove from game.
     *
     * @param gameLevel the game
     */
    void removeFromGame(GameLevel gameLevel);

    /**
     * Add to game.
     *
     * @param gameLevel the game
     */
    void addToGame(GameLevel gameLevel);
}
