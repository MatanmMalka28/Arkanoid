package game.objects.collections;

import game.objects.Collidable;
import game.objects.dataStructers.CollisionInfo;
import geometry.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Game environment.
 */
public class GameEnvironment {
    /**
     * The Collidables.
     */
    private List<Collidable> collidables;

    /**
     * Instantiates a new Game environment.
     *
     * @param collidables the collidables
     */
    public GameEnvironment(List<Collidable> collidables) {
        this.collidables = new ArrayList<>(collidables);
    }

    /**
     * Instantiates a new Game environment.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Instantiates a new Game environment.
     *
     * @param gameEnvironment the game environment
     */
    private GameEnvironment(GameEnvironment gameEnvironment) {
        this.collidables = new ArrayList<>(gameEnvironment.collidables);
    }

    /**
     * Gets collidables.
     *
     * @return the collidables
     */
    public List<Collidable> getCollidables() {
        return new ArrayList<>(this.collidables);
    }

    /**
     * Add collidable.
     *
     * @param c the c
     */
// add the given collidable to the environment.
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Add collidable.
     *
     * @param collidableList the collidables
     */
    public void addCollidable(List<Collidable> collidableList) {
        this.collidables.addAll(collidableList);
    }

    /**
     * Remove collidable.
     *
     * @param c the c
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * Gets closest collision.
     *
     * @param trajectory the trajectory
     * @return the closest collision
     */
// Assume an object moving from line.start() to line.end().
    // If this object will not collide with any of the collidables
    // in this collection, return null. Else, return the information
    // about the closest collision that is going to occur.
    public CollisionInfo getClosestCollision(Line trajectory) {
        List<CollisionInfo> collisionInfoList = new ArrayList<>();
        for (Collidable collidable : this.collidables) {
            CollisionInfo info = collidable.getCollisionInfo(trajectory);
            if (info != null) {
                collisionInfoList.add(info);
            }

        }
        if (collisionInfoList.isEmpty()) {
            return null;
        } else {
            CollisionInfo bestMatch = collisionInfoList.get(0);
            collisionInfoList.remove(0);
            for (CollisionInfo info : collisionInfoList) {
                if (info.distance(trajectory) < bestMatch.distance(trajectory)) {
                    bestMatch = info;
                }
            }
            return bestMatch;
        }
    }


    /**
     * Copy game environment.
     *
     * @return the game environment
     */
    public GameEnvironment copy() {
        return new GameEnvironment(this);
    }
}
