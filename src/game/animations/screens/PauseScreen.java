package game.animations.screens;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.animations.Animatable;

public class PauseScreen implements Animatable {
    private final KeyboardSensor keyboard;
    private boolean stop = false;

    public PauseScreen(KeyboardSensor keyboard) {
        this.keyboard = keyboard;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(d.getWidth() / 6, d.getHeight() / 2, "Game paused -- press space to continue", 32);
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
