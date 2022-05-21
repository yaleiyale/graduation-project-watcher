package com.example.watcher.di;

import android.content.Context;

import com.example.watcher.data.AppDatabase;
import com.example.watcher.data.device.DeviceDao;
import com.example.watcher.data.passRecord.PassRecordDao;
import com.example.watcher.data.person.PersonDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;


@InstallIn(SingletonComponent.class)
@Module
public class DatabaseModule {
    @Singleton
    @Provides
    AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context);
    }

    @Provides
    DeviceDao provideDeviceDao(AppDatabase appDatabase) {
        return appDatabase.deviceDao();
    }

    @Provides
    PersonDao providePersonDao(AppDatabase appDatabase) {
        return appDatabase.personDao();
    }

    @Provides
    PassRecordDao providePassRecordDao(AppDatabase appDatabase) {
        return appDatabase.passRecordDao();
    }

}
