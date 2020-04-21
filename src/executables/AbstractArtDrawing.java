package executables;

import geometry.GeometryGenerator;
import geometry.Line;
import geometry.Point;
import biuoop.DrawSurface;
import biuoop.GUI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Abstract art drawing.
 */
public class AbstractArtDrawing {
    /**
     * The constant WIDTH.
     */
    public static final int WIDTH = 400;
    /**
     * The constant HEIGHT.
     */
    public static final int HEIGHT = 400;
    /**
     * The constant SIZE.
     */
    public static final int SIZE = 3;
    /**
     * The Generator.
     */
    private GeometryGenerator generator;
    /**
     * The Gui.
     */
    private GUI gui;

    /**
     * Instantiates a new Abstract art drawing.
     */
    public AbstractArtDrawing() {
        this(WIDTH, HEIGHT);
    }

    /**
     * Instantiates a new Abstract art drawing.
     *
     * @param width  the width
     * @param height the height
     */
    public AbstractArtDrawing(int width, int height) {
        this.generator = new GeometryGenerator(width, height);
        this.gui = new GUI("Abstract Art Drawing", width, height);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        AbstractArtDrawing artDrawing = new AbstractArtDrawing(600, 600);
        artDrawing.drawAbstractLines();
    }

    /**
     * Draw abstract lines.
     */
    public void drawAbstractLines() {
        this.drawAbstractLines(10);
    }

    /**
     * Draw abstract lines.
     *
     * @param numOfLines the num of lines
     */
    public void drawAbstractLines(int numOfLines) {
        List<Line> lineList = this.generateLines(numOfLines);
        DrawSurface drawSurface = this.gui.getDrawSurface();
        for (Line line : lineList) {
            this.drawLine(line, drawSurface);
        }
        for (Line line : lineList) {
            this.drawPoint(line.middle(), drawSurface, Color.BLUE);
            for (Line other : lineList) {
                if (!other.equals(line) && other.isIntersecting(line)) {
                    this.drawPoint(other.intersectionWith(line), drawSurface, Color.RED);
                }
            }
        }
        this.gui.show(drawSurface);
    }

    /**
     * Generate lines list.
     *
     * @param numOfLines the num of lines
     * @return the list
     */
    private List<Line> generateLines(int numOfLines) {
        List<Line> lineList = new ArrayList<>();
        for (int i = 0; i < numOfLines; i++) {
            lineList.add(this.generator.getLine());
        }
        return lineList;
    }

    /**
     * Draw line.
     *
     * @param line the line
     * @param d    the d
     */
    private void drawLine(Line line, DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawLine(((int) line.start().getX()), ((int) line.start().getY()),
                ((int) line.end().getX()), ((int) line.end().getY()));
    }

    /**
     * Draw point.
     *
     * @param point the point
     * @param d     the d
     * @param color the color
     */
    private void drawPoint(Point point, DrawSurface d, Color color) {
        d.setColor(color);
        d.fillCircle(((int) point.getX()), ((int) point.getY()), SIZE);
    }
}
