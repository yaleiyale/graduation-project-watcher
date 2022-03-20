package com.example.watcher.ui.device;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.watcher.data.device.Device;
import com.example.watcher.data.device.DeviceRepository;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DeviceDetailViewModel extends ViewModel {
    DeviceRepository deviceRepository;
    int deviceId;
  public LiveData<Device> device;
    @Inject
    public DeviceDetailViewModel(@NotNull SavedStateHandle savedStateHandle,DeviceRepository repository) {
        super();
        this.deviceRepository = repository;
        this.deviceId =DeviceDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).getMyArg();
        this.device = deviceRepository.getDeviceById(this.deviceId);
    }
}
