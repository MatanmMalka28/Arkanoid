import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MultipleBouncingBallsAnimation {

    public static void main(String[] args) {
        List<Integer> sizeList = new ArrayList<>();
        if (args.length==0){
            sizeList.add(4);
            sizeList.add(6);
            sizeList.add(8);
            sizeList.add(10);
        } else {
            for (String num:args){
                try {
                    sizeList.add(Integer.parseInt(num));
                } catch (Exception e){
                    sizeList.add(5);
                }
            }
        }
        GUI gui = new GUI("Multiple Bouncing Balls Animation", 400,400);
        Sleeper sleeper = new Sleeper();
        Frame myFrame = new Frame(new Point(0,0),new Point(400,400), Color.GRAY);
        for (Integer size:sizeList){
            myFrame.addBall(size);
        }
        while (true){
            DrawSurface d = gui.getDrawSurface();
            myFrame.runFrame(d);
            gui.show(d);
            sleeper.sleepFor(50);
        }
    }
}
