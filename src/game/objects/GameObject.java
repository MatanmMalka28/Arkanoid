package game.objects;

import game.objects.sprites.Sprite;
import game.runners.Game;

public interface GameObject extends Sprite {

    void removeFromGame(Game game);

    void addToGame(Game game);
}
