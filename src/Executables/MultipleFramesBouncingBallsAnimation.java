package Executables;

import Game.Frame;
import Geometry.Point;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class MultipleFramesBouncingBallsAnimation {

    public static void main(String[] args) {
        Frame grayFrame = new Frame(new Point(50,50),new Point(500,500), Color.GRAY);
        Frame yellowFrame = new Frame(new Point(450,450),new Point(600,600), Color.YELLOW);
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
        int i ;
        for (i = 0;  i<sizeList.size()/2 ; i++) {
            grayFrame.addBall(sizeList.get(i));
        }
        for (;i<sizeList.size();i++){
            yellowFrame.addBall(sizeList.get(i));
        }
        GUI gui = new GUI("Multiple Frames Bouncing Balls Animation", 600,600);
        Sleeper sleeper = new Sleeper();
        while (true){
            DrawSurface d = gui.getDrawSurface();
            grayFrame.runFrame(d);
            yellowFrame.runFrame(d);
            gui.show(d);
            sleeper.sleepFor(50);
        }

    }
}
