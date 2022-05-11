package com.example.watcher.ui.login;

import androidx.lifecycle.ViewModel;

import com.example.watcher.data.login.LoginRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    private final LoginRepository loginRepository;
    private MyCallback mCallback;

    public void setCallback(MyCallback callback) {
        this.mCallback = callback;
    }

    public interface MyCallback {
        void OnLogin();

        void OnFail();
    }

    @Inject
    public LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;

    }

    public void login(String account, String password) {
        loginRepository.setCallback(new LoginRepository.MyCallback() {
            @Override
            public void OnLogin() {
                mCallback.OnLogin();
            }

            @Override
            public void OnFail() {
                mCallback.OnFail();
            }
        });
        loginRepository.login(account,password);

    }

}
