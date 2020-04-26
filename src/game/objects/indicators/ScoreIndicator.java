package game.objects.indicators;

import game.managers.Counter;
import game.objects.sprites.Background;
import geometry.Point;

public class ScoreIndicator extends AbstractIndicator {
    private static final String TEXT = "Score : ";

    public ScoreIndicator(Point topLeft, int width, int height, Counter value) {
        super(topLeft, width, height, value);
    }

    public ScoreIndicator(Background background, Counter value) {
        super(background, value);
    }

    public ScoreIndicator(Background background, Counter value, int fontSize) {
        super(background, value, fontSize);
    }

    @Override
    public String getText() {
        return TEXT + super.getValue();
    }
}
