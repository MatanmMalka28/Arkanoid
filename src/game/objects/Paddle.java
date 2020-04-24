package game.objects;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.objects.attributes.Velocity;
import game.objects.collections.GameEnvironment;
import game.objects.dataStructers.CollisionInfo;
import game.runners.Game;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import utilities.Axis;
import utilities.Direction;

import java.awt.Color;

public class Paddle implements Sprite, Collidable {
    //Class constants used for define purposes
    private static final int PADDLE_MOVEMENT = 10;
    private static final int HIT_COUNTS = -1;
    private static final Color PADDLE_COLOR = Color.LIGHT_GRAY;
    private biuoop.KeyboardSensor keyboard;
    private GameEnvironment environment;
    private Block paddle;

    public Paddle(Point topLeft, Point bottomRight, Color color, KeyboardSensor keyboard) {
        this(new Block(new Rectangle(topLeft, bottomRight), color, HIT_COUNTS), keyboard);
    }

    public Paddle(Block paddle, KeyboardSensor keyboard) {
        this.paddle = paddle;
        this.keyboard = keyboard;
    }


    public Paddle(Rectangle rectangle, KeyboardSensor keyboard) {
        this(new Block(rectangle, PADDLE_COLOR, HIT_COUNTS), keyboard);
    }

    public Paddle(Point topLeft, int width, int height, Color color, KeyboardSensor keyboard) {
        this(new Block(topLeft, width, height, color, HIT_COUNTS), keyboard);
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

    public void moveLeft() {
        Line trajectory = this.movementTrajectory(Direction.LEFT);
        CollisionInfo info = this.environment.getClosestCollision(trajectory);
        if (info != null) {
            if (this.paddle.getHitDirection(info.getCollisionPoint()) != Direction.BOTTOM) {
                this.paddle = new Block(info.getCollisionPoint(), ((int) this.paddle.width()),
                        ((int) this.paddle.height()), this.paddle.getColor(), HIT_COUNTS);
            }
        } else {
            this.paddle = new Block(this.paddle.getTopLeft().shiftPoint(-PADDLE_MOVEMENT, Axis.X),
                    ((int) this.paddle.width()), ((int) this.paddle.height()), this.paddle.getColor(), HIT_COUNTS);
        }
    }

    public void moveRight() {
        Line trajectory = this.movementTrajectory(Direction.RIGHT);
        CollisionInfo info = this.environment.getClosestCollision(trajectory);
        if (info != null) {
            if (this.paddle.getHitDirection(info.getCollisionPoint()) != Direction.BOTTOM) {
                this.paddle = new Block(info.getCollisionPoint().shiftPoint(-this.paddle.width(), Axis.X),
                        ((int) this.paddle.width()), ((int) this.paddle.height()), this.paddle.getColor(), HIT_COUNTS);
            }
        } else {
            this.paddle = new Block(this.paddle.getTopLeft().shiftPoint(PADDLE_MOVEMENT, Axis.X),
                    ((int) this.paddle.width()), ((int) this.paddle.height()), this.paddle.getColor(), HIT_COUNTS);
        }
    }

    // Add this paddle to the game.
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
        this.environment = g.getEnvironment();
        this.environment.removeCollidable(this);
    }

    private Line movementTrajectory(Direction direction) {
        return new Line(this.paddle.getRectangle().getEdge(Direction.TOP).middle(), this.nextStep(direction));
    }

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

    public void drawOn(DrawSurface d) {
        this.paddle.drawOn(d);
    }

    // Sprite
    public void timePassed() {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        } else if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }
}
