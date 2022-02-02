package com.example.watcher.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "people")
public class Person {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String imgUrl;
}
