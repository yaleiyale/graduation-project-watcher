package com.example.watcher.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.watcher.data.model.Device;

import java.util.List;
@Dao
public interface DeviceDao {
    @Query("SELECT * FROM devices ORDER BY id")
    LiveData<List<Device>> getDevices();
}
