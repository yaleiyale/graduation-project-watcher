package com.example.watcher.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.watcher.data.model.Person;

import java.util.List;
@Dao
public interface PersonDao {
    @Query("SELECT * FROM people ORDER BY id")
    LiveData<List<Person>> getPeople();

    @Query("SELECT * FROM people WHERE name =:personName ")
    LiveData<Person> getPerson(String personName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Person> people);
}
