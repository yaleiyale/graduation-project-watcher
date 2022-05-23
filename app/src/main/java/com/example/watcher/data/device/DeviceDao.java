package com.example.watcher.data.device;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;


@Dao
public interface DeviceDao {
    @Query("SELECT * FROM devices order by deviceId desc ")
    LiveData<List<Device>> getDevices();

    @Query("SELECT * FROM devices WHERE deviceId = :deviceId")
    Flowable<Device> getDeviceById(int deviceId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertDevice(Device device);

    @Update
    Completable updateDevice(Device device);

    @Delete
    Completable delete(Device device);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<Device> devices);

    @Query("DELETE FROM devices WHERE deviceId>0")
    Completable deleteDevices();
}