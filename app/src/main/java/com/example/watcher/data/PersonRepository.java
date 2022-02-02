package com.example.watcher.data;

import androidx.lifecycle.LiveData;

import com.example.watcher.data.model.Person;

import java.util.List;

public class PersonRepository {
    private static volatile PersonRepository instance;
    private PersonDataSource dataSource;

    private PersonRepository(PersonDataSource dataSource) {
        this.dataSource = dataSource;
    }
    public static PersonRepository getInstance(PersonDataSource dataSource) {
        if (instance == null) {
            instance = new PersonRepository(dataSource);
        }
        return instance;
    }

    LiveData<List<Person>> getPeople(){
        return dataSource.getPeople();
    }
}
