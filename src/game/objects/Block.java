package game.objects;

import game.objects.dataStructers.CollisionInfo;
import game.Velocity;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import utilities.Direction;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The type Block.
 */
public class Block implements Collidable {
    /**
     * The constant DEFAULT_COLOR.
     */
    private static final Color DEFAULT_COLOR = Color.gray;

    /**
     * The Rectangle.
     */
    private Rectangle rectangle;
    /**
     * The Color.
     */
    private Color color;

    /**
     * Instantiates a new Block.
     *
     * @param rectangle the rectangle
     */
    public Block(Rectangle rectangle) {
        this(rectangle, DEFAULT_COLOR);
    }

    /**
     * Instantiates a new Block.
     *
     * @param rectangle the rectangle
     * @param color     the color
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    /**
     * Instantiates a new Block.
     *
     * @param topLeft     the top left
     * @param bottomRight the bottom right
     */
    public Block(Point topLeft, Point bottomRight) {
        this(new Rectangle(topLeft, bottomRight), DEFAULT_COLOR);
    }

    /**
     * Instantiates a new Block.
     *
     * @param topLeft     the top left
     * @param bottomRight the bottom right
     * @param color       the color
     */
    public Block(Point topLeft, Point bottomRight, Color color) {
        this(new Rectangle(topLeft, bottomRight), color);
    }

    /**
     * Instantiates a new Block.
     *
     * @param topLeft the top left
     * @param width   the width
     * @param height  the height
     * @param color   the color
     */
    public Block(Point topLeft, int width, int height, Color color) {
        this(new Rectangle(topLeft, width, height), color);
    }

    /**
     * Instantiates a new Block.
     *
     * @param topLeft the top left
     * @param width   the width
     * @param height  the height
     */
    public Block(Point topLeft, int width, int height) {
        this(new Rectangle(topLeft, width, height), DEFAULT_COLOR);
    }

    /**
     * Gets rectangle.
     *
     * @return the rectangle
     */
    public Rectangle getRectangle() {
        return this.rectangle.copy();
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
        return this.color;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle.copy();
    }

    /**
     * Draw on.
     *
     * @param d the d
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle(((int) this.rectangle.getTopLeft().getX()), ((int) this.rectangle.getTopLeft().getY()),
                ((int) this.rectangle.getWidth()), ((int) this.rectangle.getHeight()));
        d.setColor(Color.black);
        d.drawRectangle(((int) this.rectangle.getTopLeft().getX()), ((int) this.rectangle.getTopLeft().getY()),
                ((int) this.rectangle.getWidth()), ((int) this.rectangle.getHeight()));

    }

    @Override
    public int hashCode() {
        return Objects.hash(rectangle, color);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Block)) {
            return false;
        }

        Block block = (Block) o;
        return this.equals(block);
    }

    /**
     * Equals boolean.
     *
     * @param other the other
     * @return the boolean
     */
    public boolean equals(Block other) {
        return this.rectangle.equals(other.rectangle) && this.color.equals(other.color);
    }

    @Override
    public Velocity hit(CollisionInfo collisionInfo, Velocity currentVelocity) {
        Direction direction = this.getHitDirection(collisionInfo.getCollisionPoint());
        Velocity v;
        switch (direction) {
            case TOP:
            case BOTTOM:
                v = currentVelocity.changeSign(1, -1);
                break;
            case LEFT:
            case RIGHT:
                v = currentVelocity.changeSign(-1, 1);
                break;
            case BOTH:
                v = currentVelocity.changeSign(-1, -1);
                break;
            case NONE:
            default:
                v = currentVelocity.copy();
                break;
        }
        return v;
    }


    @Override
    public CollisionInfo getCollisionInfo(Line trajectory) {
        List<Point> intersectionPoints = this.rectangle.intersectionPoints(trajectory);
        if (intersectionPoints.isEmpty()) {
            return null;
        } else {
            Point closestPoint = intersectionPoints.get(0);
            intersectionPoints.remove(0);
            for (Point intersection : intersectionPoints) {
                if (intersection.distance(trajectory.start()) < closestPoint.distance(trajectory.start())) {
                    closestPoint = intersection;
                }
            }
            return new CollisionInfo(closestPoint, this);
        }
    }

    @Override
    public Direction getHitDirection(Point hit) {
        Direction direction = Direction.NONE;
        Map<Direction, Line> edgesMap = this.rectangle.getEdgesMap();
        for (Direction key : edgesMap.keySet()) {
            if (edgesMap.get(key).pointOnLine(hit)) {
                if (direction != Direction.NONE) {
                    direction = Direction.BOTH;
                    break;
                }
                direction = key;
            }
        }
        return direction;
    }


}
