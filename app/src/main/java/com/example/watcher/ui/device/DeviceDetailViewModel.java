package com.example.watcher.ui.device;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.watcher.data.device.Device;
import com.example.watcher.data.device.DeviceRepository;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class DeviceDetailViewModel extends ViewModel {
    DeviceRepository deviceRepository;
    int deviceId;
    Device device;
    CompositeDisposable mDisposable = new CompositeDisposable();

    public interface Callback {
        void OnLoad();

        void OnDelete(View view);
    }

    private Callback mCallback;

    @Inject
    public DeviceDetailViewModel(@NotNull SavedStateHandle savedStateHandle, DeviceRepository repository) {
        super();
        this.deviceRepository = repository;
        this.deviceId = DeviceDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).getMyArg();
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
        Load();
    }

    public void Load() {
        mDisposable.add(deviceRepository.getDeviceById(deviceId)
                .subscribeOn(Schedulers.io())
                .subscribe(temp -> {
                            device = temp;
                            mCallback.OnLoad();
                        },
                        throwable -> Log.e("no detail", "Unable to get detail", throwable)));
    }

    public void adjust(String name) {
        device.customName = name;
        mDisposable.add(deviceRepository.update(device)
                .subscribeOn(Schedulers.io())
                .subscribe(this::Load,
                        throwable -> Log.e("no detail", "Unable to get detail", throwable)));
    }

    public void delete(View view) {
        mCallback.OnDelete(view);
        mDisposable.add(deviceRepository.delete(device)
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {},
                        throwable -> Log.e("no detail", "Unable to get detail", throwable)));
    }
}
