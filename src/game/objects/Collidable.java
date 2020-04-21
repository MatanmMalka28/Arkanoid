package game.objects;

import game.objects.dataStructers.CollisionInfo;
import game.Velocity;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

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
     * @param collisionPoint  the collision point
     * @param currentVelocity the current velocity
     * @return the velocity
     */
// Notify the object that we collided with it at collisionPoint with
    // a given velocity.
    // The return is the new velocity expected after the hit (based on
    // the force the object inflicted on us).
    Velocity hit(Point collisionPoint, Velocity currentVelocity);

    /**
     * Hit velocity.
     *
     * @param collisionInfo   the collision info
     * @param currentVelocity the current velocity
     * @return the velocity
     */
    Velocity hit(CollisionInfo collisionInfo, Velocity currentVelocity);

    /**
     * Gets collision info.
     *
     * @param trajectory the trajectory
     * @return the collision info
     */
    CollisionInfo getCollisionInfo(Line trajectory);
}
