package Geometry;

import java.util.Random;

public class GeometryGenerator {
    private static final int DEFAULT_MAX = 400;
    private Point topLeft, bottomRight;
    private Random rand;

    public GeometryGenerator(int topLeftX, int topLeftY, int width, int height) {
        this(new Point(topLeftX, topLeftY), new Point(topLeftX + width, topLeftY + height));
    }


    public GeometryGenerator(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.rand = new Random();
    }

    public GeometryGenerator(int width, int height) {
        this(0, width, 0, height);
    }

    public GeometryGenerator() {
        this(DEFAULT_MAX, DEFAULT_MAX);
    }

    public Point getPoint() {
        return new Point(this.getNumber(topLeft.getX(), bottomRight.getX()),
                this.getNumber(topLeft.getY(), bottomRight.getY()));
    }

    public Line getLine() {
        return new Line(this.getPoint(), this.getPoint());
    }

    public Rectangle getRectangle() {
        return new Rectangle(this.getPoint(), this.getPoint());
    }

    public float getFloat() {
        return this.rand.nextFloat();
    }

    public Rectangle getRectangle(int width, int height) {
        return new Rectangle(this.getPoint(), width, height);
    }

    public double getNumber(double lowerBound, double upperBound) {
        if (upperBound < lowerBound) {
            double temp = upperBound;
            upperBound = lowerBound;
            lowerBound = temp;
        }
        double num = lowerBound + this.rand.nextInt((int) Math.abs(upperBound - lowerBound)) + this.rand.nextDouble();
        return (num >= lowerBound && num <= upperBound) ? num : this.getNumber(lowerBound, upperBound);
    }


}
