package com.example.watcher.data.person;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PersonRepository {

    PersonDao personDao;

    @Inject
    PersonRepository(PersonDao personDao) {
        this.personDao = personDao;
    }

    LiveData<List<Person>> getPeople() {
        return personDao.getPeople();
    }
}
