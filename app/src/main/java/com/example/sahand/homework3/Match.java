package com.example.sahand.homework3;

import android.os.Parcel;
import android.os.Parcelable;

public class Match {
    private String name;
    private String imageUrl;
    private String uid;
    private String description;
    private String lat;
    private String longitude;
    private boolean liked;

    public Match() {
    }

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

    public String getLat() {
        return lat;
    }

    public String getLongitude() {
        return longitude;
    }

    public boolean getLiked() {
        return liked;
    }
}