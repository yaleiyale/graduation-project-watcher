package com.example.watcher.data.passRecord;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "records")
public class PassRecord {
    @PrimaryKey(autoGenerate = true)
    public int rid;
    public int did;
    public int pid;
    public int result;
    public String time;

}
