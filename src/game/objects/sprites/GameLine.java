package game.objects.sprites;

import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;

import java.awt.Color;

public class GameLine implements Sprite{
    private Line line;
    private Color color;

    public GameLine(Line line, Color color) {
        this.line = line;
        this.color = color;
    }

    public GameLine(Point start, Point end, Color color) {
        this(new Line(start,end),color);
    }

    public GameLine (double startX, double startY, double endX, double endY, Color color){
        this(new Point(startX,startY),new Point(endX,endY), color);
    }

    /**
     * Draw on.
     *
     * @param d the d
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        line.drawOn(d);
    }

    /**
     * Notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
        //do nothing
    }
}
