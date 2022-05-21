package com.example.watcher.api;

import com.example.watcher.data.person.PersonList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface PersonNetService {
    static PersonNetService create() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.241.125.154:8080")
                .build().create(PersonNetService.class);
    }

    @GET("/getallpeople")
    Call<PersonList> getAllPeople();
}
