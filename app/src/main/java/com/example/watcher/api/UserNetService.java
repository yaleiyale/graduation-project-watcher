package com.example.watcher.api;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserNetService {

    static UserNetService create() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.241.125.154:8080")
                .build().create(UserNetService.class);
    }

    @POST("/getuserlist")
    @FormUrlEncoded
    Call<Boolean> login(@Field("account") String account, @Field("password") String password);

    void logout();


}
