package game.objects;

import game.runners.Game;

public interface GameObject {

    void removeFromGame(Game game);

    void addToGame(Game game);
}
