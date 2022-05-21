package com.example.watcher.data.passRecord;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.watcher.api.PassRecordNetService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class PassRecordRepository {

    PassRecordDao passRecordDao;
    PassRecordNetService passRecordNetService;
    PassRecordList result;
    private MyCallback mCallback;

    @Inject
    PassRecordRepository(PassRecordDao passRecordDao, PassRecordNetService passRecordNetService) {
        this.passRecordDao = passRecordDao;
        this.passRecordNetService = passRecordNetService;
    }

    public void refreshLocal() {
        Call<PassRecordList> call = passRecordNetService.getAllRecords();
        call.enqueue(new Callback<PassRecordList>() {
            @Override
            public void onResponse(@NonNull Call<PassRecordList> call, @NonNull Response<PassRecordList> response) {
                result = response.body();
                mCallback.OnSuccess();
            }

            @Override
            public void onFailure(@NonNull Call<PassRecordList> call, @NonNull Throwable t) {

            }

        });
    }

    public LiveData<List<PassRecord>> getRecords() {
        return passRecordDao.getRecords();
    }

    public LiveData<List<PassRecord>> getRecordById(int deviceId) {
        return passRecordDao.getRecordByDid(deviceId);
    }

    public Completable insert() {
        PassRecord passRecord = new PassRecord();
        passRecord.personId = 1;
        passRecord.deviceId = 1;
        return passRecordDao.insertRecord(passRecord);
    }

    public Completable insertAll() {
        return passRecordDao.insertAll(result.records);
    }

    public Completable update(PassRecord passRecord) {
        return passRecordDao.updateRecord(passRecord);
    }

    public Completable delete(PassRecord passRecord) {
        return passRecordDao.delete(passRecord);
    }

    public void setCallback(MyCallback callback) {
        this.mCallback = callback;
    }

    public interface MyCallback {
        void OnSuccess();

        void OnFail();
    }
}
