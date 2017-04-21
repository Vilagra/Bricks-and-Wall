package com.example.wallandbricks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Vilagra on 21.04.2017.
 */

public class Verification {
    private int widthOfWall;
    private int heigthOfWall;
    List<Integer> listSortedBricksByWidth;
    Map<Integer,Integer> mapAmountOfBricksCertainWidth;
    Comparator comparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return (o1 < 02) ? 1 : ((o1 == o2) ? 0 : -1);
        }
    };

    public Verification( int widthOfWall,int heigthOfWall, Map<Integer, Integer> mapAmountOfBricksCertainWidth, List<Integer> listOfBricks) {
        this.heigthOfWall = heigthOfWall;
        this.mapAmountOfBricksCertainWidth = new HashMap<>(mapAmountOfBricksCertainWidth);
        this.widthOfWall = widthOfWall;
        listSortedBricksByWidth =new ArrayList<>(listOfBricks);
        Collections.sort(listSortedBricksByWidth,comparator);  //list of bricks starting from the biggest
    }

    public boolean check(){
        if(listSortedBricksByWidth.get(0)>widthOfWall||sumWidthOfBricks()>widthOfWall*heigthOfWall){ //quick check
            return false;
        }
        for (int i = 0; i < heigthOfWall; i++) {  //We go through all the rows in the wall
            int row=widthOfWall;
            while (true){
                int bricksWhichUsingNow;    //Looking for the largest brick that we can put in this row
                if(listSortedBricksByWidth.get(0)<=row){  //try to use the biggest
                    bricksWhichUsingNow=listSortedBricksByWidth.get(0);
                    row-=bricksWhichUsingNow;
                }else {
                    int indexOfBricks = Collections.binarySearch(listSortedBricksByWidth,row,comparator);  //looking for the the bigest brick which can fit in this row
                    if(indexOfBricks>0){
                        bricksWhichUsingNow=listSortedBricksByWidth.get(indexOfBricks);
                        row-=bricksWhichUsingNow;
                    }else{
                        indexOfBricks =Math.abs(indexOfBricks)-1;
                        if(indexOfBricks<listSortedBricksByWidth.size()){
                            bricksWhichUsingNow=listSortedBricksByWidth.get(indexOfBricks);
                            row-=bricksWhichUsingNow;
                        }
                        else {        //If not one of the bricks does not fit into the remaining space, go to the next row
                            break;
                        }
                    }
                }
                mapAmountOfBricksCertainWidth.put(bricksWhichUsingNow,mapAmountOfBricksCertainWidth.get(bricksWhichUsingNow)-1);  //Reduce the number of bricks that we used
                if(mapAmountOfBricksCertainWidth.get(bricksWhichUsingNow)==0){  //If the number of bricks of a given width is 0, remove these bricks
                    mapAmountOfBricksCertainWidth.remove(bricksWhichUsingNow);
                    listSortedBricksByWidth.remove((Object)bricksWhichUsingNow);
                }
                if(mapAmountOfBricksCertainWidth.isEmpty()){  //if used all bricks return true
                    return true;
                }
                if(row==0){ //if we completed this row, go to the next
                    break;
                }

            }
        }
        //System.out.println(mapAmountOfBricksCertainWidth);
        return mapAmountOfBricksCertainWidth.isEmpty(); //If the bricks are left then false
    }



    private int sumWidthOfBricks(){
        int result=0;
        for (Map.Entry<Integer, Integer> entry : mapAmountOfBricksCertainWidth.entrySet()) {
            result+=entry.getKey()*entry.getValue();
        }
        return result;
    }

    public static void main(String[] args) {
        Map<Integer,Integer> map = new HashMap();
        List<Integer> list = new ArrayList<>();
        list.add(3);
        map.put(3,4);
        list.add(5);
        map.put(5,2);
        list.add(1);
        map.put(1,8);

        System.out.println(new Verification(0,0,new HashMap<Integer, Integer>(map),list).check());
        System.out.println(new Verification(5,3,new HashMap<Integer, Integer>(map),list).check());
        System.out.println(new Verification(5,1,new HashMap<Integer, Integer>(map),list).check());
        System.out.println(new Verification(5,4,new HashMap<Integer, Integer>(map),list).check());
        System.out.println(new Verification(5,5,new HashMap<Integer, Integer>(map),list).check());
        System.out.println(new Verification(5,6,new HashMap<Integer, Integer>(map),list).check());
    }
}
