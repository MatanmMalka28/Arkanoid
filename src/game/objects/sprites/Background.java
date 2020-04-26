package game.objects.sprites;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

public class Background implements Sprite {
    private Rectangle rectangle;
    private Color color;

    public Background(Point topLeft, Point bottomRight, Color color) {
        this(new Rectangle(topLeft, bottomRight), color);
    }

    public Background(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    public int width() {
        return ((int) rectangle.width());
    }

    public int height() {
        return ((int) rectangle.height());
    }

    @Override
    public void drawOn(DrawSurface d) {
        Rectangle.fillRect(this.rectangle, d, this.color);
    }

    public Point getTopLeft() {
        return rectangle.getTopLeft();
    }

    @Override
    public void timePassed() {
        //nothing
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public static void drawBackgroundEdges(Background background, DrawSurface d, Color color) {
        Rectangle.drawEdges(background.rectangle, d, color);
    }
}
