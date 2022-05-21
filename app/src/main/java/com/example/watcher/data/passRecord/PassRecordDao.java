package com.example.watcher.data.passRecord;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface PassRecordDao {
    @Query("SELECT * FROM records order by recordId desc")
    LiveData<List<PassRecord>> getRecords();

    @Query("SELECT * FROM records WHERE deviceId = :deviceId")
    LiveData<List<PassRecord>> getRecordByDid(int deviceId);

    @Query("SELECT * FROM records WHERE personId = :personId")
    Flowable<PassRecord> getRecordByPid(int personId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertRecord(PassRecord passRecord);

    @Update
    Completable updateRecord(PassRecord passRecord);

    @Delete
    Completable delete(PassRecord passRecord);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<PassRecord> records);
}
