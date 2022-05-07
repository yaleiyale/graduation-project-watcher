package com.example.watcher.ui.person;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.watcher.data.person.Person;
import com.example.watcher.data.person.PersonRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Completable;

@HiltViewModel
public class PersonViewModel extends ViewModel {

   PersonRepository personRepository;
    LiveData<List<Person>> people;

    @Inject
    public PersonViewModel(PersonRepository repository) {
        this.personRepository = repository;
       people = personRepository.getPeople();
    }

    public Completable insert(String name) {
        return personRepository.insert(name);
    }

}