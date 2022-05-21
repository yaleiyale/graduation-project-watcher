package com.example.watcher.data.person;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface PersonDao {
    @Query("SELECT * FROM people ORDER BY personId desc")
    LiveData<List<Person>> getPeople();

    @Query("SELECT * FROM people WHERE personName =:personName ")
    Flowable<Person> getPersonByName(String personName);

    @Query("SELECT * FROM people WHERE personId =:personId ")
    Flowable<Person> getPersonById(int personId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<Person> persons);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPerson(Person person);

    @Delete
    void delete(Person person);
}
