package com.example.watcher.ui.passRecord;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.watcher.data.device.DeviceList;
import com.example.watcher.data.device.DeviceRepository;
import com.example.watcher.data.passRecord.PassRecord;
import com.example.watcher.data.passRecord.PassRecordRepository;
import com.example.watcher.data.person.PersonList;
import com.example.watcher.data.person.PersonRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    void showPass() {
        records = passRecordRepository.showPass();
    }

    void showBan() {
        records = passRecordRepository.showBan();
    }

    void showAll() {
        records = passRecordRepository.getRecords();
    }

    void initData() {

        passRecordRepository.setCallback(new PassRecordRepository.MyCallback() {
            @Override
            public void OnSuccess() {
                mDisposable.add(passRecordRepository.insertAll()
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> records = passRecordRepository.getRecords(), throwable -> Log.e("no detail", "Unable to get detail", throwable)));
            }

            @Override
            public void OnFail() {

            }
        });
//        deviceRepository.setCallback(new DeviceRepository.MyCallback() {
//            @Override
//            public void OnSuccess() {
//                mDisposable.add(deviceRepository.insertAll()
//                        .subscribeOn(Schedulers.io())
//                        .subscribe(() -> {
//                        }, throwable -> Log.e("no detail", "Unable to get detail", throwable)));
//            }
//
//            @Override
//            public void OnFail() {
//
//            }
//        });
//        personRepository.setCallback(new PersonRepository.MyCallback() {
//            @Override
//            public void OnSuccess() {
//                mDisposable.add(personRepository.insertAll()
//                        .subscribeOn(Schedulers.io())
//                        .subscribe(() -> {
//                        }, throwable -> Log.e("no detail", "Unable to get detail", throwable)));
//            }
//
//            @Override
//            public void OnFail() {
//
//            }
//        });
    }

    public void refresh() {
//        mDisposable.add(passRecordRepository.deleteAll()
//                .subscribeOn(Schedulers.io())
//                .subscribe(() -> {
//                }, throwable -> Log.e("no detail", "Unable to get detail", throwable)));
        Call<PersonList> personcall = personRepository.personNetService.getAllPeople();
        personcall.enqueue(new Callback<PersonList>() {
            @Override
            public void onResponse(@NonNull Call<PersonList> call, @NonNull Response<PersonList> response) {
                personRepository.result = response.body();
                mDisposable.add(personRepository.insertAll()
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> {
                        }, throwable -> Log.e("no detail", "Unable to get detail", throwable)));
            }

            @Override
            public void onFailure(@NonNull Call<PersonList> call, @NonNull Throwable t) {

            }
        });

        Call<DeviceList> devicecall = deviceRepository.deviceNetService.getAllDevices();
        devicecall.enqueue(new Callback<DeviceList>() {
            @Override
            public void onResponse(@androidx.annotation.NonNull Call<DeviceList> call, @androidx.annotation.NonNull Response<DeviceList> response) {
                deviceRepository.result = response.body();
                mDisposable.add(deviceRepository.insertAll()
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> {
                        }, throwable -> Log.e("no detail", "Unable to get detail", throwable)));
            }

            @Override
            public void onFailure(@androidx.annotation.NonNull Call<DeviceList> call, @androidx.annotation.NonNull Throwable t) {

            }
        });

        passRecordRepository.refreshLocal();
    }
}