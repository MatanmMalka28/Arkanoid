package game.objects.collections;

import game.objects.Collidable;
import game.objects.dataStructers.CollisionInfo;
import geometry.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * Game Environment is a container that holds all the {@link Collidable} objects.
 * On top of its uses as a container this object can check for collision and find the closest collision object.
 */
public class GameEnvironment {
    /**
     * The collidables container.
     */
    private List<Collidable> collidables;

    /**
     * Instantiates a new Game environment with a given list of collidables.
     *
     * @param collidables this collidables are inserted to the GameEnvironment container
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
     * Instantiates a new Game environment from a given game environment.
     *
     * @param gameEnvironment the game environment to be copied
     */
    private GameEnvironment(GameEnvironment gameEnvironment) {
        this.collidables = new ArrayList<>(gameEnvironment.collidables);
    }

    /**
     * Gets a copy of the collidables container as a {@link List}.
     *
     * @return a copy of the collidables container
     */
    public List<Collidable> getCollidables() {
        return new ArrayList<>(this.collidables);
    }

    /**
     * Add a single collidable to the container.
     *
     * @param c the collidable to be added to the container
     */
// add the given collidable to the environment.
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Add a {@link List} of collidables to the container.
     *
     * @param collidableList the collidables to be added to the container
     */
    public void addCollidable(List<Collidable> collidableList) {
        this.collidables.addAll(collidableList);
    }

    /**
     * Removes the first occurrence of the specified element from the container.
     * if it is present (optional operation). If this container does not contain the element, it is unchanged.
     *
     * @param c the element to be removed from this list, if present
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * Assume an object moving from {@link Line#start()} to {@link Line#end()}.
     * If this object will not collide with any of the collidables in this collection, return null.
     * Else, return the information about the closest collision that is going to occur.
     *
     * @param trajectory the movement vector of the object that checks for collisions
     * @return the closest collision to the start of the trajectory.
     */
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
     * @return a copy of this game environment
     */
    public GameEnvironment copy() {
        return new GameEnvironment(this);
    }

}
