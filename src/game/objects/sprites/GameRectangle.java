package game.objects.sprites;


import biuoop.DrawSurface;
import game.objects.blocks.Block;
import geometry.Rectangle;

import java.awt.*;

public class GameRectangle implements Sprite {
    private Block rect;

    public GameRectangle(Block rect) {
        this.rect = rect;
    }

    /**
     * Gets an exact copy of the rectangle defining this block.
     *
     * @return the rectangle defining this block.
     */
    public Rectangle getRectangle() {
        return rect.getRectangle();
    }

    /**
     * Gets the color of this block.
     *
     * @return the color of this block
     */
    public Color getColor() {
        return rect.getColor();
    }

    @Override
    public void drawOn(DrawSurface d) {
        rect.drawOn(d);
    }

    @Override
    public void timePassed() {
        rect.timePassed();
    }
}
