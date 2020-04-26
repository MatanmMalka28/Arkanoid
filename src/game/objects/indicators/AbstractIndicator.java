package game.objects.indicators;

import biuoop.DrawSurface;
import game.managers.Counter;
import game.objects.sprites.Background;
import game.objects.GameObject;
import game.runners.Game;
import geometry.Point;

import java.awt.*;

public abstract class AbstractIndicator implements GameObject {
    private static final int FONT_SIZE = 16;
    private final Background background;
    private Counter value;
    private int fontSize;

    public AbstractIndicator(Point topLeft, int width, int height, Counter value) {
        this(new Background(topLeft, new Point(topLeft.getX() + width, topLeft.getY() + height), Color.LIGHT_GRAY), value);
    }

    public AbstractIndicator(Background background, Counter value) {
        this(background, value, FONT_SIZE);
    }

    public AbstractIndicator(Background background, Counter value, int fontSize) {
        this.background = background;
        this.value = value;
        this.fontSize = fontSize;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getValue() {
        return this.value.getValue();
    }

    public abstract String getText();

    public void setColor(Color color) {
        this.background.setColor(color);
    }

    @Override
    public void removeFromGame(Game game) {
        game.removeSprite(this);
    }

    @Override
    public void addToGame(Game game) {
        game.addSprite(this);
    }

    @Override
    public void drawOn(DrawSurface d) {
        this.background.drawOn(d);
        Background.drawBackgroundEdges(this.background, d, Color.BLACK);
        d.setColor(Color.BLACK);
        String text = this.getText();
        int x = ((int) (this.background.getTopLeft().getX() + this.background.width() / 2 - text.length() * 4));
        int y = ((int) (this.background.height() * 0.75));
        d.drawText(x, y, text, this.fontSize);

    }

    @Override
    public void timePassed() {
        //do nothing
    }
}
