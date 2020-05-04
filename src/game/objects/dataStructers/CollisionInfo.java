package game.objects.dataStructers;

import game.objects.Collidable;
import geometry.Line;
import geometry.Point;
import utilities.Direction;

import java.util.Objects;

/**
 * Collision info is an in-game data structure that contains all the information needed on a collision between two
 * objects such that it's user can decide how to act.
 */
public class CollisionInfo implements Comparable<CollisionInfo> {
    /**
     * The Collision point.
     */
    private Point collisionPoint;
    /**
     * The Collision object.
     */
    private Collidable collisionObject;
    /**
     * The Hit direction.
     */
    private Direction hitDirection;


    /**
     * Instantiates a new Collision info.
     *
     * @param collisionPoint  the collision point
     * @param collisionObject the collision object
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
        this.hitDirection = this.collisionObject.getCollisionRectangle().pointOnEdge(this.collisionPoint);
    }

    /**
     * Gets collision point.
     *
     * @return the collision point
     */
    public Point getCollisionPoint() {
        return this.collisionPoint;
    }

    /**
     * Gets collision object.
     *
     * @return the collision object
     */
    public Collidable getCollisionObject() {
        return this.collisionObject;
    }

    /**
     * Gets hit direction.
     *
     * @return the hit direction
     */
    public Direction getHitDirection() {
        return this.hitDirection;
    }

    /**
     * Calculates the distance between the collision point to the start point of a given line.
     *
     * @param trajectory the line
     * @return the distance between the collision point to the start point of the trajectory
     */
    public double distance(Line trajectory) {
        return this.collisionPoint.distance(trajectory.start());
    }

    /**
     * Checks if this Collision Info is equal to the other Collision Info.
     *
     * @param other the other collision info
     * @return returns true if and only if both Collision Infos has the same collision object and
     * their hit directions are parallel.
     */
    public boolean equals(CollisionInfo other) {
        if (other != null) {
            return this.collisionObject.equals(other.collisionObject)
                    && this.hitDirection.directionParallel(other.hitDirection);
        }
        return false;
    }

    @Override
    public int compareTo(CollisionInfo o) {
        if (this.equals(o)) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(collisionObject);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CollisionInfo)) {
            return false;
        }
        CollisionInfo info = (CollisionInfo) o;
        return this.equals(info);
    }
}
