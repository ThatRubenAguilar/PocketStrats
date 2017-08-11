package com.innovations.aguilar.pocketstrats.ui;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ruben on 7/28/2017.
 */

public class MapTipsChild implements Parcelable {


    // TODO: Maybe integrate https://github.com/grandstaish/paperparcel
    String mapTip;


    public String getMapTip() {
        return mapTip;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mapTip);
    }

    public MapTipsChild(String mapTip) {
        this.mapTip = mapTip;
    }

    protected MapTipsChild(Parcel in) {
        this.mapTip = in.readString();
    }

    public static final Creator<MapTipsChild> CREATOR = new Creator<MapTipsChild>() {
        @Override
        public MapTipsChild createFromParcel(Parcel source) {
            return new MapTipsChild(source);
        }

        @Override
        public MapTipsChild[] newArray(int size) {
            return new MapTipsChild[size];
        }
    };
}
