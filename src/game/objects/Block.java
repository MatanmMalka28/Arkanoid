package game.objects;

import game.objects.dataStructers.CollisionInfo;
import game.objects.attributes.Velocity;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import utilities.Axis;
import utilities.Diagonal;
import utilities.Direction;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The type Block.
 */
public class Block implements Collidable, Sprite {
    private static final int DEFAULT_HIT_COUNT = 5;
    /**
     * The constant DEFAULT_COLOR.
     */
    private static final Color DEFAULT_COLOR = Color.gray;
    private static int HIT_COUNT = DEFAULT_HIT_COUNT;
    /**
     * The Rectangle.
     */
    private Rectangle rectangle;
    /**
     * The Color.
     */
    private Color color;

    private int hitCount = HIT_COUNT;

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
     */
    public Block(Point topLeft, Point bottomRight, int hitCount) {
        this(new Rectangle(topLeft, bottomRight), DEFAULT_COLOR, hitCount);
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
     * @param rectangle the rectangle
     * @param color     the color
     * @param hitCount  the hit count
     */
    public Block(Rectangle rectangle, Color color, int hitCount) {
        this.rectangle = rectangle;
        this.color = color;
        this.hitCount = hitCount;
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
     * Instantiates a new Block.
     *
     * @param topLeft  the top left
     * @param width    the width
     * @param height   the height
     * @param color    the color
     * @param hitCount the hit count
     */
    public Block(Point topLeft, int width, int height, Color color, int hitCount) {
        this(new Rectangle(topLeft, width, height), color, hitCount);
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

    public Point getTopLeft() {
        return rectangle.getTopLeft();
    }

    public Point getBottomRight() {
        return rectangle.getBottomRight();
    }

    public static void setHitCount(int hitCount) {
        HIT_COUNT = hitCount;
    }

    public double width() {
        return rectangle.width();
    }

    @Override
    public Velocity hit(CollisionInfo collisionInfo, Velocity currentVelocity) {
        return this.hit(collisionInfo.getCollisionPoint(), currentVelocity);
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        Direction direction = this.getHitDirection(collisionPoint);
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
        this.decreaseHit();
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

    private void decreaseHit() {
        if (this.hitCount > 0) {
            this.hitCount--;
        }
    }

    /**
     * Draw on.
     *
     * @param d the d
     */
    public void drawOn(DrawSurface d) {
        Rectangle.fillRect(this.rectangle, d, this.color);
        Rectangle.drawEdges(this.rectangle, d, Color.BLACK);
        this.drawText(d);
    }

    protected void drawText(DrawSurface d) {
        String text;
        if (this.hitCount > 0) {
            text = String.valueOf(this.hitCount);
        } else if (this.hitCount == 0) {
            text = "X";
        } else {
            text = "";
        }
        Point intersection = this.rectangle.getDiagonal(Diagonal.TOP_LEFT_TO_BOTTOM_RIGHT).intersectionWith(this.rectangle.getDiagonal(Diagonal.BOTTOM_LEFT_TO_TOP_RIGHT));
        if (intersection != null) {
            double size = -0.5 * text.length();
            intersection = intersection.shiftPoint(size, Axis.X).shiftPoint(this.height() / 4, Axis.Y);
            d.drawText(((int) intersection.getX()), ((int) intersection.getY()), text, 12);
        }
    }

    public double height() {
        return rectangle.height();
    }

    @Override
    public void timePassed() {
        //nothing
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
}
