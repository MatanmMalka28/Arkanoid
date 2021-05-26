package game.objects;

import game.listeners.MovementListener;
import game.objects.collections.GameEnvironment;
import game.objects.attributes.Velocity;
import game.objects.dataStructers.CollisionInfo;
import game.runners.GameLevel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import utilities.Axis;
import utilities.Utilities;
import utilities.Direction;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type Ball.
 */
public class Ball implements GameObject, MovementListener {
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
    /**
     * The Trajectory hit last turn.
     */
    private boolean trajectoryHitLastTurn;
    /**
     * The Paused.
     */
    private boolean paused = true;

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

    /**
     * Gets rect perimeter.
     *
     * @return the rect perimeter
     */
    private Rectangle getRectPerimeter() {
        double diff = this.radius * DIFF_PERCENTAGE;
        return new Rectangle(this.center.shiftPoint(-diff, Axis.XY), this.center.shiftPoint(diff, Axis.XY));
    }

    /**
     * Sets game environment.
     *
     * @param environment the game environment
     */
    public void setGameEnvironment(GameEnvironment environment) {
        this.gameEnvironment = environment;
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

    /**
     * Sets game borders.
     *
     * @param topLeft     the top left
     * @param bottomRight the bottom right
     * @param size        the size
     */
    public void setGameBorders(Point topLeft, Point bottomRight, int size) {
        this.gameEnvironment.addCollidable(Utilities.translatePointsToBlocks(topLeft, bottomRight, size));
    }

    /**
     * Checks if the ball can apply its velocity on the balls center to move one step ahead without collision.
     * If no collision occur then move the ball by that step, otherwise check the collision parameters and change the
     * ball's direction based on the hit point.
     */
    public void moveOneStep() {
        if (!this.paused) {
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
    }

    /**
     * Debug draw.
     *
     * @param d the d
     */
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

    /**
     * Check perimeter hits of the ball.
     *
     * @return the velocity
     */
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

    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    @Override
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
        gameLevel.removeMovementListener(this);
    }

    @Override
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
        gameLevel.assignGameEnvironment(this);
        gameLevel.assignMovementListener(this);
    }

    @Override
    public void movementEvent() {
        this.paused = false;
    }
}
