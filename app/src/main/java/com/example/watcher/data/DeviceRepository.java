package com.example.watcher.data;

public class DeviceRepository {
    private static volatile DeviceRepository instance;
    private DeviceDataSource dataSource;

    private DeviceRepository(DeviceDataSource dataSource) {
        this.dataSource = dataSource;
    }
    public static DeviceRepository getInstance(DeviceDataSource dataSource) {
        if (instance == null) {
            instance = new DeviceRepository(dataSource);
        }
        return instance;
    }

}
