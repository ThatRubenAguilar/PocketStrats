package com.innovations.aguilar.pocketstrats.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class TipsChild implements Parcelable {



    private String message;
    private boolean isSection;

    public String getMessage() {
        return message;
    }

    public boolean isSection() {
        return isSection;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeByte(this.isSection ? (byte) 1 : (byte) 0);
    }

    public TipsChild() {
    }

    public TipsChild(String message, boolean isSection) {
        this.message = message;
        this.isSection = isSection;
    }

    protected TipsChild(Parcel in) {
        this.message = in.readString();
        this.isSection = in.readByte() != 0;
    }

    public static final Creator<TipsChild> CREATOR = new Creator<TipsChild>() {
        @Override
        public TipsChild createFromParcel(Parcel source) {
            return new TipsChild(source);
        }

        @Override
        public TipsChild[] newArray(int size) {
            return new TipsChild[size];
        }
    };
}
