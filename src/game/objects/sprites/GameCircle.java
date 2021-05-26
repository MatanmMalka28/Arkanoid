package game.objects.sprites;

import biuoop.DrawSurface;
import geometry.Circle;
import geometry.Point;

import java.awt.Color;

public class GameCircle implements Sprite {
    private final Circle circle;
    private final Color color;

    public GameCircle(Circle circle, Color color) {
        this.circle = circle;
        this.color = color;
    }

    public GameCircle(Point center, double radius, Color color) {
        this(new Circle(center, radius), color);
    }

    public Circle getCircle() {
        return circle;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillCircle(((int) this.circle.getCenter().getX()), ((int) this.circle.getCenter().getY()), ((int) this.circle.getRadius()));
        d.setColor(Color.BLACK);
        d.drawCircle(((int) this.circle.getCenter().getX()), ((int) this.circle.getCenter().getY()), ((int) this.circle.getRadius()));
    }

    public void drawTextSelf(DrawSurface d, String string, int fontSize,Color textColor){
        d.setColor(textColor);
        if (string.length() <= 1) {
            d.drawText(((int) this.circle.getCenter().getX()) - 12, ((int) this.circle.getCenter().getY()) + 10, string, fontSize);
        } else {
            d.drawText(((int) this.circle.getCenter().getX()) - 38, ((int) this.circle.getCenter().getY()) + 15, string, fontSize);
        }
    }

    @Override
    public void timePassed() {
        //nothing
    }
}
