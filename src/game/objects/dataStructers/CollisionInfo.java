package game.objects.dataStructers;

import game.objects.Collidable;
import geometry.Line;
import geometry.Point;
import utilities.Direction;

import java.util.Objects;

public class CollisionInfo implements Comparable {
    private Point collisionPoint;
    private Collidable collisionObject;
    private Direction hitDirection;


    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
        this.hitDirection = this.collisionObject.getCollisionRectangle().pointOnEdge(this.collisionPoint);
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

    public double distance(Line trajectory) {
        return this.collisionPoint.distance(trajectory.start());
    }

    @Override
    public int compareTo(Object o) {
        if (this.equals(o)) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean equals(CollisionInfo other) {
        if (other != null) {
            return this.collisionObject.equals(other.collisionObject) &&
                    this.hitDirection.directionParallel(other.hitDirection);
        }
        return false;
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
