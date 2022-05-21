package com.example.watcher.ui.passRecord;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.watcher.data.device.DeviceRepository;
import com.example.watcher.data.passRecord.PassRecord;
import com.example.watcher.data.passRecord.PassRecordRepository;
import com.example.watcher.data.person.PersonRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class RecordListViewModel extends ViewModel {

    PassRecordRepository passRecordRepository;
    LiveData<List<PassRecord>> records;

    PersonRepository personRepository;

    DeviceRepository deviceRepository;
    CompositeDisposable mDisposable = new CompositeDisposable();


    @Inject
    public RecordListViewModel(PassRecordRepository repository, DeviceRepository deviceRepository, PersonRepository personRepository) {
        this.passRecordRepository = repository;
        this.deviceRepository = deviceRepository;
        this.personRepository = personRepository;
        initData();
        records = passRecordRepository.getRecords();


    }

    public Completable insert() {
        return passRecordRepository.insert();
    }

    void initData() {
        passRecordRepository.setCallback(new PassRecordRepository.MyCallback() {
            @Override
            public void OnSuccess() {
                mDisposable.add(passRecordRepository.insertAll()
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> {
                        }, throwable -> Log.e("no detail", "Unable to get detail", throwable)));
            }

            @Override
            public void OnFail() {

            }
        });
        deviceRepository.setCallback(new DeviceRepository.MyCallback() {
            @Override
            public void OnSuccess() {
                mDisposable.add(deviceRepository.insertAll()
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> {
                        }, throwable -> Log.e("no detail", "Unable to get detail", throwable)));
            }

            @Override
            public void OnFail() {

            }
        });
        personRepository.setCallback(new PersonRepository.MyCallback() {
            @Override
            public void OnSuccess() {
                mDisposable.add(personRepository.insertAll()
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> {
                        }, throwable -> Log.e("no detail", "Unable to get detail", throwable)));
            }

            @Override
            public void OnFail() {

            }
        });
        personRepository.refreshLocal();
        deviceRepository.refreshLocal();
        passRecordRepository.refreshLocal();
    }
}