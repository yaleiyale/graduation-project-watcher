package com.example.watcher.data.login;

import com.example.watcher.api.NetService;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@Singleton
public class LoginRepository {

    private final NetService netService;

    private LoggedInUser user = null;

    private MyCallback mCallback;

    public void setCallback(MyCallback callback) {
        this.mCallback = callback;
    }

    public interface MyCallback {
        void OnLogin();

        void OnFail();
    }

    @Inject
    LoginRepository(NetService netService) {
        this.netService = netService;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        netService.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
    }


    public void login(String account, String password) {
        Call<Boolean> call = netService.login(account, password);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.body()) {
                    mCallback.OnLogin();
                } else {
                    mCallback.OnFail();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable throwable) {

            }
        });
    }
}