package com.example.wallandbricks;

import com.example.wallandbricks.entity.Brick;
import com.example.wallandbricks.entity.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vilagra on 21.04.2017.
 * Class to verify if the bricks can fit in the wall,
 * based on the data provided in constructor.
 *
 * For verification check() method must be called
 */

public class Verification {
    private int widthOfWall;
    private int heightOfWall;
    /**
     * List of all brick height sorted in descending order
     */
    private List<Brick> sortedBricksByHeigthList;
    /**
     * Map, where keys are the specific brick and the values are their amounts
     */
    private Map<Brick,Integer> amountOfSpecificBricksMap;
    /**
     * Comparator for sorting in descending order
     */
    private Comparator comparator = new Comparator<Brick>() {
        @Override
        public int compare(Brick o1, Brick o2) {
            return (o1.getHeight() < o2.getHeight()) ? 1 : ((o1.getHeight() == o2.getHeight()) ? o2.getWidth()-o1.getWidth() : -1);
        }
    };

    /**
     * Constructs the Verification object with data needed
     * for calculation
     *
     * @param widthOfWall wall width
     * @param heightOfWall wall height
     * @param amountOfSpecificBricksMap Map, where keys are the specific brick and the values are their amounts
     * @param listOfBricks List of all brick sorted in the order they were added
     *
     */
    public Verification(int widthOfWall, int heightOfWall, Map<Brick, Integer> amountOfSpecificBricksMap, List<Brick> listOfBricks) {
        this.heightOfWall = heightOfWall;
        this.amountOfSpecificBricksMap = new HashMap<>(amountOfSpecificBricksMap);
        this.widthOfWall = widthOfWall;
        sortedBricksByHeigthList =new ArrayList<>(listOfBricks);
        Collections.sort(sortedBricksByHeigthList,comparator);  //list of bricks starting from the heightest
    }

    /**
     * Verifies if all the bricks stored in {@code amountOfSpecificBricksMap} fit
     * in the wall of specified height and width. If they all fit, the result is {@code true}.
     *
     * @return  {@code true} if all the bricks fit in the wall, {@code false} otherwise
     */
    public boolean check(){
        if(quickCheck()){
            return false;
        }
        List<Level> levels = new ArrayList<>();
        int remainHeigth=heightOfWall;
        for (Brick brick : sortedBricksByHeigthList) {
            int currentBrickAmount = amountOfSpecificBricksMap.get(brick);
            for (int i = 0; i < currentBrickAmount; i++) {
                boolean isPlaced = false;
                for (Level level : levels) {
                    if(level.tryToPlaceBrickAtThisLevel(brick)){
                        isPlaced=true;
                       break;
                    }
                }
                if(!isPlaced){
                    if(remainHeigth>=brick.getHeight()){
                        Level level = new Level(brick.getHeight(),widthOfWall);
                        level.tryToPlaceBrickAtThisLevel(brick);
                        levels.add(level);
                        remainHeigth-=brick.getHeight();
                    }
                    else{
                        return false;
                    }
                }
            }
        }
        return true;

    }



    private boolean quickCheck(){
        if(sortedBricksByHeigthList.get(0).getHeight()>heightOfWall){
            return true;
        }
        int allBricksArea=0;
        for (Map.Entry<Brick, Integer> entry : amountOfSpecificBricksMap.entrySet()) {
            if(entry.getKey().getWidth()>widthOfWall){
                return true;
            }
            allBricksArea+=entry.getKey().getBrickArea()*entry.getValue();
        }
        return allBricksArea>widthOfWall*heightOfWall;
    }



}
