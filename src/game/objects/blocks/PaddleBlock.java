package game.objects.blocks;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;

import java.awt.*;

public class PaddleBlock extends Block {

    public PaddleBlock(Rectangle rectangle) {
        super(rectangle);
    }

    public PaddleBlock(Rectangle rectangle, Color color) {
        super(rectangle, color);
    }

    public PaddleBlock(Point topLeft, Point bottomRight) {
        super(topLeft, bottomRight);
    }

    public PaddleBlock(Point topLeft, Point bottomRight, Color color) {
        super(topLeft, bottomRight, color);
    }

    public PaddleBlock(Point topLeft, int width, int height, Color color) {
        super(topLeft, width, height, color);
    }

    public PaddleBlock(Point topLeft, int width, int height) {
        super(topLeft, width, height);
    }

    public PaddleBlock(Point topLeft, int width, int height, Color color, int hitCount) {
        super(topLeft, width, height, color, hitCount);
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
