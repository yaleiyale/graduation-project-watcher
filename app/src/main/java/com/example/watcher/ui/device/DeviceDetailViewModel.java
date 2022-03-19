package com.example.watcher.ui.device;

import androidx.lifecycle.ViewModel;

import com.example.watcher.data.device.Device;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DeviceDetailViewModel extends ViewModel {
    Device device;
}
