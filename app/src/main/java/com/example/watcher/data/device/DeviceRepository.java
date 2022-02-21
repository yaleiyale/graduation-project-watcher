package com.example.watcher.data.device;


import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;


@Singleton
public
class DeviceRepository {
    DeviceDao deviceDao;

    @Inject
    DeviceRepository(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    public LiveData<List<Device>> getDevices() {
        return deviceDao.getDevices();
    }

    public Completable insert() {
        Device device = new Device();
        device.customName = "网络摄像头";
        return deviceDao.insertDevice(device);
    }
}