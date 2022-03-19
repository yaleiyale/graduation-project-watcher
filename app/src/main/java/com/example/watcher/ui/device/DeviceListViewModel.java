package com.example.watcher.ui.device;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.watcher.data.device.Device;
import com.example.watcher.data.device.DeviceRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DeviceListViewModel extends ViewModel {
  public   DeviceRepository deviceRepository;
    LiveData<List<Device>> devices;

    @Inject
    public DeviceListViewModel(DeviceRepository repository) {
        this.deviceRepository = repository;
        devices = deviceRepository.getDevices();
    }


}
