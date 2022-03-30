package com.example.watcher.data.device;


import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;


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

    public Flowable<Device> getDeviceById(int deviceId) {
        return deviceDao.getDeviceById(deviceId);
    }

    public Completable insert(String name) {
        Device device = new Device();
        device.customName = name;
        return deviceDao.insertDevice(device);
    }

    public Completable update(Device device)
    {
        return deviceDao.updateDevice(device);
    }

    public Completable delete(Device device)
    {
        return deviceDao.delete(device);
    }
}