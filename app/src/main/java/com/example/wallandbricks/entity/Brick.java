package com.example.wallandbricks.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Vilagra on 25.04.2017.
 */

public class Brick implements Parcelable,Serializable{
    private int height;
    private int width;

    public Brick(int height, int width) {
        this.height = height;
        this.width = width;
    }

    protected Brick(Parcel in) {
        height = in.readInt();
        width = in.readInt();
    }

    public static final Creator<Brick> CREATOR = new Creator<Brick>() {
        @Override
        public Brick createFromParcel(Parcel in) {
            return new Brick(in);
        }

        @Override
        public Brick[] newArray(int size) {
            return new Brick[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(height);
        dest.writeInt(width);
    }
}
