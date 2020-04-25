package game.objects;

import biuoop.DrawSurface;
import game.runners.Game;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

public class BorderBlock extends Block {
    private static final Color BORDER_COLOR = Color.GRAY;
    private static final int BORDER_HIT_COUNT = -1;

    public BorderBlock(Point topLeft, Point bottomRight) {
        this(new Rectangle(topLeft, bottomRight));
    }

    public BorderBlock(Rectangle rectangle) {
        super(rectangle, BORDER_COLOR, BORDER_HIT_COUNT);
    }

    public BorderBlock(Point topLeft, int width, int height) {
        this(new Rectangle(topLeft, width, height));
    }

    @Override
    public void removeFromGame(Game game) {
        //do nothing
    }

    @Override
    protected void decreaseHit() {
        //do nothing
    }

    @Override
    protected void drawText(DrawSurface d) {
        //do nothing
    }
}
