package com.example.watcher.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "devices")
public class Device {
    @PrimaryKey(autoGenerate = true)
    public int id;

}
