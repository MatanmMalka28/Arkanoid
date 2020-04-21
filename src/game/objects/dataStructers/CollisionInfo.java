package game.objects.dataStructers;

import game.objects.Collidable;
import geometry.Line;
import geometry.Point;
import utilities.Direction;

/**
 * The type Collision info.
 */
public class CollisionInfo {
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
     * Distance double.
     *
     * @param trajectory the trajectory
     * @return the double
     */
    public double distance(Line trajectory) {
        return this.collisionPoint.distance(trajectory.start());
    }
}
