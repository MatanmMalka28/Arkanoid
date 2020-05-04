package game.objects.sprites;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

/**
 * The type Background.
 */
public class Background implements Sprite {
    /**
     * The Rectangle.
     */
    private Rectangle rectangle;
    /**
     * The Color.
     */
    private Color color;

    /**
     * Instantiates a new Background.
     *
     * @param topLeft     the top left
     * @param bottomRight the bottom right
     * @param color       the color
     */
    public Background(Point topLeft, Point bottomRight, Color color) {
        this(new Rectangle(topLeft, bottomRight), color);
    }

    /**
     * Instantiates a new Background.
     *
     * @param rectangle the rectangle
     * @param color     the color
     */
    public Background(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    /**
     * Gets top left.
     *
     * @return the top left
     */
    public Point getTopLeft() {
        return rectangle.getTopLeft();
    }

    /**
     * Sets color.
     *
     * @param c the color
     */
    public void setColor(Color c) {
        this.color = c;
    }

    @Override
    public void drawOn(DrawSurface d) {
        Rectangle.fillRect(this.rectangle, d, this.color);
    }

    /**
     * Width int.
     *
     * @return the int
     */
    public int width() {
        return ((int) rectangle.width());
    }

    @Override
    public void timePassed() {
        //nothing
    }

    /**
     * Height int.
     *
     * @return the int
     */
    public int height() {
        return ((int) rectangle.height());
    }

    /**
     * Draw background edges.
     *
     * @param background the background
     * @param d          the d
     * @param color      the color
     */
    public static void drawBackgroundEdges(Background background, DrawSurface d, Color color) {
        Rectangle.drawEdges(background.rectangle, d, color);
    }
}
