package game.objects;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.listeners.MovementListener;
import game.listeners.MovementNotifier;
import game.objects.attributes.Velocity;
import game.objects.blocks.PaddleBlock;
import game.objects.collections.GameEnvironment;
import game.objects.dataStructers.CollisionInfo;
import game.runners.Game;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import utilities.Axis;
import utilities.Direction;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Paddle.
 */
public class Paddle implements GameObject, Collidable, MovementNotifier {
    /**
     * The constant PADDLE_MOVEMENT.
     */
//Class constants used for define purposes
    private static final int PADDLE_MOVEMENT = 10;
    /**
     * The constant HIT_COUNTS.
     */
    private static final int HIT_COUNTS = -1;
    /**
     * The constant PADDLE_COLOR.
     */
    private static final Color PADDLE_COLOR = Color.LIGHT_GRAY;
    /**
     * The Keyboard.
     */
    private final biuoop.KeyboardSensor keyboard;
    /**
     * The Ml list.
     */
    private final List<MovementListener> mlList = new ArrayList<>();
    /**
     * The Environment.
     */
    private GameEnvironment environment;
    /**
     * The Paddle.
     */
    private PaddleBlock paddle;

    /**
     * Instantiates a new Paddle.
     *
     * @param topLeft     the top left
     * @param bottomRight the bottom right
     * @param color       the color
     * @param keyboard    the keyboard
     */
    public Paddle(Point topLeft, Point bottomRight, Color color, KeyboardSensor keyboard) {
        this(new PaddleBlock(new Rectangle(topLeft, bottomRight), color), keyboard);
    }

    /**
     * Instantiates a new Paddle.
     *
     * @param paddle   the paddle
     * @param keyboard the keyboard
     */
    public Paddle(PaddleBlock paddle, KeyboardSensor keyboard) {
        this.paddle = paddle;
        this.keyboard = keyboard;
    }


    /**
     * Instantiates a new Paddle.
     *
     * @param rectangle the rectangle
     * @param keyboard  the keyboard
     */
    public Paddle(Rectangle rectangle, KeyboardSensor keyboard) {
        this(new PaddleBlock(rectangle, PADDLE_COLOR), keyboard);
    }

    /**
     * Instantiates a new Paddle.
     *
     * @param topLeft  the top left
     * @param width    the width
     * @param height   the height
     * @param color    the color
     * @param keyboard the keyboard
     */
    public Paddle(Point topLeft, int width, int height, Color color, KeyboardSensor keyboard) {
        this(new PaddleBlock(topLeft, width, height, color), keyboard);
    }


    // Collidable
    public Rectangle getCollisionRectangle() {
        return this.paddle.getCollisionRectangle();
    }

    @Override
    public Velocity hit(Ball hitter, CollisionInfo collisionInfo, Velocity currentVelocity) {
        return this.hit(hitter, collisionInfo.getCollisionPoint(), currentVelocity);
    }

    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Direction direction = this.getHitDirection(collisionPoint);
        Velocity velocity;
        switch (direction) {
            case TOP:
            case BOTH:
                velocity = this.hitSectorHelp(collisionPoint, currentVelocity);
                break;
            default:
            case LEFT:
            case BOTTOM:
            case RIGHT:
                velocity = this.paddle.hit(hitter, collisionPoint, currentVelocity);
                break;
        }
        return velocity;
    }

    @Override
    public CollisionInfo getCollisionInfo(Line trajectory) {
        CollisionInfo info = this.paddle.getCollisionInfo(trajectory);
        if (info != null) {
            info = new CollisionInfo(info.getCollisionPoint(), this);
        }
        return info;
    }

    @Override
    public Direction getHitDirection(Point hit) {
        return this.paddle.getHitDirection(hit);
    }

    /**
     * Move left.
     */
    public void moveLeft() {
        Line trajectory = this.movementTrajectory(Direction.LEFT);
        CollisionInfo info = this.environment.getClosestCollision(trajectory);
        if (info != null) {
            if (this.paddle.getHitDirection(info.getCollisionPoint()) != Direction.BOTTOM) {
                this.paddle = new PaddleBlock(info.getCollisionPoint(), ((int) this.paddle.width()),
                        ((int) this.paddle.height()), this.paddle.getColor(), HIT_COUNTS);
            }
        } else {
            this.paddle = new PaddleBlock(this.paddle.getTopLeft().shiftPoint(-PADDLE_MOVEMENT, Axis.X),
                    ((int) this.paddle.width()), ((int) this.paddle.height()), this.paddle.getColor(), HIT_COUNTS);
        }
    }

    /**
     * Move right.
     */
    public void moveRight() {
        Line trajectory = this.movementTrajectory(Direction.RIGHT);
        CollisionInfo info = this.environment.getClosestCollision(trajectory);
        if (info != null) {
            if (this.paddle.getHitDirection(info.getCollisionPoint()) != Direction.BOTTOM) {
                this.paddle = new PaddleBlock(info.getCollisionPoint().shiftPoint(-this.paddle.width(), Axis.X),
                        ((int) this.paddle.width()), ((int) this.paddle.height()), this.paddle.getColor(), HIT_COUNTS);
            }
        } else {
            this.paddle = new PaddleBlock(this.paddle.getTopLeft().shiftPoint(PADDLE_MOVEMENT, Axis.X),
                    ((int) this.paddle.width()), ((int) this.paddle.height()), this.paddle.getColor(), HIT_COUNTS);
        }
    }

    /**
     * Hit sector help velocity.
     *
     * @param hitPoint        the hit point
     * @param currentVelocity the current velocity
     * @return the velocity
     */
    private Velocity hitSectorHelp(Point hitPoint, Velocity currentVelocity) {
        double sectorValue = this.paddle.width() / 5;
        double hitSector = Math.abs(this.paddle.getTopLeft().getX() - hitPoint.getX());
        Velocity velocity;
        if (hitSector >= 0 && hitSector < sectorValue) {
            velocity = Velocity.fromAngleAndSpeed(300, currentVelocity.getSpeed());
        } else if (hitSector >= sectorValue && hitSector < sectorValue * 2) {
            velocity = Velocity.fromAngleAndSpeed(330, currentVelocity.getSpeed());
        } else if (hitSector >= sectorValue * 2 && hitSector < sectorValue * 3) {
            velocity = Velocity.fromAngleAndSpeed(0, currentVelocity.getSpeed());
        } else if (hitSector >= sectorValue * 3 && hitSector < sectorValue * 4) {
            velocity = Velocity.fromAngleAndSpeed(30, currentVelocity.getSpeed());
        } else {
            velocity = Velocity.fromAngleAndSpeed(60, currentVelocity.getSpeed());
        }
        return velocity;

    }

    /**
     * Movement trajectory line.
     *
     * @param direction the direction
     * @return the line
     */
    private Line movementTrajectory(Direction direction) {
        return new Line(this.paddle.getRectangle().getEdge(Direction.TOP).middle(), this.nextStep(direction));
    }

    /**
     * Next step point.
     *
     * @param direction the direction
     * @return the point
     */
    private Point nextStep(Direction direction) {
        switch (direction) {
            case LEFT:
                return this.paddle.getTopLeft().shiftPoint(-PADDLE_MOVEMENT, Axis.X);
            case RIGHT:
                return this.paddle.getTopLeft().shiftPoint(PADDLE_MOVEMENT + this.paddle.width(), Axis.X);
            default:
                return this.paddle.getTopLeft();
        }
    }

    /**
     * Notify movement.
     */
    private void notifyMovement() {
        for (MovementListener ml : this.mlList) {
            ml.movementEvent();
        }
    }

    public void drawOn(DrawSurface d) {
        Rectangle.fillRect(this.paddle.getRectangle(), d, this.paddle.getColor());
        Rectangle.drawEdges(this.paddle.getRectangle(), d, Color.BLACK);
    }

    // Sprite
    public void timePassed() {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
            this.notifyMovement();
        } else if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
            this.notifyMovement();
        }
    }

    @Override
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    // Add this paddle to the game.
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
        this.environment = g.getEnvironment();
        this.environment.removeCollidable(this);
    }

    @Override
    public void addMovementListener(MovementListener ml) {
        this.mlList.add(ml);
    }

    @Override
    public void removeMovementListener(MovementListener ml) {
        this.mlList.remove(ml);
    }
}
