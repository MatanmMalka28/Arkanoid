package game.objects;

import game.objects.dataStructers.CollisionInfo;
import game.objects.attributes.Velocity;
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
     * Hit velocity.
     *
     * @param collisionInfo   the collision info
     * @param currentVelocity the current velocity
     * @return the velocity
     */
    Velocity hit(CollisionInfo collisionInfo, Velocity currentVelocity);

    Velocity hit(Point collisionPoint, Velocity currentVelocity);

    /**
     * Gets collision info.
     *
     * @param trajectory the trajectory
     * @return the collision info
     */
    CollisionInfo getCollisionInfo(Line trajectory);

    Direction getHitDirection(Point hit);
}
