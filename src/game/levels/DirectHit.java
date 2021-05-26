package game.levels;

import game.objects.attributes.Velocity;
import game.objects.blocks.Block;
import game.objects.sprites.Background;
import game.objects.sprites.GameCircle;
import game.objects.sprites.GameLine;
import game.objects.sprites.Sprite;
import geometry.Circle;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DirectHit extends BaseLevel{

    public DirectHit(String levelName, int windowHeight, int windowWidth, int paddleSpeed, int paddleWidth) {
        super(levelName, windowHeight, windowWidth, paddleSpeed, paddleWidth);
    }

    @Override
    protected Background createBackground() {
        /*int radius = 50;
        int size = 50;
        double dSize = size * 0.75;
        Point p1 = new Point(START_X, START_Y);
        GameCircle innerCircle = new GameCircle(p1, radius + 15, Color.BLUE);
        GameCircle midCircle = new GameCircle(new Circle(p1, 2 * radius), Color.BLUE, false, Color.BLUE, true);
        GameCircle outCircle = new GameCircle(new Circle(p1, 3 * radius), Color.BLUE, false, Color.BLUE, true);
        Line vertical1 = new Line(p1.getX(), p1.getY() - outCircle.getRadius() - 15, p1.getX(), p1.getY() - dSize);
        Line vertical2 = new Line(p1.getX(), p1.getY() + outCircle.getRadius() + 15, p1.getX(), p1.getY() + dSize);
        Line horizontal1 = new Line(p1.getX() - outCircle.getRadius() - 15, p1.getY(), p1.getX() - dSize, p1.getY());
        Line horizontal2 = new Line(p1.getX() + outCircle.getRadius() + 15, p1.getY(), p1.getX() + dSize, p1.getY());
        GameLine gameLine1 = new GameLine(vertical1, Color.BLUE);
        GameLine gameLine2 = new GameLine(vertical2, Color.BLUE);
        GameLine gameLine3 = new GameLine(horizontal1, Color.BLUE);
        GameLine gameLine4 = new GameLine(horizontal2, Color.BLUE);
        List<Sprite> spriteList = new ArrayList<>();
        spriteList.add(innerCircle);
        spriteList.add(midCircle);
        spriteList.add(outCircle);
        spriteList.add(gameLine1);
        spriteList.add(gameLine2);
        spriteList.add(gameLine3);
        spriteList.add(gameLine4);
        GameRectangle backgroundRect = new GameRectangle(new Rectangle(new Point(0, 0),
                GameLevel.getGuiWidth(), GameLevel.getGuiHeight()), Color.BLACK);
        this.directHit = new Background(backgroundRect, spriteList);*/
        return null;
    }

    @Override
    protected List<Velocity> createInitialBallsVelocities() {
        return null;
    }

    @Override
    protected List<Block> createBlocks() {
        return null;
    }
}
