package com.example.watcher.ui.passRecord;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.watcher.data.device.Device;
import com.example.watcher.data.device.DeviceRepository;
import com.example.watcher.data.passRecord.PassRecord;
import com.example.watcher.data.passRecord.PassRecordRepository;
import com.example.watcher.data.person.Person;
import com.example.watcher.data.person.PersonRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Completable;

@HiltViewModel
public class RecordListViewModel extends ViewModel {

    PassRecordRepository passRecordRepository;
    LiveData<List<PassRecord>> records;

    PersonRepository personRepository;


    DeviceRepository deviceRepository;


    @Inject
    public RecordListViewModel(PassRecordRepository repository,DeviceRepository deviceRepository,PersonRepository personRepository) {
        this.passRecordRepository = repository;
        records = passRecordRepository.getRecords();

        this.deviceRepository = deviceRepository;

        this.personRepository = personRepository;

    }
    public Completable insert() {
        return passRecordRepository.insert();
    }
}