package com.innovations.aguilar.pocketstrats.ui;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.collect.Lists;

import java.util.List;

public class HeroPickTipsChild implements Parcelable {


    private List<String> messages;
    private int heroId;


    public List<String> getMessages() {
        return messages;
    }

    public int getHeroId() {
        return heroId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.messages);
        dest.writeInt(this.heroId);
    }

    public HeroPickTipsChild() {
        messages = Lists.newArrayList();
        heroId = 0;
    }
    public HeroPickTipsChild(List<String> messages, int heroId) {
        this.messages = messages;
        this.heroId = heroId;
    }

    protected HeroPickTipsChild(Parcel in) {
        this.messages = in.createStringArrayList();
        this.heroId = in.readInt();
    }

    public static final Creator<HeroPickTipsChild> CREATOR = new Creator<HeroPickTipsChild>() {
        @Override
        public HeroPickTipsChild createFromParcel(Parcel source) {
            return new HeroPickTipsChild(source);
        }

        @Override
        public HeroPickTipsChild[] newArray(int size) {
            return new HeroPickTipsChild[size];
        }
    };
}
