package Game.Objects;

import Game.Objects.Collections.GameEnvironment;
import Game.Objects.DataStructers.CollisionInfo;
import Game.Velocity;
import Geometry.Line;
import Geometry.Point;
import Utilities.Utilities;
import Utilities.Direction;
import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.util.Map;

public class Ball {

    private Point center;
    private int size;
    private Color color;
    private Velocity velocity;
    private Map<Direction, Line> gameBorders;
    private GameEnvironment gameEnvironment;

    // constructor


    public Ball(Point center, int size, Color color, GameEnvironment gameEnvironment) {
        this.center = center;
        this.size = size;
        this.color = color;
        this.setGameEnvironment(gameEnvironment);
    }

    public Ball(Point center, int size, Color color) {
        this(center, size, color, new GameEnvironment());
    }

    public Ball(double x, double y, int size, Color color) {
        this(new Point(x, y), size, color);
    }

    // accessors
    public int getX() {
        return ((int) this.center.getX());
    }

    public int getY() {
        return ((int) this.center.getY());
    }

    public int getSize() {
        return this.size;
    }

    public Color getColor() {
        return this.color;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public GameEnvironment getGameEnvironment() {
        return this.gameEnvironment.copy();
    }

    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment.copy();
    }

    public void setGameBorders(Map<Direction, Line> gameBorders) {
        if (gameBorders.size() == 4) {
            this.gameBorders = gameBorders;
        }
    }

    public void setVelocity(double dx, double dy) {
        this.setVelocity(new Velocity(dx, dy));
    }

    public void setGameBorders(Point topLeft, Point bottomRight) {
        this.gameBorders = Utilities.translatePointsToBorders(topLeft, bottomRight);
    }

    public void moveOneStep() {
        Line trajectory = this.calcTrajectory(this.center);
        CollisionInfo info = this.gameEnvironment.getClosestCollision(trajectory);
        if (info != null) {
            Point newCenter = this.applyHit(info.getCollisionPoint());
            this.velocity = info.getCollisionObject().hit(info.getCollisionPoint(), this.velocity);
            this.center = newCenter;
        } else {
            this.center = this.getVelocity().applyToPoint(this.center);
        }
    }

    public Point nextStep() {
        if (this.velocity.getSpeed() < this.size) {
            Velocity v = Velocity.fromAngleAndSpeed(this.velocity.getAngle(), this.velocity.getSpeed() + this.size);
            return v.applyToPoint(this.center);
        } else {
            return this.velocity.applyToPoint(this.center);
        }

    }

    // draw the ball on the given DrawSurface
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillCircle(this.getX(), this.getY(), this.size);
        d.setColor(Color.BLACK);
        d.drawCircle(this.getX(), this.getY(), this.size);
    }

    private Line calcTrajectory() {
        return new Line(this.center, this.nextStep());
    }

    private Point getCollisionPoint(Line trajectory) {
        List<Point> hitPoints = new ArrayList<>();
        for (Line border : this.gameBorders.values()) {
            if (trajectory.isIntersecting(border)) {
                hitPoints.add(trajectory.intersectionWith(border));
            }
        }
        if (hitPoints.isEmpty()) {
            return null;
        } else if (hitPoints.size() == 1) {
            return hitPoints.get(0);
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

    private Point applyHit(Point hit) {
        double scalar = Math.sqrt(0.5);
        Velocity velocity;
        double angle = this.velocity.getAngle();
        if (Utilities.compareDoubles(angle, 90) || Utilities.compareDoubles(angle, 270)) {
            //take the ball back by the radius of the ball, but only on the Y axis
            velocity = new Velocity(-Math.signum(this.velocity.getDx()) * this.size, 0);
            //if the ball's movement is Y axis based
        } else if (Utilities.compareDoubles(angle, 0) || Utilities.compareDoubles(angle, 180)
                || Utilities.compareDoubles(angle, 360)) {
            //take the ball back by the radius of the ball, but only on the X axis
            velocity = new Velocity(0, -(Math.signum(this.velocity.getDy()) * this.size));
        } else {
            velocity = new Velocity(-scalar * Math.signum(this.velocity.getDx()) * this.size, -scalar * (Math.signum(this.velocity.getDy()) * this.size));
        }
        return velocity.applyToPoint(hit);
    }

    private Velocity changeDirection(Point hit) {
        Velocity tempVelocity = this.velocity.copy();
        if (this.gameBorders.get(Direction.LEFT).pointOnLine(hit) ||
                this.gameBorders.get(Direction.RIGHT).pointOnLine(hit)) {
            tempVelocity = tempVelocity.changeSign(-1, 1);
        }
        if (this.gameBorders.get(Direction.TOP).pointOnLine(hit) ||
                this.gameBorders.get(Direction.BOTTOM).pointOnLine(hit)) {
            tempVelocity = tempVelocity.changeSign(1, -1);
        }
        return tempVelocity;
    }
}
