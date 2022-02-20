package com.example.watcher.data.device;


import androidx.lifecycle.LiveData;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

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

    public void insert() {
        Device device = new Device();
        device.customName = "网络摄像头";
        List<Device> l = new LinkedList<>();
        Device devices = new Device();
        devices.customName = "网络摄像头";
        l.add(device);
        l.add(devices);
        deviceDao.insertDevices(l);
    }
}