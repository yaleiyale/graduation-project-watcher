package com.example.watcher.api;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.watcher.data.device.Device;
import com.example.watcher.data.device.DeviceList;
import com.example.watcher.data.person.PersonList;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NetService {

    @POST("/getuserlist")
    @FormUrlEncoded
    Call<Boolean> login(@Field("account") String account, @Field("password") String password);

    void logout();

    @GET("/getalldevices")
    Call<DeviceList> getAllDevices();

    @GET("/getallpeople")
    Call<PersonList> getAllPeople();

    static NetService create() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.241.125.154:8080")
                .build().create(NetService.class);
    }


}
