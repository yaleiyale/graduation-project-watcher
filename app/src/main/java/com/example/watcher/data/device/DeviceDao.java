package com.example.watcher.data.device;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;


@Dao
public interface DeviceDao {
    @Query("SELECT * FROM devices ")
    LiveData<List<Device>> getDevices();

    @Query("SELECT * FROM devices WHERE did = :deviceId")
    LiveData<Device> getDeviceById(int deviceId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertDevice(Device device);
}