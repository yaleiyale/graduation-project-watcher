package com.example.watcher.ui.device;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.watcher.data.device.DeviceRepository;
import com.example.watcher.data.passRecord.PassRecord;
import com.example.watcher.data.passRecord.PassRecordRepository;
import com.example.watcher.data.person.PersonRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DeviceRecordViewModel extends ViewModel {
    PassRecordRepository passRecordRepository;
    LiveData<List<PassRecord>> records;
    int deviceId;
    PersonRepository personRepository;

    DeviceRepository deviceRepository;



    @Inject
    public DeviceRecordViewModel(@NotNull SavedStateHandle savedStateHandle, PassRecordRepository repository, DeviceRepository deviceRepository, PersonRepository personRepository) {
        super();
        this.passRecordRepository = repository;
        this.deviceRepository = deviceRepository;
        this.personRepository = personRepository;
        this.deviceId = DeviceRecordFragmentArgs.fromSavedStateHandle(savedStateHandle).getMyArg();
        records = passRecordRepository.getRecordById(deviceId);
    }


}
