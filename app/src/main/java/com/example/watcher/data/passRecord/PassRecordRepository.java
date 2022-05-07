package com.example.watcher.data.passRecord;

import androidx.lifecycle.LiveData;

import com.example.watcher.data.device.Device;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Singleton
public class PassRecordRepository {

    PassRecordDao passRecordDao;

    @Inject
    PassRecordRepository(PassRecordDao passRecordDao) {
        this.passRecordDao = passRecordDao;
    }

    public LiveData<List<PassRecord>> getRecords() {
        return passRecordDao.getRecords();
    }

    public Flowable<PassRecord> getDeviceByDId(int deviceId) {
        return passRecordDao.getRecordByDid(deviceId);
    }

    public Completable insert() {
        PassRecord passRecord = new PassRecord();
        passRecord.pid = 1;
        passRecord.did = 1;
        return passRecordDao.insertRecord(passRecord);
    }



    public Completable update(PassRecord passRecord) {
        return passRecordDao.updateRecord(passRecord);
    }

    public Completable delete(PassRecord passRecord) {
        return passRecordDao.delete(passRecord);
    }
}
