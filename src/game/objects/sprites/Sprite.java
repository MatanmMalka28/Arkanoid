package game.objects.sprites;

import biuoop.DrawSurface;

/**
 * The Sprite interface.
 */
public interface Sprite {
    /**
     * Draw the sprite to the screen.
     *
     * @param d the drawing surface to draw on
     */
    void drawOn(DrawSurface d);


    /**
     * Notify the sprite that time has passed.
     */
    void timePassed();
}
