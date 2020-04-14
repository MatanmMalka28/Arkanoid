package Game;

import Geometry.Point;

public class Velocity {
    private double dx,dy, angle;
    // constructor
    public Velocity(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
        this.calcAngle();
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public double getAngle() {
        return angle;
    }

    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.sin(Math.toRadians(angle)) * speed;
        double dy = Math.cos(Math.toRadians(angle - 180)) * speed;
        return new Velocity(dx, dy);
    }

    // Take a point with position (x,y) and return a new point
    // with position (x+dx, y+dy)
    public Point applyToPoint(Point p){
        return new Point(p.getX()+this.dx,p.getY()+this.dy);
    }

    public Point applyToPoint(Point p, int size){
        return new Point(p.getX()+this.dx + size,p.getY()+this.dy+ size);
    }

    public Velocity changeSign(double x, double y){
        return new Velocity(this.dx*Math.signum(x),this.dy*Math.signum(y));
    }

    public void calcAngle() {
        this.setAngle(Math.toDegrees(Math.atan((-this.dx) / this.dy))-180);
    }

    public void setAngle(double newAngle) {
        if (newAngle >= 0 && newAngle <= 360) {
            this.angle = newAngle;
        } else if (newAngle < 0) {
            if (Math.signum(-this.dy) < 0) {
                this.angle = newAngle + 360;
            } else if (Math.signum(-this.getDy()) > 0) {
                this.angle = newAngle + 180;
            }

        } else {
            while (newAngle < 0) {
                newAngle = Math.abs(newAngle - 180);
            }
            this.angle = newAngle;
        }
    }

    public Velocity copy(){
        return this.changeSign(1,1);
    }
}
