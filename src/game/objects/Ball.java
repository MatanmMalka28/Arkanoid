package game.objects;

import game.objects.collections.GameEnvironment;
import game.objects.attributes.Velocity;
import game.objects.dataStructers.CollisionInfo;
import game.runners.Game;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import utilities.Axis;
import utilities.Utilities;
import utilities.Direction;
import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Ball.
 */
public class Ball implements Sprite, GameObject {
    /**
     * The constant DIFF_PERCENTAGE.
     */
    private static final double DIFF_PERCENTAGE = 0.98;
    /**
     * The constant debugMode.
     */
    private static boolean debugMode = false;
    /**
     * The Center.
     */
    private Point center;
    /**
     * The Radius.
     */
    private int radius;
    /**
     * The Color.
     */
    private Color color;
    /**
     * The Velocity.
     */
    private Velocity velocity;
    /**
     * The Game borders.
     */
    private Map<Direction, Line> gameBorders;
    /**
     * The Game environment.
     */
    private GameEnvironment gameEnvironment;
    private boolean trajectoryHitLastTurn;

    // constructor


    /**
     * Instantiates a new Ball.
     *
     * @param x      the x
     * @param y      the y
     * @param radius the radius
     * @param color  the color
     */
    public Ball(double x, double y, int radius, Color color) {
        this(new Point(x, y), radius, color);
    }

    /**
     * Instantiates a new Ball.
     *
     * @param center the center
     * @param radius the radius
     * @param color  the color
     */
    public Ball(Point center, int radius, Color color) {
        this(center, radius, color, new GameEnvironment());
    }

    /**
     * Instantiates a new Ball.
     *
     * @param center          the center
     * @param radius          the radius
     * @param color           the color
     * @param gameEnvironment the game environment
     */
    public Ball(Point center, int radius, Color color, GameEnvironment gameEnvironment) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.setGameEnvironment(gameEnvironment);
    }

    /**
     * Gets radius.
     *
     * @return the radius
     */
    public int getRadius() {
        return this.radius;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Gets velocity.
     *
     * @return the velocity
     */
    public Velocity getVelocity() {
        return velocity;
    }

    /**
     * Sets newVelocity.
     *
     * @param newVelocity the newVelocity
     */
    public void setVelocity(Velocity newVelocity) {
        this.velocity = newVelocity;
    }

    /**
     * Gets game environment.
     *
     * @return the game environment
     */
    public GameEnvironment getGameEnvironment() {
        return this.gameEnvironment.copy();
    }

    /**
     * Sets game environment.
     *
     * @param environment the game environment
     */
    public void setGameEnvironment(GameEnvironment environment) {
        this.gameEnvironment = environment.copy();
    }

    /**
     * Sets game borders.
     *
     * @param borders the game borders
     */
    public void setGameBorders(Map<Direction, Line> borders) {
        if (borders.size() == 4) {
            this.gameBorders = borders;
        }
    }

    /**
     * Sets debug mode.
     *
     * @param newDebugMode the debug mode
     */
    public static void setDebugMode(boolean newDebugMode) {
        Ball.debugMode = newDebugMode;
    }

    /**
     * Sets velocity.
     *
     * @param dx the dx
     * @param dy the dy
     */
    public void setVelocity(double dx, double dy) {
        this.setVelocity(new Velocity(dx, dy));
    }

    /**
     * Sets game borders.
     *
     * @param topLeft     the top left
     * @param bottomRight the bottom right
     */
    public void setGameBorders(Point topLeft, Point bottomRight) {
        this.gameBorders = Utilities.translatePointsToBorders(topLeft, bottomRight);
    }

    public void setGameBorders(Point topLeft, Point bottomRight, int size) {
        this.gameEnvironment.addCollidable(Utilities.translatePointsToBlocks(topLeft, bottomRight, size));
    }

    /**
     * Gets collision point.
     *
     * @param trajectory the trajectory
     * @return the collision point
     */
    private Point getCollisionPoint(Line trajectory) {
        List<Point> hitPoints = new ArrayList<>();
        for (Line border : this.gameBorders.values()) {
            if (trajectory.isIntersecting(border)) {
                hitPoints.add(trajectory.intersectionWith(border));
            }
        }
        if (hitPoints.isEmpty()) {
            return null;
        } else {
            Point closestHit = hitPoints.get(0);
            hitPoints.remove(0);
            for (Point hit : hitPoints) {
                if (hit.distance(this.center) < closestHit.distance(this.center)) {
                    closestHit = hit;
                }
            }
            return closestHit;
        }
    }

    /**
     * Change velocity upon hit velocity.
     *
     * @param hit the hit
     * @return the velocity
     */
    private Velocity changeVelocityUponHit(Point hit) {
        Direction direction = Direction.NONE;
        for (Direction key : this.gameBorders.keySet()) {
            if (this.gameBorders.get(key).pointOnLine(hit)) {
                if (direction != Direction.NONE) {
                    direction = Direction.BOTH;
                    break;
                }
                direction = key;
            }
        }
        Velocity v;
        switch (direction) {
            case TOP:
            case BOTTOM:
                v = this.velocity.changeSign(1, -1);
                break;
            case LEFT:
            case RIGHT:
                v = this.velocity.changeSign(-1, 1);
                break;
            case BOTH:
                v = this.velocity.changeSign(-1, -1);
                break;
            case NONE:
            default:
                v = this.velocity.copy();
                break;
        }
        return v;
    }

    /**
     * Draw on.
     *
     * @param d the d
     */
// draw the ball on the given DrawSurface
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillCircle(this.getX(), this.getY(), this.radius);
        d.setColor(Color.BLACK);
        d.drawCircle(this.getX(), this.getY(), this.radius);
        //for debug:
        if (debugMode) {
            this.debugDraw(d);
        }
    }

    /**
     * Gets x.
     *
     * @return the x
     */
// accessors
    public int getX() {
        return ((int) this.center.getX());
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return ((int) this.center.getY());
    }

    private void debugDraw(DrawSurface d) {
        d.setColor(Color.red);
        this.calcTrajectory().drawOn(d);
        Rectangle.drawEdges(this.getRectPerimeter(), d, Color.green);
    }

    /**
     * Calc trajectory line.
     *
     * @return the line
     */
    private Line calcTrajectory() {
        return new Line(this.center, this.nextStep());
    }

    private Rectangle getRectPerimeter() {
        double diff = this.radius * DIFF_PERCENTAGE;
        return new Rectangle(this.center.shiftPoint(-diff, Axis.XY), this.center.shiftPoint(diff, Axis.XY));
    }

    /**
     * Next step point.
     *
     * @return the point
     */
    private Point nextStep() {
        if (this.velocity.getSpeed() < this.radius) {
            Velocity v = Velocity.fromAngleAndSpeed(this.velocity.getAngle(), this.velocity.getSpeed() + this.radius);
            return v.applyToPoint(this.center);
        } else {
            return this.velocity.applyToPoint(this.center);
        }

    }

    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    public void moveOneStep() {
        Line trajectory = this.calcTrajectory();
        CollisionInfo info = this.gameEnvironment.getClosestCollision(trajectory);
        if (info != null) {
            Velocity v = Velocity.fromAngleAndSpeed(this.velocity.getAngle(), this.radius);
            v = v.changeSign(-1, -1);
            this.center = v.applyToPoint(info.getCollisionPoint());
            this.velocity = info.getCollisionObject().hit(this, info, this.velocity.copy());
            this.trajectoryHitLastTurn = true;
        } else if (trajectoryHitLastTurn) {
            this.center = this.velocity.applyToPoint(this.center);
            this.trajectoryHitLastTurn = false;
        } else {
            this.velocity = this.checkPerimeterHits();
            this.center = this.velocity.applyToPoint(this.center);
        }
    }

    private Velocity checkPerimeterHits() {
        //todo: add point more on line function.
        Rectangle ballRect = this.getRectPerimeter();
        Direction direction = Direction.NONE;
        Set<CollisionInfo> infoSet = new TreeSet<>();
        for (Direction side : ballRect.getEdgesMap().keySet()) {
            CollisionInfo info = this.gameEnvironment.getClosestCollision(ballRect.getEdge(side));
            if (info != null) {
                infoSet.add(info);
                if (direction != Direction.NONE && !direction.directionParallel(side)) {
                    direction = Direction.BOTH;
                    break;
                }
                if (direction != Direction.BOTH) {
                    direction = side;
                }
            }
        }
        for (CollisionInfo info : infoSet) {
            info.getCollisionObject().hit(this, info.getCollisionPoint(), this.velocity);
        }
        Velocity v;
        switch (direction) {
            case TOP:
            case BOTTOM:
                v = this.velocity.changeSign(-1, 1);
                break;
            case LEFT:
            case RIGHT:
                v = this.velocity.changeSign(1, -1);
                break;
            case BOTH:
                double sign = Utilities.getSign();
                v = this.velocity.changeSign(sign, -sign);
                break;
            case NONE:
            default:
                v = this.velocity.copy();
                break;
        }
        return v;
    }

    @Override
    public void removeFromGame(Game game) {
        game.removeSprite(this);
    }

    @Override
    public void addToGame(Game game) {
        game.addSprite(this);
        game.assignGameEnvironment(this);
    }
}
