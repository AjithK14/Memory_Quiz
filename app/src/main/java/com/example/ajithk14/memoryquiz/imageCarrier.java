package com.example.ajithk14.memoryquiz;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ajithk14 on 1/11/2018.
 */

public class imageCarrier implements Parcelable {
    byte[] myImg;
    public imageCarrier(byte[] arr)
    {
        myImg=arr;

    }

    protected imageCarrier(Parcel in) {
        myImg = in.createByteArray();
    }
    public byte[] getArray()
    {
        return myImg;
    }

    public static final Creator<imageCarrier> CREATOR = new Creator<imageCarrier>() {
        @Override
        public imageCarrier createFromParcel(Parcel in) {
            return new imageCarrier(in);
        }

        @Override
        public imageCarrier[] newArray(int size) {
            return new imageCarrier[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(myImg);
    }
}
