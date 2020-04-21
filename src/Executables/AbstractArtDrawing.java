package Executables;

import Geometry.GeometryGenerator;
import Geometry.Line;
import Geometry.Point;
import biuoop.DrawSurface;
import biuoop.GUI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class AbstractArtDrawing {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    public static final int SIZE = 3;
    private GeometryGenerator generator;
    private GUI gui;

    public AbstractArtDrawing(int width, int height) {
        this.generator = new GeometryGenerator(width, height);
        this.gui = new GUI("Abstract Art Drawing", width, height);
    }

    public AbstractArtDrawing() {
        this(WIDTH, HEIGHT);
    }

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
    public void drawAbstractLines(){
        this.drawAbstractLines(10);
    }


    private List<Line> generateLines(int numOfLines) {
        List<Line> lineList = new ArrayList<>();
        for (int i = 0; i < numOfLines; i++) {
            lineList.add(this.generator.getLine());
        }
        return lineList;
    }

    private void drawLine(Line line, DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawLine(((int) line.start().getX()), ((int) line.start().getY()),
                ((int) line.end().getX()), ((int) line.end().getY()));
    }

    private void drawPoint(Point point, DrawSurface d, Color color) {
        d.setColor(color);
        d.fillCircle(((int) point.getX()), ((int) point.getY()), SIZE);
    }


    public static void main(String[] args) {
        AbstractArtDrawing artDrawing = new AbstractArtDrawing(600,600);
        artDrawing.drawAbstractLines();
    }
}
