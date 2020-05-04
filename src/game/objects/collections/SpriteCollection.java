package game.objects.collections;

import biuoop.DrawSurface;
import game.objects.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;


/**
 * Sprite Collection is a container that holds all the {@link Sprite} objects.
 * On top of its uses as a container this object can notify all sprites that time has passed.
 * Given a {@link DrawSurface} Sprite Collection can call {@link Sprite#drawOn(DrawSurface)} on all sprites.
 */
public class SpriteCollection {
    /**
     * The Sprite container.
     */
    private final List<Sprite> spriteList;

    /**
     * Instantiates a new Sprite collection.
     */
    public SpriteCollection() {
        this.spriteList = new ArrayList<>();
    }

    /**
     * Instantiates a new Sprite collection with a given list of sprites.
     *
     * @param spriteList this sprites are inserted to the SpriteCollection's container
     */
    public SpriteCollection(List<Sprite> spriteList) {
        this();
        this.addSprite(spriteList);
    }

    /**
     * Gets a copy of the sprites container as a {@link List}.
     *
     * @return a copy of the sprites container.
     */
    public List<Sprite> getSpriteList() {
        return new ArrayList<>(this.spriteList);
    }

    /**
     * Add a single sprite to the sprites container.
     *
     * @param s the sprite to be added
     */
    public void addSprite(Sprite s) {
        this.spriteList.add(s);
    }

    /**
     * Add a list of sprites.
     *
     * @param sprites the sprites list
     */
    public void addSprite(List<Sprite> sprites) {
        this.spriteList.addAll(sprites);
    }

    /**
     * Removes the first occurrence of the specified element from the container.
     * if it is present (optional operation). If this container does not contain the element, it is unchanged.
     *
     * @param s the s
     */
    public void removeSprite(Sprite s) {
        this.spriteList.remove(s);
    }

    /**
     * Notify all time passed.
     */
// call timePassed() on all sprites.
    public void notifyAllTimePassed() {
        List<Sprite> tempSpriteList = new ArrayList<>(this.spriteList);
        for (Sprite sprite : tempSpriteList) {
            sprite.timePassed();
        }
    }

    /**
     * Calls {@link Sprite#drawOn(DrawSurface)} on all sprites.
     *
     * @param d the draw surface to draw on
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : this.spriteList) {
            sprite.drawOn(d);
        }
    }


}
