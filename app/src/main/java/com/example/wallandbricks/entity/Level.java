package com.example.wallandbricks.entity;

import com.example.wallandbricks.Verification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Vilagra on 25.04.2017.
 */

public class Level {
    public int levelHeight;
    private int levelWidth;
    public int[][] levelField;

    private int filledWidthLeftOnTheFloor;
    private int filledWidthLeftOnTheCeil;
    private int filledWidthRightOnTheCeil;

    public Level(int height, int width) {
        this.levelHeight = height;
        this.levelWidth = width;
        levelField=new int[height][levelWidth];
    }


    public boolean tryToPlaceBrickAtThisLevel(Brick brick) {
        if(tryToPlaceAtFloor(brick)||tryToPaceAtCeil(brick)){
            return true;
        }
        return false;
    }

    private boolean tryToPlaceAtFloor(Brick brick) {
        if (remainFloorWidth()>=brick.getWidth()){
            placeAtFloor(brick);
            return true;
        }
        return false;
    }

    private boolean tryToPaceAtCeil(Brick brick){
        if(remainCeilWidth()>=brick.getWidth()&&!intersection(brick)){
            placeAtCeil(brick);
            return true;
        }
        return false;
    }



    private int remainFloorWidth() {
        return levelWidth-filledWidthLeftOnTheFloor;
    }

    private int remainCeilWidth() {
        return levelWidth-filledWidthLeftOnTheCeil-filledWidthRightOnTheCeil;
    }

    private boolean intersection(Brick brick){
        int startX = levelWidth-filledWidthRightOnTheCeil;
        int startY = -1;
        int finishX = startX-brick.getWidth();
        int finishY = startY+brick.getHeight();
        if(levelField[finishY][finishX]==1){
            return true;
        }
        return false;
    }


    private void placeAtFloor(Brick brick){
        int startHeigthIndex = levelHeight-1;
        int startWidthIndex = filledWidthLeftOnTheFloor;
        for (int i = 0; i < brick.getHeight(); i++) {
            for (int j = 0; j < brick.getWidth(); j++) {
                levelField[startHeigthIndex-i][startWidthIndex+j]=1;
            }
        }
        filledWidthLeftOnTheFloor+=brick.getWidth();
        if(brick.getHeight()==levelHeight){
            filledWidthLeftOnTheCeil+=brick.getWidth();
        }
    }

    private void placeAtCeil(Brick brick) {
        int startHeigthIndex = 0;
        int startWidthIndex = levelWidth-filledWidthRightOnTheCeil-1;
        for (int i = 0; i < brick.getHeight(); i++) {
            for (int j = 0; j < brick.getWidth(); j++) {
                levelField[startHeigthIndex+i][startWidthIndex-j]=1;
            }
        }
        filledWidthRightOnTheCeil+=brick.getWidth();
    }

    public static void main(String[] args) {
        Level level = new Level(3,6);
        level.tryToPlaceBrickAtThisLevel(new Brick(3,2));
        level.tryToPlaceBrickAtThisLevel(new Brick(2,3));
        level.tryToPlaceBrickAtThisLevel(new Brick(2,1));
        level.tryToPlaceBrickAtThisLevel(new Brick(1,2));
        level.tryToPlaceBrickAtThisLevel(new Brick(1,1));
        //level.tryToPlaceBrickAtThisLevel(new Brick(1,1));
        ArrayList<Brick> list  = new ArrayList<>();
        HashMap<Brick,Integer> map = new HashMap<>();
        //list.add(new Brick(3,3));
        list.add(new Brick(5,2));
        list.add(new Brick(3,2));
        list.add(new Brick(2,2));
        //list.add(new Brick(1,1));
        map.put(new Brick(5,2),1);
        map.put(new Brick(3,2),1);
        map.put(new Brick(2,2),1);
        //map.put(new Brick(1,2),2);
        //map.put(new Brick(1,1),3);
        Verification verification = new Verification(5,5,map,list);
        System.out.println(verification.check());

    }

}
