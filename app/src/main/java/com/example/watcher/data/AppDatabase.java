package com.example.watcher.data;


import static com.example.watcher.utilities.DatabaseConfig.DATABASE_NAME;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.watcher.data.model.Device;
import com.example.watcher.data.model.Person;


@Database(entities = {Person.class, Device.class}, version = 1,exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    abstract PersonDao personDao();

    abstract DeviceDao deviceDao();

    private static volatile AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = buildDatabase(context);
        }
        return instance;
    }

    private static AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
    }
}
