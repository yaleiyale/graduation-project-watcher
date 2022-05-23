package com.example.watcher.data.device;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.watcher.api.DeviceNetService;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@Singleton
public
class DeviceRepository {
    public DeviceNetService deviceNetService;
    public DeviceList result;
    DeviceDao deviceDao;
    private MyCallback mCallback;

    @Inject
    DeviceRepository(DeviceDao deviceDao, DeviceNetService deviceNetService) {
        this.deviceDao = deviceDao;
        this.deviceNetService = deviceNetService;
    }

    public void setCallback(MyCallback callback) {
        this.mCallback = callback;
    }

    public LiveData<List<Device>> getDevices() {
        return deviceDao.getDevices();
    }

    public Flowable<Device> getDeviceById(int deviceId) {
        return deviceDao.getDeviceById(deviceId);
    }

    public void updateDeviceNet(Device device) {
        Gson gson = new Gson();
        String json_device = gson.toJson(device);
        Call<Boolean> call = deviceNetService.updateDevice(json_device);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {

            }
        });
    }

    public void deleteDeviceNet(Device device) {
        Gson gson = new Gson();
        String json_device = gson.toJson(device);
        Call<Boolean> call = deviceNetService.deleteDevice(json_device);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {

            }
        });
    }


    public void refreshLocal() {
        Call<DeviceList> call = deviceNetService.getAllDevices();
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

    public Completable deleteAll() {
        return deviceDao.deleteDevices();
    }

    public interface MyCallback {
        void OnSuccess();

        void OnFail();
    }
}