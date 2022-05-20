package com.example.watcher.data.device;


import androidx.lifecycle.LiveData;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.watcher.api.NetService;
import com.example.watcher.ui.device.DeviceListViewModel;
import com.google.gson.JsonObject;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Flowable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@Singleton
public
class DeviceRepository {
    DeviceDao deviceDao;
    NetService netService;
    DeviceList result;

    public interface MyCallback {
        void OnSuccess();

        void OnFail();
    }

    private MyCallback mCallback;

    public void setCallback(MyCallback callback) {
        this.mCallback = callback;
    }

    @Inject
    DeviceRepository(DeviceDao deviceDao, NetService netService) {
        this.deviceDao = deviceDao;
        this.netService = netService;
    }

    public LiveData<List<Device>> getDevices() {
        return deviceDao.getDevices();
    }

    public Flowable<Device> getDeviceById(int deviceId) {
        return deviceDao.getDeviceById(deviceId);
    }

    public void refreshLocal() {
        Call<DeviceList> call = netService.getAllDevices();
        call.enqueue(new Callback<DeviceList>() {
            @Override
            public void onResponse(@androidx.annotation.NonNull Call<DeviceList> call, @androidx.annotation.NonNull Response<DeviceList> response) {
                assert response.body() != null;
                result = response.body();
                mCallback.OnSuccess();
            }

            @Override
            public void onFailure(@androidx.annotation.NonNull Call<DeviceList> call, @androidx.annotation.NonNull Throwable t) {

            }
        });
    }

    public Completable insertAll() {
        return deviceDao.insertAll(result.devices);
    }


    public Completable insert(String name) {
        Device device = new Device();
        device.customName = name;
        return deviceDao.insertDevice(device);
    }

    public Completable update(Device device) {
        return deviceDao.updateDevice(device);
    }

    public Completable delete(Device device) {
        return deviceDao.delete(device);
    }
}