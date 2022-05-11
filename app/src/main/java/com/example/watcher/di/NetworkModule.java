package com.example.watcher.di;

import com.example.watcher.api.NetService;

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
    NetService provideNetService(){
        return NetService.create();
    }
}
