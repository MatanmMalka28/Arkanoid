package game.objects;

import game.objects.attributes.Velocity;
import game.objects.dataStructers.CollisionInfo;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import utilities.Direction;

/**
 * The interface Collidable.
 */
public interface Collidable {
    /**
     * Gets collision rectangle.
     *
     * @return the collision rectangle
     */
// Return the "collision shape" of the object.
    Rectangle getCollisionRectangle();

    /**
     * This method takes a number of factors into consideration and decide's on the ball's new velocity.
     *
     * @param hitter          the ball which made the hit
     * @param collisionInfo   the collision info
     * @param currentVelocity the current velocity of the ball
     * @return the new velocity based on all factors calculated
     */
    Velocity hit(Ball hitter, CollisionInfo collisionInfo, Velocity currentVelocity);

    /**
     * This method takes a number of factors into consideration and decide's on the ball's new velocity.
     *
     * @param hitter          the hitter
     * @param collisionPoint  the collision point
     * @param currentVelocity the current velocity
     * @return the new velocity based on all factors calculated
     */
//todo check to delete.
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    //todo add hitPerimeter

    /**
     * Gets collision info.
     *
     * @param trajectory the trajectory
     * @return the collision info
     */
    CollisionInfo getCollisionInfo(Line trajectory);

    /**
     * Gets hit direction.
     *
     * @param hit the hit
     * @return the hit direction
     */
    Direction getHitDirection(Point hit);
}
