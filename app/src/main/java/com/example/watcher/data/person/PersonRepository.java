package com.example.watcher.data.person;

import androidx.lifecycle.LiveData;

import com.example.watcher.data.device.Device;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Singleton
public class PersonRepository {

    PersonDao personDao;

    @Inject
    PersonRepository(PersonDao personDao) {
        this.personDao = personDao;
    }

    public Completable insert(String name) {
        Person person = new Person();
        person.name = name;
        person.imgUrl = "https://s2.loli.net/2022/04/27/8pzSHNObnC1EJxF.jpg";
        return personDao.insertPerson(person);
    }

    public Flowable<Person> getPersonById(int personId) {
        return personDao.getPersonById(personId);
    }
    public LiveData<List<Person>> getPeople() {
        return personDao.getPeople();
    }
}
