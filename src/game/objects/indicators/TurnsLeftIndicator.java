package game.objects.indicators;

import biuoop.DrawSurface;
import game.managers.Counter;
import game.objects.sprites.Background;
import geometry.Point;

/**
 * Turns Left Indicator is a concrete sub class of AbstractIndicator. It is a game object that can be drawn
 * on a {@link DrawSurface} and indicates a the remaining lives.
 */
public class TurnsLeftIndicator extends AbstractIndicator {
    /**
     * The constant TURNS_MESSAGE.
     */
    private static final String TURNS_MESSAGE = "Turns left : ";
    /**
     * The constant LAST_TURN_MESSAGE.
     */
    private static final String LAST_TURN_MESSAGE = "Turns left: Last Turn";

    /**
     * Instantiates a new Turns left indicator.
     *
     * @param topLeft the top left corner of the Indicator
     * @param width   the width of the Indicator
     * @param height  the height of the Indicator
     * @param value   the initial value of the Indicator
     */
    public TurnsLeftIndicator(Point topLeft, int width, int height, Counter value) {
        super(topLeft, width, height, value);
    }

    /**
     * Instantiates a new Turns left indicator.
     *
     * @param background the background of the Indicator
     * @param value      the initial value of the Indicator
     */
    public TurnsLeftIndicator(Background background, Counter value) {
        super(background, value);
    }

    /**
     * Instantiates a new Turns left indicator.
     *
     * @param background the background of the Indicator
     * @param value      the initial value of the Indicator
     * @param fontSize   the font size of the Indicator
     */
    public TurnsLeftIndicator(Background background, Counter value, int fontSize) {
        super(background, value, fontSize);
    }

    @Override
    public String getText() {
        String text;
        if (super.getValue() > 0) {
            text = TURNS_MESSAGE + super.getValue();
        } else {
            text = LAST_TURN_MESSAGE;
        }
        return text;
    }
}
