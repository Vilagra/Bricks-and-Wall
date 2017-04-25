package com.example.wallandbricks.entity;

/**
 * Created by Vilagra on 25.04.2017.
 */

public class Brick {
    private int height;
    private int width;

    public Brick(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getBrickArea(){
        return width*height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brick brick = (Brick) o;

        if (height != brick.height) return false;
        return width == brick.width;

    }

    @Override
    public int hashCode() {
        int result = height;
        result = 31 * result + width;
        return result;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
