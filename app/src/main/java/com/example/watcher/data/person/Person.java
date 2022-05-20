package com.example.watcher.data.person;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "people")
public class Person {
    @PrimaryKey(autoGenerate = true)
    public int personId;
    public String personName;
    public String imageUrl;
    public Integer power;
}
