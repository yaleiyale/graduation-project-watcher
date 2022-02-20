package com.example.watcher.data.device;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


@Dao
public interface DeviceDao {
    @Query("SELECT * FROM devices ")
    LiveData<List<Device>> getDevices();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDevices(List<Device> devices);

}