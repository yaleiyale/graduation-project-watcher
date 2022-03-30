package com.example.watcher.ui.device;

import android.util.Log;

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
    public Device device;
    CompositeDisposable mDisposable = new CompositeDisposable();

    public interface Callback {
        void DoWork();
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
        work();
    }

    public void work() {
        mDisposable.add(deviceRepository.getDeviceById(deviceId)
                .subscribeOn(Schedulers.io())
                .subscribe(temp -> {
                            device = temp;
                            mCallback.DoWork();
                        },
                        throwable -> Log.e("no detail", "Unable to get detail", throwable)));
    }

}
