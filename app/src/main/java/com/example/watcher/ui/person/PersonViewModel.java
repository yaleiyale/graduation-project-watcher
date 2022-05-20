package com.example.watcher.ui.person;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.watcher.data.device.DeviceRepository;
import com.example.watcher.data.person.Person;
import com.example.watcher.data.person.PersonRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class PersonViewModel extends ViewModel {

    PersonRepository personRepository;
    LiveData<List<Person>> people;
    CompositeDisposable mDisposable = new CompositeDisposable();

    @Inject
    public PersonViewModel(PersonRepository repository) {
        this.personRepository = repository;
        initData();
        people = personRepository.getPeople();
    }

    public Completable insert(String name) {
        return personRepository.insert(name);
    }

    public void initData() {
        personRepository.setCallback(new PersonRepository.MyCallback() {
            @Override
            public void OnSuccess() {
                mDisposable.add(personRepository.insertAll()
                        .subscribeOn(Schedulers.io())
                        .subscribe(() -> {
                        }, throwable -> Log.e("no detail", "Unable to get detail", throwable)));
            }

            @Override
            public void OnFail() {

            }
        });
        personRepository.refreshLocal();
    }
}