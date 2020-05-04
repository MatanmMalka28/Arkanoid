package game.objects.indicators;

import biuoop.DrawSurface;
import game.managers.Counter;
import game.objects.sprites.Background;
import geometry.Point;


/**
 * Score Indicator is a concrete sub class of AbstractIndicator. It is a game object that can be drawn
 * on a {@link DrawSurface} and indicates a the game score.
 */
public class ScoreIndicator extends AbstractIndicator {
    /**
     * The constant TEXT.
     */
    private static final String TEXT = "Score : ";

    /**
     * Instantiates a new Score indicator.
     *
     * @param topLeft the top left corner of the Indicator
     * @param width   the width of the Indicator
     * @param height  the height of the Indicator
     * @param value   the initial value of the Indicator
     */
    public ScoreIndicator(Point topLeft, int width, int height, Counter value) {
        super(topLeft, width, height, value);
    }

    /**
     * Instantiates a new Score indicator.
     *
     * @param background the background of the Indicator
     * @param value      the initial value of the Indicator
     */
    public ScoreIndicator(Background background, Counter value) {
        super(background, value);
    }

    /**
     * Instantiates a new Score indicator.
     *
     * @param background the background of the Indicator
     * @param value      the initial value of the Indicator
     * @param fontSize   the font size of the Indicator
     */
    public ScoreIndicator(Background background, Counter value, int fontSize) {
        super(background, value, fontSize);
    }

    @Override
    public String getText() {
        return TEXT + super.getValue();
    }
}
