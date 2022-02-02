package com.example.watcher.data;

import androidx.lifecycle.LiveData;

import com.example.watcher.data.model.Person;

import java.util.List;

public class PersonDataSource {
     PersonDao personDao;
     LiveData<List<Person>> getPeople(){
        return personDao.getPeople();
    }
}
