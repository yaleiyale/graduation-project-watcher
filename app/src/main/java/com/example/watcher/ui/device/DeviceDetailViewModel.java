package com.example.watcher.ui.device;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.watcher.api.DeviceNetService;
import com.example.watcher.data.device.Device;
import com.example.watcher.data.device.DeviceRepository;
import com.example.watcher.data.passRecord.PassRecordRepository;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

@HiltViewModel
public class DeviceDetailViewModel extends ViewModel {
    private final Handler handler = new Handler(Looper.getMainLooper());
    DeviceRepository deviceRepository;
    PassRecordRepository recordRepository;
    int deviceId;
    Device device;
    DeviceNetService netService;
    CompositeDisposable mDisposable = new CompositeDisposable();
    private Callback mCallback;

    @Inject
    public DeviceDetailViewModel(@NotNull SavedStateHandle savedStateHandle, DeviceNetService netService, DeviceRepository repository, PassRecordRepository recordRepository) {
        super();
        this.netService = netService;
        this.deviceRepository = repository;
        this.recordRepository = recordRepository;
        this.deviceId = DeviceDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).getMyArg();
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
        Load();
    }

    public void Load() {
        mDisposable.add(deviceRepository.getDeviceById(deviceId)
                .subscribeOn(Schedulers.io())
                .subscribe(temp -> mCallback.OnLoad(temp),
                        throwable -> Log.e("no detail", "Unable to get detail", throwable)));
    }

    public void adjust(String name) {
        device.customName = name;
        deviceRepository.updateDeviceNet(device);
        mDisposable.add(deviceRepository.update(device)
                .subscribeOn(Schedulers.io())
                .subscribe(this::Load,
                        throwable -> Log.e("no detail", "Unable to get detail", throwable)));
    }

    public void delete(View view) {
        deviceRepository.deleteDeviceNet(device);
        mDisposable.add(
                deviceRepository.delete(device)
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(() -> handler.post(() -> mCallback.OnDelete(view)),
                                throwable -> Log.e("no detail", "Unable to get detail", throwable)));
        mDisposable.add(recordRepository.deleteByDeviceId(deviceId)
                .subscribeOn(Schedulers.newThread())
                .subscribe(() -> {

                        },
                        throwable -> Log.e("no detail", "Unable to get detail", throwable)));


    }

    public void open() {
        Call<Boolean> call = netService.openDoor(deviceId);
        call.enqueue(new retrofit2.Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {

            }
        });
    }

    public interface Callback {
        void OnLoad(Device device);

        void OnDelete(View view);
    }
}
