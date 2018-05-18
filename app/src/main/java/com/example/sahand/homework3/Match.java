package com.example.sahand.homework3;

import android.os.Parcel;
import android.os.Parcelable;

public class Match implements Parcelable {
    private String name;
    private String imageUrl;
    private String uid;
    private String description;

    public boolean done;

    public Match() {}

    public Match(String name, String imageUrl, String uid) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.uid = uid;

        this.description = "Placeholder Description";
    }

    protected Match(Parcel in) {
        name = in.readString();
        imageUrl = in.readString();
        uid = in.readString();
        description = in.readString();
        done = in.readByte() != 0;
    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUid() {
        return uid;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (done ? 1 : 0));
    }
}
