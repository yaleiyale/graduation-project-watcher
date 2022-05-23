package com.example.watcher.ui.person;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.watcher.data.device.DeviceRepository;
import com.example.watcher.data.passRecord.PassRecord;
import com.example.watcher.data.passRecord.PassRecordRepository;
import com.example.watcher.data.person.PersonRepository;
import com.example.watcher.ui.device.DeviceRecordFragmentArgs;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PersonRecordViewModel extends ViewModel {
    PassRecordRepository passRecordRepository;
    LiveData<List<PassRecord>> records;
    int personId;
    PersonRepository personRepository;

    DeviceRepository deviceRepository;

    @Inject
    public PersonRecordViewModel(@NotNull SavedStateHandle savedStateHandle, PassRecordRepository repository, DeviceRepository deviceRepository, PersonRepository personRepository) {
        super();
        this.passRecordRepository = repository;
        this.deviceRepository = deviceRepository;
        this.personRepository = personRepository;
        this.personId = DeviceRecordFragmentArgs.fromSavedStateHandle(savedStateHandle).getMyArg();
        records = passRecordRepository.getRecordByPid(personId);
    }
}
