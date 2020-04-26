package game.objects.indicators;

import game.managers.Counter;
import game.objects.sprites.Background;
import geometry.Point;

public class TurnsLeftIndicator extends AbstractIndicator {
    private static final String TURNS_MESSAGE = "Turns left : ";
    private static final String LAST_TURN_MESSAGE = "Turns left: Last Turn";

    public TurnsLeftIndicator(Point topLeft, int width, int height, Counter value) {
        super(topLeft, width, height, value);
    }

    public TurnsLeftIndicator(Background background, Counter value) {
        super(background, value);
    }

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
