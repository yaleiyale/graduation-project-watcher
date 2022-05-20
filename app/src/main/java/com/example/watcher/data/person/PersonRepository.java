package com.example.watcher.data.person;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.watcher.api.NetService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class PersonRepository {

    PersonDao personDao;
    NetService netService;
    PersonList result;

    public void refreshLocal() {
        Call<PersonList> call = netService.getAllPeople();
        call.enqueue(new Callback<PersonList>() {
            @Override
            public void onResponse(@NonNull Call<PersonList> call, @NonNull Response<PersonList> response) {
                result = response.body();
                mCallback.OnSuccess();
            }

            @Override
            public void onFailure(@NonNull Call<PersonList> call, @NonNull Throwable t) {

            }
        });
    }

    public interface MyCallback {
        void OnSuccess();

        void OnFail();
    }

    private MyCallback mCallback;

    public void setCallback(MyCallback callback) {
        this.mCallback = callback;
    }

    @Inject
    PersonRepository(PersonDao personDao, NetService netService) {
        this.personDao = personDao;
        this.netService = netService;
    }

    public Completable insert(String name) {
        Person person = new Person();
        person.personName = name;
        person.imageUrl = "http://10.240.94.83:8080/2.jpg";
        return personDao.insertPerson(person);
    }

    public Flowable<Person> getPersonById(int personId) {
        return personDao.getPersonById(personId);
    }

    public LiveData<List<Person>> getPeople() {
        return personDao.getPeople();
    }

    public Completable insertAll() {
        return personDao.insertAll(result.people);
    }
}
