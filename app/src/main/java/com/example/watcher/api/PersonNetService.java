package com.example.watcher.api;

import static com.example.watcher.adapters.BindingAdapters.SERVER_IP;

import com.example.watcher.data.person.PersonList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PersonNetService {
    static PersonNetService create() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://" + SERVER_IP + ":8080")
                .build().create(PersonNetService.class);
    }

    @GET("/getallpeople")
    Call<PersonList> getAllPeople();

    @POST("/updateperson")
    @FormUrlEncoded
    Call<Boolean> updatePerson(@Field("json_person") String json_person);

    @POST("/deleteperson")
    @FormUrlEncoded
    Call<Boolean> deletePerson(@Field("json_person") String json_person);
}
