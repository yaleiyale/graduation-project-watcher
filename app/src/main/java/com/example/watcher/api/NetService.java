package com.example.watcher.api;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NetService {

    @POST("/getuserlist")
    @FormUrlEncoded
    Call<Boolean> login(@Field("account") String account, @Field("password") String password);

    void logout();

    static NetService create() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.240.94.83:8080")
                .build().create(NetService.class);
    }


}
