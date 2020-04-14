package Game.Objects;

import Game.Objects.DataStructers.CollisionInfo;
import Game.Velocity;
import Geometry.Line;
import Geometry.Point;
import Geometry.Rectangle;
import Utilities.Direction;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.List;
import java.util.Objects;

public class Block implements Collidable {
    private static final Color DEFAULT_COLOR = Color.gray;

    private Rectangle rectangle;
    private Color color;

    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    public Block(Rectangle rectangle) {
        this(rectangle, DEFAULT_COLOR);
    }

    public Block(Point topLeft, Point bottomRight) {
        this(new Rectangle(topLeft, bottomRight), DEFAULT_COLOR);
    }

    public Block(Point topLeft, Point bottomRight, Color color) {
        this(new Rectangle(topLeft, bottomRight), color);
    }

    public Block(Point topLeft, int width, int height, Color color) {
        this(new Rectangle(topLeft, width, height), color);
    }

    public Block(Point topLeft, int width, int height) {
        this(new Rectangle(topLeft, width, height), DEFAULT_COLOR);
    }

    public Rectangle getRectangle() {
        return this.rectangle.copy();
    }

    public Color getColor() {
        return this.color;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle.copy();
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        return this.changeVelocity(this.rectangle.pointOnEdge(collisionPoint),currentVelocity);
    }

    @Override
    public Velocity hit(CollisionInfo collisionInfo, Velocity currentVelocity) {
        if (this.equals(collisionInfo.getCollisionObject())){
            return this.changeVelocity(collisionInfo.getHitDirection(),currentVelocity);
        } else {
            return this.hit(collisionInfo.getCollisionPoint(),currentVelocity);
        }
    }

    private Velocity changeVelocity(Direction edge, Velocity currentVelocity){
        Velocity newVelocity;
        switch (edge){
            case TOP:
            case BOTTOM:
                newVelocity = currentVelocity.changeSign(1,-1);
                break;
            case LEFT:
            case RIGHT:
                newVelocity = currentVelocity.changeSign(1,-1);
                break;
            case BOTH:
                newVelocity = currentVelocity.changeSign(-1,-1);
                break;
            case NONE:
                default:
                    newVelocity = currentVelocity.copy();
                    break;
        }
        return newVelocity;
    }

    @Override
    public CollisionInfo getCollisionInfo(Line trajectory) {
        List<Point> intersectionPoints = this.rectangle.intersectionPoints(trajectory);
        if (intersectionPoints.isEmpty()){
            return null;
        } else {
            Point closestPoint = intersectionPoints.get(0);
            intersectionPoints.remove(0);
            for (Point intersection:intersectionPoints){
                if (intersection.distance(trajectory.start())<closestPoint.distance(trajectory.start())){
                    closestPoint = intersection;
                }
            }
            return new CollisionInfo(closestPoint,this);
        }
    }

    public boolean equals(Block other) {
        return this.rectangle.equals(other.rectangle) && this.color.equals(other.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rectangle, color);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Block)) return false;

        Block block = (Block) o;
        return this.equals(block);
    }

    public void drawOn(DrawSurface d){
        d.setColor(this.color);
        d.fillRectangle(((int) this.rectangle.getTopLeft().getX()), ((int) this.rectangle.getTopLeft().getY()),
                ((int) this.rectangle.getWidth()), ((int) this.rectangle.getHeight()));
        d.setColor(Color.black);
        d.drawRectangle(((int) this.rectangle.getTopLeft().getX()), ((int) this.rectangle.getTopLeft().getY()),
                ((int) this.rectangle.getWidth()), ((int) this.rectangle.getHeight()));

    }
}
