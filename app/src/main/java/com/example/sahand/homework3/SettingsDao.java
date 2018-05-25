package com.example.sahand.homework3;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SettingsDao {
    @Query("SELECT * FROM localsettings")
    List<LocalSettings> getAll();

    @Query("SELECT * FROM localsettings WHERE userId LIKE :id LIMIT 1")
    LocalSettings findById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(LocalSettings... localSettings);
}
