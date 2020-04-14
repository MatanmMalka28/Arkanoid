import java.util.Random;

public class GeometryGenerator {
    private static final int DEFAULT_MIN = 0;

    ;
    private static final int DEFAULT_MAX = 200;
    private int minX, maxX, minY, maxY;
    private Random random = new Random();
    public GeometryGenerator(int minX, int maxX, int minY, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }


    public GeometryGenerator(Point topLeft, Point bottomRight) {
        this(((int) topLeft.getX()), ((int) bottomRight.getX()), ((int) topLeft.getY()), ((int) bottomRight.getY()));
    }

    public GeometryGenerator(int width, int height) {
        this(0, width, 0, height);
    }

    public GeometryGenerator() {
        this(DEFAULT_MIN, DEFAULT_MAX);
    }

    public Point generatePoint() {
        return new Point(this.generateNumber(Var.X), this.generateNumber(Var.Y));
    }

    public Line generateLine() {
        return new Line(this.generatePoint(), this.generatePoint());
    }

    private double generateNumber(Var var) {
        double num;
        switch (var) {
            default:
            case X:
                num = this.minX + this.random.nextInt(Math.abs(this.maxX - this.minX + 1)) + this.random.nextDouble();
                return (this.minX <= num && num <= this.maxX) ? num : this.generateNumber(var);
            case Y:
                num = this.minY + this.random.nextInt(Math.abs(this.maxY - this.minY + 1)) + this.random.nextDouble();
                return (this.minY <= num && num <= this.maxY) ? num : this.generateNumber(var);
        }
    }

    private enum Var {X, Y}

}
