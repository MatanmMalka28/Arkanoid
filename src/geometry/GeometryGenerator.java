package geometry;

import java.util.Random;

/**
 * The type Geometry generator.
 */
public class GeometryGenerator {
    /**
     * The constant DEFAULT_MAX.
     */
    private static final int DEFAULT_MAX = 400;
    /**
     * The Top left.
     */
    private Point topLeft, /**
     * The Bottom right.
     */
    bottomRight;
    /**
     * The Rand.
     */
    private Random rand;

    /**
     * Instantiates a new Geometry generator.
     */
    public GeometryGenerator() {
        this(DEFAULT_MAX, DEFAULT_MAX);
    }


    /**
     * Instantiates a new Geometry generator.
     *
     * @param width  the width
     * @param height the height
     */
    public GeometryGenerator(int width, int height) {
        this(0, width, 0, height);
    }

    /**
     * Instantiates a new Geometry generator.
     *
     * @param topLeftX the top left x
     * @param topLeftY the top left y
     * @param width    the width
     * @param height   the height
     */
    public GeometryGenerator(int topLeftX, int topLeftY, int width, int height) {
        this(new Point(topLeftX, topLeftY), new Point(topLeftX + width, topLeftY + height));
    }

    /**
     * Instantiates a new Geometry generator.
     *
     * @param topLeft     the top left
     * @param bottomRight the bottom right
     */
    public GeometryGenerator(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.rand = new Random();
    }

    /**
     * Gets point.
     *
     * @return the point
     */
    public Point getPoint() {
        return new Point(this.getNumber(topLeft.getX(), bottomRight.getX()),
                this.getNumber(topLeft.getY(), bottomRight.getY()));
    }

    /**
     * Gets line.
     *
     * @return the line
     */
    public Line getLine() {
        return new Line(this.getPoint(), this.getPoint());
    }

    /**
     * Gets rectangle.
     *
     * @return the rectangle
     */
    public Rectangle getRectangle() {
        return new Rectangle(this.getPoint(), this.getPoint());
    }

    /**
     * Gets float.
     *
     * @return the float
     */
    public float getFloat() {
        return this.rand.nextFloat();
    }

    /**
     * Gets rectangle.
     *
     * @param width  the width
     * @param height the height
     * @return the rectangle
     */
    public Rectangle getRectangle(int width, int height) {
        return new Rectangle(this.getPoint(), width, height);
    }

    /**
     * Gets number.
     *
     * @param lowerBound the lower bound
     * @param upperBound the upper bound
     * @return the number
     */
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
