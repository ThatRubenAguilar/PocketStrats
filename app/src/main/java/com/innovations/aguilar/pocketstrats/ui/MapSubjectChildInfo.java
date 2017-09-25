package com.innovations.aguilar.pocketstrats.ui;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.collect.Lists;

import java.util.List;

public class MapSubjectChildInfo implements Parcelable {
    // TODO: Maybe integrate https://github.com/grandstaish/paperparcel
    List<TipsChild> tips;


    List<HeroPickTipsChild> picks;

    public List<TipsChild> getTips() {
        return tips;
    }

    public List<HeroPickTipsChild> getPicks() {
        return picks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.tips);
        dest.writeTypedList(this.picks);
    }

    public MapSubjectChildInfo() {
        tips = Lists.newArrayList();
        picks = Lists.newArrayList();
    }

    protected MapSubjectChildInfo(Parcel in) {
        this.tips = in.createTypedArrayList(TipsChild.CREATOR);
        this.picks = in.createTypedArrayList(HeroPickTipsChild.CREATOR);
    }

    public static final Creator<MapSubjectChildInfo> CREATOR = new Creator<MapSubjectChildInfo>() {
        @Override
        public MapSubjectChildInfo createFromParcel(Parcel source) {
            return new MapSubjectChildInfo(source);
        }

        @Override
        public MapSubjectChildInfo[] newArray(int size) {
            return new MapSubjectChildInfo[size];
        }
    };
}



