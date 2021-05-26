package game.objects.indicators;

import biuoop.DrawSurface;
import game.managers.Counter;
import game.objects.GameObject;
import game.objects.sprites.Background;
import game.runners.GameLevel;
import geometry.Point;

import java.awt.Color;

/**
 * Abstract Indicator is a base class for other indicators. It is a game object that can be drawn
 * on a {@link DrawSurface} and indicates a certain factor of the game such as score, remaining lives ,etc.
 */
public abstract class AbstractIndicator implements GameObject {
    /**
     * The constant FONT_SIZE.
     */
    private static final int FONT_SIZE = 16;
    /**
     * The Background.
     */
    private final Background background;
    /**
     * The value of the indicated game factor.
     */
    private Counter value;
    /**
     * The Font size.
     */
    private int fontSize;

    /**
     * Instantiates a new Abstract indicator.
     *
     * @param topLeft the top left corner of the Indicator
     * @param width   the width of the Indicator
     * @param height  the height of the Indicator
     * @param value   the initial value of the Indicator
     */
    public AbstractIndicator(Point topLeft, int width, int height, Counter value) {
        this(new Background(topLeft, new Point(topLeft.getX() + width, topLeft.getY() + height), Color.LIGHT_GRAY),
                value);
    }

    /**
     * Instantiates a new Abstract indicator.
     *
     * @param background the background of the Indicator
     * @param value      the initial value of the Indicator
     */
    public AbstractIndicator(Background background, Counter value) {
        this(background, value, FONT_SIZE);
    }

    /**
     * Instantiates a new Abstract indicator.
     *
     * @param background the background of the Indicator
     * @param value      the initial value of the Indicator
     * @param fontSize   the font size of the Indicator
     */
    public AbstractIndicator(Background background, Counter value, int fontSize) {
        this.background = background;
        this.value = value;
        this.fontSize = fontSize;
    }

    /**
     * Gets the font size of this indicator.
     *
     * @return the font size
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * Sets font size.
     *
     * @param size the font size
     */
    public void setFontSize(int size) {
        this.fontSize = size;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getValue() {
        return this.value.getValue();
    }

    /**
     * Gets the text of the indicator.
     *
     * @return the text
     */
    public abstract String getText();

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(Color color) {
        this.background.setColor(color);
    }

    @Override
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }

    @Override
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
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
