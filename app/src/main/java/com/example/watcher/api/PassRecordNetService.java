package com.example.watcher.api;

import static com.example.watcher.adapters.BindingAdapters.SERVER_IP;

import com.example.watcher.data.passRecord.PassRecordList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface PassRecordNetService {
    static PassRecordNetService create() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://" + SERVER_IP + ":8080")
                .build().create(PassRecordNetService.class);
    }

    @GET("/getallrecords")
    Call<PassRecordList> getAllRecords();
}
