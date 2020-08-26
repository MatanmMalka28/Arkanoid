package game.animations;

import biuoop.DrawSurface;

public interface Animatable {
    void doOneFrame(DrawSurface d);
    boolean shouldStop();
}
