package com.example.watcher.di;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.example.watcher.data.AppDatabase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;


public class DatabaseModule {

    AppDatabase provideAppDatabase(Context context) {
        return AppDatabase.getInstance(context);
    }


}
