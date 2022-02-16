package com.example.watcher.data.device;


import androidx.lifecycle.LiveData;

import com.example.watcher.data.person.Person;
import com.example.watcher.data.person.PersonDao;

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
        l.add(device);
        deviceDao.insertAll(l);
    }
}