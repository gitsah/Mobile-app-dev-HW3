package com.example.sahand.homework3;

import android.os.Parcel;
import android.os.Parcelable;

public class Match implements Parcelable {
    private String name;
    private String imageUrl;
    private String uid;
    private String description;
    private boolean liked;

    public Match() {}

//    public Match(String name, String imageUrl, String uid, boolean liked) {
//        this.name = name;
//        this.imageUrl = imageUrl;
//        this.uid = uid;
//        this.liked = liked;
//
//        this.description = "Placeholder Description";
//    }

    protected Match(Parcel in) {
        name = in.readString();
        imageUrl = in.readString();
        uid = in.readString();
        description = in.readString();
        liked = in.readByte() != 0;
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

    public boolean getLiked() {
        return liked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeString(description);
        dest.writeByte((byte) (liked ? 1 : 0));
    }
}
