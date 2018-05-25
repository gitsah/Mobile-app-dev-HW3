package com.example.sahand.homework3;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class LocalSettings {
    @PrimaryKey
    private int userId;
    @ColumnInfo(name = "reminder_time")
    private String reminderTime;
    @ColumnInfo(name = "max_distance")
    private String maxDistance;
    @ColumnInfo(name = "gender")
    private String gender;
    @ColumnInfo(name = "gender_interest")
    private String genderInterest;
    @ColumnInfo(name = "age_min_interest")
    private String ageMinInterest;
    @ColumnInfo(name = "age_max_interest")
    private String ageMaxInterest;
    @ColumnInfo(name = "privacy")
    private boolean privacy;

    public LocalSettings() {}

    @Ignore
    public LocalSettings(int userId, String reminderTime, String maxDistance, String gender,
                         String genderInterest, String ageMinInterest, String ageMaxInterest, boolean privacy) {
        this.userId = userId;
        this.reminderTime = reminderTime;
        this.maxDistance = maxDistance;
        this.gender = gender;
        this.genderInterest = genderInterest;
        this.ageMinInterest = ageMinInterest;
        this.ageMaxInterest = ageMaxInterest;
        this.privacy = privacy;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(String maxDistance) {
        this.maxDistance = maxDistance;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGenderInterest() {
        return genderInterest;
    }

    public void setGenderInterest(String genderInterest) {
        this.genderInterest = genderInterest;
    }

    public String getAgeMinInterest() {
        return ageMinInterest;
    }

    public void setAgeMinInterest(String ageMinInterest) {
        this.ageMinInterest = ageMinInterest;
    }

    public String getAgeMaxInterest() {
        return ageMaxInterest;
    }

    public void setAgeMaxInterest(String ageMaxInterest) {
        this.ageMaxInterest = ageMaxInterest;
    }

    public boolean isPrivacy() {
        return privacy;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }
}
