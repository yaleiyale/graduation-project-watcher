package com.example.watcher.data.person;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.watcher.api.PersonNetService;
import com.google.gson.Gson;

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

    public PersonNetService personNetService;
    public PersonList result;
    PersonDao personDao;
    private MyCallback mCallback;

    @Inject
    PersonRepository(PersonDao personDao, PersonNetService personNetService) {
        this.personDao = personDao;
        this.personNetService = personNetService;
    }

    public void refreshLocal() {
        Call<PersonList> call = personNetService.getAllPeople();
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

    public void setCallback(MyCallback callback) {
        this.mCallback = callback;
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

    public Completable update(Person person) {
        return personDao.updatePerson(person);
    }

    public void updatePersonNet(Person person) {
        Gson gson = new Gson();
        String json_person = gson.toJson(person);
        Call<Boolean> call = personNetService.updatePerson(json_person);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {

            }
        });
    }

    public Completable delete(Person person) {
        return personDao.delete(person);
    }

    public void deletePersonNet(Person person) {
        Gson gson = new Gson();
        String json_person = gson.toJson(person);
        Call<Boolean> call = personNetService.deletePerson(json_person);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {

            }
        });
    }

    public Completable deleteAll() {
        return personDao.deletePeople();
    }

    public interface MyCallback {
        void OnSuccess();

        void OnFail();
    }
}
