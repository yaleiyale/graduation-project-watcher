package com.example.watcher.data.passRecord;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "records")
public class PassRecord {
    @PrimaryKey(autoGenerate = true)
    public int recordId;
    public int deviceId;
    public int personId;
    public Boolean result;
    public String time;
    public String imageUrl;


}
