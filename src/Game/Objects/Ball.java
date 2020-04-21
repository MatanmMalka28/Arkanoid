package Game.Objects;

import Game.Objects.Collections.GameEnvironment;
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
    private static final double DIFF_PERCENTAGE = 0.95;
    public static boolean debugMode = false;
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;
    private Map<Direction, Line> gameBorders;
    private GameEnvironment gameEnvironment;

    // constructor


    public Ball(Point center, int radius, Color color, GameEnvironment gameEnvironment) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        this.setGameEnvironment(gameEnvironment);
    }

    public Ball(Point center, int radius, Color color) {
        this(center, radius, color, new GameEnvironment());
    }

    public Ball(double x, double y, int radius, Color color) {
        this(new Point(x, y), radius, color);
    }

    // accessors
    public int getX() {
        return ((int) this.center.getX());
    }

    public int getY() {
        return ((int) this.center.getY());
    }

    public int getRadius() {
        return this.radius;
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
        Line trajectory = this.calcTrajectory();
        Point hit = this.getCollisionPoint(trajectory);
        if (hit != null) {
            Velocity v = Velocity.fromAngleAndSpeed(this.velocity.getAngle(), this.radius);
            v = v.changeSign(-1, -1);
            this.center = v.applyToPoint(hit);
            this.velocity = this.changeVelocityUponHit(hit);
        } else {
            this.center = this.velocity.applyToPoint(this.center);
            this.velocity = this.checkPerimetrHits();
        }
    }

    // draw the ball on the given DrawSurface
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillCircle(this.getX(), this.getY(), this.radius);
        d.setColor(Color.BLACK);
        d.drawCircle(this.getX(), this.getY(), this.radius);
        //for debug:
        if (debugMode) {
            d.setColor(Color.red);
            Line trajectory = this.calcTrajectory();
            d.drawLine(((int) trajectory.start().getX()), ((int) trajectory.start().getY()),
                    ((int) trajectory.end().getX()), ((int) trajectory.end().getY()));
        }
    }

    private Velocity checkPerimetrHits() {
        double diff = this.radius * DIFF_PERCENTAGE;
        Velocity velocity = this.velocity.copy();
        Point hitX = null, hitY = null;
        Line diameterX = new Line(new Point(this.center.getX() - diff, this.center.getY()),
                new Point(this.center.getX() + diff, this.center.getY()));
        Point hit = this.getCollisionPoint(diameterX);
        if (hit != null) {
            velocity = this.changeVelocityUponHit(hit);
            Velocity v = Velocity.fromAngleAndSpeed(90, Math.copySign(diff, velocity.getDx()));
            hitX = v.applyToPoint(hit);
        }
        Line diameterY = new Line(new Point(this.center.getX(), this.center.getY() - diff),
                new Point(this.center.getX(), this.center.getY() + diff));
        hit = this.getCollisionPoint(diameterY);
        if (hit != null) {
            velocity = this.changeVelocityUponHit(hit);
            Velocity v = Velocity.fromAngleAndSpeed(0, Math.copySign(diff, -velocity.getDy()));
            hitY = v.applyToPoint(hit);
        }
        if (hitX != null && hitY != null) {
            this.center = new Point(hitX.getX(), hitY.getY());
        } else if (hitX != null) {
            this.center = hitX;
        } else if (hitY != null) {
            this.center = hitY;
        }
        return velocity;
    }

    private Point nextStep() {
        if (this.velocity.getSpeed() < this.radius) {
            Velocity v = Velocity.fromAngleAndSpeed(this.velocity.getAngle(), this.velocity.getSpeed() + this.radius);
            return v.applyToPoint(this.center);
        } else {
            return this.velocity.applyToPoint(this.center);
        }

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

    /*private Point applyHit(Point hit) {
        double scalar = Math.sqrt(0.5);
        Velocity velocity;
        double angle = this.velocity.getAngle();

        return velocity.applyToPoint(hit);
    }*/


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
}
