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

@Dao
public interface PassRecordDao {
    @Query("SELECT * FROM records order by recordId desc")
    LiveData<List<PassRecord>> getRecords();

    @Query("SELECT * FROM records WHERE deviceId = :deviceId")
    LiveData<List<PassRecord>> getRecordByDid(int deviceId);

    @Query("SELECT * FROM records WHERE personId = :personId")
    LiveData<List<PassRecord>> getRecordByPid(int personId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertRecord(PassRecord passRecord);

    @Update
    Completable updateRecord(PassRecord passRecord);

    @Delete
    Completable delete(PassRecord passRecord);

    @Query("DELETE  FROM records WHERE recordId>0")
    Completable deleteRecords();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<PassRecord> records);


    @Query("DELETE  FROM records WHERE personId =:personId")
    Completable deleteByPid(int personId);

    @Query("DELETE  FROM records WHERE deviceId =:deviceId")
    Completable deleteByDid(int deviceId);

    @Query("SELECT * FROM records WHERE result = 1 order by recordId desc")
    LiveData<List<PassRecord>> showPass();

    @Query("SELECT * FROM records WHERE result = 0 order by recordId desc")
    LiveData<List<PassRecord>> showBan();
}
