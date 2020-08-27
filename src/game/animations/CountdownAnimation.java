package game.animations;

import biuoop.DrawSurface;
import game.objects.collections.SpriteCollection;
import game.objects.sprites.GameCircle;
import geometry.Point;

import java.awt.Color;

public class CountdownAnimation implements Animatable {
    private static final Color DEFAULT_COLOR = new Color(0xFFFFFF);
    private static final String GO_MESSAGE = "GO";

    private Integer countFrom;
    private SpriteCollection sprites;
    private final long startTime;
    private final double timeUnit;
    private double timePassed;
    private final GameCircle shape;

    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection sprites, Point textPosition) {
        this.countFrom = countFrom;
        this.sprites = sprites;
        this.countFrom = countFrom;
        this.sprites = sprites;
        this.timeUnit = (numOfSeconds * 1000) / (this.countFrom + 1);
        this.timePassed = timeUnit;
        //todo make topLeft & bottomRight static
        this.shape = new GameCircle(textPosition, 50, Color.WHITE);
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Draw count from.
     *
     * @param drawSurface the draw surface
     */
    private void drawCountFrom(DrawSurface drawSurface) {
        if (this.countFrom > 0) {
            this.shape.drawTextSelf(drawSurface, this.countFrom.toString(), 50, Color.BLACK);
        } else {
            this.shape.drawTextSelf(drawSurface, GO_MESSAGE, 50, Color.BLACK);
        }
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        long t = System.currentTimeMillis() - this.startTime;
        if (System.currentTimeMillis() - this.startTime >= this.timePassed) {
            this.timePassed += this.timeUnit;
            this.countFrom--;
        }
        this.sprites.drawAllOn(d);
        this.shape.drawOn(d);
        this.drawCountFrom(d);
    }

    @Override
    public boolean shouldStop() {
        return this.countFrom < 0;
    }
}
