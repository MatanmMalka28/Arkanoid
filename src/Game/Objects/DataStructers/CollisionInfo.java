package Game.Objects.DataStructers;

import Game.Objects.Collidable;
import Geometry.Line;
import Geometry.Point;
import Geometry.Rectangle;
import Utilities.Direction;

public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;
    private Direction hitDirection;


    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
        this.hitDirection = this.collisionObject.getCollisionRectangle().pointOnEdge(this.collisionPoint);
    }

    public double distance(Line trajectory){
        return this.collisionPoint.distance(trajectory.start());
    }

    public Point getCollisionPoint() {
        return this.collisionPoint;
    }

    public Collidable getCollisionObject() {
        return this.collisionObject;
    }

    public Direction getHitDirection() {
        return this.hitDirection;
    }
}
