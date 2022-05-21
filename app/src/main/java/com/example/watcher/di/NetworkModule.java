package com.example.watcher.di;

import com.example.watcher.api.DeviceNetService;
import com.example.watcher.api.PassRecordNetService;
import com.example.watcher.api.PersonNetService;
import com.example.watcher.api.UserNetService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {
    @Singleton
    @Provides
    UserNetService provideUserNetService() {
        return UserNetService.create();
    }

    @Provides
    PersonNetService providePersonNetService() {
        return PersonNetService.create();
    }

    @Provides
    DeviceNetService provideDeviceNetService() {
        return DeviceNetService.create();
    }

    @Provides
    PassRecordNetService providePassRecordNetService() {
        return PassRecordNetService.create();
    }
}
