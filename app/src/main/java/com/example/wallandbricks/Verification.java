package com.example.wallandbricks;

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
     * List of all brick widths sorted in descending order
     */
    private List<Integer> listSortedBricksByWidth;
    /**
     * Map, where keys are the brick widths and the values are their amounts
     */
    private Map<Integer,Integer> mapAmountOfBricksCertainWidth;
    /**
     * Comparator for sorting in descending order
     */
    private Comparator comparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return (o1 < o2) ? 1 : ((o1 == o2) ? 0 : -1);
        }
    };

    /**
     * Constructs the Verification object with data needed
     * for calculation
     *
     * @param widthOfWall wall width
     * @param heightOfWall wall height
     * @param mapAmountOfBricksCertainWidth Map, where keys are the brick widths and the values are their amounts
     * @param listOfBricks List of all brick widths sorted in the order they were added
     *
     */
    public Verification(int widthOfWall, int heightOfWall, Map<Integer, Integer> mapAmountOfBricksCertainWidth, List<Integer> listOfBricks) {
        this.heightOfWall = heightOfWall;
        this.mapAmountOfBricksCertainWidth = new HashMap<>(mapAmountOfBricksCertainWidth);
        this.widthOfWall = widthOfWall;
        listSortedBricksByWidth =new ArrayList<>(listOfBricks);
        Collections.sort(listSortedBricksByWidth,comparator);  //list of bricks starting from the biggest
    }

    /**
     * Verifies if all the bricks stored in {@code mapAmountOfBricksCertainWidth} fit
     * in the wall of specified height and width. If they all fit, the result is {@code true}.
     *
     * @return  {@code true} if all the bricks fit in the wall, {@code false} otherwise
     */
    public boolean check(){
        if(listSortedBricksByWidth.get(0)>widthOfWall||sumWidthOfBricks()>widthOfWall* heightOfWall){ //quick check. if the biggest brick is bigger than the wall width or the wall area is less than bricks sum
            return false;
        }
        for (int i = 0; i < heightOfWall; i++) {  //then we go through all the rows in the wall
            int row=widthOfWall;
            while (true){
                int bricksWhichUsingNow;    //we look for the biggest brick that we can put in this row
                if(listSortedBricksByWidth.get(0)<=row){  //try to use the biggest brick
                    bricksWhichUsingNow=listSortedBricksByWidth.get(0);
                    row-=bricksWhichUsingNow;
                }else {
                    int indexOfBricks = Collections.binarySearch(listSortedBricksByWidth,row,comparator);  //we look for the the biggest brick which can fit in this row
                    if(indexOfBricks>0){                                     //if the index in more than 0, it means that we found the brick which will fill the space left in the row
                        bricksWhichUsingNow=listSortedBricksByWidth.get(indexOfBricks);
                        row-=bricksWhichUsingNow;
                    }else{                                                   // if the index is less than 0, we determine the position of the biggest brick which fit in the space left in the row
                        indexOfBricks =Math.abs(indexOfBricks)-1;
                        if(indexOfBricks<listSortedBricksByWidth.size()){
                            bricksWhichUsingNow=listSortedBricksByWidth.get(indexOfBricks);
                            row-=bricksWhichUsingNow;
                        }
                        else {        //If none of the bricks fits into the remaining space, we go to the next row
                            break;
                        }
                    }
                }
                mapAmountOfBricksCertainWidth.put(bricksWhichUsingNow,mapAmountOfBricksCertainWidth.get(bricksWhichUsingNow)-1);  //Reducing the number of bricks that we used
                if(mapAmountOfBricksCertainWidth.get(bricksWhichUsingNow)==0){  //If the amount of bricks of a given width is 0, we remove these bricks
                    mapAmountOfBricksCertainWidth.remove(bricksWhichUsingNow);
                    listSortedBricksByWidth.remove((Object)bricksWhichUsingNow);
                }
                if(mapAmountOfBricksCertainWidth.isEmpty()){  //if all bricks are used, method returns true
                    return true;
                }
                if(row==0){ //if we completed this row, we go to the next
                    break;
                }

            }
        }
        return mapAmountOfBricksCertainWidth.isEmpty(); //if the bricks are left then false
    }



    private int sumWidthOfBricks(){
        int result=0;
        for (Map.Entry<Integer, Integer> entry : mapAmountOfBricksCertainWidth.entrySet()) {
            result+=entry.getKey()*entry.getValue();
        }
        return result;
    }



}
