package game.objects.collections;

import biuoop.DrawSurface;
import game.objects.Sprite;

import java.util.ArrayList;
import java.util.List;

public class SpriteCollection {
    private List<Sprite> spriteList;

    public SpriteCollection() {
        this(new ArrayList<>());
    }

    public SpriteCollection(List<Sprite> spriteList) {
        this.spriteList = spriteList;
    }

    public List<Sprite> getSpriteList() {
        return new ArrayList<>(this.spriteList);
    }

    public void addSprite(Sprite s) {
        this.spriteList.add(s);
    }

    public void addSprite(List<Sprite> sprites) {
        this.spriteList.addAll(sprites);
    }

    public void removeSprite(Sprite s) {
        this.spriteList.remove(s);
    }

    // call timePassed() on all sprites.
    public void notifyAllTimePassed() {
        List<Sprite> tempSpriteList = new ArrayList<>(this.spriteList);
        for (Sprite sprite : tempSpriteList) {
            sprite.timePassed();
        }
    }

    // call drawOn(d) on all sprites.
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : this.spriteList) {
            sprite.drawOn(d);
        }
    }


}
