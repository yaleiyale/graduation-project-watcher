package com.example.watcher.data.device;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "devices")
public class Device {
    @PrimaryKey(autoGenerate = true)
    public int did;
    public String customName;
}

