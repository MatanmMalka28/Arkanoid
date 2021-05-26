package game.objects.blocks;

import biuoop.DrawSurface;
import game.listeners.HitListener;
import game.listeners.HitNotifier;
import game.objects.Ball;
import game.objects.Collidable;
import game.objects.GameObject;
import game.objects.attributes.Velocity;
import game.objects.dataStructers.CollisionInfo;
import game.runners.GameLevel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import utilities.Axis;
import utilities.Diagonal;
import utilities.Direction;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Block is a game object represented by a {@link Rectangle} that can draw itself on a given {@link DrawSurface}.
 */
public class Block implements Collidable, HitNotifier, GameObject {
    /**
     * The constant DEFAULT_HIT_COUNT.
     */
    private static final int DEFAULT_HIT_COUNT = 5;
    /**
     * The constant DEFAULT_COLOR.
     */
    private static final Color DEFAULT_COLOR = Color.gray;

    /**
     * initialHitCount is used to determine every blocks initial hit count value in the case no value is given.
     */
    private static int initialHitCount = DEFAULT_HIT_COUNT;
    /**
     * drawHits is used to determine whether all blocks should draw their hit count values.
     */
    private static boolean drawHits = true;
    /**
     * The Rectangle.
     */
    private Rectangle rectangle;
    /**
     * The Color.
     */
    private Color color;

    /**
     * The Hit count.
     */
    private int hitCount = initialHitCount;
    /**
     * The list of {@link HitListener}s this block needs to notify upon being hit.
     */
    private List<HitListener> hitListeners = new ArrayList<>();

    /**
     * Instantiates a new Block with a default pre defined color.
     *
     * @param rectangle the rectangle defining the block
     */
    public Block(Rectangle rectangle) {
        this(rectangle, DEFAULT_COLOR);
    }

    /**
     * Instantiates a new Block with a given color.
     *
     * @param rectangle the rectangle defining the block
     * @param color     the color of the block
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    /**
     * Instantiates a new Block with a default predefined color.
     * The block's {@link Rectangle} is defined by two {@link Point}s.
     *
     * @param topLeft     the top left corner of the block
     * @param bottomRight the bottom right corner of the block
     */
    public Block(Point topLeft, Point bottomRight) {
        this(new Rectangle(topLeft, bottomRight), DEFAULT_COLOR);
    }

    /**
     * Instantiates a new Block with a default predefined color.
     * The block's {@link Rectangle} is defined by two {@link Point}s.
     * The block's hit count is set to an initial value.
     *
     * @param topLeft     the top left corner of the block
     * @param bottomRight the bottom right corner of the block
     * @param hitCount    the initial hit count for the block
     */
    public Block(Point topLeft, Point bottomRight, int hitCount) {
        this(new Rectangle(topLeft, bottomRight), DEFAULT_COLOR, hitCount);
    }

    /**
     * Instantiates a new Block with a given color.
     * The block's {@link Rectangle} is defined by two {@link Point}s.
     *
     * @param topLeft     the top left corner of the block
     * @param bottomRight the bottom right corner of the block
     * @param color       the color of the block
     */
    public Block(Point topLeft, Point bottomRight, Color color) {
        this(new Rectangle(topLeft, bottomRight), color);
    }

    /**
     * Instantiates a new Block with a given color.
     * The block's {@link Rectangle} is defined by a single {@link Point}, height and width.
     *
     * @param topLeft the top left corner of the block
     * @param width   the width of the block
     * @param height  the height of the block
     * @param color   the color of the block
     */
    public Block(Point topLeft, int width, int height, Color color) {
        this(new Rectangle(topLeft, width, height), color);
    }

    /**
     * Instantiates a new Block with a given color and hit initial hit count value.
     *
     * @param rectangle the rectangle defining the block
     * @param color     the color of the block
     * @param hitCount  the initial hit count for the block
     */
    public Block(Rectangle rectangle, Color color, int hitCount) {
        this.rectangle = rectangle;
        this.color = color;
        this.hitCount = hitCount;
    }

    /**
     * Instantiates a new Block with a default predefined color.
     * The block's {@link Rectangle} is defined by a single {@link Point}, height and width.
     *
     * @param topLeft the top left corner of the block
     * @param width   the width of the block
     * @param height  the height of the block
     */
    public Block(Point topLeft, int width, int height) {
        this(new Rectangle(topLeft, width, height), DEFAULT_COLOR);
    }

    /**
     * Instantiates a new Block with a given color.
     * The block's {@link Rectangle} is defined by a single {@link Point}, height and width.
     * The block's hit count is set to an initial value.
     *
     * @param topLeft  the top left corner of the block
     * @param width    the width of the block
     * @param height   the height of the block
     * @param color    the color of the block
     * @param hitCount the initial hit count for the block
     */
    public Block(Point topLeft, int width, int height, Color color, int hitCount) {
        this(new Rectangle(topLeft, width, height), color, hitCount);
    }

    /**
     * Gets an exact copy of the rectangle defining this block.
     *
     * @return the rectangle defining this block.
     */
    public Rectangle getRectangle() {
        return this.rectangle.copy();
    }

    /**
     * Gets the color of this block.
     *
     * @return the color of this block
     */
    public Color getColor() {
        return this.color;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle.copy();
    }

    /**
     * Gets a point which is the top left corner of this block's rectangle.
     *
     * @return the top left corner of this block's rectangle
     */
    public Point getTopLeft() {
        return rectangle.getTopLeft();
    }

    /**
     * Gets a point which is the bottom right corner of this block's rectangle.
     *
     * @return the bottom right corner of this block's rectangle
     */
    public Point getBottomRight() {
        return rectangle.getBottomRight();
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

    /**
     * Gets this block hit count value.
     *
     * @return the current hit count value of this block
     */
    public int getHitCount() {
        return this.hitCount;
    }

    /**
     * Sets {@link Block#initialHitCount} which is the default hit count for all blocks that
     * are instantiated without an explicit hit count value.
     *
     * @param value the new hit count value
     */
    public static void setInitialHitCount(int value) {
        Block.initialHitCount = value;
    }

    /**
     * Sets {@link Block#drawHits} which is the default hit count for all blocks that
     * are instantiated without an explicit hit count value.
     *
     * @param draw the new hit count value
     */
    public static void setDrawHits(boolean draw) {
        Block.drawHits = draw;
    }

    /**
     * Get's the width of the block.
     *
     * @return the width of the block
     */
    public double width() {
        return rectangle.width();
    }

    /**
     * Get's the height of the block.
     *
     * @return the height of the block
     */
    public double height() {
        return rectangle.height();
    }

    /**
     * Check's if this block is equal to the other block.
     *
     * @param other the other block
     * @return true if and only if both block's rectangles and color are equal, otherwise returns false.
     */
    public boolean equals(Block other) {
        return this.rectangle.equals(other.rectangle) && this.color.equals(other.color);
    }

    /**
     * Notifies all of this block's hit listeners on a hit.
     *
     * @param hitter the hitter object
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Decrease this blocks hit count.
     */
    protected void decreaseHit() {
        this.hitCount--;
        this.color = this.color.darker();
    }

    /**
     * Draw's the block's hit count on the given {@link DrawSurface}.
     * The hit count is drawn exactly at the middle of block.
     * If the hit count is 0 draw 'X' instead, otherwise draw the number of hits.
     *
     * @param d the draw surface to draw on
     */
    protected void drawText(DrawSurface d) {
        String text;
        if (this.hitCount > 0) {
            text = String.valueOf(this.hitCount);
        } else {
            text = "X";
        }
        Point intersection = this.rectangle.getDiagonal(Diagonal.TOP_LEFT_TO_BOTTOM_RIGHT).
                intersectionWith(this.rectangle.getDiagonal(Diagonal.BOTTOM_LEFT_TO_TOP_RIGHT));
        if (intersection != null) {
            double size = -0.5 * text.length();
            intersection = intersection.shiftPoint(size, Axis.X).shiftPoint(this.height() / 4, Axis.Y);
            d.drawText(((int) intersection.getX()), ((int) intersection.getY()), text, 12);
        }
    }

    @Override
    public Velocity hit(Ball hitter, CollisionInfo collisionInfo, Velocity currentVelocity) {
        return this.hit(hitter, collisionInfo.getCollisionPoint(), currentVelocity);
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
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
        this.notifyHit(hitter);
        return v;
    }

    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    @Override
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addCollidable(this);
        gameLevel.addSprite(this);
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

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    public void drawOn(DrawSurface d) {
        Rectangle.fillRect(this.rectangle, d, this.color);
        Rectangle.drawEdges(this.rectangle, d, Color.BLACK);
        if (drawHits) {
            this.drawText(d);
        }
    }

    @Override
    public void timePassed() {
        //nothing
    }
}
