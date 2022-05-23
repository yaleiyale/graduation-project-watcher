package com.example.watcher.ui.device;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.watcher.data.device.Device;
import com.example.watcher.data.device.DeviceRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class DeviceListViewModel extends ViewModel {
    DeviceRepository deviceRepository;
    LiveData<List<Device>> devices;
    CompositeDisposable mDisposable = new CompositeDisposable();

    @Inject
    public DeviceListViewModel(DeviceRepository repository) {

        this.deviceRepository = repository;
        initData();
        devices = deviceRepository.getDevices();
    }

    public void initData() {
//        mDisposable.add(deviceRepository.deleteAll()
//                .subscribeOn(Schedulers.io())
//                .subscribe(() -> {
//                }, throwable -> Log.e("no detail", "Unable to get detail", throwable)));
        deviceRepository.setCallback(new DeviceRepository.MyCallback() {
            @Override
            public void OnSuccess() {
                mDisposable.add(deviceRepository.insertAll()
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> devices = deviceRepository.getDevices(), throwable -> Log.e("no detail", "Unable to get detail", throwable)));
            }

            @Override
            public void OnFail() {

            }
        });
    }
}
