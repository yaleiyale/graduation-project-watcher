package com.example.watcher.api;

import com.example.watcher.data.passRecord.PassRecordList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface PassRecordNetService {
    static PassRecordNetService create() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.241.125.154:8080")
                .build().create(PassRecordNetService.class);
    }

    @GET("/getallrecords")
    Call<PassRecordList> getAllRecords();
}
