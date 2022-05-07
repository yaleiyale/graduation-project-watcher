package com.example.watcher.data;


import static com.example.watcher.utilities.DatabaseConfig.DATABASE_NAME;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import com.example.watcher.data.device.Device;

import com.example.watcher.data.device.DeviceDao;
import com.example.watcher.data.passRecord.PassRecord;
import com.example.watcher.data.passRecord.PassRecordDao;
import com.example.watcher.data.person.Person;
import com.example.watcher.data.person.PersonDao;


@Database(entities = {Person.class, Device.class, PassRecord.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    public abstract PersonDao personDao();

    public abstract DeviceDao deviceDao();

    public abstract PassRecordDao passRecordDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = buildDatabase(context);
        }
        return instance;
    }

    private static AppDatabase buildDatabase(Context context) {

        return Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).//allowMainThreadQueries().
                addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }
        }).
                build();
    }
}
