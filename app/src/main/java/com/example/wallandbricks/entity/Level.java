package com.example.wallandbricks.entity;

import com.example.wallandbricks.Verification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Vilagra on 25.04.2017.
 */

public class Level {
    private int levelHeight;
    private int levelWidth;
    private int[][] levelField;

    private int filledWidthLeftOnTheFloor;
    private int filledWidthLeftOnTheCeil;
    private int filledWidthRightOnTheCeil;

    public Level(int height, int width) {
        this.levelHeight = height;
        this.levelWidth = width;
        levelField = new int[height][levelWidth];
    }


    public boolean tryToPlaceBrickAtThisLevel(Brick brick) {
        if (tryToPlaceAtFloor(brick) || tryToPaceAtCeil(brick)) {
            return true;
        }
        return false;
    }

    private boolean tryToPlaceAtFloor(Brick brick) {
        if (remainFloorWidth() >= brick.getWidth()) {
            placeAtFloor(brick);
            return true;
        }
        return false;
    }

    private boolean tryToPaceAtCeil(Brick brick) {
        if (remainCeilWidth() >= brick.getWidth() && !intersection(brick)) {
            placeAtCeil(brick);
            return true;
        }
        return false;
    }


    private int remainFloorWidth() {
        return levelWidth - filledWidthLeftOnTheFloor;
    }

    private int remainCeilWidth() {
        return levelWidth - filledWidthLeftOnTheCeil - filledWidthRightOnTheCeil;
    }

    private boolean intersection(Brick brick) {
        int startX = levelWidth - filledWidthRightOnTheCeil;
        int startY = -1;
        int finishX = startX - brick.getWidth();
        int finishY = startY + brick.getHeight();
        if (levelField[finishY][finishX] == 1) {
            return true;
        }
        return false;
    }


    private void placeAtFloor(Brick brick) {
        int startHeigthIndex = levelHeight - 1;
        int startWidthIndex = filledWidthLeftOnTheFloor;
        for (int i = 0; i < brick.getHeight(); i++) {
            for (int j = 0; j < brick.getWidth(); j++) {
                levelField[startHeigthIndex - i][startWidthIndex + j] = 1;
            }
        }
        filledWidthLeftOnTheFloor += brick.getWidth();
        if (brick.getHeight() == levelHeight) {
            filledWidthLeftOnTheCeil += brick.getWidth();
        }
    }

    private void placeAtCeil(Brick brick) {
        int startHeigthIndex = 0;
        int startWidthIndex = levelWidth - filledWidthRightOnTheCeil - 1;
        for (int i = 0; i < brick.getHeight(); i++) {
            for (int j = 0; j < brick.getWidth(); j++) {
                levelField[startHeigthIndex + i][startWidthIndex - j] = 1;
            }
        }
        filledWidthRightOnTheCeil += brick.getWidth();
    }


}
