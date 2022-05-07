package com.example.watcher.data.person;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.watcher.data.device.Device;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface PersonDao {
    @Query("SELECT * FROM people ORDER BY pid")
   LiveData<List<Person>>  getPeople();

    @Query("SELECT * FROM people WHERE name =:personName ")
    Flowable<Person> getPersonByName(String personName);

    @Query("SELECT * FROM people WHERE pid =:personId ")
    Flowable<Person> getPersonById(int personId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Person... person);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPerson(Person person);

    @Delete
    void delete(Person person);
}
